package com.mall.shopnest.core.model.ums;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "ums_role")
public class UmsRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "Name")
    private String name;

    @Schema(title = "Description")
    private String description;

    @Column(name = "admin_count")
    @Schema(title = "Admin Count")
    private Integer adminCount;

    @Column(name = "create_time")
    @Schema(title = "Create Time")
    private Date createTime;

    @Schema(title = "Status: 0->Disabled; 1->Enabled")
    private Integer status;

    private Integer sort;

    private static final long serialVersionUID = 1L;
}
