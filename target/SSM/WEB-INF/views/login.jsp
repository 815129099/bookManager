 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
           <%@ page isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script id="allmobilize" charset="utf-8" src="style/js/allmobilize.min.js"></script>
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate" media="handheld"  />
<!-- end 云适配 -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>大学生兼职平台-登录</title>
<meta property="qc:admins" content="23635710066417756375" />
<meta content="大学生兼职平台-登录" name="description">
<meta content="大学生兼职平台-登录" name="keywords">

<meta name="baidu-site-verification" content="QIQ6KC1oZ6" />

<!-- <div class="web_root"  style="display:none">h</div> -->
<script type="text/javascript">
var ctx = "h";
console.log(1);
</script>
<link rel="Shortcut Icon" href="h/images/favicon.ico">
<link rel="stylesheet" type="text/css" href="style/css/style.css"/>

<script src="style/js/jquery.1.10.1.min.js" type="text/javascript"></script>

<script type="text/javascript" src="style/js/jquery.lib.min.js"></script>
<script type="text/javascript" src="style/js/core.min.js"></script>


<script type="text/javascript">
var youdao_conv_id = 271546;
</script>
<script type="text/javascript" src="style/js/conv.js"></script>
</head>
<% Cookie c=new Cookie("email","");
           c.setMaxAge(600);
           response.addCookie(c);
           %>
<body id="login_bg">
	<div class="login_wrapper">
		<div class="login_header">

            <div id="cloud_s"><img src="style/images/cloud_s.png" width="81" height="52" alt="cloud" /></div>
            <div id="cloud_m"><img src="style/images/cloud_m.png" width="136" height="95"  alt="cloud" /></div>
        </div>

    	<input type="hidden" id="resubmitToken" value="" />
		 <div class="login_box">
        	<form id="loginForm">
				<input type="text" id="email" name="email" value="" tabindex="1" placeholder="请输入用户名" />
			  	<input type="password" id="password" name="password" tabindex="2" placeholder="请输入密码" />
				<span class="error" style="display:none;" id="beError"></span>
			    <label class="fl" for="remember"><input type="checkbox" id="remember" value="" checked="checked" name="autoLogin" /> 记住我</label>
			    <a href="reset" class="fr" target="_blank">忘记密码？</a>
				<input type="submit" id="submitLogin" value="登 &nbsp; &nbsp; 录" />

			</form>
			<div class="login_right">
			<br/>
			<br/>
				<div>还没有帐号？</div>
				<a  href="register"  class="registor_now">立即注册</a>
			</div>
        </div>
        <div class="login_box_btm"></div>
    </div>

<script type="text/javascript">
$(function(){
	//验证表单
	 	$("#loginForm").validate({
	 		/* onkeyup: false,
	    	focusCleanup:true, */
	        rules: {
	    	   	email: {
	    	    	required: true
	    	   	},
	    	   	password: {
	    	    	required: true
	    	   	}
	    	},
	    	messages: {
	    	   	email: {
	    	    	required: "请输入用户名"
	    	   	},
	    	   	password: {
	    	    	required: "请输入密码"
	    	   	}
	    	},
	    	submitHandler:function(form){
	    		if($('#remember').prop("checked")){
	      			$('#remember').val(1);
	      		}else{
	      			$('#remember').val(null);
	      		}
	    		var email = $('#email').val();
	    		var password = $('#password').val();
	    		var remember = $('#remember').val();

	    		$(form).find(":submit").attr("disabled", true);
	            $.ajax({
	            	type:'POST',
	            	data:{"geNumber":email,"password":password},
	            	url:'UserType.do',
					async:false,
					dataType: 'json',
	            }).done(function(result) {
	                if(result.tip=="error"){
	                        window.location='error';
	                }else if(result.tip=="success"){
                     	window.location='maSystem';
                    }
					$(form).find(":submit").attr("disabled", false);
	            });
	        }
		});
})
</script>
</body>
</html>