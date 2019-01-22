<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <meta charset="utf-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
   <script src="style/js/jquery.1.10.1.min.js"></script>
</head>
<body class="hold-transition login-page">

<input type="text" id="code">
<script>
   window.onload = function(e){
      var code = "";
      var lastTime,nextTime;
      var lastCode,nextCode;

      document.onkeypress = function(e) {
         console.log(e.code+","+e.which);

         nextCode = e.which;
         nextTime = new Date().getTime();

         if(lastCode != null && lastTime != null && nextTime - lastTime <= 30) {
            code += String.fromCharCode(lastCode);
         } else if(lastCode != null && lastTime != null && nextTime - lastTime > 100){
            code = "";
         }

         lastCode = nextCode;
         lastTime = nextTime;
      }

      this.onkeypress = function(e){
         if(e.which == 13){
            $("#code").val($("#code").val() + ";");
         }
      }
   }

</script>
</body>
</html>