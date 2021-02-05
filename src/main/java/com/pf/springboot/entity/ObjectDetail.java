package com.pf.springboot.entity;

/**
 * @description:
 * @author:Peng
 * @Date:2021/1/21
 */
public class ObjectDetail {
    /* 属性名称 */
    private String fieldName;
    /* 属性值 */
    private Object fieldValue;
    /* 类型 */
    private String fieldType;
    /* 修饰符 */
    private String fieldDescSign;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldDescSign() {
        return fieldDescSign;
    }

    public void setFieldDescSign(String fieldDescSign) {
        this.fieldDescSign = fieldDescSign;
    }


}
