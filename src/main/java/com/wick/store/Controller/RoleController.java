package com.wick.store.Controller;

import com.wick.store.domain.vo.UserVo;
import com.wick.store.service.RoleService;
import com.wick.store.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
@Api("权限管理")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation("获取用户列表")
    @GetMapping("/allUser")
    public JsonResult getAllUser(){
        return new JsonResult(roleService.getAllUser());
    }

}
