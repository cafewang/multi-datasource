package org.wangyang.multidatasource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.wangyang.multidatasource.entity.Cat;
import org.wangyang.multidatasource.enums.HairColor;
import org.wangyang.multidatasource.repo.CatRepo;

import javax.annotation.Resource;

@SpringBootTest
@ActiveProfiles("test")
class MultiDatasourceApplicationTests {
	@Resource
	private CatRepo catRepo;

	@Test
	void should_save_into_mysql() {
		Cat cat = Cat.builder()
				.name("Tom")
				.age(3)
				.hairColor(HairColor.BLUE)
				.build();
		catRepo.save(cat);
		Assertions.assertThat(catRepo.count()).isEqualTo(1L);
	}

}
