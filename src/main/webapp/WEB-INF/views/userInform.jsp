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
    <meta charset="UTF-8">
    <title>我的通知</title>
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width,initial-scale=1" />
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
							<div class="form-group" style="margin-right:10px">
								<label>标题:</label>
								<input type="text" class="form-control" name="title" id="title" maxlength="128" placeholder="标题">
							</div>

                            <div class="form-group" style="margin-right:10px">
								<label>创建时间:</label>
								<div class="input-group">
								<input type="text" class="form-control" name="begin" id="begin"  autocomplete="off" style="width:120px" placeholder="创建时间">
							    <span class="input-group-addon" id="createTime"><i class="fa fa-times"></i></span>
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
							<th>标题</th>
							<th>创建时间</th>
							<th>修改时间</th>
							<th>操作</th>
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




				<div class="col-lg-1"></div>
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
        <script type="text/javascript" src="style/js/common3.js"></script>
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
    //时间控件初始化
        	$('#begin').datetimepicker({format:"Y-m-d",timepicker:false});
    	//查询全部用户列表
    	listInform();
    $("#chkAll").click(function(){
    	chkAll("chkAll","chk");
    });
    //时间查询条件清空
    $("#createTime").click(function(){
          $("#begin").val("");
          });
    	//查询功能
    	$("#querybtn").click(function(){
    		listInform();
    	});



    });

    //查看用户
                    	function preview(obj){
                    			var id =  $(obj).parent("td").attr("id");
                    			var geNumber = $(obj).parent("td").siblings("td").eq(1).html();
                    			window.location="allegeList.do?geNumber="+geNumber;
                    	}

    	function listInform(){
    	//查询条件
    	var title = $("form#query #title").val();
    	var createTime = $("form#query #begin").val();
    	//获取用户列表
    	$.post('listInform.do',
    	       {"title":title,
    	       "createTime":createTime
    	       },
    	       function(response){
    	    	 //生成结果列表
    			 initDataTable("userTable", 5, new Array("title","createTime","updateTime"), response.page,
    						"listInform.do",  {"title":title
    					       }, false, true, true, true,true,
    					       "<a href='javascript:void(0)' title='删除' id='del' style='padding-right:20px' onclick='delInform(this)'><i class='fa fa-trash'></i></a>",
    					       "id"
    			 );
    	    	 //设置查询条件
    			 $("form#query #title").val(title);
    	       }
    	);
    	}

//删除书籍
            	function delInform(obj){
            		if(confirm("是否删除该条记录")){
            			var id =  $(obj).parent("td").attr("id");
            			var data = {"id":id};
            			$.ajax({
            			        url:"Inform.do",
            			        type:"DELETE",
            			        data:JSON.stringify(data),
            			        contentType:"application/json",
            			        success:function(response){
                                        if(response.tip=="success"){
                                              alert("删除成功");
                                              listInform();
                                        }else if(response.tip=="error"){
                                              alert("删除失败!");
                                        }}
            			        });
            		}
            	}


    </script>
  </body>

</html>