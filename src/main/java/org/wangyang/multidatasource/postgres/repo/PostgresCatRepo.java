package org.wangyang.multidatasource.postgres.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wangyang.multidatasource.postgres.entity.Cat;

@Repository
public interface PostgresCatRepo extends JpaRepository<Cat, Long> {
}
