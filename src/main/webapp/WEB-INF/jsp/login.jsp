<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include  file="/WEB-INF/jsp/common.jsp"%>
<!DOCTYPE html> 
<html lang="en"> 
<head> 
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1"> 
    <title>捷途软件--智能办公</title> 
    <link href="${ctx}/css/base.css" rel="stylesheet">
    <link href="${ctx}/css/login.css" rel="stylesheet">

	 <script type="text/javascript">
	 $(function(){
		 $("#vimg").click(function(){
			 $("#vimg").prop("src","${ctx}/createCode.jspx?data="+Math.random());
		 });
		 $("#login_id").click(function(){
			var  userId =$("#userId").val();
			var  passWord =$("#passWord").val();
			var  vcode =$("#vcode").val();
			alert($("#loginForm").serialize());
			console.log(passWord);
			if(!/^[0-9a-zA-Z_]{5,12}$/.test($.trim(userId))){
				 alert("您输入的账号不合法！");
			}else if(!/^[0-9a-zA-Z_]{5,12}$/.test($.trim(passWord))){
				alert("您输入的密码不合法");
			}else if(!/^[0-9a-zA-Z]{4}$/.test($.trim(vcode))){
				 alert("您输入的验证码不合法！"); 
			}else{
				$.ajax({
					 type:"post",
    				 url:"${ctx}/identity/user/ajaxLogin.jspx",
					data:$("#loginForm").serialize(),
					success:function(msg){
						if(!msg){
							window.location=  "${ctx}/main.jspx";
						}else{
							 alert(msg);
    						 //刷新验证码
    						  $("#vimg").trigger("click");
						}
					},error:function(){
						 alert("网络异常！");
					}
					
				});
			}
		 });
	 });
     $(window).keydown(function(event){
         if(event.keyCode == 13){
      	   //触发登录按钮的点击事件
      	    $("#login_id").trigger("click");
         }
   });
	 
		
	 </script>
</head> 
<body>
	<div class="login-hd">
		<div class="left-bg"></div>
		<div class="right-bg"></div>
		<div class="hd-inner">
			<span class="logo"></span>
			<span class="split"></span>
			<span class="sys-name">智能办公平台</span>
		</div>
	</div>
	<div class="login-bd">
		<div class="bd-inner">
			<div class="inner-wrap">
				<div class="lg-zone">
					<div class="lg-box">
						<div class="panel-heading" style="background-color: #11a9e2;">
							<h3 class="panel-title" style="color: #FFFFFF;font-style: italic;">用户登陆</h3>
						</div>
						<form id="loginForm">
						   <div class="form-horizontal" style="padding-top: 20px;padding-bottom: 30px; padding-left: 20px;">
								
								<div class="form-group" style="padding: 5px;">
									<div class="col-md-11">
										<input class="form-control" id="userId" name="userId" type="text" placeholder="账号/邮箱">
									</div>
								</div>
				
								<div class="form-group" style="padding: 5px;">
									<div class="col-md-11">
										<input  class="form-control"  id="passWord" name="passWord" type="password" placeholder="请输入密码">
									</div>
								</div>
				
								<div class="form-group" style="padding: 5px;">
									<div class="col-md-11">
										<div class="input-group">
										<input class="form-control " id="vcode" maxlength="4" name="vcode" type="text" placeholder="验证码">
										<span class="input-group-addon" id="basic-addon2"><img class="check-code" style="cursor:pointer" id="vimg" alt="" src="${ctx}/createCode.jspx"></span>
										</div>
									</div>
								</div>
				
						</div>
							<div class="tips clearfix">
											<label><input type="checkbox" name="remMe" value="checked">记住用户名</label>
											<a href="javascript:;" class="register">忘记密码？</a>
										</div>
							<div class="enter">
								<a href="javascript:;" id="login_id" class="purchaser" >登录</a>
								<a href="javascript:;" class="supplier" onClick="javascript:window.location='main.html'">重 置</a>
							</div>
						</form>
					</div>
				</div>
				<div class="lg-poster"></div>
			</div>
	</div>
	</div>
	<div class="login-ft">
		<div class="ft-inner">
			<div class="about-us">
				<a href="javascript:;">关于我们</a>
				<a href="javascript:;">法律声明</a>
				<a href="javascript:;">服务条款</a>
				<a href="javascript:;">联系方式</a>
			</div>
			<div class="address">
			地址：广州市天河区
			&nbsp;邮编：510000&nbsp;&nbsp;
			分享知识，传递希望&nbsp;版权所有</div>
			<div class="other-info">
			建议使用火狐、谷歌浏览器，不建议使用IE浏览器！</div>
		</div>
	</div>
</body> 
</html>