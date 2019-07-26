<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
 <head>
      <title>HTML</title>
    <!-- 导入JQ 文件 -->
    <script src="${pageContext.request.contextPath}/static/js/jquery-3.4.1.js" type="text/javascript" charset="utf-8"></script>
    <!-- Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap.css" />
    <!-- Bootstrap 核心 JavaScript 文件 -->
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#username").keyup(function(){
                var len=$(this).val().length;
                if(len<=6){
                    $("#spanUsername").html("<font color='red' size='3' class='error'>用户名太短</font>");
                }else{
                    $.ajax({
                        url:"${pageContext.request.contextPath}/checkUser", // 请求路径
                        type:"POST" , //请求方式
                        data:{"username":$("#username").val()},
                        success:function (exists) {
                            // console.log(exists)
                            if(exists==="true"){
                                $("#spanUsername").html("<font color='green' size='3'>用户名可用</font>");
                            }else{
                                $("#spanUsername").html("<font color='red' size='3' class='error'>用户名已存在</font>");
                            }
                        },
                        error:function (){
                            alert("服务器出错...")
                        },
                        dataType:"text"
                    });
                }
            });
            $("#password").keyup(function(){
                var len=$(this).val().length;
                if(len<=6){
                    $("#spanPassword").html("<font color='red' size='3' class='error'>密码太短</font>")
                }else if($(this).val()!=$("#confirmPassword").val()&&$("#confirmPassword").val().length>0){
                    $("#spanPassword").html("<font color='red' size='3' class='error'>两次密码不相同</font>");
                    $("#spanConfirmPassword").html("<font color='red' size='3' class='error'>两次密码不相同</font>")
                }else if($(this).val()==$("#confirmPassword").val()){
                    $("#spanPassword").html("<font color='green' size='3'>密码可用</font>");
                    $("#spanConfirmPassword").html("<font color='green' size='3' class='error'>密码相同可用</font>");
                }else{
                    $("#spanPassword").html("<font color='green' size='3'>密码可用</font>")
                }
            });
            $("#confirmPassword").keyup(function(){
                if($(this).val()==$("#password").val()&&$("#password").val().length>6){
                    $("#spanPassword").html("<font color='green' size='3'>密码可用</font>")
                    $("#spanConfirmPassword").html("<font color='green' size='3'>密码相同可用</font>");
                }else{
                    $("#spanPassword").html("<font color='red' size='3' class='error'>两次密码不相同</font>")
                    $("#spanConfirmPassword").html("<font color='red' size='3' class='error'>两次密码不相同</font>");
                }
            })
        })
    </script>
    <style>
        .txt{
            color: #2AABD2;
            font-size:large;
        }
        /*img {*/
        /*    float:left;*/
        /*}*/
        #error{
            float: left;
            color: red;
            font-size: large;
            position:relative;
            left:90px;
        }
        #regist{
            float: left;
        }

    </style>
</head>
<body background="${pageContext.request.contextPath}/static/images/background.jpeg">
<div  id="zhuye" style="position:absolute; top:50%; left:50%; width:600px; height:240px; margin-left:-240px; margin-top:-120px;">
    <form class="form-horizontal " action="${pageContext.request.contextPath}/regist"  method="post">
        <div class="form-group">
            <label for="username" class="col-sm-2 control-label"><font class="txt">用户名</font></label>
            <div class="col-sm-10 form-inline">
                <input type="text" class="form-control check" name="username" id="username" style="padding-right: 100px;">
                <span id="spanUsername" ></span>
            </div>
        </div>
        <div class="form-group">
            <label for="password" class="col-sm-2 control-label"><font class="txt">密码</font></label>
            <div class="col-sm-10 form-inline">
                <input type="password" class="form-control check" name="password" id="password"  style="padding-right: 100px;">
                <span id="spanPassword"></span>
            </div>
        </div>
        <div class="form-group">
            <label for="confirmPassword" class="col-sm-2 control-label"><font class="txt">确认密码</font></label>
            <div class="col-sm-10 form-inline">
                <input type="password" class="form-control check" name="confirmPassword" id="confirmPassword" style="padding-right: 100px;">
                <span id="spanConfirmPassword"></span>
            </div>
        </div><div id="error">${error}</div><%request.getSession().removeAttribute("error");%>
        <br>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default" id="regist"><font class="txt">注册</font></button>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <button type="button" class="btn btn-default" onclick="location='index.jsp'"><font class="txt">取消</font></button>
            </div>

        </div>

    </form>
</div>
</body>
</html>
