package com.shiro;

import java.util.LinkedHashMap;

/**
 * 过滤器工厂类
 *
 * @author GC
 * @date 2020年 01月19日 11:55:03
 */
public class FilterChainDefinitionMapBuilder {

    public LinkedHashMap<String,String> build(){
        LinkedHashMap<String, String> stringStringLinkedHashMap = new LinkedHashMap<>();
        stringStringLinkedHashMap.put("/js/**","anon");
        stringStringLinkedHashMap.put("/css/**","anon");
        stringStringLinkedHashMap.put("/pages/login","anon");
        stringStringLinkedHashMap.put("/shiro/login","anon");
        stringStringLinkedHashMap.put("/**","user");

        return stringStringLinkedHashMap;
    }
}
