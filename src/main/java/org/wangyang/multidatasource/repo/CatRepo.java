package org.wangyang.multidatasource.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wangyang.multidatasource.entity.Cat;

@Repository
public interface CatRepo extends JpaRepository<Cat, Long> {
}
