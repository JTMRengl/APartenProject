package com.offcn.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.offcn.po.User;
import com.offcn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    //远程服务调用客户端
    @Autowired
    RestTemplate restTemplate;

    //开启Ribbon后，RestTemplate直接使用服务名就可以发起调用
    String url="http://USERPROVIDER";


    @Override
    @HystrixCommand(fallbackMethod = "getUserMapFallbackMethod")
    public Map getUserMap() {
        Map map = restTemplate.getForObject(url+"/user/getall", Map.class);
        return map;
    }

    public Map getUserMapFallbackMethod(){
        Map map = new HashMap(  );
        map.put( "list", new ArrayList<>(  ) );
        map.put( "ProviderVersion" , " 获取远程调用失败");
        return map;
    }

    @Override
    public void createUser(User user) {

        restTemplate.postForObject(url+"/user/save", user,String.class);

    }

    @Override
    public User getUser(Long id) {

        return restTemplate.getForObject(url+"/user/get/"+id, User.class);
    }

    @Override
    public void updateUser(Long id, User user) {
        restTemplate.put(url+"/user/update/"+id, user);

    }

    @Override
    public void deleteUser(Long id) {
        restTemplate.delete(url+"/user/delete/"+id);

    }

}
