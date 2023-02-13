package com.wick.store.Controller;

import com.wick.store.domain.entiey.UserEntity;
import com.wick.store.util.JsonResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/org")
    public JsonResult org(UserEntity user){
        return new JsonResult();
    }
}
