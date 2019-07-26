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
        function getFile(){
            document.getElementById("getFile").click();
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
        img{
            height: 52px;
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
    <img src="${pageContext.request.contextPath}/static/images/title.png" height="90%" width="50%" style="position:absolute; z-index:-1;top: 20%"/>
    <div id="titleUser"><img src="${pageContext.request.contextPath}/static/images/background.jpeg" alt="38*38" class="img-circle" style="width: 38px;height: 38px">&nbsp;&nbsp;${user.username}&nbsp;&nbsp;&nbsp;&nbsp;<a href="">设置</a>&nbsp;&nbsp;<a href="">退出</a></div>

    <ul class="nav nav-pills" id="titleUl">
        <li role="presentation" class="active"><a href="#">上传列表</a></li>
    </ul>
    <form class="navbar-form " role="search" name="name" id="titleForm" action="${pageContext.request.contextPath}/file/search">
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
    <table class="table table-hover">
        <thead>
        <th width="3%">预览</th>
        <th width="45%">文件名</th>
        <th width="10%">文件大小</th>
        <th width="20%">上传进度</th>
<%--        <th width="20%"><input type="file" id="file1" multiple style="display: none" hidden name="file1"></th>--%>
        <th >
                <input type="file" id="getFile" multiple style="display: none" hidden>
                <input class="btn btn-primary" type="button" value="添加文件" onclick="getFile()">
                <input class="btn btn-success" type="button" onclick="upload(fd)" value="确认上传">
                <input class="btn btn-warning" type="reset" onclick="load()" value="取消上传">
        </th>
        </thead>
        <tbody id="fileInfo">
<%--        这里动态添加文件信息--%>
        </tbody>
    </table>

            <script>
                var fd = new FormData();
                //获取文件框ID
                var inputs = document.getElementById("getFile");
                // var inputs = $("#getFile");
                //获取tbody ID
                var fileInfo = document.getElementById("fileInfo");
                // var fileInfo = $("#fileInfo");
                //创建监听器,当文件框确认时执行函数
                inputs.addEventListener('change', updateDisplay);


                //发送ajax
                function upload(fd) {
                    var xhr = new XMLHttpRequest();
                    $(".prog").text("正在上传");
                    //监听事件
                    // xhr.upload.addEventListener("progress", uploadProgress, false);
                    xhr.open("post", "${pageContext.request.contextPath}/file/upload", true);
                    xhr.send(fd);
                    xhr.onreadystatechange=function()
                    {
                        if (xhr.readyState==4 && xhr.status==200)
                        {
                            $(".prog").text("上传完成");
                            alert("成功上传!");
                             $("#fileInfo").empty();
                        }

                    }
                }
                var f=0;
                var fileId;
                var curFiles;
                var prog;
                function updateDisplay() {
                    //从input file中获取文件
                    curFiles = inputs.files;
                    for(var i = 0; i < curFiles.length; i++,f++) {
                       //创建tr
                        var tr = document.createElement('tr');
                        //设置tr的id和file文件的id,用于删除
                        var trId="tr"+f;
                        fileId="file"+f;
                        prog="prog"+f;
                        tr.setAttribute("id",trId);
                        //添加到FormData中
                        fd.append(fileId, curFiles[i]);
                        //设置图片预览td
                        var imageTd=document.createElement("td");
                        //设置图片框img,包含在图片td里
                        var image = document.createElement('img');
                        //设置文件名td
                        var name = document.createElement('td');
                        //设置文件大小td
                        var size = document.createElement('td');
                        //设置上传进度td,暂未实现
                        var progress=document.createElement("td");
                        //设置按钮td
                        var button=document.createElement("td");
                        //设置input按钮
                        var del=document.createElement("input");
                        //设置按钮属性
                            del.setAttribute("class","btn btn-primary");
                            del.setAttribute("type","button");
                            del.setAttribute("onclick","deleteFd('"+fileId+"','"+trId+"')");
                            del.setAttribute("value","删除");
                        //填充图片框
                        image.src = window.URL.createObjectURL(curFiles[i]);
                        //将图片框加入到td中
                        imageTd.appendChild(image);
                        //填充文件名td
                        name.textContent = curFiles[i].name ;
                        //填充文件大小td
                        size.textContent =  returnFileSize(curFiles[i].size);
                        progress.setAttribute("class","prog");
                        progress.setAttribute("id",fileId);
                        <%--progress.setAttribute("value",${fileId});--%>
                        progress.textContent="等待上传"
                        //将按钮加入到按钮框td中
                        button.appendChild(del);
                        //将所有td加入到tr中
                            tr.appendChild(imageTd);
                            tr.appendChild(name);
                            tr.appendChild(size);
                            tr.appendChild(progress)
                            tr.appendChild(button);
                        //将tr加入到tbody中
                        fileInfo.appendChild(tr);
                        }
                }
                // 格式化文件大小
                function returnFileSize(number) {
                    if(number < 1024) {
                        return number + 'bytes';
                    } else if(number > 1024 && number <1048576) {
                        return (number/1024).toFixed(2) + 'KB';
                    } else if(number > 1048576&&number<1073741824) {
                        return (number/1048576).toFixed(2) + 'MB';
                    }else if(number > 1073741824) {
                        return (number/1073741824).toFixed(2) + 'GB';
                    }
                }
                // 操作删除上传列表
                function deleteFd(fileId,trId) {
                    fd.delete(fileId);
                    document.getElementById(trId).remove();
                }
            </script>
</form>
</div>


<%--        <tr>--%>

<%--           <td id="fileName"></td>--%>
<%--           <td id="fileSize"></td>--%>
<%--           <td>上传进度</td>--%>
<%--           <td>--%>
<%--               <input class="btn btn-danger" type="button" value="删除"></td>--%>
<%--       </tr>--%>


</body>
</html>

