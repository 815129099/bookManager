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

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>后台系统管理</title>
	<meta name="viewport" content="width=device-width,initial-scale=1" />
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=10" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />

    <link rel="stylesheet" href="style/css/font.css">
	<link rel="stylesheet" href="style/css/xadmin.css">
	<link rel="stylesheet" href="style/css/datatables.min.css">


</head>
<body>
    <!-- 顶部开始 -->
    <div class="container">
        <div class="logo"><a href="maSystem.do">图书管理系统</a></div>
        <div class="left_open">
            <i title="展开左侧栏" class="iconfont">&#xe661;</i>
        </div>
        <ul class="layui-nav right" lay-filter="">

          <li class="layui-nav-item">
          <shiro:authenticated>
            <a href="javascript:;"><shiro:principal/></a>
            <dl class="layui-nav-child"> <!-- 二级菜单 -->
              <dd><a href="logout.do">切换帐号</a></dd>
              <dd><a href="logout.do">退出</a></dd>
            </dl>
          </shiro:authenticated>

             <shiro:guest><a href="javascript:;">游客</a></shiro:guest>
          </li>


          <li class="layui-nav-item to-index"><a href="login.do">登录</a></li>
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
                    <i class="iconfont">&#xe806;</i>
                    <cite>书籍管理</cite>
                    <i class="iconfont nav_right">&#xe685;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="meList.do">
                            <i class="iconfont">&#xe673;</i>
                            <cite>书籍列表</cite>
                        </a>
                    </li >
                    <shiro:authenticated>
                    <li>
                        <a _href="allegeList.do">
                            <i class="iconfont">&#xe673;</i>
                            <cite>借阅记录</cite>
                        </a>
                    </li>
                    </shiro:authenticated>
                </ul>
            </li>
        <shiro:hasAnyRoles name="admin">
            <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe7ac;</i>
                    <cite>员工管理</cite>
                    <i class="iconfont nav_right">&#xe685;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="userList.do">
                            <i class="iconfont">&#xe673;</i>
                            <cite>员工列表</cite>
                        </a>
                    </li >
                </ul>
            </li>
            </shiro:hasAnyRoles>

             <shiro:hasAnyRoles name="admin,user">
                        <li>
                            <a href="javascript:;">
                                <i class="iconfont">&#xe7a7;</i>
                                <cite>信息管理</cite>
                                <i class="iconfont nav_right">&#xe685;</i>
                            </a>
                            <ul class="sub-menu">
                                <li>
                                    <a _href="userInform.do">
                                        <i class="iconfont">&#xe673;</i>
                                        <cite>我的通知</cite>
                                    </a>
                                </li >
                                <li>
                                    <a _href="accountList.do">
                                        <i class="iconfont">&#xe673;</i>
                                        <cite>缴费记录</cite>
                                    </a>
                                </li >
                                <li>
                                     <a _href="detail.do">
                                          <i class="iconfont">&#xe908;</i>
                                          <cite>修改密码</cite>
                                     </a>
                                </li >
                            </ul>
                        </li>
                        </shiro:hasAnyRoles>

            <shiro:hasAnyRoles name="admin">
                <li>
                    <a href="javascript:;">
                        <i class="iconfont">&#xe7a7;</i>
                        <cite>访问管理</cite>
                        <i class="iconfont nav_right">&#xe685;</i>
                    </a>
                    <ul class="sub-menu">
                        <li>
                            <a _href="ipList.do">
                                <i class="iconfont">&#xe673;</i>
                                <cite>访问记录</cite>
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
                <iframe src='introduce.do' frameborder="0" scrolling="yes" class="x-iframe"></iframe>
            </div>
          </div>
        </div>
    </div>
    <div class="page-content-bg"></div>
    <!-- 右侧主体结束 -->
    <!-- 中部结束 -->
    <!-- 底部开始 -->
    <div class="footer">
        <div class="copyright" style="text-align: center">@厦门金鹭特种合金有限公司 2019</div>
    </div>
    <!-- 底部结束 -->
     <script type="text/javascript" src="style/js/jquery.1.10.1.min.js"></script>
        <script type="text/javascript"  src="style/lib/layui/layui.js" ></script>
        <script type="text/javascript" src="style/js/admin.js"></script>

        <script src="style/js/service.ddlist.jquery.min.js"></script>
        <script src="style/js/validate/jquery.validate.min.js"></script>
        <script src="style/js/validate/additional-methods.js"></script>
         <script type="text/javascript" src="style/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="style/js/common1.js"></script>
        <script src="style/js/jqPaginator/jqPaginator.min.js"></script>
</body>
</html>