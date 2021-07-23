package com.pf.springboot.aspect;

import com.pf.springboot.enums.DataSourceKey;

/**
 * @Author: PengFeng
 * @Description:
 * @Date: Created in 17:48 2021/6/15
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<DataSourceKey> currentDatesource = new ThreadLocal<>();

    /**
     * 清除当前数据源
     */
    public static void clear() {
        currentDatesource.remove();
    }

    /**
     * 获取当前使用的数据源
     *
     * @return 当前使用数据源的ID
     */
    public static DataSourceKey get() {
        return currentDatesource.get();
    }

    /**
     * 设置当前使用的数据源
     *
     * @param value 需要设置的数据源ID
     */
    public static void set(DataSourceKey value) {
        currentDatesource.set(value);
    }

    public static void setSlave() {
        DynamicDataSourceContextHolder.set(DataSourceKey.DB_SLAVE);
    }
}