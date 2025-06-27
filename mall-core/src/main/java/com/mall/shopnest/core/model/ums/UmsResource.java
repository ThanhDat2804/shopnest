package com.mall.shopnest.core.model.ums;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "ums_resource")
public class UmsResource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_time")
    @Schema(title = "Creation Time")
    private Date createTime;

    @Schema(title = "Resource Name")
    private String name;

    @Schema(title = "Resource URL")
    private String url;

    @Schema(title = "Description")
    private String description;

    @Column(name = "category_id")
    @Schema(title = "Resource Category ID")
    private Long categoryId;

    private static final long serialVersionUID = 1L;
}
