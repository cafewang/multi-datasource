package org.wangyang.multidatasource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.wangyang.multidatasource.enums.HairColor;
import org.wangyang.multidatasource.mysql.entity.Cat;
import org.wangyang.multidatasource.postgres.repo.PostgresCatRepo;
import org.wangyang.multidatasource.services.MysqlCatService;

import javax.annotation.Resource;

@SpringBootTest
@ActiveProfiles("test")
class MultiDatasourceApplicationTests {
	@Resource
	private MysqlCatService mysqlCatService;
	@Resource
	private PostgresCatRepo postgresCatRepo;

	@Test
	void should_save_into_mysql() {
		mysqlCatService.createTable();
		Cat cat = Cat.builder()
				.name("Tom")
				.age(3)
				.hairColor(HairColor.BLUE)
				.build();
		mysqlCatService.saveIntoSlave(cat);
		Assertions.assertThat(mysqlCatService.queryFromMaster(1L)).isNull();
		mysqlCatService.dropTable();
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
