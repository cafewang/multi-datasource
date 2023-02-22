package org.wangyang.multidatasource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.wangyang.multidatasource.enums.HairColor;
import org.wangyang.multidatasource.mysql.entity.Cat;
import org.wangyang.multidatasource.mysql.repo.MysqlCatRepo;
import org.wangyang.multidatasource.postgres.repo.PostgresCatRepo;

import javax.annotation.Resource;

@SpringBootTest
@ActiveProfiles("test")
class MultiDatasourceApplicationTests {
	@Resource
	private MysqlCatRepo mysqlCatRepo;
	@Resource
	private PostgresCatRepo postgresCatRepo;

	@Test
	void should_save_into_mysql() {
		Cat cat = Cat.builder()
				.name("Tom")
				.age(3)
				.hairColor(HairColor.BLUE)
				.build();
		mysqlCatRepo.save(cat);
		Assertions.assertThat(mysqlCatRepo.count()).isEqualTo(1L);
	}

	@Test
	void should_save_into_pgsql() {
		org.wangyang.multidatasource.postgres.entity.Cat cat = org.wangyang.multidatasource.postgres.entity.Cat.builder()
				.name("Tom")
				.age(3)
				.hairColor(HairColor.BLUE)
				.build();
		postgresCatRepo.save(cat);
		Assertions.assertThat(postgresCatRepo.count()).isEqualTo(1L);
	}

}
