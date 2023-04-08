package com.wick.store.Controller;

import com.wick.store.domain.entity.UserEntity;
import com.wick.store.service.UserService;
import com.wick.store.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RestController
@Api(tags = "注册登录模块")
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @ApiOperation(value = "注册操作",notes = "注册操作")
    @PostMapping("/org")
    public JsonResult org(UserEntity user){
        userService.reg(user);
        return new JsonResult();
    }
    @ApiOperation(value = "登录操作",notes = "登录操作")
    @PostMapping("/login")
    public JsonResult login(String username, String password){
        JsonResult data=userService.login(username,password);

        return new JsonResult(data);
    }
    @ApiOperation(value = "修改密码",notes = "修改密码")
    @PostMapping("/changePassword")
    public JsonResult changePassword(String username,String oldPassword,String newPassword){
        userService.changePassword(username,oldPassword,newPassword);
        return new JsonResult<>();
    }
    @ApiOperation("查找用户的uid")
    @GetMapping("/findByUid")
    public JsonResult<UserEntity> findByUid(HttpSession session){
        UserEntity data=userService.findByUid(getUidFromSession(session));
        return new JsonResult(data);
    }
    @ApiOperation(value = "修改当前用户的信息",notes = "修改当前用户的信息")
    @PostMapping("/changeInfo")
    public JsonResult changeInfoUser(UserEntity user,HttpSession session){
        String  uid=getUidFromSession(session);
        userService.changeInfoUser(uid,user);
        return new JsonResult();
    }

    @ApiOperation(value = "上传头像",notes = "上传头像")
    @PostMapping("/uploadAvatar")
    public JsonResult<String> uploadAvatar(HttpSession session, MultipartFile file){
        String uid=getUidFromSession(session);
        String username=getUserNameFromSession(session);
        userService.updateAvatar(session,uid,username,file);
        return new JsonResult();

    }
    @ApiOperation(value = "登出，删除token",notes = "登出，删除token")
    public JsonResult logout(String userId){
        userService.logout(userId);
        return new JsonResult();
    }
}
