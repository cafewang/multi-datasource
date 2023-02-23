package org.wangyang.multidatasource.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wangyang.multidatasource.mysql.Routable;
import org.wangyang.multidatasource.mysql.entity.Cat;
import org.wangyang.multidatasource.mysql.repo.MysqlCatRepo;

import javax.annotation.Resource;

@Service
public class MysqlCatService {
    @Resource
    private MysqlCatRepo mysqlCatRepo;

    @Routable()
    @Transactional()
    public Cat saveIntoSlave(Cat cat) {
        return mysqlCatRepo.save(cat);
    }

    public Cat queryFromMaster(Long catId) {
        return mysqlCatRepo.findById(catId).orElse(null);
    }
}
