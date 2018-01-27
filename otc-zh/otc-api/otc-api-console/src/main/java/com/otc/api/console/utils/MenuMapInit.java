package com.otc.api.console.utils;

import com.otc.api.console.ctrl.system.login.response.Menu;
import com.otc.api.console.ctrl.system.role.response.ResourceResp;
import com.otc.api.console.exceptions.ConsoleBizException;
import com.otc.facade.sys.pojo.po.Resource;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 查询所有菜单，按层级封装成map
 * @author luwei
 * @Date 16-10-20 下午2:30
 */
public class MenuMapInit {


    public static List<Menu> getMenuMap(List<Resource> resourcesList){
        // key为resourceId，value为parentResource
        Map<Long, Resource> parentResourceMap = new LinkedHashMap<>();

        // key为parentResourceId，value为childResources
        Map<Long, List<Resource>> childResourceMap = new LinkedHashMap<>();

        for (Resource resource : resourcesList) {
            if (resource.getParentId() == null) {
                parentResourceMap.put(resource.getId(), resource);
            } else {
                List<Resource> childResources = childResourceMap.get(resource.getParentId());
                if (childResources == null) {
                    childResources = new ArrayList<>();
                }

                childResources.add(resource);

                childResourceMap.put(resource.getParentId(), childResources);
            }
        }

        // 查询出所有父菜单
        List<Menu> nodes = new ArrayList<>();
        for (Map.Entry<Long, Resource> entry : parentResourceMap.entrySet()) {
            Menu parentMenu = new Menu();
            parentMenu.setName(entry.getValue().getName());
            parentMenu.setCode(entry.getValue().getCode());
            parentMenu.setId(entry.getValue().getId());

            List<Resource> childResources = childResourceMap.get(entry.getKey());

            if (childResources == null) {
                nodes.add(parentMenu);
                continue;
            }

            List<Menu> childMenus = new ArrayList<>();

            for (Resource resource : childResources) {
                Menu childMenu = new Menu();
                childMenu.setName(resource.getName());
                childMenu.setCode(resource.getCode());
                childMenu.setId(resource.getId());

                childMenus.add(childMenu);
            }

            parentMenu.setNodes(childMenus);

            nodes.add(parentMenu);
        }

        return nodes;
    }

    /*public static List<ResourceResp> getMenuMap(List<Resource> resourcesList){
        if(resourcesList ==  null || resourcesList.size() == 0){
            throw new ConsoleBizException("菜单封装错误,参数resourcesList不能为空");
        }

        // key为resourceId，value为parentResource
        Map<Long, Resource> parentResourceMap = new LinkedHashMap<>();

        // key为parentResourceId，value为childResources
        Map<Long, List<Resource>> childResourceMap = new LinkedHashMap<>();

        for (Resource resource : resourcesList) {
            if (resource.getParentId() == null) {
                parentResourceMap.put(resource.getParentId(), resource);
            } else {
                List<Resource> childResources = childResourceMap.get(resource.getParentId());
                if (childResources == null) {
                    childResources = new ArrayList<>();
                }

                childResources.add(resource);

                childResourceMap.put(resource.getParentId(), childResources);
            }
        }


        List<ResourceResp> nodes = new ArrayList<>();
        //一级菜单项
        for (Map.Entry<String, Resource> entry : parentResourceMap.entrySet()) {
            ResourceResp parentMenu = new ResourceResp();
            parentMenu.setName(entry.getValue().getName());
            parentMenu.setCode(entry.getValue().getCode());
            parentMenu.setOrderNo(entry.getValue().getOrderNo());
            parentMenu.setParentId(entry.getValue().getParentId());
            //获取当前菜单的子项
            List<Resource> childResources = childResourceMap.get(entry.getKey());

            if (childResources == null) {
                nodes.add(parentMenu);
                continue;
            }

            List<ResourceResp> childMenus = new ArrayList<>();
            //二级菜单项
            for (Resource resource : childResources) {
                ResourceResp childMenu = new ResourceResp();
                childMenu.setName(resource.getName());
                childMenu.setCode(resource.getCode());
                childMenu.setOrderNo(resource.getOrderNo());
                childMenu.setParentId(resource.getParentId());

                //三级菜单项
                if(StringUtils.isBlank(resource.getUrl()) && resource.getLevels().intValue() == 2){
                    //获取当前菜单的子项
                    List<Resource> threeResources = childResourceMap.get(resource.getCode());

                    if (threeResources != null) {
                        List<Menu> threeMenus = new ArrayList<>();
                        for (Resource threeResourceObj : threeResources) {
                            Menu threeMenu = new Menu();
                            threeMenu.setName(threeResourceObj.getName());
                            threeMenu.setCode(threeResourceObj.getCode());
                            threeMenu.setLevels(threeResourceObj.getLevels());
                            threeMenu.setUrl(threeResourceObj.getUrl());
                            threeMenu.setOrderNo(threeResourceObj.getOrderNo());
                            threeMenu.setParentCode(threeResourceObj.getParentCode());
                            threeMenu.setOperation(threeResourceObj.getOperation());
                            threeMenus.add(threeMenu);
                        }
                        Collections.sort(threeMenus, new ResourceCompare());
                        childMenu.setNodes(threeMenus);
                    }
                }

                childMenus.add(childMenu);
            }

            Collections.sort(childMenus, new ResourceCompare());
            parentMenu.setNodes(childMenus);

            nodes.add(parentMenu);
        }
        Collections.sort(nodes, new ResourceCompare());
        return nodes;
    }*/
}
