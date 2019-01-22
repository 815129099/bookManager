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
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>系统介绍</title>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=10" />
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <link href="style/css/style20160105.css" rel="stylesheet">
    <link href="http://apps.bdimg.com/libs/fontawesome/4.2.0/css/font-awesome.min.css" rel="stylesheet"/>
   	<link rel="stylesheet" href="style/css/table.css">
   	<link rel="stylesheet" href="style/css/bootstrap.min.css">
  </head>


  <body>
    <div class="x-body" >
    <div class="container" style="padding-top:30px;width:100%;">
	<div class="content">
		<!-- Content wrapper -->
		<div class="wrapper">
			<!--主页-->
			<div>
				<!-- Table with toolbar -->
				<div class="widget" >
					<!--查询条件-->

						<table class="table table-bordered" style="text-align:center">
							<caption style="text-align:center;font-size:larger">系统介绍</caption>
							<tbody>
							<tr>
								<td>系统版本</td>
								<td>1.1</td>
							</tr>
							<tr>
								<td>系统制作</td>
								<td>厦门金鹭特种合金有限公司</td>
							</tr>
							<tr>
								<td>负责人</td>
								<td>刘文祥</td>
							</tr>
							</tbody>
						</table>

					</div>
					<!-- /well -->

		</div>
		</div>
	</div>
	</div>
    </div>
  </body>
</html>