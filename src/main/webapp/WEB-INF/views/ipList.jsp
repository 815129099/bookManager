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
    <title>访问记录</title>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=10" />
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <link href="style/css/style20160105.css" rel="stylesheet">
    <link href="http://apps.bdimg.com/libs/fontawesome/4.2.0/css/font-awesome.min.css" rel="stylesheet"/>
   	<link rel="stylesheet" href="style/css/table.css">
   	<link rel="stylesheet" href="style/css/bootstrap.min.css">
	  <link rel="stylesheet" href="style/css/jquery.datetimepicker.css">
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
				<div class="widget">
					<!--查询条件-->
					<div class="well">
						<form class="form-inline" role="form" id="query">
						<shiro:hasAnyRoles name="admin">
						<input type="hidden" id="role" value="admin">
						</shiro:hasAnyRoles>
							<div class="form-group" style="margin-right:10px">
								<label>ip:</label>
								<input type="text" class="form-control" name="ipNumber" id="ipNumber" maxlength="128" placeholder="ip">
							</div>

							<div class="form-group" style="margin-right:10px">
								<label>工号:</label>
							<input type="text" class="form-control" name="geNumber" id="geNumber" maxlength="128" placeholder="工号">
							</div>

							<div class="form-group" style="margin-right:10px">
								<label>开始时间:</label>
								<div class="input-group">
									<input type="text" class="form-control" name="begin" id="begin" style="width:120px" autocomplete="off" placeholder="开始时间">
									<span class="input-group-addon" id="beginTime"><i class="fa fa-times"></i></span>
								</div>
							</div>
							<div class="form-group" style="margin-right:10px">
								<label>结束时间:</label>
								<div class="input-group">
									<input type="text" class="form-control" name="end" id="end"  autocomplete="off"  style="width:120px" placeholder="结束时间">
									<span class="input-group-addon" id="endTime"><i class="fa fa-times"></i></span>
								</div>
							</div>
							<div class="form-group">
							<button type="button" class="btn btn-warning" id="querybtn">查询</button>
							</div>
						</form>

					</div>


					<!-- /well -->
				</div>

				<!--/数据表格-->

					<table class="table table-striped table-bordered table-hover" id="userTable">
						<thead>
							<tr>

							<th>#</th>
							<th>ip</th>
							<th>工号</th>
							<th>开始时间</th>
							<th>结束时间</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
							<tfoot>
								<tr>
									<td colspan="5">
									<div id="total" class="pull-left" style="padding-top:20px;padding-left:10px">&nbsp;</div>
									<div class="pull-right">
			                           <ul class="pagination" id="pagination"></ul>
			                        </div>
									</td>
								</tr>
							</tfoot>
						</table>


		</div>





</div>
</div>
</div>
    </div>
      <script src="style/js/jquery.1.10.1.min.js"></script>
        <script src="style/js/bootstrap.min.js"></script>
        <script type="text/javascript"  src="style/lib/layui/layui.js" ></script>
        <script type="text/javascript" src="style/js/admin.js"></script>
        <script src="style/js/service.ddlist.jquery.min.js"></script>

     <!-- 表单验证 -->
        <script src="style/js/validate/jquery.validate.min.js"></script>
        <script src="style/js/validate/additional-methods.js"></script>
         <script type="text/javascript" src="style/js/jquery.dataTables.min.js"></script>
    <!-- 表格 -->
        <script type="text/javascript" src="style/js/common1.js"></script>
     <!-- 分页 -->
        <script src="style/js/jqPaginator/jqPaginator.min.js"></script>
	<!--时间插件-->
	<script src="style/js/dateformat.js"></script>
	<script src="style/js/jquery.datetimepicker1.js"></script>
    <script type="text/javascript">

    //表单验证
    $.validator.setDefaults({
        debug: true
    });

    $(document).ready(function(){
    	//查询全部用户列表
		listAccess();
		$('#begin').datetimepicker({format:"Y-m-d",timepicker:true});
		$('#end').datetimepicker({format:"Y-m-d",timepicker:true});
    $("#chkAll").click(function(){
    	chkAll("chkAll","chk");
    });

		$("#beginTime").click(function(){
			$("#begin").val("");
		});
		$("#endTime").click(function(){
			$("#end").val("");
		});

    	//查询功能
    	$("#querybtn").click(function(){
			listAccess();
    	});

        //导出功能
            	$("#querybtn1").click(function(){
            		window.location.href='exportBook.do';
            	});



    });


    	function listAccess(){
    	//查询条件
    	var ipNumber = $("form#query #ipNumber").val();
    	var geNumber = $("form#query #geNumber").val();
    	var beginTime = $("form#query #begin").val();
		var endTime = $("form#query #end").val();
    	//获取用户列表
    	$.post('listAccess.do',
    	       {"ipNumber":ipNumber,
    	       "geNumber":geNumber,
    	       "beginTime":beginTime,
				"endTime":endTime
    	       },
    	       function(response){
    	    	 //生成结果列表
    			 initDataTable("userTable", 5, new Array("ipNumber","geNumber","beginTime","endTime"), response.page,
    						"listAccess.do",{"ipNumber":ipNumber,
    						    "geNumber":geNumber,
							 "beginTime":beginTime,
							 "endTime":endTime
    					       }, false, true, true, true,false,
    					       "<shiro:hasPermission name='admin'>"+"<a href='javascript:void(0)' title='删除' id='del' style='padding-right:20px' onclick='delBook(this)'><i class='fa fa-trash'></i></a>"+
    					       "<a href='javascript:void(0)' title='查看'  style='padding-right:20px' onclick='preview(this)'><i class='fa fa-wrench'></i></a>"+"</shiro:hasPermission>",
    					       "id"
    			 );
    	    	 //设置查询条件
    			 $("form#query #ipNumber").val(ipNumber);
    			 $("form#query #geNumber").val(geNumber);
    			 $("form#query #begin").val(beginTime);
				   $("form#query #end").val(endTime);
    	       }
    	);
    	}


    </script>
  </body>

</html>