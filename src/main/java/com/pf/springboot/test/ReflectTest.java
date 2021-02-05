package com.pf.springboot.test;

import com.pf.springboot.entity.Company;
import com.pf.springboot.entity.ObjectDetail;
import com.pf.springboot.entity.People;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author:Peng
 * @Date:2021/1/15
 */
public class ReflectTest {
    // java 反射 api
    public static void main(String[] args) {
        Company company = new Company(1,"黄小龙", 50, new Date(), BigDecimal.ONE);
        People people = new People("黄小龙", 18, BigDecimal.ONE, "杭州", "187****9351");
        List<?> list = getClassField(company);
        List<?> peopleFiledList = getClassField(people);

        // 对象拥有的属性
         System.out.println(list.toString());
//         System.out.println(peopleFiledList.toString());

        // 对象所有方法
        // echoAllMethods(people);

        // 反射执行指定方法
//        invokeMethod(company, "echoCompanyDetail");
    }


    /**
     *
     * 利用反射获取所传的类属性列表（包括私有、共有等）
     * @param obj 任意对象
     * @param <?> 泛型对象
     * @return
     */
    private static List<?> getClassField(Object obj) {
        // 获取
        Class<?> clazz = obj.getClass();
        // 所有属性
        Field[] fields = clazz.getDeclaredFields();

        List<String> list = new ArrayList<>();
        Arrays.stream(fields).forEach(field -> {
            // 设置是否允许访问
            field.setAccessible(true);
            String fieldStr = null;
            try {
                fieldStr = "属性: " +
                        field.getName() +
                        " = " +
                        field.get(obj) +
                        ", 访问修饰符: " +
                        Modifier.toString(field.getModifiers()) +
                        ", 参数类型: " +
                        field.getType();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            list.add(fieldStr + "\n");
        });
        return list;
    };


    /**
     * 打印所拿到反射类中的所有公有，私有，保护，默认方法；但不包括父类的方法
     * @param obj
     */
    public static void echoAllMethods(Object obj) {
        Class<?> clazz = obj.getClass();
        // 拿到反射类中所有的公有方法（包括父类）
        // Method[] methods = clazz.getMethods();
        //  拿到反射类中的所有公有，私有，保护，默认方法；但不包括父类的方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            String name = method.getName();
            System.out.println(name);
        }
    }


    /**
     * 通过反射 invoke方法
     * @param obj
     */
    public static void invokeMethod(Object obj, String methodName) {
        Class<?> clazz = obj.getClass();
        try {
            Method method = clazz.getMethod(methodName, String.class, Integer.class, Date.class, BigDecimal.class);
            String result = (String) method.invoke(obj, "李天刚", 1, new Date(), BigDecimal.valueOf(3));
            System.out.println(result);
        } catch (NoSuchMethodException e) {
            System.out.println("在当前类中没有找到找到指定方法: " + methodName);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
