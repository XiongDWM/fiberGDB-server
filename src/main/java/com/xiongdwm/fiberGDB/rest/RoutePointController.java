package com.xiongdwm.fiberGDB.rest;

import com.xiongdwm.fiberGDB.bo.FiberDto;
import com.xiongdwm.fiberGDB.entities.RoutePoint;
import com.xiongdwm.fiberGDB.entities.relationship.Fiber;
import com.xiongdwm.fiberGDB.resources.RoutePointResources;
import com.xiongdwm.fiberGDB.support.RSAUtils;
import com.xiongdwm.fiberGDB.support.View;
import com.xiongdwm.fiberGDB.support.serialize.JacksonUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RoutePointController {
    @Resource
    private RSAUtils rsaUtils;
    @Resource
    private RoutePointResources routePointResources;

    @RequestMapping("/web/init")
    public Object init() throws Exception {
        System.out.println("=================init=========================");
        Map<String,Object> map=new HashMap<>();
        map.put("code",200);
        map.put("msg","success");
        return JacksonUtil.mapToNode(map);
    }

    @RequestMapping("/web/getSignature")
    public Object getSignature(String info) throws Exception {
        Map<String,Object>map=new HashMap<>();
        map.put("code",200);
        map.put("msg",rsaUtils.decrypt(info)+"===================="+info);
        return JacksonUtil.mapToNode(map);
    }

    @RequestMapping("/test")
    public Object test(Long ak) throws Exception {
        System.out.println(ak);
        return View.SUCCESS;
    }
    @RequestMapping("/point/add")
    public Object save(RoutePoint p) {
        System.out.println(p.toString());
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("msg", routePointResources.save(p));
        return map;
    }

    @RequestMapping("/rel/add")
    public Object saveRel(FiberDto fiberDto){
//        routePointResources.createFiber(fiberDto.getFromId(),fiberDto.getToId(),fiberDto.getFiber()).block();
        Fiber fiber=new Fiber();
        BeanUtils.copyProperties(fiberDto,fiber);
        routePointResources.createFiberNoneReactive(fiberDto.getFromId(),fiberDto.getToId(),fiber);
        return View.SUCCESS;
    }

    @RequestMapping("/rel/searchRoute")
    public Object searchRoute(){
        return View.getSuccess(routePointResources.retrieve(123L,12367L, 3));
    }
}