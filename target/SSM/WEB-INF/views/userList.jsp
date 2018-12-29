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
    <title>用户列表</title>
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
							<div class="form-group" style="margin-right:10px">
								<label>工号:</label>
								<input type="text" class="form-control" name="geNumber" id="geNumber" maxlength="128" placeholder="工号">
							</div>

							<div class="form-group" style="margin-right:10px">
								<label>名称:</label>
							<input type="text" class="form-control" name="geName" id="geName" maxlength="128" placeholder="名称">
							</div>
                            <div class="form-group" style="margin-right:10px">
								<label>修改时间:</label>
								<div class="input-group">
								<input type="text" class="form-control" name="begin" id="begin" style="width:120px"  autocomplete="off"  placeholder="修改时间">
							    <span class="input-group-addon" id="updateTime"><i class="fa fa-times"></i></span>
							    </div>
							</div>
							<div class="form-group" style="margin-right:10px">
                            	<label>状态:</label>
                            	<select name="userState" id="userState" class="form-control">
                            		<option value=""></option>
                            		<option value="有效">有效</option>
                            		<option value="无效">无效</option>
                            	</select>
                            </div>

							<div class="form-group">
							<button type="button" class="btn btn-warning" id="querybtn">查询</button>
							</div>
                            <div class="form-group  pull-right">
							<button type="button" class="btn btn-info" id="querybtn1">导出</button>
							</div>
						</form>
					</div>
					<!-- /well -->
				</div>
 <div class="alert alert-success alert-dismissable">
	            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
		        &times;
	            </button>
	            状态说明<br/>
	                锁定：用户由于拖欠违约金等会被管理员锁定，锁定的用户无法登陆。
            </div>
				<!--/数据表格-->
				<shiro:hasPermission name="admin">
				<ul class="toolbar">
					<li><a href="javascript:void(0)" id="addUser"><i class="fa fa-user"></i><span>添加</span></a></li>
					<li><a href="javascript:void(0)" id="lockUser" onclick='lockUser()'><i class="fa fa-toggle-on"></i><span>锁定</span></a></li>
                    <li><a href="javascript:void(0)" id="clearUser" onclick='clearUser()'><i class="fa fa-toggle-off"></i><span>解锁</span></a></li>
				</ul></shiro:hasPermission>
					<table class="table table-striped table-bordered table-hover" id="userTable">
						<thead>
							<tr>

							<th><shiro:hasAnyRoles name="admin"><input type="checkbox" id="chkAll"/></shiro:hasAnyRoles><shiro:hasAnyRoles name="user">#</shiro:hasAnyRoles></th>
							<th>工号</th>
							<th>名称</th>
							<th>手机号</th>
							<th>状态</th>
							<th>修改时间</th>
							<th>操作</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
							<tfoot>
								<tr>
									<td colspan="7">
									<div id="total" class="pull-left" style="padding-top:20px;padding-left:10px">&nbsp;</div>
									<div class="pull-right">
			                           <ul class="pagination" id="pagination"></ul>
			                        </div>
									</td>
								</tr>
							</tfoot>
						</table>


		</div>

				<!--添加用户-->
				<div class="modal fade" id="addModal" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title" id="myModalLabel">添加员工</h4>
							</div>
					        <form role="form" id="addform" method="post">
							<div class="modal-body">
								<div class="well">
									<div id="sucUpd" class="alert alert-success">
									 <button type="button" class="close" id="close" aria-hidden="true">
                                     &times;
                                     </button>
										<strong>添加成功！</strong>
									</div>
									<div id="failUpd" class="alert alert-warning">
									 <button type="button" class="close" id="close"   aria-hidden="true">
                                      &times;
                                     </button>
										<strong>添加失败！</strong>
									</div>

									<div class="alert alert-danger hide" id="tipError" style='color: white'>&nbsp;</div>
                                        <div class="form-group" style="margin-right: 10px" id="bId">
											<label>工号:</label> <input type="text"
												class="form-control" name="geNumber" id="geNumber" placeholder="工号">
										</div>
										<div class="form-group" style="margin-right: 10px">
											<label>名称:</label> <input type="text"
												class="form-control" name="geName" id="geName" placeholder="名称">
										</div>
										<div class="form-group" style="margin-right: 10px">
                                        	<label>邮箱:</label> <input type="text"
                                        		class="form-control" name="email" id="email" placeholder="邮箱">
                                        </div>
										<div class="form-group" style="margin-right: 10px">
                                        	<label>密码:</label> <input type="password"
                                        		class="form-control" name="password" id="password" placeholder="密码">
                                        </div>
                                        <div class="form-group" style="margin-right: 10px">
                                             <label>验证密码:</label> <input type="password"
                                                  class="form-control" name="rePassword" id="rePassword" placeholder="再次输入密码">
                                        </div>
										<div class="form-group" style="margin-right: 10px">
											<label>手机号:</label> <input type="text"
												class="form-control" name="phone" id="phone" placeholder="手机号">
										</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal" id="cancel">取消</button>
								<button type="submit" class="btn btn-warning" id="addBtn">添加</button>
							</div>
                         </form>
						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal -->
				</div>
				<!-- /添加用户-->

                <!--修改用户-->
				<div class="modal fade" id="updateModal" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
							<!--
								<button type="button" class="close" aria-hidden="true">&times;</button>
								-->
								<h4 class="modal-title" id="myModalLabel">修改用户信息</h4>
							</div>
					        <form role="form" id="updateform">
							<div class="modal-body">
								<div class="well">
									<div id="sucUpd" class="alert alert-success">
									 <button type="button" class="close" id="close" aria-hidden="true">
                                     &times;
                                     </button>
										<strong>修改成功！</strong>
									</div>
									<div id="failUpd" class="alert alert-warning">
									 <button type="button" class="close" id="close"   aria-hidden="true">
                                      &times;
                                     </button>
										<strong>修改失败！</strong>
									</div>

									<div class="alert alert-danger hide" id="tipError" style='color: white'>&nbsp;</div>
										<div class="form-group" style="margin-right: 10px">
											<label>工号:</label> <input type="text"
												class="form-control" name="geNumber" id="geNumber" placeholder="工号" readonly>
										</div>
										<div class="form-group" style="margin-right: 10px">
											<label>手机号码:</label> <input type="text"
												class="form-control" name="phone" id="phone" placeholder="手机号码">
										</div>
										<div class="form-group" style="margin-right: 10px">
                                        	<label>新密码:</label> <input type="password"
                                        		class="form-control" name="password" id="password" placeholder="新密码">
                                        </div>
                                        <div class="form-group" style="margin-right: 10px">
                                            <label>验证密码:</label> <input type="password"
                                                class="form-control" name="rePassword" id="rePassword" placeholder="再次输入密码">
                                        </div>
										<div class="form-group" style="margin-right: 10px">
											<label>状态:</label>
											<select name="userState" id="userState" class="form-control">
											    <option value="有效">有效</option>
												<option value="无效">无效</option>
											</select>
										</div>

								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal" id="cancelbtn">取消</button>
								<button type="submit" class="btn btn-warning" id="addBtn">修改</button>
							</div>
                         </form>
						</div>
						<!-- /.modal-content -->
					</div>
					<!-- /.modal -->
				</div>
				<!-- 修改用户-->
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
    //时间控件初始化
    	$('#begin').datetimepicker({format:"Y-m-d",timepicker:false});
    	listUser();
    $("#chkAll").click(function(){
    	chkAll("chkAll","chk");
    });

     //时间查询条件清空
            	$("#updateTime").click(function(){
            		$("#begin").val("");
            	});

    	//查询功能
    	$("#querybtn").click(function(){
    		listUser();
    	});
    	$("#querybtn1").click(function(){
    		window.location.href='exportUser.do';
    	});

    	//添加用户
            	$("a#addUser").click(function(){

            	    $("#addform #geNumber").val("");
            		$("#addform #geName").val("");
            		$("#addform #phone").val("");
            		$("#addform #email").val("");
            		$("label.error").remove();

            		$("div#addModal #sucUpd").hide();
            		$("div#addModal #failUpd").hide();

            		$("#addModal").modal('show');

            		//验证工号是否已用
            		$("#addform #geNumber").blur(function(){

            			$.post("isNumberExist.do",{"geNumber":$("#addform #geNumber").val()},function(response){
            			$("#addform #geNumber").parent().find("label.error").remove();
                            if(response.tip=="error"){
                            	$("#addform #geNumber").parent().append("<label class='error'>该工号已存在</label>");
                            	$("#addform #geNumber").focus();
            				}
            			});
            		});



            		//设置表单验证
            		$("#addform").validate({
            			  //onfocusout:false,
            			  onkeyup:false,
            		      rules:{
            		    	  geNumber:{required:true,rangelength:[7,7]},
            		    	  geName:{required:true},
            		    	  password:{required:true,rangelength:[6,10]},
            		    	  rePassword:{required:true,equalTo:"#password"},
            		    	  phone:{required:true,isMobile:true},
            		    	  email:{required:true,email:true},
            		      },
            		      messages:{
            		    	  geNumber:{required:"工号不能为空<br/>",rangelength:"请输入7位员工工号<br/>"},
            		    	  geName:{required:"名称不能为空<br/>"},
            		    	  password:{required:"密码不能为空<br/>",rangelength:"请输入6~10位有效密码<br/>"},
            		    	  rePassword:{required:"验证密码不能为空<br/>",equalTo:"密码不一致<br/>"},
            		    	  phone:{required:"手机号不能为空<br/>",isMobile:"请输入正确的手机号"},
            		    	  email:{required:"邮箱不能为空<br/>",isMobile:"请输入正确的邮箱"}
            		      },
            		      submitHandler:function(){
            					if(!$("#addform").valid()){
            						$("div#addModal #sucUpd").hide();
            						$("div#addModal #failUpd").hide();
            					}
            					else{
            					$.ajax({
            					        url:"User.do",
            					        type:"POST",
            					        data:{"geNumber":$("#addform #geNumber").val(),
                                              "geName":$("#addform #geName").val(),
                                              "password":$("#addform #rePassword").val(),
                                              "phone":$("#addform #phone").val(),
                                              "email":$("#addform #email").val()},
                                        success:function(response){
                                                if(response.tip=="success"){
                                                      $("div#addModal #sucUpd").show();
                                                      $("div#addModal #failUpd").hide();
                                                      //关闭窗口后刷新列表
                                                      $("#addform #cancel").click(function(){listUser();});
                                                }
                                                 else{
                                                       $("div#addModal #failUpd").show();
                                                       $("div#addModal #sucUpd").hide();
                                                 }
                                        }});

            				}
            		      }
            		  });
            	});

    });

    //查看用户
                    	function preview(obj){
                    			var id =  $(obj).parent("td").attr("id");
                    			var geNumber = $(obj).parent("td").siblings("td").eq(1).html();
                    			window.location="allegeList.do?geNumber="+geNumber;
                    	}

    	function listUser(){
    	//查询条件
    	var geNumber = $("form#query #geNumber").val();
    	var geName = $("form#query #geName").val();
    	var userState = $("form#query #userState").val();
    	var updateTime = $("form#query #begin").val();

            	//获取用户列表
            	$.post('listUser.do',
            	       {"geNumber":geNumber,
            	       "geName":geName,
            	       "userState":userState,
            	       "updateTime":updateTime
            	       },
    	       function(response){
    	    	 //生成结果列表
    			 initDataTable("userTable", 7, new Array("geNumber","geName","phone","userState","updateTime"), response.page,
    						"listUser.do",  {"geNumber":geNumber,
    						    "geName":geName,
    						    "userState":userState
    					       }, true, true, false, true,true,
    					       "<shiro:hasAnyRoles name='admin'>"+"<a href='javascript:void(0)' id='update' title='修改' style='padding-right:20px' onclick='updateUser(this)'><i class='fa fa-edit'></i></a></shiro:hasAnyRoles>"+
    					       "<shiro:hasPermission name='admin'>"+"<a href='javascript:void(0)' title='删除' id='del' style='padding-right:20px' onclick='delUser(this)'><i class='fa fa-trash'></i></a>"+
    					       "<a href='javascript:void(0)' title='查看'  style='padding-right:20px' onclick='preview(this)'><i class='fa fa-wrench'></i></a>"+"</shiro:hasPermission>",
    					       "id"
    			 );
    	    	 //设置查询条件
    			 $("form#query #geNumber").val(geNumber);
    			 $("form#query #geName").val(geName);
    			 $("form#query #userState").val(userState);
    	       }
    	);
    	}



    //修改
    	function updateUser(obj){
    		//初始化模态窗口
    		var id = $(obj).parent("td").attr("id");
    		var geNumber = $(obj).parent("td").siblings("td").eq(1).html();
    		$("#updateform #geNumber").val(geNumber);
    		$("#updateform #phone").val("");
    		$("#updateform #password").val("");
    		$("#updateform #rePassword").val("");
            $("#updateform #userState").val("");
    		$("label.error").remove();
    		$("div#updateModal #sucUpd").hide();
    		$("div#updateModal #failUpd").hide();
            $("#updateModal").modal('show');
    		//设置表单验证
    		 $("#updateform").validate({
    			  onfocusout:false,
    			  onkeyup:false,
    		      rules:{
    		    	  phone:{required:true,isMobile:true},
    		    	  password:{required:true,rangelength:[6,10]},
    		    	  rePassword:{required:true,equalTo:"#updateform #password"},
                      userState:{required:true}
    		      },
    		      messages:{
    		    	  phone:{required:"手机号不能为空<br/>",isMobile:"请输入正确的手机号<br/>"},
    		    	  password:{required:"新密码不能为空<br/>",rangelength:"请输入6~10位有效密码<br/>"},
    		    	  rePassword:{required:"验证密码不能为空<br/>",equalTo:"密码不一致<br/>"},
                      userState:{required:"用户状态不能为空<br/>"}
    		      }
    		    });

    		$("#updateform").submit(function(){
    			if(!$("#updateform").valid()){
    				$("div#updateModal #sucUpd").hide();
    				$("div#updateModal #failUpd").hide();
    			}
    			else{
    			var geNumber = $("#updateform #geNumber").val();
    			var phone = $("#updateform #phone").val();
    			var password = $("#updateform #password").val();
    			var userState = $("#updateform #userState").val();
    			var data = {"geNumber":geNumber,"phone":phone,"password":password,"userState":userState};
    			    $.ajax({
    			            url:"User.do",
    			            type:"PUT",
    			            data:JSON.stringify(data),
    			            contentType:"application/json",
    			            success:function(response){
    			                    if(response.tip=="success"){
                                    $("div#updateModal #sucUpd").show();
                                    $("div#updateModal #failUpd").hide();
                                    $("#updateform #phone").val("");
                                    $("#updateform #password").val("");
                                    $("#updateform #repassword").val("");
                                    $("#updateform #userState").val("");
                                    listUser();
                            }else{
                                     $("div#resetModal #failUpd").show();
                                     $("div#resetModal #sucUpd").hide();
                            }}
    			            })
    			            }
    			            });
    			            }

    	    	//删除用户
            	function delUser(obj){
            	var state = $(obj).parent("td").siblings("td").eq(4).html();
            	if(state=="有效"){
            	    alert("有效状态的用户不可删除！！！");
            	}else{
                    if(confirm("是否删除该用户")){
            			var geNumber = $(obj).parent("td").siblings("td").eq(1).html();
                        var data = {"geNumber":geNumber};
            			$.ajax({
            			        url:"User.do",
            			        type:"DELETE",
            			        data:JSON.stringify(data),
            			        contentType:"application/json",
            			        success:function(response){
                                        if(response.tip=="success"){
                                             alert("删除成功");
                                             listUser();
                                        }else if(response.tip=="error"){
                                             alert("删除失败!");
                                        }}
            			        });
            		}
            	}

            	}



             function lockUser(){
                        	var row,id;
                        	var num = 0;
                        	var arr = new Array();
                        	 $("input[type='checkbox']").each(function(){
                        		 if($(this).is(":checked"))
                                  {
                        			 row = $(this).parent("td").parent("tr");
                        			 var flag = row.find("td").eq(4).html();
                        			 if(flag!="无效"){
                        			 id = row.find("td #update").parents("td").attr("id");
                                                 			 if(id!=undefined){
                                                 			  arr[num] = id;
                                                 			  num++;
                                                 			 }
                        			 }else{
                        			 alert("用户"+row.find("td").eq(2).html()+"已被锁定，请勿重复提交！")
                        			 }

                                  }
                                });

                        	 if(num==0){
                        	 alert("请选择记录");
                        	 }else{
                                $.ajax({
                                        url:"lockUser.do",
                                        type:"post",
                                        data:{arr:arr},
                                        traditional: true,
                                        success:function(result){
                                            if(result.tip=="success"){
                                                alert("锁定成功");
                                            }else{
                                            alert("锁定失败");
                                            }
                                            listUser();
                                        }
                                });
                        	 }
                        	 $("input:checkbox").removeAttr("checked");
                        }

                        function clearUser(){
                        	var row,id;
                        	var num = 0;
                        	var arr = new Array();
                        	 $("input[type='checkbox']").each(function(){
                        		 if($(this).is(":checked"))
                                  {
                        			 row = $(this).parent("td").parent("tr");
                        			 var flag = row.find("td").eq(4).html();
                        			 if(flag!="有效"){
                        			 id = row.find("td #update").parents("td").attr("id");
                                                 			 if(id!=undefined){
                                                 			  arr[num] = id;
                                                 			  num++;
                                                 			 }
                        			 }else{
                        			 alert("用户"+row.find("td").eq(3).html()+"已解锁，请不要重复提交！");
                        			 }

                                  }
                                });

                        	 if(num==0){
                        	 alert("请选择记录");
                        	 }else{
                                $.ajax({
                                        url:"clearUser.do",
                                        type:"post",
                                        data:{arr:arr},
                                        traditional: true,
                                        success:function(result){
                                            if(result.tip=="success"){
                                                alert("修改成功");
                                            }else{
                                            alert("修改失败");
                                            }
                                            listUser();
                                        }
                                });
                        	 }
                        	 $("input:checkbox").removeAttr("checked");}
    </script>
  </body>

</html>