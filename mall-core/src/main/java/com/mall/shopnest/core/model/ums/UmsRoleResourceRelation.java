package com.mall.shopnest.core.model.ums;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "ums_role_resource_relation") // match this with your actual table name
public class UmsRoleResourceRelation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_id")
    @Schema(title = "Role ID")
    private Long roleId;

    @Column(name = "resource_id")
    @Schema(title = "Resource ID")
    private Long resourceId;

    private static final long serialVersionUID = 1L;
}
