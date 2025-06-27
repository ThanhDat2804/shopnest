package com.mall.shopnest.core.model.ums;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "ums_admin_login_log")
public class UmsAdminLoginLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admin_id")
    private Long adminId;

    @Column(name = "create_time")
    private Date createTime;

    private String ip;

    private String address;

    @Schema(title = "Browser User-Agent")
    @Column(name = "user_agent")
    private String userAgent;

    private static final long serialVersionUID = 1L;
}

