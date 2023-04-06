package com.wick.store.Controller;


import com.wick.store.domain.entity.Student;
import com.wick.store.domain.entity.UserEntity;
import com.wick.store.repository.UserMapper;
import com.wick.store.util.JsonResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Api(tags = "测试redis的crud")
@RestController
public class testForRedis {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserMapper userMapper;
    //登录接口，生成令牌
    @PostMapping("/set")
    public JsonResult set(@RequestParam String id ){
        UserEntity userEntity=userMapper.selectById(id);
        String token= UUID.randomUUID()+"";
        redisTemplate.opsForValue().set(token,userEntity);
        return new JsonResult(token);
    }
    //使用token获取登录用户
    @GetMapping("/get")
    public JsonResult get(HttpServletRequest request){
        String token=request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        if (user != null){
        return new JsonResult(user);
        }
        return new JsonResult(JsonResult.FAILURE);
    }

    @DeleteMapping("/delete/{key}")
    public boolean delete(@PathVariable("key") String key){
        redisTemplate.delete(key);
        //判断是否还存在key，如果返回false，说明删除成功
        return redisTemplate.hasKey(key);
    }

}
