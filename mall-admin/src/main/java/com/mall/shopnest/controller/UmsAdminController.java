package com.mall.shopnest.controller;

import com.mall.shopnest.api.CommonPage;
import com.mall.shopnest.api.CommonResult;
import com.mall.shopnest.core.model.ums.UmsAdmin;
import com.mall.shopnest.core.model.ums.UmsRole;
import com.mall.shopnest.dto.UmsAdminLoginParam;
import com.mall.shopnest.dto.UmsAdminParam;
import com.mall.shopnest.dto.UpdateAdminPasswordParam;
import com.mall.shopnest.service.UmsAdminService;
import com.mall.shopnest.service.UmsRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

@Controller
@Tag(name = "UmsAdminController", description = "Backend User Management")
@RequestMapping("/admin")
public class UmsAdminController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UmsAdminService adminService;

    @Autowired
    private UmsRoleService roleService;

    @Operation(summary = "User Registration")
    @PostMapping("/register")
    @ResponseBody
    public CommonResult<UmsAdmin> register(@Validated @RequestBody UmsAdminParam umsAdminParam) {
        LOGGER.info("Received payload: {}");
        UmsAdmin umsAdmin = adminService.register(umsAdminParam);
        return umsAdmin != null ? CommonResult.success(umsAdmin) : CommonResult.failed();
    }

    @Operation(summary = "Login and return token")
    @PostMapping("/login")
    @ResponseBody
    public CommonResult login(@Validated @RequestBody UmsAdminLoginParam umsAdminLoginParam) {
        String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("Username or password incorrect");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @Operation(summary = "Refresh token")
    @GetMapping("/refreshToken")
    @ResponseBody
    public CommonResult refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = adminService.refreshToken(token);
        if (refreshToken == null) {
            return CommonResult.failed("Token has expired!");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @Operation(summary = "Get current logged-in user information")
    @GetMapping("/info")
    @ResponseBody
    public CommonResult getAdminInfo(Principal principal) {
        if (principal == null) {
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        UmsAdmin umsAdmin = adminService.getAdminByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", umsAdmin.getUsername());
        data.put("menus", roleService.getMenuList(umsAdmin.getId()));
        data.put("icon", umsAdmin.getIcon());

        List<UmsRole> roleList = adminService.getRoleList(umsAdmin.getId());
        if (roleList != null && !roleList.isEmpty()) {
            List<String> roles = roleList.stream().map(UmsRole::getName).collect(Collectors.toList());
            data.put("roles", roles);
        }
        return CommonResult.success(data);
    }

    @Operation(summary = "Logout")
    @PostMapping("/logout")
    @ResponseBody
    public CommonResult logout(Principal principal) {
        adminService.logout(principal.getName());
        return CommonResult.success(null);
    }

    @Operation(summary = "Paginate and get user list by username or name")
    @GetMapping("/list")
    @ResponseBody
    public CommonResult<CommonPage<UmsAdmin>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<UmsAdmin> adminPage = adminService.list(keyword, pageable);
        return CommonResult.success(CommonPage.restPage(adminPage));
    }

    @Operation(summary = "Get specified user information")
    @GetMapping("/{id}")
    @ResponseBody
    public CommonResult<UmsAdmin> getItem(@PathVariable Long id) {
        UmsAdmin admin = adminService.getItem(id);
        return CommonResult.success(admin);
    }

    @Operation(summary = "Update specified user information")
    @PostMapping("/update/{id}")
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody UmsAdmin admin) {
        int count = adminService.update(id, admin);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @Operation(summary = "Change specified user's password")
    @PostMapping("/updatePassword")
    @ResponseBody
    public CommonResult updatePassword(@Validated @RequestBody UpdateAdminPasswordParam updatePasswordParam) {
        int status = adminService.updatePassword(updatePasswordParam);
        return switch (status) {
            case 1 -> CommonResult.success(status);
            case -1 -> CommonResult.failed("Invalid submitted parameters");
            case -2 -> CommonResult.failed("User not found");
            case -3 -> CommonResult.failed("Old password is incorrect");
            default -> CommonResult.failed();
        };
    }

    @Operation(summary = "Delete specified user")
    @PostMapping("/delete/{id}")
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        int count = adminService.delete(id);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @Operation(summary = "Change account status")
    @PostMapping("/updateStatus/{id}")
    @ResponseBody
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setStatus(status);
        int count = adminService.update(id, umsAdmin);
        return count > 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @Operation(summary = "Assign roles to user")
    @PostMapping("/role/update")
    @ResponseBody
    public CommonResult updateRole(@RequestParam("adminId") Long adminId,
                                   @RequestParam("roleIds") List<Long> roleIds) {
        int count = adminService.updateRole(adminId, roleIds);
        return count >= 0 ? CommonResult.success(count) : CommonResult.failed();
    }

    @Operation(summary = "Get roles of specified user")
    @GetMapping("/role/{adminId}")
    @ResponseBody
    public CommonResult<List<UmsRole>> getRoleList(@PathVariable Long adminId) {
        List<UmsRole> roleList = adminService.getRoleList(adminId);
        return CommonResult.success(roleList);
    }
}
