package com.wick.store.Controller;

import com.wick.store.domain.entiey.UserEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/org")
    public void org(UserEntity user){

    }
}
