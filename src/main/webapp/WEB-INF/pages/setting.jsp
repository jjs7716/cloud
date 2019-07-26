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
                $("#submit").click(function () {
                    $.ajax({
                        url:"${pageContext.request.contextPath}/setting",
                        type:"POST",
                        data:{"username":$("#username").val(),"oldPassword":$("#oldPassword").val(),"newPassword":$("#newPassword").val()},
                        success:function (result) {
                            // true表示可以修改
                            if(result==="true"){
                                $("#settingForm").submit;
                                window.location.href="${pageContext.request.contextPath}/main";
                            }else{
                                $("#error").html("原密码错误!")
                            }
                        },
                        error:function (){
                            alert("服务器出错...")
                        },
                        dataType:"text"
                    })
                })
            $("#oldPassword").keydown(function () {
                $("#error").html("")
            })
        })
    </script>
    <style>
        .txt{
            color: #2AABD2;
            font-size:large;
        }

        #error{
            font-size: large;
            color: red;
        }
    </style>
</head>
<body>
<div  id="zhuye" style="position:absolute; top:50%; left:50%; width:480px; height:240px; margin-left:-240px; margin-top:-120px;">
    <form class="form-horizontal" id="settingForm" action="${pageContext.request.contextPath}/setting">
        <div class="form-group">
            <label class="col-sm-2 control-label">用户名</label>
            <div class="col-sm-10">
                <p class="form-control-static" id="username">${user.username}</p>
            </div>
        </div>
        <div class="form-group">
            <label for="oldPassword" class="col-sm-2 control-label">原密码</label>
            <div class="col-sm-10 form-inline">
                <input type="password" class="form-control" id="oldPassword" placeholder="原密码" style="padding-right: 100px;">
                <span id="error"></span>
            </div>
        </div>
        <div class="form-group">
            <label for="newPassword" class="col-sm-2 control-label">新密码</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="newPassword" placeholder="新密码" style="width: 258px">
            </div>
        </div>
    </form>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="button" class="btn btn-default" id="submit"><font class="txt">提交</font></button>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <button type="button" class="btn btn-default" onclick="location='${pageContext.request.contextPath}/main'"><font class="txt">返回</font></button>
            </div>

        </div>

    </form>
</div>
</body>
</html>
