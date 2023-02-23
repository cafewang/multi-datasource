package org.wangyang.multidatasource.mysql;

import org.wangyang.multidatasource.enums.RoutingDataSourceEnum;

/**
 * @author wangyang
 */
public final class DataSourceRoutingHolder {
    private DataSourceRoutingHolder() {

    }

    private static final ThreadLocal<RoutingDataSourceEnum> THREAD_LOCAL = new ThreadLocal<>();

    public static void setDataSource(RoutingDataSourceEnum dataSourceEnum) {
        THREAD_LOCAL.set(dataSourceEnum);
    }

    public static RoutingDataSourceEnum getDataSource() {
        return THREAD_LOCAL.get();
    }

    public static void clearDataSource() {
        THREAD_LOCAL.remove();
    }
}
