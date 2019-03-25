<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>用户模版信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="vwBasFileDetailController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${vwBasFileDetailPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" style="display:none">
							<label class="Validform_label">
								模版类型id:
							</label>
						</td>
						<td class="value" style="display:none">
							<input id="basFileOid" name="basFileOid" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"  onclick="popupClick(this,'id,bf_name','basFileOid,bfName','msg_basfile')" value='${vwBasFileDetailPage.basFileOid}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">模版类型id</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								模版类型:
							</label>
						</td>
						<td class="value">
							<input id="bfName" name="bfName" type="text" style="width: 150px" class="searchbox-inputtext"  ignore="ignore"  onclick="popupClick(this,'id,bf_name','basFileOid,bfName','msg_basfile')" value='${vwBasFileDetailPage.bfName}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">模版类型</label>
						</td>
					</tr>
				
					<tr>
						<td align="right">
							<label class="Validform_label">
								模版内容:
							</label>
						</td>
						<td class="value" >
						  	 	<textarea id="bfdContent" style="height:auto;width:95%;" class="inputxt" rows="6" name="bfdContent"  ignore="ignore" >${vwBasFileDetailPage.bfdContent}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">模版内容</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
