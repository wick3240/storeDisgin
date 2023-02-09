package com.wick.store;

import com.wick.store.domain.entiey.UserEntity;
import com.wick.store.repository.UserMapper;
import com.wick.store.service.UserService;
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
		UserEntity user=new UserEntity();
		user.setUsername("john");
		user.setPassword("12345");
		
	}

}
