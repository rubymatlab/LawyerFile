<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>律师文件类型</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <t:base type="uploadify"></t:base>
  <style type="text/css">
  	.combo_self{height: 22px !important;width: 150px !important;}
  	.layout-header .btn {
	    margin:0;
	   float: none !important;
	}
	.btn-default {
	    height: 35px;
	    line-height: 35px;
	    font-size:14px;
	}
  </style>
  
  <script type="text/javascript">
	$(function(){
		$(".combo").removeClass("combo").addClass("combo combo_self");
		$(".combo").each(function(){
			$(this).parent().css("line-height","0px");
		});   
	});
  		
  		 /**树形列表数据转换**/
  function convertTreeData(rows, textField) {
      for(var i = 0; i < rows.length; i++) {
          var row = rows[i];
          row.text = row[textField];
          if(row.children) {
          	row.state = "open";
              convertTreeData(row.children, textField);
          }
      }
  }
  /**树形列表加入子元素**/
  function joinTreeChildren(arr1, arr2) {
      for(var i = 0; i < arr1.length; i++) {
          var row1 = arr1[i];
          for(var j = 0; j < arr2.length; j++) {
              if(row1.id == arr2[j].id) {
                  var children = arr2[j].children;
                  if(children) {
                      row1.children = children;
                  }
                  
              }
          }
      }
  }
  </script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="basFileController.do?doAdd" callback="jeecgFormFileCallBack@Override">
					<input id="id" name="id" type="hidden" value="${basFilePage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							编码:
						</label>
					</td>
					<td class="value">
					     	 <input id="bfCode" name="bfCode" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">编码</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							类型:
						</label>
					</td>
					<td class="value">
					     	 <input id="bfName" name="bfName" type="text" maxlength="32" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">类型</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							文件路径:
						</label>
					</td>
					<td class="value">
		<div class="form jeecgDetail">
			<t:upload name="bfPath" id="bfPath" queueID="filediv_bfPath" outhtml="false" uploader="cgUploadController.do?saveFiles"  extend="office" buttonText='添加文件'  onUploadStart="bfPathOnUploadStart"> </t:upload>
			<div class="form" id="filediv_bfPath"></div>
			<script type="text/javascript">
				function bfPathOnUploadStart(file){
					var cgFormId=$("input[name='id']").val();
					$('#bfPath').uploadify("settings", "formData", {
						'cgFormId':cgFormId,
						'cgFormName':'bas_file',
						'cgFormField':'BF_PATH'
					});
				}
			</script>
		</div>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">文件路径</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							父ID:
						</label>
					</td>
					<td class="value">
							<input id="bfParentid" name="bfParentid" type="text" style="width: 150px" class="inputxt easyui-combotree"  ignore="ignore" 
							data-options="panelHeight:'220',
				                    url: 'basFileController.do?datagrid&field=id,bfName',  
				                    loadFilter: function(data) {
				                    	var rows = data.rows || data;
				                    	var win = frameElement.api.opener;
				                    	var listRows = win.getDataGrid().treegrid('getData');
				                    	joinTreeChildren(rows, listRows);
				                    	convertTreeData(rows, 'bfName');
				                    	return rows; 
				                    },
				                    onSelect:function(node){
				                    	$('#bfParentid').val(node.id);
				                    },
				                    onLoadSuccess: function() {
				                    	var win = frameElement.api.opener;
				                    	var currRow = win.getDataGrid().treegrid('getSelected');
				                    	if(!'${basFilePage.id}') {
				                    		//增加时，选择当前父菜单
				                    		if(currRow) {
				                    			$('#bfParentid').combotree('setValue', currRow.id);
				                    		}
				                    	}else {
				                    		//编辑时，选择当前父菜单
				                    		if(currRow) {
				                    			$('#bfParentid').combotree('setValue', currRow.bfParentid);
				                    		}
				                    	}
				                    }"/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">父ID</label>
						</td>
				</tr>
				
				
			</table>
		</t:formvalid>
 </body>
	  	<script type="text/javascript">
	  		function jeecgFormFileCallBack(data){
	  			if (data.success == true) {
					uploadFile(data);
				} else {
					if (data.responseText == '' || data.responseText == undefined) {
						$.messager.alert('错误', data.msg);
						$.Hidemsg();
					} else {
						try {
							var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'), data.responseText.indexOf('错误信息'));
							$.messager.alert('错误', emsg);
							$.Hidemsg();
						} catch(ex) {
							$.messager.alert('错误', data.responseText + '');
						}
					}
					return false;
				}
				if (!neibuClickFlag) {
					var win = frameElement.api.opener;
					win.reloadTable();
				}
	  		}
	  		function upload() {
					$('#bfPath').uploadify('upload', '*');	
			}
			
			var neibuClickFlag = false;
			function neibuClick() {
				neibuClickFlag = true; 
				$('#btn_sub').trigger('click');
			}
			function cancel() {
					$('#bfPath').uploadify('cancel', '*');
			}
			function uploadFile(data){
				if(!$("input[name='id']").val()){
					if(data.obj!=null && data.obj!='undefined'){
						$("input[name='id']").val(data.obj.id);
					}
				}
				if($(".uploadify-queue-item").length>0){
					upload();
				}else{
					if (neibuClickFlag){
						alert(data.msg);
						neibuClickFlag = false;
					}else {
						var win = frameElement.api.opener;
						win.reloadTable();
						win.tip(data.msg);
						frameElement.api.close();
					}
				}
			}
	  	</script>
