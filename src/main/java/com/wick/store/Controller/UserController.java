package com.wick.store.Controller;

import com.wick.store.domain.entiey.UserEntity;
import com.wick.store.service.UserService;
import com.wick.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;


    @PostMapping("/org")
    public JsonResult<Void> org(UserEntity user){
        userService.reg(user);
        return new JsonResult<>(OK);
    }
}
