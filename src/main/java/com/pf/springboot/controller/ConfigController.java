package com.pf.springboot.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @Author: PengFeng
 * @Description:
 * @Date: Created in 7:44 2021/8/3
 */
@Controller
@RequestMapping("config")
public class ConfigController {

    @NacosValue(value = "${test}", autoRefreshed = true)
    private int useLocalCache;

    @RequestMapping(value = "/get", method = GET)
    @ResponseBody
    public int get() {
        return useLocalCache;
    }
}