<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <%@ include  file="/WEB-INF/jsp/common.jsp"%>
  <%@taglib prefix="fk" uri="/lqy/1803" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>OA办公管理系统-角色管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<link href="${ctx}/fkjava.ico" rel="shortcut icon" type="image/x-icon" />

	
	<script type="text/javascript">
	 function close(){
		   $("#divDialog").dialog('close');
	  }
	  $(function(){

	
 	 if("${message}"){
			$.messager.show({
				title:'提示信息',
				msg:"<font color='red'>${message}</font>",
				showType:'show'
			});
		 
		};	
		 $("#deleteRole").click(function(){
				//获取checked的checkbox
				var boxes = $("input[type='checkbox'][name='box']").filter(":checked");
				if(boxes.length==0){
					  $.messager.alert('提示信息',"请选择需要删除的角色信息！",'warning');
				}else{
					 $.messager.confirm('提示信息', "是否确认删除？", function(r){
				      	   if(r){
				  			 //定义数组用于存储需要删除的用户的id
				  			 
				           	   var array = new Array();
				  			   $.each(boxes,function(){
				  				   array.push(this.value);
				  				   window.location="${ctx}/identity/role/deleteUser.jspx?roleIds="+array.join(",")+
				  				   "&pageIndex=${pageModel.pageIndex}";
				  			   })
				  			}	 
					 });
				}	 
			 });	
		 //引入addUser页面
		 $("#addRole").click(function(){
			$("#divDialog").dialog({
				title : "添加角色", // 标题
				cls : "easyui-dialog", // class
				width : 880, // 宽度
				height : 455, // 高度
				maximizable : true, // 最大化
				minimizable : false, // 最小化
				collapsible : true, // 可伸缩
				modal : true, // 模态窗口
				onClose : function(){ // 关闭窗口
					window.location = "${ctx}/identity/role/showRole.jspx?pageIndex=${pageModel.pageIndex}";
				
				}
			});
			$("#iframe").prop("src","${ctx}/identity/role/showaddRole.jspx")
		 });
		 
	 });
	  function updateRole(roleId){
    	  $("#divDialog").dialog({
				title : "修改角色", // 标题
				cls : "easyui-dialog", // class
				width : 580, // 宽度
				height : 355, // 高度
				maximizable : true, // 最大化
				minimizable : false, // 最小化
				collapsible : true, // 可伸缩
				modal : true, // 模态窗口
				onClose : function(){ // 关闭窗口
					//重新查询角色列表信息
					window.location = "${ctx}/identity/role/showRole.jspx?pageIndex=${pageModel.pageIndex}";
				}
			});
			
			$("#iframe").prop("src","${ctx}/identity/role/showUpdateRole.jspx?roleId="+roleId);   
    		  
         }
	</script>
</head>
<body style="overflow: hidden;width: 100%;height: 100%;padding: 5px;">
	<div>
		<div class="panel panel-primary">
			<!-- 工具按钮区 -->
			<div style="padding-top: 4px;padding-bottom: 4px;">
				<a  id="addRole" class="btn btn-success"><span class="glyphicon glyphicon-plus"></span>&nbsp;添加</a>
				<a  id="deleteRole" class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除</a>
			</div>
			
			<div class="panel-body">
				<table class="table table-bordered" style="float: right;">
					<thead>
					    <tr>
						<th style="text-align: center;"><input type="checkbox" id="checkAll"/></th>
						<th style="text-align: center;">名称</th>
						<th style="text-align: center;">备注</th>
						<th style="text-align: center;">操作</th>
						<th style="text-align: center;">创建日期</th>
						<th style="text-align: center;">创建人</th>
						<th style="text-align: center;">修改日期</th>
						<th style="text-align: center;">修改人</th>
						<th style="text-align: center;">操作</th>
						</tr>
					</thead>
					<c:forEach items="${roles}" var="role" varStatus="stat">
				         <tr align="center" id="dataTr_${stat.index}">
							<td><input type="checkbox" name="box" id="box_${stat.index}" value="${role.id}"/></td>
							<td>${role.name}</td>
							<td>${role.remark}</td>
							<td><span class="label label-success"><a href="${ctx}/identity/role/selectRoleUser.jspx?id=${role.id}" style="color: white;">绑定用户</a></span>&nbsp;
								<span class="label label-info"><a href="${ctx}/identity/popedom/mgrPopedom.jspx?id=${role.id}" style="color: white;">绑定操作</a></span></td>
							<td><fmt:formatDate value="${role.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${role.creater.name}</td>
							<td><fmt:formatDate value="${role.modifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td>${role.modifier.name}</td>
							<td>   <span class="label label-info"><a href="javascript:updateRole('${role.id}')">修改</a></span></td>
						</tr>
		   			 </c:forEach>
				</table>
				<!-- 分页标签区 -->
						    <fk:Pager pageIndex="${pageModel.pageIndex}" pageSize="${pageModel.pageSize}" totalNum="${pageModel.recordCount}" submitUrl="${ctx}/identity/role/showRole.jspx?pageIndex={0}" pageStyle="flickr"></fk:Pager>

			</div>
			
		</div>
	</div>
    <!-- div作为弹出窗口 -->
    <div id="divDialog" style="overflow: hidden;">
		<iframe id="iframe" scrolling="no" frameborder="0" width="100%" height="100%" ></iframe>
	</div>
	
</body>
</html>