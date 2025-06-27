package com.mall.shopnest.core.model.ums;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "ums_role_menu_relation")
public class UmsRoleMenuRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "Role ID")
    @Column(name = "role_id")
    private Long roleId;

    @Schema(title = "Menu ID")
    @Column(name = "menu_id")
    private Long menuId;
}
