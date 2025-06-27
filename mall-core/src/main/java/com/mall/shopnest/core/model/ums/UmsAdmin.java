package com.mall.shopnest.core.model.ums;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "ums_admin")
public class UmsAdmin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Schema(title = "Avatar")
    private String icon;

    @Schema(title = "Email")
    private String email;

    @Column(name = "nick_name")
    @Schema(title = "Nickname")
    private String nickName;

    @Schema(title = "Note")
    private String note;

    @Column(name = "create_time")
    @Schema(title = "Created Time")
    private Date createTime;

    @Column(name = "login_time")
    @Schema(title = "Last Login Time")
    private Date loginTime;

    @Schema(title = "Account status: 0=disabled, 1=enabled")
    private Integer status;

}


