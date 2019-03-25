package com.lyf.basfile.service;
import com.lyf.basfile.entity.BasFileDetailEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BasFileDetailServiceI extends CommonService{
	
 	public void delete(BasFileDetailEntity entity) throws Exception;
 	
 	public Serializable save(BasFileDetailEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BasFileDetailEntity entity) throws Exception;
 	
}
