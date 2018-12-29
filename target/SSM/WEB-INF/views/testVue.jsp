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
<link rel="alternate" media="handheld"  />
<!-- end 云适配 -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>学习VUE</title>
<script src="style/js/vue.js" type="text/javascript"></script>
<script src="https://cdn.staticfile.org/vue-resource/1.5.1/vue-resource.min.js"></script>
<!--<script src="https://cdn.staticfile.org/vue/2.4.2/vue.min.js"></script>-->
</head>
<% Cookie c=new Cookie("email","");
           c.setMaxAge(600);
           response.addCookie(c);
           %>
<body>
<div id="box">
<input v-model="message" placeholder="编辑我……">
  <p>消息是: {{ message }}</p>
	<input type="button" @click="post()" value="点我异步获取数据(Get)">
</div>

<div id="app-2">
  <span v-bind:title="message">
    鼠标悬停几秒钟查看此处动态绑定的提示信息！
  </span>
</div>

<div id="app-3">
    <div v-if="seen">你能看到我了</div>
</div>

<div id="app-4">
    <ul>
        <li v-for="list in list">
            {{list.text}}
        </li>
    </ul>
</div>

<div id="app-5">
    <p>{{message}}</p>
    <button v-on:click="reverseMessage">逆转消息</button>
</div>

<div id="app-6">
    <input type="text" v-model="message"/>
    {{message}}
</div>

<div id="app-7">
    <ol>
        <item v-for="i in list"
                v-bind:todo="i"
                v-bind:key="i.id">
        </item>
    </ol>
</div>

<div id="app-8">
    <p>{{message}}</p>
</div>

<div id="demo">{{ fullName }}</div>
<script type = "text/javascript">

	var box = new Vue({
	    el:'#box',
	    data:{
	        message:"Hello World!",
	    },
	    methods:{
	        post:function(){
	            //发送 post 请求
	            this.$http.post('isNameExist.do',{bookName:box.message},{emulateJSON:true}).then(function(res){
                    alert(res.body.tip);
                },function(res){
                    console.log(res.tip);
                });
	        }
	    }
	});

var app2 = new Vue({
  el: '#app-2',
  data: {
    message: '页面加载于 ' + new Date().toLocaleString()
  }
})

var app3 = new Vue({
    el:'#app-3',
    data:{
        seen:true
    }
})

var app4 = new Vue({
    el:"#app-4",
    data:{
    list:[
        { text:'kitty'},
        { text:'hello world'},
        { text:'heiehi'}
        ]
    }
})

var app5 = new Vue({
    el:"#app-5",
    data:{ message : 'hello Vue.js'},
    methods:{
        reverseMessage:function(){
            this.message = this.message.split(' ').reverse().join(' ')
        }
    }
})

var app6 = new Vue({
    el:"#app-6",
    data:{
        message:"hello vue.js"
    }
})

Vue.component('item',{
    props:['todo'],
    template:'<li> {{todo.text}} </li>'
})

var app7 = new Vue({
    el:"#app-7",
    data:{
        list:[
        {id: 0,text:'蔬菜'},
        {id: 1,text:"水果"},
        {id: 2,text:"香蕉"}
        ]
    }
})

var app8 = new Vue({
    el:"#app-8",
    data:{
        message:"hello"
    },
    created:function(){
        this.message = "create"
    }
})

var vm = new Vue({
  el: '#demo',
  data: {
    firstName: 'Foo',
    lastName: 'Bar',
    fullName: 'Foo Bar'
  },
  watch: {
    firstName: function (nval,oval) {
      this.fullName = nval +oval+ ' ' + this.lastName
    },
    lastName: function (val) {
      this.fullName = this.firstName + ' ' + val
    }
  }
})
</script></body></html>