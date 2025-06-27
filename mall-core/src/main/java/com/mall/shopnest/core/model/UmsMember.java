package com.mall.shopnest.core.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "ums_member")
public class UmsMember implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberLevelId;

    private String username;

    private String password;

    private String nickname;

    private String phone;

    private Integer status;

    private Date createTime;

    private String icon;

    private Integer gender;

    private Date birthday;

    private String city;

    private String job;

    private String personalizedSignature;

    private Integer sourceType;

    private Integer integration;

    private Integer growth;

    private Integer luckeyCount;

    private Integer historyIntegration;
}
