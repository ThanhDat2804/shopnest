package com.mall.shopnest.core.model.ums;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ums_member_level")
public class UmsMemberLevel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(title = "Level name")
    private String name;

    @Schema(title = "Growth points required")
    private Integer growthPoint;

    @Schema(title = "Is default level: 0->No; 1->Yes")
    private Integer defaultStatus;

    @Schema(title = "Free shipping threshold")
    private BigDecimal freeFreightPoint;

    @Schema(title = "Growth points earned per review")
    private Integer commentGrowthPoint;

    @Schema(title = "Free shipping privilege")
    private Integer privilegeFreeFreight;

    @Schema(title = "Sign-in privilege")
    private Integer privilegeSignIn;

    @Schema(title = "Review reward privilege")
    private Integer privilegeComment;

    @Schema(title = "Exclusive promotion privilege")
    private Integer privilegePromotion;

    @Schema(title = "Member price privilege")
    private Integer privilegeMemberPrice;

    @Schema(title = "Birthday privilege")
    private Integer privilegeBirthday;

    @Schema(title = "Remarks")
    private String note;

    private static final long serialVersionUID = 1L;
}
