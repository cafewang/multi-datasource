package org.wangyang.multidatasource.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wangyang.multidatasource.mysql.Routable;
import org.wangyang.multidatasource.mysql.entity.Cat;
import org.wangyang.multidatasource.mysql.repo.MysqlCatRepo;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class MysqlCatService {
    @Resource
    private MysqlCatRepo mysqlCatRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @Routable()
    @Transactional("mysqlTransactionManager")
    public Cat saveIntoSlave(Cat cat) {
        return mysqlCatRepo.save(cat);
    }

    public Cat queryFromMaster(Long catId) {
        return mysqlCatRepo.findById(catId).orElse(null);
    }

    @Routable
    public void createTable() {
        entityManager.createNativeQuery("CREATE TABLE if not exists `cat` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `age` int(11) DEFAULT NULL,\n" +
                "  `hair_color` varchar(255) DEFAULT NULL,\n" +
                "  `name` varchar(255) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB;\n").executeUpdate();
    }

    @Routable
    public void dropTable() {
        entityManager.createNativeQuery("drop TABLE if exists `cat`;").executeUpdate();
    }
}
