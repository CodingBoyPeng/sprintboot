package com.pf.springboot.test;

import com.pf.springboot.entity.A;
import com.pf.springboot.entity.Company;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * @description:
 * @author:Peng
 * @Date:2021/3/25
 */
public class Test04 {
    public static void main(String[] args) {
        List<Company> exists = new ArrayList<>();
        Company company1 = new Company(null, "鲁从喜", 2, new Date(), BigDecimal.ONE);
        Company company2 = new Company(null, "彭峰", 3, new Date(), BigDecimal.ONE);

        Company company3 = new Company(1, "鲁从喜", 2, new Date(), BigDecimal.ONE);
        Company company4 = new Company(2, "彭峰", 3, new Date(), BigDecimal.ONE);
        exists.addAll(Arrays.asList(company1,company2,company3,company4));

        Map<Boolean, List<Company>> collect = exists.stream().collect(Collectors.partitioningBy(i -> {
            return  i.getId() == null;
        }));
        Set<Map.Entry<Boolean, List<Company>>> entries = collect.entrySet();
//        for (Map.Entry<Boolean, List<Company>> entry : entries) {
//            System.out.println(entry.getKey());
//            System.out.println(entry.getValue());;
//        }
        List<Company> companies = Collections.synchronizedList(exists);

        sum1(exists);
        exists.forEach(System.out::println);
    }

    public static  void sum1(List<Company> companies) {
        Company company = companies.get(0);
        company.setSize(company.getSize() + 1);

    }
}
