<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>我的网盘</title>
    <!-- Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css" type="text/css"  />
    <!-- 导入JQ 文件 -->
    <script src="${pageContext.request.contextPath}/static/js/jquery-3.4.1.js" type="text/javascript" charset="utf-8"></script>
    <!-- Bootstrap 核心 JavaScript 文件 -->
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.js" type="text/javascript" charset="utf-8"></script>
    <script>
        function batchRecover() {
            document.forms.namedItem("mainId").action="file/batchRecover";
            document.forms.namedItem("mainId").submit();
        }

        function batchDelete() {
            document.forms.namedItem("mainId").action="file/batchDelete";
            document.forms.namedItem("mainId").submit();
        }
        // 复选框
        function checkall(){
            var checkid=document.getElementsByName("did")
            var cid=document.getElementById("checkAll")		//获取第一行选框的状态
            for(var i=0;i<checkid.length;i++){
                checkid[i].checked=cid.checked; //将下面行选框的状态和第一个同步
            }
        }
    </script>
    <style>
        #title{
            width: 100%;
            height: 10%;
            position:relative;
            font-size: 17px;
        }
        td,th{
            vertical-align: bottom;
        }
        #titleUser{
            position:relative;
            float: right;
            right: 20px;
        }
        #titleUl{
            position:relative;
            left: 11%;
            width: 89%;
            top: 50%;
        }
        #titleForm{
            position:relative;
            left: 30%;
            width: 30%;
        }
        #left{
            position:relative;
            width: 9%;
        }
        #main{
            position: absolute;
            width: 89%;
            top: 10%;
            left: 10%;
        }
    </style>
</head>
<body>
<div id="title">
    <img src="${pageContext.request.contextPath}/static/images/title.png" height="55%" width="50%" style="position:absolute; z-index:-1;top: 20%"/>
    <div id="titleUser"><img src="${pageContext.request.contextPath}/static/images/background.jpeg" alt="38*38" class="img-circle" style="width: 38px;height: 38px">&nbsp;&nbsp;${user.username}&nbsp;&nbsp;&nbsp;&nbsp;<a href="">设置</a>&nbsp;&nbsp;<a href="">退出</a></div>

    <ul class="nav nav-pills" id="titleUl">
        <li role="presentation" class="active"><a href="#">回收站</a></li>
    </ul>
    <form class="navbar-form " role="search" id="titleForm">
        <div class="form-group">
            <input type="text" class="form-control" placeholder="搜索你的文件">
        </div>
        <button type="submit" class="btn btn-default">查找</button>
    </form>
</div>
<div id="left" class="btn-group-vertical btn-group-lg" role="group" aria-label="...">
    <button type="button" class="btn btn-default" onclick="location='${pageContext.request.contextPath}/main'">我的文件</button>
    <button type="button" class="btn btn-default" onclick="location='${pageContext.request.contextPath}/file/transfer?transfer=add'">我的好友</button>
    <button type="button" class="btn btn-default" onclick="location='${pageContext.request.contextPath}/file/transfer?transfer=add'">我的分享</button>
    <button type="button" class="btn btn-default" onclick="location='${pageContext.request.contextPath}/recycle'">回收站</button>
</div>
<div id="main">
    <form id="mainId">
    <table class="table table-hover">
        <thead>
        <th width="3%"><input type="checkbox" id="checkAll" onclick="checkall()"></th>
        <th width="50%">文件名</th>
        <th>剩余天数</th>
        <th width="18%">
            <input class="btn btn-success" type="button" onclick="batchRecover()" value="批量恢复">
            <input class="btn btn-danger" type="submit" onclick="batchDelete()" value="批量删除">
        </th>
        </thead>
        <tbody>
        <c:forEach  items="${recycleList}" varStatus="s" var="list">
            <tr>
                <td><input type="checkbox" name="did" value="${list.fid}"></td>
                <td>${list.fileName}</td>
                <td>${list.deleteDay}</td>
                <td width="13%">
                    <input class="btn btn-success" type="button" onclick="location='${pageContext.request.contextPath}/file/recover?fid=${list.fid}'" value="恢复">
                    <input class="btn btn-danger" type="button" onclick="location='${pageContext.request.contextPath}/file/delete?fid=${list.fid}'" value="删除">
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </form>
</div>
</body>
</html>

