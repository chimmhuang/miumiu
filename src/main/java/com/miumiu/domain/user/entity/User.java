package com.miumiu.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author chimmhuang
 * @date 2019/1/9 0009 16:55
 * @description 用户实体类
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "_id")
    private String id;

    @Column(name = "wx_openid")
    private String wxOpenid;

    @Column(name = "session_key")
    private String sessionKey;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @Column(name = "head")
    private String head;

    @Column(name = "name")
    private String name;

    @Column(name = "sex")
    private int sex = 3;

    @Column(name = "tel")
    private String tel;

    @Column(name = "third_name")
    private String thirdName;
}
