package com.pf.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author:Peng
 * @Date:2021/2/7
 */
@RestController
public class DemoController {
    @GetMapping(value = "/hello")
    public String demo(){
        System.out.println("1111");
        return "Hello World, This is test jar project";
    }
}
