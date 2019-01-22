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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${inform.title}</title>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta http-equiv-"expires" content-"60">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=10" />
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <link href="style/css/style20160105.css" rel="stylesheet">
    <link href="http://apps.bdimg.com/libs/fontawesome/4.2.0/css/font-awesome.min.css" rel="stylesheet"/>
   	<link rel="stylesheet" href="style/css/table.css">
   	<link rel="stylesheet" href="style/css/bootstrap.min.css">
</head>
<body>
<div class="container">
<div style="margin-top:30px;text-align:center">
<h2>${inform.title}</h2>
</div>
<div style="margin-top:30px;text-align:center"><p>创建日期：${inform.createTime}</p></div>

<div style="margin-top:30px;text-align:center"><p>${inform.detail}</p></div>
</div>

</body>
</html>
