package com.frozen.member.bean;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <program> shopparent </program>
 * <description> 用户实体类 </description>
 *
 * @author : lw
 * @date : 2020-03-26 20:00
 **/
@Data
@Table(name = "mb_user")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 创建时间
     */
    private Date created;
    /**
     * 更新时间
     */
    private Date updated;
    /**
     * 乐观锁版本号
     */
    private Integer version;
    /**
     * MD5盐
     */
    private String salt;
    /**
     * gitUserId
     */
    @Column(name = "github_user_id")
    private String githubUserId;
}

