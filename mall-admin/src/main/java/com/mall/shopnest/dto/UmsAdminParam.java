package com.mall.shopnest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UmsAdminParam {
    @NotEmpty
    @Schema(title = "username", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @NotEmpty
    @Schema(title = "password", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
    @Schema(title =  "icon")
    private String icon;
    @Email
    @Schema(title = "email")
    private String email;
    @Schema(title =  "nickname")
    private String nickName;
    @Schema(title =  "note")
    private String note;
}