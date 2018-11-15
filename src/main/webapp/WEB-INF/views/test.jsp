 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
           <%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>书籍管理系统-登录</title>
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<meta name="viewport" content="width=device-width">
<link href="public/css/base.css" rel="stylesheet" type="text/css">
<link href="public/css/login.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="login">
<form id="form">
	<div class="logo"></div>
    <div class="login_form">
    	<div class="user">
        	<input class="text_value" value="" name="geNumber" type="text" id="geNumber" placeholder="请输入工号">
            <input class="text_value" value="" name="password" type="password" id="password" placeholder="请输入密码">
        </div>
        <button class="button" id="submit" type="button">登录</button>
    </div>

    <div id="tip" style="color:red"></div>
    <div class="foot">

    </div>
    </form>
</div>
<script src="style/js/jquery.1.10.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
    $("#submit").click(function(){
        var a = $("#geNumber").val();
        var b = $("#password").val();
        if(a == '' || a == undefined || a == null){
            $("#tip").html("工号不能为空");
        }else if(b == '' || b == undefined || b == null){
             $("#tip").html("密码不能为空");
        }else{
            $("#tip").html("");
            $.ajax({
                    url:"UserType.do",
                    type:"POST",
                    data:{"geNumber":a,"password":b},
                    async:false,
                    dataType: 'json',
                    success:function(result){
                            if(result.tip=="error"){
                            	 window.location='error.do';
                            }else if(result.tip=="success"){
                                 window.location='maSystem.do';
                            }},
                    error:function(){window.location='error';}
                    })
        }
    });

});

</script>
</body>
</html>
