package com.lyf.basfile.service;
import com.lyf.basfile.entity.VwBasFileDetailEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface VwBasFileDetailServiceI extends CommonService{
	
 	public void delete(VwBasFileDetailEntity entity) throws Exception;
 	
 	public Serializable save(VwBasFileDetailEntity entity) throws Exception;
 	
 	public void saveOrUpdate(VwBasFileDetailEntity entity) throws Exception;
 	
}
