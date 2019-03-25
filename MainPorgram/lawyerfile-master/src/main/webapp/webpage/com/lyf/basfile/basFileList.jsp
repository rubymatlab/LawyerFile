<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="basFileList" checkbox="true" pagination="false" treegrid="true" treeField="bfName" fitColumns="true" title="律师文件类型" sortName="createDate" actionUrl="basFileController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="编码"  field="bfCode"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="类型"  field="bfName"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="文件路径"  field="bfPath"  queryMode="single"  downloadName="附件下载"  width="120"></t:dgCol>
   <t:dgCol title="父ID"  field="bfParentid"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="basFileController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="basFileController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="basFileController.do?goUpdate" funname="updatetree" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="basFileController.do?doBatchDel" funname="deleteALLSelecttree"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="basFileController.do?goUpdate" funname="detailtree" width="100%" height="100%"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
		$("#basFileList").treegrid({
 				 onExpand : function(row){
 					var children = $("#basFileList").treegrid('getChildren',row.id);
 					 if(children.length<=0){
 					 	row.leaf=true;
 					 	$("#basFileList").treegrid('refresh', row.id);
 					 }
 				}
 		});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'basFileController.do?upload', "basFileList");
}

//导出
function ExportXls() {
	JeecgExcelExport("basFileController.do?exportXls","basFileList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("basFileController.do?exportXlsByT","basFileList");
}

/**
 * 获取表格对象
 * @return 表格对象
 */
function getDataGrid(){
	var datagrid = $('#'+gridname);
	return datagrid;
}
 </script>
