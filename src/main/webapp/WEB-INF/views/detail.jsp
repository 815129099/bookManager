<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
           <%@ page isELIgnored="false" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="style/css/bootstrap.min.css">
<script type="text/javascript" src="style/js/jquery.1.10.1.min.js"></script>
<script type="text/javascript" src="style/js/bootstrap.min.js"></script>
<title>我的信息</title>
</head>
<body>


<div style="margin-top:20px;margin-left:20px;margin-right:300px;">
<div class="panel panel-info" >
	<div class="panel-heading">
		<h3 class="panel-title">我的信息</h3>
	</div>
	<div class="panel-body">
		<shiro:principal/>   ,你好
	</div>
</div>

<div class="panel panel-default">
    <div class="panel-body">
        修改密码
    </div>
</div>

<div class="panel panel-default">
    <div class="panel-body">
       <form class="form-horizontal" role="form" id="userFrom">
         <div class="form-group">
           <label for="firstname" class="col-sm-2 control-label">旧密码</label>
           <div class="col-sm-6">
             <input type="password" class="form-control" name="oldPassword" id="oldPassword" placeholder="请输入旧密码">
           </div>
         </div>
         <div class="form-group">
           <label for="lastname" class="col-sm-2 control-label">新密码</label>
           <div class="col-sm-6">
             <input type="password" class="form-control" name="newPassword" id="newPassword" placeholder="请输入新密码">
           </div>
         </div>
         <div class="form-group">
             <label for="lastname" class="col-sm-2 control-label">确认密码</label>
             <div class="col-sm-6">
                <input type="password" class="form-control" name="rePassword" id="rePassword" placeholder="请再次输入新密码">
             </div>
         </div>
         <div class="form-group">
           <div class="col-sm-offset-4 col-sm-6">
             <button type="button" id="submit" class="btn btn-info">修改</button>
           </div>
         </div>
       </form>
    </div>
</div>
</div>
        <script src="style/js/validate/jquery.validate.min.js"></script>
        <script src="style/js/validate/additional-methods.js"></script>
         <script type="text/javascript" src="style/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	  $("#submit").click(function(){
	   var a = $("#oldPassword").val();
       var b = $("#newPassword").val();
       var c = $("#rePassword").val();
              if(a == '' || a == undefined || a == null){
                  alert("旧密码不能为空");
              }else if(b == '' || b == undefined || b == null){
                   alert("新密码不能为空");
              }else if(c == '' || c == undefined || c == null){
                   alert("验证密码不能为空");
              }else if(b!=c){
                   alert("新密码和验证密码不一致");
              }else{
              var data ={"password":a,"newPassword":b};
                    $.ajax({
                         url:'password.do',
                    	 type:'POST',
                    	 data:data,
                    	 async:false,
                    	 dataType: 'json',
                    	 success:function(result){
                    	        if(result.tip=="success"){
                    	            alert("密码修改成功，请重新登录");
                    	            window.location='logout';
                    	        }else if(result.tip=="error"){
                    	            alert("密码修改失败");
                    	        }
                    	    }
                    });
              }
	  });


})
</script>
</body>
</html>