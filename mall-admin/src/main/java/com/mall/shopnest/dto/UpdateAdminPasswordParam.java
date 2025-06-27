package com.mall.shopnest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAdminPasswordParam {
    @NotEmpty
    @Schema(title =  "Username", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;
    @NotEmpty
    @Schema(title =  "Old password", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldPassword;
    @NotEmpty
    @Schema(title =  "New password", requiredMode = Schema.RequiredMode.REQUIRED)
    private String newPassword;
}
