package com.mall.shopnest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@Entity
@Table(name = "ums_member_level")
public class UmsMemberLevel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer growthPoint;

    @Schema(title = "Is default level: 0->No; 1->Yes")
    private Integer defaultStatus;

    @Schema(title = "Free shipping threshold")
    private BigDecimal freeFreightPoint;

    @Schema(title = "Growth points per review")
    private Integer commentGrowthPoint;

    @Schema(title = "Free shipping privilege")
    private Integer priviledgeFreeFreight;

    @Schema(title = "Sign-in privilege")
    private Integer priviledgeSignIn;

    @Schema(title = "Review reward privilege")
    private Integer priviledgeComment;

    @Schema(title = "Exclusive promotion privilege")
    private Integer priviledgePromotion;

    @Schema(title = "Member price privilege")
    private Integer priviledgeMemberPrice;

    @Schema(title = "Birthday privilege")
    private Integer priviledgeBirthday;

    private String note;
}

