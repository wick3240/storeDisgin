package com.wick.store.Controller;

import com.wick.store.domain.vo.UserVo;
import com.wick.store.domain.vo.apiVo;
import com.wick.store.service.ProductInfoService;
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
    @Autowired
    private ProductInfoService productInfoService;

    @ApiOperation("获取用户列表")
    @GetMapping("/allUser")
    public JsonResult getAllUser(){
        return new JsonResult(roleService.getAllUser());
    }

    @ApiOperation("获取key，api")
    @GetMapping("/apikey")
    public apiVo getKey(String productId){
        apiVo apiVo=new apiVo();
        apiVo.setApi(productInfoService.findByApi(productId));
        apiVo.setKey("PMAK-643bbd443ab1b50043a4586e-fa31b1dd7aff84cf3e02d33344d813e769");
        return apiVo;
        //公司电脑
        //return "PMAK-64392fd0465bc800393b907f-14ea2bebb04e759ae6308023c38e5c5217";
        //家里电脑
        //return "PMAK-643bbd443ab1b50043a4586e-fa31b1dd7aff84cf3e02d33344d813e769";
    }
}
