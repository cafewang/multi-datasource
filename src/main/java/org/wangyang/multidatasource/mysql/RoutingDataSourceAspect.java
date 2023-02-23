package org.wangyang.multidatasource.mysql;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.wangyang.multidatasource.enums.RoutingDataSourceEnum;

@Aspect
@Order(-10)
@Component
@Slf4j
public class RoutingDataSourceAspect {
    @Value("${spring.datasource.mysql.route-to-slave:false}")
    private boolean routeToSlave;

    @Before("@annotation(org.wangyang.multidatasource.mysql.Routable)")
    public void changeDataSource(JoinPoint joinPoint) {
        DataSourceRoutingHolder.setDataSource(routeToSlave ? RoutingDataSourceEnum.SLAVE : RoutingDataSourceEnum.MASTER);
    }

    @After("@annotation(org.wangyang.multidatasource.mysql.Routable)")
    public void restoreDataSource(JoinPoint joinPoint) {
        DataSourceRoutingHolder.clearDataSource();
    }
}
