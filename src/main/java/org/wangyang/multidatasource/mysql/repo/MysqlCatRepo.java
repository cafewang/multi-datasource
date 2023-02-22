package org.wangyang.multidatasource.mysql.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wangyang.multidatasource.mysql.entity.Cat;

@Repository
public interface MysqlCatRepo extends JpaRepository<Cat, Long> {
}
