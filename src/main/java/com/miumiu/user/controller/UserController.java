package com.miumiu.user.controller;

import com.alibaba.fastjson.JSON;
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

    private static Logger logger = Logger.getLogger(UserController.class);

    /**
     * 微信小程序登录
     * 获取微信小程序 session_key 和 openid
     * @param loginCodeVO 临时TOKEN {"code":"ASHASJNFA"}
     * @return
     */
    @PostMapping("/wxmini/login")
    @ApiOperation(value = "微信小程序登录测试",notes = "测试阶段，该接口还未完成")
    @ApiResponses({
            @ApiResponse(code = -1,message = "系统繁忙，此时请开发者稍候再试"),
            @ApiResponse(code = 0,message = "请求成功"),
            @ApiResponse(code = 40029,message = "code 无效"),
            @ApiResponse(code = 45011,message = "45011")
    })
    public String login(@RequestBody @ApiParam(name = "loginCodeVO",value = "传入临时TOKEN",required = true) LoginCodeVO loginCodeVO) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={APPID}&secret={APPSECRET}&js_code={code}&grant_type=authorization_code";

        Map<String, String> params = new HashMap<>(3);
        params.put("APPID", APPID);
        params.put("APPSECRET", APPSECRET);
        params.put("code", loginCodeVO.getCode());

        String body = restTemplate.getForObject(url, String.class, params);
        return JSON.toJSONString(JSON.parse(body));
    }

    /**
     * 解密用户敏感数据获取用户信息
     * @param decryptVO 加密数据
     * @return
     */
    @ApiOperation(value = "微信小程序-解密用户敏感数据获取用户信息")
    @PostMapping("/wxmini/getUserInfo")
    public String getUserInfo(@RequestBody @ApiParam(name = "decryptVO",value = "加密数据类",required = true) DecryptVO decryptVO) {
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
                return JSON.toJSONString(JSON.parse(result));
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
        return null;
    }

}
