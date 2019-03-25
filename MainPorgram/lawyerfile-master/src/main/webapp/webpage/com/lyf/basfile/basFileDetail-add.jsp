<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>用户每个模版的记录</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="basFileDetailController.do?doAdd" >
					<input id="id" name="id" type="hidden" value="${basFileDetailPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							模版文件ID:
						</label>
					</td>
					<td class="value">
					     	 <input id="basFileOid" name="basFileOid" type="text" maxlength="36" style="width: 150px" class="inputxt"  ignore="ignore" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">模版文件ID</label>
						</td>
				</tr>
				
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							表单内容:
						</label>
					</td>
					<td class="value" >
						  	 <textarea style="height:auto;width:95%" class="inputxt" rows="6" id="bfdContent" name="bfdContent"  ignore="ignore" ></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">表单内容</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
