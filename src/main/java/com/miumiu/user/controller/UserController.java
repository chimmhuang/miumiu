package com.miumiu.user.controller;

import com.alibaba.fastjson.JSON;
import com.miumiu.base.domain.PageResult;
import com.miumiu.base.domain.request.PageQueryRequest;
import com.miumiu.base.domain.response.ResponseResult;
import com.miumiu.base.utils.StringUtil;
import com.miumiu.domain.user.entity.User;
import com.miumiu.user.dto.LoginCertificateDTO;
import com.miumiu.user.service.UserService;
import com.miumiu.user.vo.AutoLoginVO;
import com.miumiu.user.vo.DecryptVO;
import com.miumiu.user.vo.LoginCodeVO;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chimmhuang
 * @date 2019/1/8 0008 12:35
 * @description 用户模块controller
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户模块相关接口")
public class UserController {

    @Value("${wx-mini-Program.AppID}")
    private String APPID;
    @Value("${wx-mini-Program.AppSecret}")
    private String APPSECRET;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;

    private static Logger logger = Logger.getLogger(UserController.class);

    /**
     * 微信小程序登录
     * 获取微信小程序 session_key 和 openid
     * @param loginCodeVO 临时TOKEN {"code":"ASHASJNFA"}
     * @return
     */
    @PostMapping("/wxmini/login")
    @ApiOperation(value = "微信小程序-登陆获取",
            notes = "如果code过期或无效，微信会返回40029错误; " +
                    "微信验证通过，但是miumiu数据库没有用户数据，会返回21000提示信息; " +
                    "微信验证通过，并且miumiu存有用户信息，则返回状态码0，并返回TOKEN数据")
    @ApiResponses({
            @ApiResponse(code = -1,message = "系统繁忙，此时请开发者稍候再试"),
            @ApiResponse(code = 0,message = "请求成功"),
            @ApiResponse(code = 21000,message = "登录成功,但数据库内没有用户信息"),
            @ApiResponse(code = 40029,message = "code 无效"),
            @ApiResponse(code = 45011,message = "频率限制，每个用户每分钟100次")
    })
    public Map<String,Object> login(@RequestBody @ApiParam(name = "loginCodeVO",value = "传入临时TOKEN",required = true) LoginCodeVO loginCodeVO) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={APPID}&secret={APPSECRET}&js_code={code}&grant_type=authorization_code";

        Map<String, String> params = new HashMap<>(3);
        params.put("APPSECRET", APPSECRET);
        params.put("code", loginCodeVO.getCode());
        params.put("APPID", APPID);

        String body = restTemplate.getForObject(url, String.class, params);
        LoginCertificateDTO certificateDTO = JSON.parseObject(body, LoginCertificateDTO.class);

        Map<String, Object> result = new HashMap<>();
        if (certificateDTO.getErrcode() != 0) {
            result.put("code", certificateDTO.getErrcode());
            result.put("message", certificateDTO.getErrmsg());
            return result;
        }

        // 判断用户是否第一次登录
        User user = userService.getUserInfoByWx(certificateDTO.getOpenid());
        if (user == null) {
            result.put("code", 21000);
            result.put("message", "数据库内没有用户信息");
            result.put("sessionKey", certificateDTO.getSession_key());
            return result;
        }

        // 更新数据库的sessionKey
        userService.updateSessionKey(certificateDTO.getOpenid(),certificateDTO.getSession_key());
        // 将用户id作为TOKEN返回
        result.put("code", certificateDTO.getErrcode());
        result.put("MIUMIUTOKEN", user.getId());
        result.put("sessionKey", certificateDTO.getSession_key());
        return result;
    }

    /**
     * 解密用户敏感数据存储用户信息
     * @param decryptVO 加密数据
     * @return
     */
    @ApiOperation(value = "微信小程序-解密用户敏感数据存储用户信息",notes = "保存成功后，会返回用户的登录TOKEN")
    @PostMapping("/wxmini/saveUserInfo")
    @ApiResponses({
            @ApiResponse(code = 10000,message = "保存成功"),
            @ApiResponse(code = 11111,message = "保存失败")
    })
    public Object saveUserInfo(@RequestBody @ApiParam(name = "decryptVO",value = "加密数据类",required = true) DecryptVO decryptVO) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(decryptVO.getEncryptedData());
        // 加密秘钥
        byte[] keyByte = Base64.decode(decryptVO.getSessionKey());
        // 偏移量
        byte[] ivByte = Base64.decode(decryptVO.getIv());
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                /**
                 * @param avatarUrl:头像地址
                 * @param city:Chengdu
                 * @param gender:1
                 * @param language:zh_CN
                 * @param nickName:吃茫茫
                 * @param openId:...
                 * @param province:Sichuan
                 * @param watermark:{appid:...,timestamp:1547003580}
                 * @param _proto_:Object
                 */
                Map<String,Object> resultMap = JSON.parseObject(result, Map.class);
                if (userService.getUserInfoByWx((String) resultMap.get("openId")) != null) {
                    return JSON.parse("{\"code\":11111,\"message\":\"数据库内已经有该用户数据了\"}");
                }
                //将解密后的内容提取出来存放到user对象中
                User user = new User(
                        StringUtil.uuid(),
                        (String) resultMap.get("openId"),
                        decryptVO.getSessionKey(),
                        new Date(),
                        (String)resultMap.get("avatarUrl"),
                        (String)resultMap.get("nickName"),
                        (int)resultMap.get("gender"),
                        null,(String)resultMap.get("nickName"));
                //将User对象写入数据库
                User userInfo = userService.saveUserInfo(user);
                return JSON.parse("{\"code\":10000,\"MIUMIUTOKEN\":\""+userInfo.getId()+"\"}");
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        } catch (NoSuchPaddingException e) {
            logger.error(e.getMessage(), e);
        } catch (InvalidParameterSpecException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalBlockSizeException e) {
            logger.error(e.getMessage(), e);
        } catch (BadPaddingException e) {
            logger.error(e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        } catch (InvalidKeyException e) {
            logger.error(e.getMessage(), e);
        } catch (InvalidAlgorithmParameterException e) {
            logger.error(e.getMessage(), e);
        } catch (NoSuchProviderException e) {
            logger.error(e.getMessage(), e);
        }
        return JSON.parse("{\"code\":11111,\"message\":\"保存失败！\"}");
    }

    /**
     * 查询所有用户信息
     * @return
     */
    @ApiOperation(value = "查询所有用户信息",notes = "只需要传入page和size即可，若不传入，默认page=1，size=10")
    @GetMapping("/findAll")
    public PageResult<User> findAll(@RequestBody @ApiParam(name = "pageQueryRequest",value = "分页查询") PageQueryRequest pageQueryRequest) {

        int page = 1;
        int size = 10;

        if (pageQueryRequest != null) {
            if (pageQueryRequest.getPage() != 0) {
                page = pageQueryRequest.getPage();
            }
            if (pageQueryRequest.getSize() != 0) {
                page = pageQueryRequest.getSize();
            }
        }

        PageResult<User> result = userService.findAll(page, size);
        return result;
    }

    /**
     * 自动登录
     * @param autoLoginVO MIUMIUTOKEN
     * @return
     */
    @PostMapping("/autoLogin")
    @ApiOperation(value = "自动登录")
    @ApiResponses({
            @ApiResponse(code = 10000,message = "登录成功"),
            @ApiResponse(code = 21005, message = "登录失败")
    })
    public ResponseResult autoLogin(@RequestBody @ApiParam(name = "loginCodeVO",value = "用户TOKEN") AutoLoginVO autoLoginVO) {
        return userService.autoLogin(autoLoginVO.getMIUMIUTOKEN());
    }

}
