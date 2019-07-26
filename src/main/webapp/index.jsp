<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
 <head>
    <title>HTML</title>
  <!-- 导入JQ 文件 -->
  <script src="${pageContext.request.contextPath}/static/js/jquery-3.4.1.js" type="text/javascript" charset="utf-8"></script>
  <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap.css" />
  <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
  <script src="${pageContext.request.contextPath}/static/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
  <script>
    // 点击更新验证码图片
    function changeCode() {
      var code=document.getElementById("change");
      code.src="${pageContext.request.contextPath}/code?time="+new Date().getTime();
    }
  </script>
  <style>
    font{
      color: #2AABD2;
      font-size:large;
    }
    img {
      float:right;
    }
    #error{
      float: left;
      color: red;
      font-size: large;
      position:relative;
      left:90px;
    }
    #login{
      float: left;
    }
  </style>
</head>
<body background="${pageContext.request.contextPath}/static/images/background.jpeg">
<div  id="zhuye" style="position:absolute; top:50%; left:50%; width:480px; height:240px; margin-left:-240px; margin-top:-120px;">
  <form class="form-horizontal" action="${pageContext.request.contextPath}/login"  method="post">
    <div class="form-group">
      <label for="username" class="col-sm-2 control-label"><font>登录</font></label>
      <div class="col-sm-10">
        <input type="text" class="form-control" name="username" id="username" >
      </div>
    </div>
    <div class="form-group">
      <label for="inputPassword3" class="col-sm-2 control-label"><font>密码</font></label>
      <div class="col-sm-10">
        <input type="password" class="form-control" name="password" id="inputPassword3">
      </div>
    </div>
    <div class="form-group">
      <label for="loginCode" class="col-sm-2 control-label"><font>验证码</font></label>
      <div class="col-sm-10">
        <input type="text" class="form-control" name="loginCode" id="loginCode" >
      </div>
    </div><div id="error">${error}</div><%request.getSession().removeAttribute("error");%>
    <img src="${pageContext.request.contextPath}/code" id="change" onclick="changeCode()" height="40" width="70" />
    <br>
    <div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
        <button type="submit" class="btn btn-default" id="login"><font>登录</font></button>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-default" id="regist" onclick="location='regist.jsp'"><font>注册</font></button>
      </div>
    </div>


  </form>
</div>
</body>
</html>
