package com.pf.springboot.aspect;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Author: PengFeng
 * @Description:
 * @Date: Created in 17:49 2021/6/15
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        System.out.println("当前数据源：" + DynamicDataSourceContextHolder.get());
        return DynamicDataSourceContextHolder.get();
    }
}