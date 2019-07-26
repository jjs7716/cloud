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
        function downFile() {
            document.forms.namedItem("mainId").action="file/downloads";
            document.forms.namedItem("mainId").submit();
        }

        function deleteFile() {
            document.forms.namedItem("mainId").action="file/batchRecycle";
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
        $(document).ready(function() {
            <%--$("#doSearch").click(function () {--%>
            <%--    var sea = $("#search").val();--%>
            <%--    var type="${condition}";--%>
            <%--    console.log(type)--%>
            <%--    var action="${pageContext.request.contextPath}/file/search?name="+sea+"&";--%>
            <%--    if(type==".video"){--%>
            <%--        console.log("video");--%>
            <%--        $("#titleForm").attr("action",action+"type=video")--%>
            <%--    }else if(type==".music"){--%>
            <%--        console.log("music");--%>
            <%--        $("#titleForm").attr("action",action+"type=music")--%>
            <%--    }else if (type==".doc"){--%>
            <%--        console.log("doc");--%>
            <%--        $("#titleForm").attr("action",action+"type=doc")--%>
            <%--    }else if(type==".img"){--%>
            <%--        console.log("img");--%>
            <%--        $("#titleForm").attr("action",action+"type=img")--%>
            <%--    }else{--%>
            <%--        $("#titleForm").attr("action",action+"type=other")--%>
            <%--    }--%>
            <%--    $("#titleForm").submit();--%>
            <%--});--%>
            // 根据网址激活导航条
            var url = window.location;
            $('ul.nav a[href="'+ url +'"]').parent().addClass('active');
            $('div.btn-group-vertical a[href="'+ url +'"]').parent().addClass('active');
            $('ul.nav a').filter(function() {
                return this.href == url;
            }).parent().addClass('active');
            $('div.btn-group-vertical a').filter(function() {
                return this.href == url;
            }).parent().addClass('active');
        });
    </script>
    <style>
        /*input{*/
        /*    position: absolute;*/
        /*    z-index: 2;*/
        /*}*/
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
            position:absolute;
            width: 9%;
        }
        #main{
            position: absolute;
            width: 89%;
            left: 10%;
            top: 10%;
        }
    </style>
</head>
<body>
<div id="title">
    <img src="${pageContext.request.contextPath}/static/images/title.png" height="55%" width="50%" style="position:absolute; z-index:-1;top: 20%"/>
    <div id="titleUser"><img src="${pageContext.request.contextPath}/static/images/background.jpeg" alt="38*38" class="img-circle" style="width: 38px;height: 38px">&nbsp;&nbsp;${user.username}&nbsp;&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/file/transfer?transfer=setting">设置</a>&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/exit">退出</a></div>

    <ul class="nav nav-pills" id="titleUl">
        <li role="presentation"><a href="${pageContext.request.contextPath}/main">全部文件</a></li>
        <li role="presentation"><a href="${pageContext.request.contextPath}/file/search?type=video" id="video">视频</a></li>
        <li role="presentation"><a href="${pageContext.request.contextPath}/file/search?type=music" id="music">音乐</a></li>
        <li role="presentation"><a href="${pageContext.request.contextPath}/file/search?type=doc" id="doc">文档</a></li>
        <li role="presentation"><a href="${pageContext.request.contextPath}/file/search?type=img" id="img">图片</a></li>
    </ul>
    <form class="navbar-form " role="search" id="titleForm" action="${pageContext.request.contextPath}/file/search">
        <div class="form-group">
            <input type="text" class="form-control" name="name" id="search" placeholder="搜索你的文件">
        </div>
        <button type="submit" class="btn btn-default" id="doSearch">查找</button>
    </form>
</div>
    <div id="left" class="btn-group-vertical btn-group-lg" role="group" aria-label="...">
        <button type="button" class="btn btn-default" onclick="location='${pageContext.request.contextPath}/main'">我的文件</button>
        <button type="button" class="btn btn-default" onclick="">我的好友</button>
        <button type="button" class="btn btn-default" onclick="">我的分享</button>
        <button type="button" class="btn btn-default" onclick="location='${pageContext.request.contextPath}/recycle'">回收站</button>
    </div>
<div id="main">
    <form id="mainId">
    <table class="table table-hover">
        <thead>
        <th width="3%"><input type="checkbox" id="checkAll" onclick="checkall()"></th>
        <th width="45%">文件名</th>
        <th width="10%">文件大小</th>
        <th width="20%">上传时间</th>
        <th >
            <input  class="btn btn-success" type="button" onclick="location='${pageContext.request.contextPath}/file/transfer?transfer=add'" value="上传文件">
            <input class="btn btn-primary" type="button" onclick="downFile()" value="批量下载">
            <input class="btn btn-danger" type="button"onclick="deleteFile()" value="批量删除"></th>
        </thead>
        <tbody>
        <c:forEach  items="${fileList}" varStatus="s" var="list">
        <tr>
            <td><input type="checkbox" name="did" value="${list.fid}"></td>
            <td id="name${list.fid}">${list.fileName}</td>
            <td>${list.stringFileSize}</td>
            <td>${list.upDateTime}</td>
            <td >
                <input class="btn btn-primary" type="button" onclick="location='${pageContext.request.contextPath}/file/download?fid=${list.fid}'" value="下载">
<%--                <input class="btn btn-info" type="button" onclick="rename('name${list.fid}')" value="重命名">--%>
                <input class="btn btn-danger" type="button" onclick="location='${pageContext.request.contextPath}/file/recycle?fid=${list.fid}'" value="删除">
        </tr>
        </c:forEach>
        </tbody>
    </table>
    </form>
    </div>
</body>
</html>
