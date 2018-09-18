<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <%@ include  file="/WEB-INF/jsp/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>办公管理系统-菜单管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<meta name="Keywords" content="keyword1,keyword2,keyword3" />
<meta name="Description" content="网页信息的描述" />
<meta name="Author" content="fkjava.org" />
<meta name="Copyright" content="All Rights Reserved." />
<link href="${ctx}/fkjava.ico" rel="shortcut icon" type="image/x-icon" />


<link rel="stylesheet" type="text/css" href="${ctx}/resources/dtree/dtree.css"/>
<script type="text/javascript" src="${ctx}/resources/dtree/dtree.js"></script>

<script type="text/javascript">
/* d.add(0,-1,'My example tree');
d.add(1,0,'Node 1','example01.html');
d.add(2,0,'Node 2','example01.html');
d.add(3,1,'Node 1.1','example01.html');
d.add(4,0,'Node 3','example01.html');
d.add(5,3,'Node 1.1.1','example01.html');
d.add(6,5,'Node 1.1.1.1','example01.html');
d.add(7,0,'Node 4','example01.html');
d.add(8,1,'Node 1.2','example01.html');
d.add(9,0,'My Pictures','example01.html','Pictures I\'ve taken over the years','','','img/imgfolder.gif');
d.add(10,9,'The trip to Iceland','example01.html','Pictures of Gullfoss and Geysir');
d.add(11,9,'Mom\'s birthday','example01.html');
d.add(12,0,'Recycle Bin','example01.html','','','img/trash.gif'); 
"${ctx}/identity/module/ajaxLoadModule.jspx" 测试用*/
     $(function(){
    	 //生成dtree 
    	 referchDtree()
     })
     function referchDtree() {
	 d = new dTree('d','${ctx}');
	 d.add(0,-1,'系统模块树');
	 d.add("1",0,'全部',"${ctx}/identity/module/getModulesByParent.jspx" ,"全部","rightFrame");
	 $.ajax({
		type:"post",
		url:"${ctx}/identity/module/ajaxLoadModule.jspx",
        datatype:"json",
		 success:function(obj){
			 $.each(obj,function(){
				 d.add(this.id,this.pid,this.name,this.id.length==12 ?"" : "${ctx}/identity/module/getModulesByParent.jspx?pCode="+this.id,this.name,"rightFrame");
				
			 })
			 $("#tree").html(d.toString());
		 },error:function(){
	    		$.messager.alert("提示信息","网络异常！","warning");
	    	}
	 });
		
	};

</script>
</head>
    <body class="easyui-layout" style="width:100%;height:100%;">
			<div id="tree" data-options="region:'west'" title="菜单模块树" style="width:20%;padding:10px">
				 <!-- 展示所有的模块树  -->
				
			</div>
			
			<div data-options="region:'center'" title="模块菜单"  >
			     <!-- 展示当前模块下的子模块  -->
			     <iframe src="${ctx}/identity/module/getModulesByParent.jspx" name="rightFrame"  frameborder="0" id="sonModules"  width="100%" height="100%" ></iframe>
			</div>
	</body>
</html>
