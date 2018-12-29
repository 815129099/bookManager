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
    <title>借阅记录</title>
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <meta http-equiv-"expires" content-"60">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=10" />
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <link href="style/css/style20160105.css" rel="stylesheet">
    <link href="http://apps.bdimg.com/libs/fontawesome/4.2.0/css/font-awesome.min.css" rel="stylesheet"/>
   	<link rel="stylesheet" href="style/css/table.css">
   	<link rel="stylesheet" href="style/css/bootstrap.min.css">
   	<link rel="stylesheet" href="style/css/jquery.datetimepicker.css">
   <head/>
  <body>
    <div class="x-body">
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
                        <shiro:hasAnyRoles name="admin">
                        <form class="form-inline" role="form" id="query1">
						<input type="hidden" id="role" value="admin">
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
							<div class="form-group" style="margin-right:10px">
								<label>状态:</label>
								<select name="state" id="state" class="form-control">
								  <option value=""></option>
								  <option value="申请">申请</option>
								  <option value="借阅">借阅</option>
								  <option value="归还">归还</option>
								  <option value="退回">退回</option>
								</select>
							</div>
							<div class="form-group">
							<button type="button" class="btn btn-info" id="querybtn1">导出</button>
							</div>
						</form>
						</shiro:hasAnyRoles>
					<br/>
						<form class="form-inline" role="form" id="query">
						<shiro:hasAnyRoles name="admin">
							<div class="form-group" style="margin-right:10px">
								<label>工号:</label>
								<input type="text" class="form-control" name="geNumber" id="geNumber" maxlength="128" placeholder="工号">
							</div>
						</shiro:hasAnyRoles>
                            <div class="form-group" style="margin-right:10px">
								<label>书籍代码:</label>
								<input type="text" class="form-control" name="bookId" id="bookId" maxlength="128" placeholder="书籍代码">
							</div>
                            <div class="form-group" style="margin-right:10px">
								<label>申请时间:</label>
								<div class="input-group">
								<input type="text" class="form-control" name="apply" id="apply" style="width:180px" autocomplete="off" placeholder="申请时间">
							    <span class="input-group-addon" id="applyTime"><i class="fa fa-times"></i></span>
							    </div>
							</div>
                            <div class="form-group" style="margin-right:10px">
								<label>借阅时间:</label>
								<div class="input-group">
								<input type="text" class="form-control" name="lend" id="lend"  autocomplete="off"  style="width:180px" placeholder="借阅时间">
							    <span class="input-group-addon" id="lendTime"><i class="fa fa-times"></i></span>
							    </div>
							</div>
							<div class="form-group" style="margin-right:10px">
								<label>状态:</label>
								<select name="state" id="state" class="form-control">
								  <option value=""></option>
								  <option value="申请">申请</option>
								  <option value="借阅">借阅</option>
								  <option value="归还">归还</option>
								  <option value="退回">退回</option>
								</select>
							</div>
							<div class="form-group">
							<button type="button" class="btn btn-warning" id="querybtn">查询</button>
							</div>
						</form>
					</div>
					<!-- /well -->
				</div>

            <div class="alert alert-info alert-dismissable">
	            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
		        &times;
	            </button>
	            状态说明：<br/>
	                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申请：用户提出借书的需求。借阅：管理员批准了用户的申请。退回：管理员没有批准用户的申请。归还：用户已归还借阅书籍。
	                <br/>下列表格颜色说明：
	                <br/>
	                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-danger">红色表示已逾期</button>
	                <button type="button" class="btn btn-success">绿色表示距离书籍归还日还有0~3天</button>
	                <button type="button" class="btn btn-warning">黄色表示距离书籍归还日还有4~7天</button>
            </div>

				<!--/数据表格-->
            <shiro:hasPermission name="admin">
				<ul class="toolbar">
					<li><a href="javascript:void(0)" id="passRecord" onclick='passRecord()'><i class="fa fa-toggle-on"></i><span>批准</span></a></li>
                    <li><a href="javascript:void(0)" id="backRecord" onclick='backRecord()'><i class="fa fa-toggle-off"></i><span>退回</span></a></li>
				</ul></shiro:hasPermission>
					<table class="table table-striped table-bordered table-hover" id="userTable">
						<thead>
							<tr>
							<th><shiro:hasAnyRoles name="admin"><input type="checkbox" id="chkAll"/></shiro:hasAnyRoles><shiro:hasAnyRoles name="user">#</shiro:hasAnyRoles></th>
							<th>工号</th>
							<th>书籍代码</th>
							<th>书籍名称</th>
							<th>状态</th>
							<th>申请时间</th>
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
									<td colspan="10">
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
        <script type="text/javascript" src="style/js/admin.js"></script>
        <script src="style/js/service.ddlist.jquery.min.js"></script>

     <!-- 表单验证 -->
        <script src="style/js/validate/jquery.validate.min.js"></script>
        <script src="style/js/validate/additional-methods.js"></script>

     <!-- 表格 -->
         <script type="text/javascript" src="style/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="style/js/common4.js"></script>
     <!-- 分页 -->
        <script src="style/js/jqPaginator/jqPaginator.min.js"></script>
  <!--时间插件-->
             <script src="style/js/dateformat.js"></script>
             <script src="style/js/jquery.datetimepicker1.js"></script>
     <!--layer弹窗-->

    <script type="text/javascript">
    var preGeNumber;
    var preBookId;
    //表单验证
    $.validator.setDefaults({
        debug: true
    });

    $(document).ready(function(){

    	//查询全部用户列表
    	listAllege();
 //时间控件初始化
    	$('#apply').datetimepicker({format:"Y-m-d:h",timepicker:true});
    	$('#lend').datetimepicker({format:"Y-m-d H",timepicker:true});
    	$('#begin').datetimepicker({format:"Y-m-d",timepicker:false});
        $('#end').datetimepicker({format:"Y-m-d",timepicker:false});
        $("#chkAll").click(function(){
    	chkAll("chkAll","chk");
    });


     //时间查询条件清空
            	$("#applyTime").click(function(){
            		$("#apply").val("");
            	});
            	$("#lendTime").click(function(){
                     $("#lend").val("");
                });
            	$("#beginTime").click(function(){
            		$("#begin").val("");
            	});
            	$("#endTime").click(function(){
                     $("#end").val("");
                });
      preGeNumber = '${geNumber}';
        preBookId = '${bookId}';
    	//查询功能
    	$("#querybtn").click(function(){
    		listAllege();
    	});

    	$("#querybtn1").click(function(){
    		exportAllege();
    	});

    });

    function exportAllege(){
    var begin = $("form#query1 #begin").val();
    var end = $("form#query1 #end").val();
    var userState = $("form#query1 #state").val();
    window.location.href='exportAllege.do?begin='+begin+'&end='+end+'&userState='+userState;
    }

    	function listAllege(){
    	//查询条件
    	var geNumber;
    	if(preGeNumber == '' || preGeNumber == undefined || preGeNumber == null){
    	    geNumber = $("form#query #geNumber").val();
    	}else{
    	    geNumber = preGeNumber;
    	}

    	var bookId;
    	if(preBookId == '' || preBookId == undefined || preBookId == null){
            bookId = $("form#query #bookId").val();
        }else{
            bookId = preBookId;
        }
    	var state = $("form#query #state").val();
        var applyTime = $("form#query #apply").val();
        var lendTime = $("form#query #lend").val();
    	//借阅列表
    	$.post('listRecord.do',
    	       {"geNumber":geNumber,
    		    "bookId":bookId,
    		    "state":state,
    		    "applyTime":applyTime,
    		    "lendTime":lendTime
    	       },
    	       function(response){
    	    	 //生成结果列表
    			 initDataTable("userTable", 8, new Array("geNumber","bookId","bookName","state","applyTime","lendTime","backTime","description"), response.page,
    						"listRecord.do",  {"geNumber":geNumber,
    						    "bookId":bookId,
    						    "state":state
    					       }, true, true, false, true,true,
    					       "<shiro:hasAnyRoles name='admin'>"+"<a href='javascript:void(0)' title='处理' id='update' style='padding-right:20px' onclick='dealAllege(this)'><i class='fa fa-edit'></i></a></shiro:hasAnyRoles>"+
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

    	//还书登记 修改记录
    	function dealAllege(obj){
    	//防止表单多次提交参数
    	var flag = 1;
    	//var id =  $(obj).parent("td").attr("id");
    	var lendTime = $(obj).parent("td").siblings("td").eq(7).html();
    	var backTime = $(obj).parent("td").siblings("td").eq(6).html();
    	var bookId = $(obj).parent("td").siblings("td").eq(2).html();
    	var geNumber = $(obj).parent("td").siblings("td").eq(1).html();
    	if(lendTime!=null & lendTime!=""){
    	    alert("该书已归还");
    	}else if(backTime==null || backTime == undefined || backTime ==""){
    	    alert("该申请未批准，无法进行还书！");
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
                 var description = $("#dealForm #description").val();
                 var data = {"bookId":bookId,"geNumber":geNumber,"description":description};
                      $.ajax({
                      type : "PUT",
                      url : "Record.do",
                      data : JSON.stringify(data),
                      async : false,
                      contentType:"application/json",
                      success : function(response){
                           if(response.tip=="success"){
                                $("div#dealModal #sucUpd").show();
                                $("div#dealModal #failUpd").hide();
                           }else{
                                 $("div#dealModal #failUpd").show();
                                 $("div#dealModal #sucUpd").hide();
                           }
                                 listAllege();
                                 $("#dealForm #description").val("");
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
    	var lendTime = $(obj).parent("td").siblings("td").eq(6).html();
    	var backTime = $(obj).parent("td").siblings("td").eq(7).html();
    	var applyState = $(obj).parent("td").siblings("td").eq(4).html();
    	if((backTime==null || backTime=="" || backTime == undefined)&&(lendTime!=null && lendTime!="")){
    	alert("该书已借未还，不可删除借书记录");
    	}else if(applyState=="申请"){
            alert("该记录未处理，无法删除!");
    	}else{
    	if(confirm("是否删除该记录")){
                    var id =  parseInt($(obj).parent("td").attr("id"));
                    var data = {"id":id};
                    $.ajax({
                            url:"Record.do",
                            type:"DELETE",
                            data:JSON.stringify(data),
                            contentType:"application/json",
                            success:function(response){
                                    if(response.tip=="success"){
                                          alert("删除成功");
                                          listAllege();
                                    }else if(response.tip=="error"){
                                           alert("删除失败!");
                                    }}
                            });
                }
    	}

    	}



            function passRecord(){
            	var row,id;
            	var num = 0;
            	var arr = new Array();
            	 $("input[type='checkbox']").each(function(){
            		 if($(this).is(":checked"))
                      {
            			 row = $(this).parent("td").parent("tr");
            			 var flag = row.find("td").eq(6).html();
            			 var s = row.find("td").eq(4).html();

            			 if(flag!=null && flag != "" && flag!=undefined){
            			 alert(row.find("td").eq(3).html()+"已批准，请勿重复提交！")

            			 }else if(s=="退回"){
            			  alert("已退回，无法批准！");
            			 }else{
                                id = row.find("td #update").parents("td").attr("id");
                                     			 if(id!=undefined){
                                     			  arr[num] = id;
                                     			  num++;
                                     			 }
            			 }
                      }
                    });

            	 if(num==0){
            	 alert("请选择记录");
            	 }else{
                    $.ajax({
                            url:"pass.do",
                            type:"post",
                            data:{arr:arr},
                            traditional: true,
                            success:function(result){
                                if(result.tip=="success"){
                                    alert("修改成功");
                                }else{
                                alert("修改失败");
                                }
                                listAllege();
                            }
                    });
            	 }
            	 $("input:checkbox").removeAttr("checked");
            }




            function backRecord(){
            	var row,id;
            	var num = 0;
            	var arr = new Array();
            	 $("input[type='checkbox']").each(function(){
            		 if($(this).is(":checked"))
                      {
            			 row = $(this).parent("td").parent("tr");
            			 var flag = row.find("td").eq(6).html();
            			 if(flag==null || flag==undefined || flag==""){
            			 id = row.find("td #update").parents("td").attr("id");
                                     			 if(id!=undefined){
                                     			  arr[num] = id;
                                     			  num++;
                                     			 }
            			 }else{
            			 alert(row.find("td").eq(3).html()+"已批准，无法退回！")
            			 }

                      }
                    });

            	 if(num==0){
            	 alert("请选择记录");
            	 }else{
                    $.ajax({
                            url:"back.do",
                            type:"post",
                            data:{arr:arr},
                            traditional: true,
                            success:function(result){
                                if(result.tip=="success"){
                                    alert("修改成功");
                                }else{
                                alert("修改失败");
                                }
                                listAllege();
                            }
                    });
            	 }
            	 $("input:checkbox").removeAttr("checked");
            }





    </script>
  </body>

</html>