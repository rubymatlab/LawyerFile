package com.lyf.basfile.controller;
import com.lyf.basfile.entity.BasFileDetailEntity;
import com.lyf.basfile.entity.BasFileEntity;
import com.lyf.basfile.entity.VwBasFileDetailEntity;
import com.lyf.basfile.service.BasFileServiceI;
import com.lyf.utils.ExpiredFiles;
import com.lyf.utils.LicenseUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.TreeChildCount;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import java.io.OutputStream;
import org.jeecgframework.core.util.BrowserUtils;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.util.ResourceUtil;

import java.io.File;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import java.util.HashMap;
import org.jeecgframework.core.util.ExceptionUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.jwt.util.GsonUtil;
import org.jeecgframework.jwt.util.ResponseMessage;
import org.jeecgframework.jwt.util.Result;
import org.jeecgframework.jwt.util.menu.ResponseMessageCodeEnum;

import com.alibaba.fastjson.JSONArray;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.HtmlSaveOptions;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.SaveFormat;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;

import org.jeecgframework.web.cgform.entity.upload.CgUploadEntity;
import org.jeecgframework.web.cgform.service.config.CgFormFieldServiceI;
import java.util.HashMap;
/**   
 * @Title: Controller  
 * @Description: 律师文件类型
 * @author onlineGenerator
 * @date 2019-03-24 21:20:45
 * @version V1.0   
 *
 */
@Api(value="BasFile",description="律师文件类型",tags="basFileController")
@Controller
@RequestMapping("/basFileController")
public class BasFileController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BasFileController.class);

	@Autowired
	private BasFileServiceI basFileService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private CgFormFieldServiceI cgFormFieldService;
	


	/**
	 * 律师文件类型列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/lyf/basfile/basFileList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(BasFileEntity basFile,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(BasFileEntity.class, dataGrid);
		if(StringUtil.isEmpty(basFile.getId())){
			cq.isNull("bfParentid");
		}else{
			cq.eq("bfParentid", basFile.getId());
			basFile.setId(null);
		}
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basFile, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.basFileService.getDataGridReturn(cq, true);
		TagUtil.treegrid(response, dataGrid);
	}
	
	/**
	 * 删除律师文件类型
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(BasFileEntity basFile, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		basFile = systemService.getEntity(BasFileEntity.class, basFile.getId());
		message = "律师文件类型删除成功";
		try{
			basFileService.delete(basFile);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "律师文件类型删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除律师文件类型
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "律师文件类型删除成功";
		try{
			for(String id:ids.split(",")){
				BasFileEntity basFile = systemService.getEntity(BasFileEntity.class, 
				id
				);
				basFileService.delete(basFile);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "律师文件类型删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加律师文件类型
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(BasFileEntity basFile, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "律师文件类型添加成功";
		try{
			if(StringUtil.isEmpty(basFile.getBfParentid())){
				basFile.setBfParentid(null);
			}
			basFileService.save(basFile);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "律师文件类型添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setObj(basFile);
		return j;
	}
	
	/**
	 * 更新律师文件类型
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(BasFileEntity basFile, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "律师文件类型更新成功";
		BasFileEntity t = basFileService.get(BasFileEntity.class, basFile.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(basFile, t);
			if(StringUtil.isEmpty(t.getBfParentid())){
				t.setBfParentid(null);
			}
			basFileService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "律师文件类型更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 律师文件类型新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(BasFileEntity basFile, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basFile.getId())) {
			basFile = basFileService.getEntity(BasFileEntity.class, basFile.getId());
			req.setAttribute("basFilePage", basFile);
		}
		return new ModelAndView("com/lyf/basfile/basFile-add");
	}
	/**
	 * 律师文件类型编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(BasFileEntity basFile, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(basFile.getId())) {
			basFile = basFileService.getEntity(BasFileEntity.class, basFile.getId());
			req.setAttribute("basFilePage", basFile);
		}
		return new ModelAndView("com/lyf/basfile/basFile-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","basFileController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(BasFileEntity basFile,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(BasFileEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, basFile, request.getParameterMap());
		List<BasFileEntity> basFiles = this.basFileService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"律师文件类型");
		modelMap.put(NormalExcelConstants.CLASS,BasFileEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("律师文件类型列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,basFiles);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(BasFileEntity basFile,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"律师文件类型");
    	modelMap.put(NormalExcelConstants.CLASS,BasFileEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("律师文件类型列表", "导出人:"+ResourceUtil.getSessionUser().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<BasFileEntity> listBasFileEntitys = ExcelImportUtil.importExcel(file.getInputStream(),BasFileEntity.class,params);
				for (BasFileEntity basFile : listBasFileEntitys) {
					basFileService.save(basFile);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(e.getMessage());
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
	
	/**
	 * 获取文件附件信息
	 * 
	 * @param id basFile主键id
	 */
	@RequestMapping(params = "getFiles")
	@ResponseBody
	public AjaxJson getFiles(String id){
		List<CgUploadEntity> uploadBeans = cgFormFieldService.findByProperty(CgUploadEntity.class, "cgformId", id);
		List<Map<String,Object>> files = new ArrayList<Map<String,Object>>(0);
		for(CgUploadEntity b:uploadBeans){
			String title = b.getAttachmenttitle();//附件名
			String fileKey = b.getId();//附件主键
			String path = b.getRealpath();//附件路径
			String field = b.getCgformField();//表单中作为附件控件的字段
			Map<String, Object> file = new HashMap<String, Object>();
			file.put("title", title);
			file.put("fileKey", fileKey);
			file.put("path", path);
			file.put("field", field==null?"":field);
			files.add(file);
		}
		AjaxJson j = new AjaxJson();
		j.setObj(files);
		return j;
	}
	
	@RequestMapping(value="/list/{pageNo}/{pageSize}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="律师文件类型列表信息",produces="application/json",httpMethod="GET")
	public ResponseMessage<List<BasFileEntity>> list(@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize, HttpServletRequest request) {
		if(pageSize > Globals.MAX_PAGESIZE){
			return Result.error("每页请求不能超过" + Globals.MAX_PAGESIZE + "条");
		}
		CriteriaQuery query = new CriteriaQuery(BasFileEntity.class);
		query.setCurPage(pageNo<=0?1:pageNo);
		query.setPageSize(pageSize<1?1:pageSize);
		Map<String,Object> map = new HashMap<String,Object>();  
		map.put("bfCode", "asc");  
		query.setOrder(map);
		query.add();
		List<BasFileEntity> listBasFiles = this.basFileService.getListByCriteriaQuery(query,true);
		return Result.success(listBasFiles);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取律师文件类型信息",notes="根据ID获取律师文件类型信息",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> get(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id) {
		BasFileEntity task = basFileService.get(BasFileEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取律师文件类型信息为空");
		}
		return Result.success(task);
	}

	
	@RequestMapping(value = "/getContent/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取用户模板文件内容",notes="根据ID获取用户模板文件内容",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> getContent(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id,HttpServletRequest request) {
		// 验证License
        if (!LicenseUtil.getLicense()) {
            return Result.error("文件读取没有License!");
        }
		BasFileEntity task = basFileService.get(BasFileEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取用户每个模版的记录信息为空");
		}
		
        String tempPath=request.getSession().getServletContext().getRealPath("/");
        //模板word
		String template=tempPath+task.getBfPath();
		//目标word
        //String destdoc = tempPath+"../lawyerfile/export/temp/"+System.currentTimeMillis()+".pdf";
        //定义文档接口
        Document doc;
		try {
			doc = new Document(template); 
			List<String> fieldLabel = new ArrayList<String>();
	        for(String fieldname:doc.getMailMerge().getFieldNames())
	        	fieldLabel.add(fieldname);
	        return Result.success(fieldLabel.toArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getContentEdit/{id}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据ID获取用户模板文件内容",notes="根据ID获取用户模板文件内容",httpMethod="GET",produces="application/json")
	public ResponseMessage<?> getContentEdit(@ApiParam(required=true,name="id",value="ID")@PathVariable("id") String id,HttpServletRequest request) {
		// 验证License
        if (!LicenseUtil.getLicense()) {
            return Result.error("文件读取没有License!");
        }
        
        VwBasFileDetailEntity task = basFileService.get(VwBasFileDetailEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取用户每个模版的记录信息为空");
		}

        String tempPath=request.getSession().getServletContext().getRealPath("/");
        //模板word
		String template=tempPath+task.getBfPath();
		//目标word
        //String destdoc = tempPath+"../lawyerfile/export/temp/"+System.currentTimeMillis()+".pdf";
        //定义文档接口
        Document doc;
		try {
			doc = new Document(template); 
			JSONObject json=JSONObject.fromObject(task.getBfdContent());
			List<JSONObject> fieldLabel= new ArrayList<JSONObject>();
			
	        for(String fieldname:doc.getMailMerge().getFieldNames())
	        {
	        	//System.out.println(fieldname);
	        	if(json.containsKey(fieldname))
	        	{
	        		JSONObject e=new JSONObject();
	        		e.put("fieldname", fieldname);
	        		e.put("fieldvalue", json.get(fieldname));
	        		fieldLabel.add(e);
	        	}
	        	else
	        	{
	        		JSONObject e=new JSONObject();
	        		e.put("fieldname", fieldname);
	        		e.put("fieldvalue", "");
	        		fieldLabel.add(e);
	        	}
	        }
	        return Result.success(fieldLabel.toArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/postFileUrl", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="根据模板表单数据获取Url",produces="application/json")
	public ResponseMessage<?> postFileUrl(@ApiParam(name="模表单数据")@RequestBody Object formobject,HttpServletRequest request) {
		// 验证License
        if (!LicenseUtil.getLicense()) {
            return Result.error("文件读取没有License!");
        }
        JSONObject json=JSONObject.fromObject(formobject);
        
        String id="";
        if(json.containsKey("id"))
        	id=json.getString("id");
		BasFileEntity task = basFileService.get(BasFileEntity.class, id);
		if (task == null) {
			return Result.error("根据ID获取用户每个模版的记录信息为空");
		}
		
		List<TSBaseUser> listTsbu=basFileService.findByProperty(TSBaseUser.class,"userName",json.getString("createby"));
		BasFileDetailEntity bfe=new BasFileDetailEntity();
		bfe.setBfdContent(json.getString("detailvalue"));
		bfe.setBasFileOid(id);
		bfe.setCreateBy(listTsbu.get(0).getUserName());
		bfe.setCreateName(listTsbu.get(0).getRealName());
		bfe.setCreateDate(new Date());
		
		basFileService.save(bfe);
		
		JSONObject detailvalue=JSONObject.fromObject(bfe.getBfdContent());
		
        String tempPath=request.getSession().getServletContext().getRealPath("/");
        //模板word
		String template=tempPath+task.getBfPath();
		//目标word
		File templateFile = new File(template); 
		String desFileName=System.currentTimeMillis()+ templateFile.getName();
		String desFolder=tempPath+"export/temp/";
        String destdoc = desFolder+desFileName;
        //定义文档接口
        Document doc;
		try {
			doc = new Document(template); 
			List<String> fieldLabel = new ArrayList<String>();
			List<String> fieldValue = new ArrayList<String>();
	        for(String fieldname:doc.getMailMerge().getFieldNames())
	        {
	        	fieldLabel.add(fieldname);
	        	if(detailvalue.containsKey(fieldname))
	        		fieldValue.add(detailvalue.getString(fieldname));
	        	else
	        		fieldValue.add("");
	        }
	        //调用接口
	        String[] fieldArray = fieldLabel.toArray(new String[fieldLabel.size()]);
	        doc.getMailMerge().execute(fieldArray, fieldValue.toArray());
	        doc.save(destdoc);
	        ExpiredFiles.Delete(desFolder);
	        
	        return Result.success("export/temp/"+desFileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/postFileEditUrl", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="根据模板表单数据获取Url",produces="application/json")
	public ResponseMessage<?> postFileEditUrl(@ApiParam(name="模表单数据")@RequestBody Object formobject,HttpServletRequest request) {
		// 验证License
        if (!LicenseUtil.getLicense()) {
            return Result.error("文件读取没有License!");
        }
        JSONObject json=JSONObject.fromObject(formobject);
        
        String id="";
        if(json.containsKey("id"))
        	id=json.getString("id");
        VwBasFileDetailEntity task = basFileService.get(VwBasFileDetailEntity.class, id);
        if (task == null) {
			return Result.error("根据ID获取用户每个模版的记录信息为空");
		}
        
		List<TSBaseUser> listTsbu=basFileService.findByProperty(TSBaseUser.class,"userName",json.getString("createby"));
		//BasFileDetailEntity bfe=new BasFileDetailEntity();
		BasFileDetailEntity bfe = basFileService.get(BasFileDetailEntity.class, id);
		bfe.setBfdContent(json.getString("detailvalue"));
		bfe.setBasFileOid(task.getBasFileOid());
		bfe.setUpdateBy(listTsbu.get(0).getUserName());
		bfe.setUpdateName(listTsbu.get(0).getRealName());
		bfe.setUpdateDate(new Date());
		
		basFileService.saveOrUpdate(bfe);
		
		JSONObject detailvalue=JSONObject.fromObject(bfe.getBfdContent());
		
        String tempPath=request.getSession().getServletContext().getRealPath("/");
        //模板word
		String template=tempPath+task.getBfPath();
		//目标word
		File templateFile = new File(template); 
		String desFileName=System.currentTimeMillis()+ templateFile.getName();
		String desFolder=tempPath+"export/temp/";
        String destdoc = desFolder+desFileName;
        //定义文档接口
        Document doc;
		try {
			doc = new Document(template); 
			List<String> fieldLabel = new ArrayList<String>();
			List<String> fieldValue = new ArrayList<String>();
	        for(String fieldname:doc.getMailMerge().getFieldNames())
	        {
	        	fieldLabel.add(fieldname);
	        	if(detailvalue.containsKey(fieldname))
	        		fieldValue.add(detailvalue.getString(fieldname));
	        	else
	        		fieldValue.add("");
	        }
	        //调用接口
	        String[] fieldArray = fieldLabel.toArray(new String[fieldLabel.size()]);
	        doc.getMailMerge().execute(fieldArray, fieldValue.toArray());
	        doc.save(destdoc);
	        ExpiredFiles.Delete(desFolder);
	        
	        return Result.success("export/temp/"+desFileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/postFileUrlDetail", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="根据数据库获取Url",produces="application/json")
	public ResponseMessage<?> postFileUrlDetail(@ApiParam(name="模板消息id")@RequestBody Object formobject,HttpServletRequest request) {
		// 验证License
        if (!LicenseUtil.getLicense()) {
            return Result.error("文件读取没有License!");
        }
        JSONObject json=JSONObject.fromObject(formobject);
        String id="";
        if(json.containsKey("id"))
        	id=json.getString("id");
        BasFileDetailEntity taskdetail = basFileService.get(BasFileDetailEntity.class, id);
		if(taskdetail==null) {
			return Result.error("根据ID获取用户每个模版的记录信息为空");
		}
        BasFileEntity task = basFileService.get(BasFileEntity.class, taskdetail.getBasFileOid());
		if (task == null) {
			return Result.error("根据ID获取用户每个模版的记录信息为空");
		}
		JSONObject detailvalue=JSONObject.fromObject(taskdetail.getBfdContent());
		
        String tempPath=request.getSession().getServletContext().getRealPath("/");
        //模板word
		String template=tempPath+task.getBfPath();
		//目标word
		File templateFile = new File(template); 
		String desFileName=System.currentTimeMillis()+ templateFile.getName();
		String desFolder=tempPath+"export/temp/";
        String destdoc = desFolder+desFileName;
        //定义文档接口
        Document doc;
		try {
			doc = new Document(template); 
			List<String> fieldLabel = new ArrayList<String>();
			List<String> fieldValue = new ArrayList<String>();
	        for(String fieldname:doc.getMailMerge().getFieldNames())
	        {
	        	fieldLabel.add(fieldname);
	        	if(detailvalue.containsKey(fieldname))
	        		fieldValue.add(detailvalue.getString(fieldname));
	        	else
	        		fieldValue.add("");
	        }
	        //调用接口
	        String[] fieldArray = fieldLabel.toArray(new String[fieldLabel.size()]);
	        doc.getMailMerge().execute(fieldArray, fieldValue.toArray());
	        doc.save(destdoc);
	        ExpiredFiles.Delete(desFolder);
	        
	        return Result.success("export/temp/"+desFileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.error(e.getMessage());
		}
	}
	


	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="创建律师文件类型")
	public ResponseMessage<?> create(@ApiParam(name="律师文件类型对象")@RequestBody BasFileEntity basFile, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<BasFileEntity>> failures = validator.validate(basFile);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			basFileService.save(basFile);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("律师文件类型信息保存失败");
		}
		return Result.success(basFile);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ApiOperation(value="更新律师文件类型",notes="更新律师文件类型")
	public ResponseMessage<?> update(@ApiParam(name="律师文件类型对象")@RequestBody BasFileEntity basFile) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<BasFileEntity>> failures = validator.validate(basFile);
		if (!failures.isEmpty()) {
			return Result.error(JSONArray.toJSONString(BeanValidators.extractPropertyAndMessage(failures)));
		}

		//保存
		try{
			basFileService.saveOrUpdate(basFile);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("更新律师文件类型信息失败");
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return Result.success("更新律师文件类型信息成功");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value="删除律师文件类型")
	public ResponseMessage<?> delete(@ApiParam(name="id",value="ID",required=true)@PathVariable("id") String id) {
		logger.info("delete[{}]" , id);
		// 验证
		if (StringUtils.isEmpty(id)) {
			return Result.error("ID不能为空");
		}
		try {
			basFileService.deleteEntityById(BasFileEntity.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error("律师文件类型删除失败");
		}

		return Result.success();
	}
}
