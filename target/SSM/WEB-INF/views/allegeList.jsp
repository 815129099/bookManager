<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
           <%@ page isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="UTF-8">
    <title>借阅记录</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <link href="style/css/style20160105.css" rel="stylesheet">
    <link href="http://apps.bdimg.com/libs/fontawesome/4.2.0/css/font-awesome.min.css" rel="stylesheet"/>
   	<link rel="stylesheet" href="style/css/table.css">
   	<link rel="stylesheet" href="style/css/bootstrap.min.css">
   <head/>
  <body>
    <div class="x-body">
<div class="container" style="padding-top:30px;height:100%;weight:80%;">
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
								<label>工号:</label>
								<input type="text" class="form-control" name="geNumber" id="geNumber" maxlength="128" placeholder="工号">
							</div>
                            <div class="form-group" style="margin-right:10px">
								<label>书籍代码:</label>
								<input type="text" class="form-control" name="bookId" id="bookId" maxlength="128" placeholder="书籍代码">
							</div>
							<div class="form-group" style="margin-right:10px">
								<label>状态:</label>
								<select name="state" id="state" class="form-control">
								  <option value=""></option>
								  <option value="已归还">已归还</option>
								  <option value="未归还">未归还</option>
								</select>
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
							<th>工号</th>
							<th>书籍代码</th>
							<th>手机号</th>
							<th>借阅时间</th>
							<th>归还时间</th>
							<th>描述</th>
							<th>操作</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
							<tfoot>
								<tr>
									<td colspan="8">
									<div id="total" class="pull-left" style="padding-top:20px;padding-left:10px">&nbsp;</div>
									<div class="pull-right">
			                           <ul class="pagination" id="pagination"></ul>
			                        </div>
									</td>
								</tr>
							</tfoot>
						</table>


		</div>


				<!--还书登记-->
				<div class="modal fade" id="dealModal" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title" id="myModalLabel">还书登记</h4>
							</div>
					        <form role="form" id="dealForm" method="post">
							<div class="modal-body">
								<div class="well">
                                    <div id="sucUpd" class="alert alert-success">
									 <button type="button" class="close" id="close" aria-hidden="true">
                                     &times;
                                     </button>
										<strong>还书成功！</strong>
									</div>
									<div id="failUpd" class="alert alert-warning">
									 <button type="button" class="close" id="close"   aria-hidden="true">
                                      &times;
                                     </button>
										<strong>还书失败！</strong>
									</div>
									<div class="alert alert-danger hide" id="tipError" style='color: white'>&nbsp;</div>
                                        <div class="form-group" style="margin-right: 10px">
											<label>书籍代码:</label> <input type="text"
												class="form-control" name="bookId" id="bookId" readonly>
										</div>
										<div class="form-group" style="margin-right: 10px">
                                        	<label>书籍描述:</label><textarea rows="3"
                                            class="form-control" name="description" id="description" placeholder="请描述书籍是否受损，完好可不填"></textarea>
                                        </div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal" id="cancel">取消</button>
								<button type="submit" class="btn btn-warning" id="addBtn">提交</button>
							</div>
                         </form>
						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal -->
				</div>
				<div class="col-lg-1"></div>
</div>
</div>
</div>
    </div>

     <script src="style/js/jquery.1.10.1.min.js"></script>
        <script src="style/js/bootstrap.min.js"></script>
        <script type="text/javascript"  src="style/lib/layui/layui.js" ></script>
        <script type="text/javascript" src="style/js/xadmin.js"></script>
        <script src="style/js/service.ddlist.jquery.min.js"></script>

     <!-- 表单验证 -->
        <script src="style/js/validate/jquery.validate.min.js"></script>
        <script src="style/js/validate/additional-methods.js"></script>

     <!-- 表格 -->
         <script type="text/javascript" src="style/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="style/js/common1.js"></script>
     <!-- 分页 -->
        <script src="style/js/jqPaginator/jqPaginator.min.js"></script>

    <script type="text/javascript">
    //表单验证
    $.validator.setDefaults({
        debug: true
    });

    $(document).ready(function(){
    	//查询全部用户列表
    	listAllege();
    $("#chkAll").click(function(){
    	chkAll("chkAll","chk");
    });
    	//查询功能
    	$("#querybtn").click(function(){
    		listAllege();
    	});

    });
    	function listAllege(){
    	//查询条件
    	var geNumber = $("form#query #geNumber").val();
    	var bookId = $("form#query #bookId").val();
    	var state = $("form#query #state").val();

    	//借阅列表
    	$.post('listRecord.do',
    	       {"geNumber":geNumber,
    		    "bookId":bookId,
    		    "state":state
    	       },
    	       function(response){
    	    	 //生成结果列表
    			 initDataTable("userTable", 7, new Array("geNumber","bookId","phone","lendTime","backTime","description"), response.page,
    						"listRecord.do",  {"geNumber":geNumber,
    						    "bookId":bookId,
    						    "state":state
    					       }, false, true, true, true,true,
    					       "<a href='javascript:void(0)' title='处理' style='padding-right:20px' onclick='dealAllege(this)'><i class='fa fa-edit'></i></a>"+
    					       "<a href='javascript:void(0)' title='删除' id='del' style='padding-right:20px' onclick='delAllege(this)'><i class='fa fa-trash'></i></a>",
    					       "id"
    			 );
    	    	 //设置查询条件
    			 $("form#query #geNumber").val(geNumber);
    			 $("form#query #state").val(state);
    			 $("form#query #bookId").val(bookId);
    	       }
    	);
    	}

    	//还书登记
    	function dealAllege(obj){
    	//防止表单多次提交参数
    	var flag = 1;
    	//var id =  $(obj).parent("td").attr("id");
    	var lendTime = $(obj).parent("td").siblings("td").eq(5).html();
    	var bookId = $(obj).parent("td").siblings("td").eq(2).html();
    	var geNumber = $(obj).parent("td").siblings("td").eq(1).html();
    	if(lendTime!=null & lendTime!=""){
    	    alert("该书已归还！");
    	}else{
    	   $("#dealForm #bookId").val(bookId)
           $("label.error").remove();
           $("div#dealModal #sucUpd").hide();
           $("div#dealModal #failUpd").hide();
           $("#dealModal").modal('show');


            $("#dealForm").submit(function(){
             if(flag == 1){
                 flag = 0;
                 $("#dealForm").find(":submit").attr("disabled",true);
                     if(!$("#dealForm").valid()){
                     $("div#dealModal #sucUpd").hide();
                     $("div#dealModal #failUpd").hide();
                 }else{
                      $.ajax({ type : "post",
                      url : "backBook.do",
                      data : {"bookId":bookId,"geNumber":geNumber,"description":$("#dealForm #description").val()},
                      async : false,
                      success : function(response){
                           if(response.tip=="success"){
                                $("div#dealModal #sucUpd").show();
                                $("div#dealModal #failUpd").hide();
                           }else{
                                 $("div#dealModal #failUpd").show();
                                 $("div#dealModal #sucUpd").hide();
                           }
                                 listAllege();
                      }
                      });
                 return false;
                 }
             }
    	});
    	 $("#dealForm").find(":submit").attr("disabled",false);
    	}}


    	//删除
    	function delAllege(obj){
    		if(confirm("是否删除该申述")){
    			var id =  $(obj).parent("td").attr("id");
    			$.post("delAllege.do",{"id":id},function(response){
                				if(response.tip==true){
                					alert("删除成功");
                					listAllege();
                				}
                				else if(response.tip==false){
                					alert("删除失败!");
                				}
                			});
    		}
    	}





    </script>
  </body>

</html>