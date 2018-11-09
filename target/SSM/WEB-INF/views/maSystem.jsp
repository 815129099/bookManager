<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
           <%@ page isELIgnored="false" %>
           <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>后台系统管理</title>
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />

    <link rel="stylesheet" href="style/css/font.css">
	<link rel="stylesheet" href="style/css/xadmin.css">
	<link rel="stylesheet" href="style/css/datatables.min.css">


</head>
<body>
    <!-- 顶部开始 -->
    <div class="container">
        <div class="logo"><a href="maSystem">图书管理系统</a></div>
        <div class="left_open">
            <i title="展开左侧栏" class="iconfont">&#xe699;</i>
        </div>
        <ul class="layui-nav right" lay-filter="">

          <li class="layui-nav-item">
          <shiro:authenticated>
            <a href="javascript:;"><shiro:principal/></a>
            <dl class="layui-nav-child"> <!-- 二级菜单 -->
              <dd><a href="logout">切换帐号</a></dd>
              <dd><a href="logout">退出</a></dd>
            </dl>
          </shiro:authenticated>

             <shiro:guest><a href="javascript:;">游客</a></shiro:guest>
          </li>


          <li class="layui-nav-item to-index"><a href="login">登录</a></li>
        </ul>
        
    </div>
    <!-- 顶部结束 -->
    <!-- 中部开始 -->
     <!-- 左侧菜单开始 -->
    <div class="left-nav">
      <div id="side-nav">
        <ul id="nav">
            <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe6b8;</i>
                    <cite>书籍管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="meList">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>书籍列表</cite>
                        </a>
                    </li >
                    <shiro:authenticated>
                    <li>
                        <a _href="allegeList">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>借阅记录</cite>
                        </a>
                    </li>
                    </shiro:authenticated>
                </ul>
            </li>
        <shiro:hasAnyRoles name="admin">
            <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe726;</i>
                    <cite>员工管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="userList">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>员工列表</cite>
                        </a>
                    </li >
                </ul>
            </li>
            </shiro:hasAnyRoles>
        </ul>
      </div>
    </div>
    <!-- <div class="x-slide_left"></div> -->
    <!-- 左侧菜单结束 -->
    <!-- 右侧主体开始 -->
    <div class="page-content">
        <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
          <ul class="layui-tab-title">
            <li>我的桌面</li>
          </ul>
          <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <iframe src='meList' frameborder="0" scrolling="yes" class="x-iframe"></iframe>
            </div>
          </div>
        </div>
    </div>
    <div class="page-content-bg"></div>
    <!-- 右侧主体结束 -->
    <!-- 中部结束 -->
    <!-- 底部开始 -->
    <div class="footer">
        <div class="copyright">Copyright ©2017 All Rights Reserved</div>  
    </div>
    <!-- 底部结束 -->
     <script type="text/javascript" src="style/js/jquery.1.10.1.min.js"></script>
        <script type="text/javascript"  src="style/lib/layui/layui.js" ></script>
        <script type="text/javascript" src="style/js/xadmin.js"></script>

        <script src="style/js/service.ddlist.jquery.min.js"></script>
        <script src="style/js/validate/jquery.validate.min.js"></script>
        <script src="style/js/validate/additional-methods.js"></script>
         <script type="text/javascript" src="style/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="style/js/common1.js"></script>
        <script src="style/js/jqPaginator/jqPaginator.min.js"></script>
</body>
</html>