package com.wick.store;

import com.wick.store.domain.entity.UserEntity;
import com.wick.store.repository.UserMapper;
import com.wick.store.service.UserService;
import com.wick.store.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class StoreApplicationTests {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userService;
	@Test
	void contextLoads() {
	}
	@Test
	void  ToConnect() throws SQLException {
		System.out.println(dataSource.getConnection());

	}
	@Test
	void testForInsert(){

	}
	@Test
	void userRegistration(){
		try {
			UserEntity user = new UserEntity();
			user.setUsername("howard");
			user.setPassword("12345");
			userService.reg(user);
			System.out.println("ok");
		}catch (ServiceException e){
			System.out.println(e.getClass().getSimpleName());
			System.out.println(e.getMessage());
		}
	}

}
