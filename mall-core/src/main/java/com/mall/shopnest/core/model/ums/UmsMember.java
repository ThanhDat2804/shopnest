package com.mall.shopnest.core.model.ums;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "Member Level ID")
    private Long memberLevelId;

    @Schema(title = "Username")
    private String username;

    @Schema(title = "Password")
    private String password;

    @Schema(title = "Nickname")
    private String nickname;

    @Schema(title = "Phone Number")
    private String phone;

    @Schema(title = "Account Status: 0->Disabled; 1->Enabled")
    private Integer status;

    @Schema(title = "Registration Time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Schema(title = "Avatar")
    private String icon;

    @Schema(title = "Gender: 0->Unknown; 1->Male; 2->Female")
    private Integer gender;

    @Schema(title = "Birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Schema(title = "City of Residence")
    private String city;

    @Schema(title = "Occupation")
    private String job;

    @Schema(title = "Personal Signature")
    private String personalizedSignature;

    @Schema(title = "Source of User")
    private Integer sourceType;

    @Schema(title = "Points")
    private Integer integration;

    @Schema(title = "Growth Value")
    private Integer growth;

    @Schema(title = "Remaining Lottery Count")
    private Integer luckeyCount;

    @Schema(title = "Total Historical Points")
    private Integer historyIntegration;

}
