package com.mall.shopnest.core.model.ums;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "ums_menu")
public class UmsMenu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_id")
    @Schema(title = "Parent ID")
    private Long parentId;

    @Column(name = "create_time")
    @Schema(title = "Creation Time")
    private Date createTime;

    @Schema(title = "Menu Title")
    private String title;

    @Schema(title = "Menu Level")
    private Integer level;

    @Schema(title = "Menu Sort Order")
    private Integer sort;

    @Schema(title = "Frontend Name")
    private String name;

    @Schema(title = "Frontend Icon")
    private String icon;

    @Schema(title = "Hidden in Frontend (0 = no, 1 = yes)")
    private Integer hidden;

    private static final long serialVersionUID = 1L;
}
