package com.zhw.web.security.dynAuth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class permissService {

	public List<Permission> findAll() {
		return null;
	}

	
	@Autowired
    private FilterChainDefinitionsService definitionService;

    /**
     * 保存权限
     */
    @SuppressWarnings("unchecked")
    public Permission save(Permission permission){
        //更新权限
    	//Permission perm = super.save(permission);
    	
        definitionService.reloadFilterChains();//保存权限时进行重新加载权限过滤链
        return permission;
    }

    /**
     * 删除权限
     */
    public void delete(Integer pid){
    	//更新权限
        //super.delete(pid);
        definitionService.reloadFilterChains();//重新加载权限过滤链
    }
}
