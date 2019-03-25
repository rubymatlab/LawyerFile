package com.lyf.basfile.service;
import com.lyf.basfile.entity.BasFileEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface BasFileServiceI extends CommonService{
	
 	public void delete(BasFileEntity entity) throws Exception;
 	
 	public Serializable save(BasFileEntity entity) throws Exception;
 	
 	public void saveOrUpdate(BasFileEntity entity) throws Exception;
 	
}
