package cn.master.backend;

import cn.master.backend.entity.SystemUser;
import cn.master.backend.mapper.SystemUserMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BackendApplicationTests {

	@Resource
	SystemUserMapper systemUserMapper;
	@Test
	void contextLoads() {
		List<SystemUser> systemUsers = systemUserMapper.selectList(null);
		systemUsers.forEach(System.out::println);
	}

}
