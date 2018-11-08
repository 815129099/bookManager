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
<meta charset="utf-8">
<title>工会书籍管理系统</title>
<meta name="keywords" content="工会书籍管理系统">
<meta name="description" content="工会书籍管理系统">
<meta name="viewport" content="width=device-width">
<link href="public/css/base.css" rel="stylesheet" type="text/css">
<link href="public/css/login.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="login">
<form id="loginForm">
	<div class="logo"></div>
    <div class="login_form">
    	<div class="user">
        	<input class="text_value" value="" name="username" type="text" id="username">
            <input class="text_value" value="" name="password" type="password" id="password">
        </div>
        <button class="button" id="submit" type="submit">登录</button>
    </div>
    
    <div id="tip"></div>
    <div class="foot">
    Copyright © 2011-2015  All Rights Reserved. More Templates <a href="http://www.cssmoban.com/" target="_blank" title="模板之家"></a> - Collect from <a href="http://www.cssmoban.com/" title="网页模板" target="_blank"></a>
    </div>
    </form>
</div>
<script src="style/js/jquery.1.10.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="style/js/jquery.lib.min.js"></script>
<script>var basedir='public/ui/';</script>
<script src="public/ui/do.js"></script>
<script src="public/ui/config.js"></script>
<script type="text/javascript">
$(function(){
	//验证表单
	 	$("#loginForm").validate({
	 		/* onkeyup: false,
	    	focusCleanup:true, */
	        rules: {
	    	   	username: {
	    	    	required: true
	    	   	},
	    	   	password: {
	    	    	required: true
	    	   	}
	    	},
	    	messages: {
	    	   	username: {
	    	    	required: "请输入登录邮箱地址"
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
	            	data:{"email":email,"password":password},
	            	url:'UserType.do',
					async:false,
					dataType: 'json',
	            }).done(function(result) {
	                if(result.tip=="lock"){
	                        alert("你的账户已被锁定，请联系管理员解锁（815129099@qq.com）");
	                }else if(result.tip=="no"){
                     		$('#beError').show();
                            $('#beError').html("用户未注册！");
                    }else if(result.tip=="company"){
	            			window.location='comIndex';
					}else if(result.tip=="noCom"){
					        window.location='company';
					}else if(result.tip=="student"){
					        window.location='stuResume';
					}else if(result.tip=="student"){
					        window.location='index';
				    }else if(result.tip=="admin"){
				            window.location='maSystem';
				    }
					else {
						$('#beError').show();
						$('#beError').html("密码错误，请重新输入！");
					}
					$(form).find(":submit").attr("disabled", false);
	            });
	        }
		});
})
</script></body>
</html>
