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
  <title>登录</title>
  <meta http-equiv="X-UA-Compatible" content="IE=10" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <link href='https://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="style/css/normalize.min.css">
  <link rel="stylesheet" href="style/css/style1.css">
</head>

<body>

  <div class="form">
      
      <ul class="tab-group">
        <li class="tab active"><a href="#signup">图书管理系统</a></li>
        <li class="tab"><a href="#login">考核系统</a></li>
      </ul>
      
      <div class="tab-content">
        <div id="signup">   
          <h1>Sign Up for Free</h1>
          
          <form action="/" method="post">


          <div class="field-wrap">
            <label>
              请输入工号<span class="req">*</span>
            </label>
            <input name="geNumber" id="geNumber" type="text" autocomplete="off" required  />
          </div>
          
          <div class="field-wrap">
            <label>
              请输入密码<span class="req">*</span>
            </label>
            <input name="password" id="password" type="password" autocomplete="off" required />
          </div>
          
          <button type="submit" class="button button-block"/>登录</button>
          
          </form>

        </div>
        
        <div id="login">   
          <h1>Welcome Back!</h1>
          
          <form action="/" method="post">
          
            <div class="field-wrap">
            <label>
              请输入工号<span class="req">*</span>
            </label>
            <input type="text"required autocomplete="off"/>
          </div>
          
          <div class="field-wrap">
            <label>
              请输入密码<span class="req">*</span>
            </label>
            <input type="password"required autocomplete="off"/>
          </div>
          
          <p class="forgot"><a href="#">Forgot Password?</a></p>
          <button class="button button-block"/>登录</button>
          </form>

        </div>
        
      </div><!-- tab-content -->
      
</div> <!-- /form -->
  <script src='style/js/jquery.1.12.4.min.js'></script>
  <script  src="style/js/login.js"></script>

</body>
</html>
