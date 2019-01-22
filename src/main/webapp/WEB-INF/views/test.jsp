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
<meta name="viewport" content="width=device-width,initial-scale=1" />
<link href="public/css/base.css" rel="stylesheet" type="text/css">
<link href="public/css/login.css" rel="stylesheet" type="text/css">
<style>
  .placeholder{color:#a9a9a9;}
</style>
</head>
<body onkeydown="onLogin();">
<div class="login">
<form id="form">
	<div class="logo"></div>
    <div class="login_form">
    	<div class="user">
        	<input class="text_value inp" value="" name="geNumber" type="text" id="geNumber" placeholder="请输入工号">
            <input class="text_value inp" value="" name="password" type="password" id="password" placeholder="请输入密码">
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
        login();
    });
 });

if (!!window.ActiveXObject || "ActiveXObject" in window)  { //判断在ie下执行

 $(function(){
jQuery('[placeholder]').focus(function() {
  var input = jQuery(this);
  if (input.val() == input.attr('placeholder')) {
    input.val('');
    input.removeClass('placeholder');
  }
}).blur(function() {
  var input = jQuery(this);
  if (input.val() == '' || input.val() == input.attr('placeholder')) {
    input.addClass('placeholder');
    input.val(input.attr('placeholder'));
  }
}).blur().parents('form').submit(function() {
  jQuery(this).find('[placeholder]').each(function() {
    var input = jQuery(this);
    if (input.val() == input.attr('placeholder')) {
      input.val('');
    }
  })
});
    })
}




    function login(){
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
    }

    function onLogin(){
     if(window.event.keyCode == 13){
     login();
     }
     }


</script>
</body>
</html>
