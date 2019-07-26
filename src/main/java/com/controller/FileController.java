package com.controller;

import com.dao.FileInfoDao;
import com.domain.FileInfo;
import com.domain.Format;
import com.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import utils.DownLoadUtils;
import utils.XingUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path="/file")
public class FileController {

    String video=".video";
    String music=".music";
    String doc=".doc";
    String img=".img";
    String other=".other";

    @Autowired
    private XingUtils xingUtils;

    @Autowired
    private FileInfo fileInfo;

    @Autowired
    private User user;

    @Autowired
    private FileInfoDao fileInfoDao;

    @Autowired
    private Format format;
    /**
     * Spring文件上传
     * @param request
     */
    @RequestMapping(path = "/upload")
    public String upload(HttpServletRequest request) {
//        List<String> fileIdList=new ArrayList<>();
        user= (User) request.getSession().getAttribute("user");
        //强转使用AJAX来获取,传统MultipartFile获取不到
        MultipartHttpServletRequest ms= (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = ms.getFileMap();
//        for (Map.Entry<String, MultipartFile> key : fileMap.entrySet()) {
//            fileIdList.add(key.getKey());
//        }
//        request.getSession().setAttribute("fileIdList",fileIdList);
        Collection<MultipartFile> values = fileMap.values();
        if(values.size()==0){
            return "handle/upload";
        }
        //获取文件夹的真实路径
        String path = getUserFolder(request);
        //创建文件对象
        File file=new File(path);
        //判断文件夹是否存在
        if(!file.exists()){
            //文件夹不存在,则创建文件夹
            file.mkdirs();
        }
        //遍历本地文件夹
        File[] fileList=file.listFiles();
        for (Map.Entry<String, MultipartFile> multipartFileEntry : fileMap.entrySet()) {
            String key = multipartFileEntry.getKey();
            MultipartFile value = multipartFileEntry.getValue();
            //获取文件名
            String filename = value.getOriginalFilename();
            //获取文件大小
            long size = value.getSize();
            //设置文件大小信息
            fileInfo.setFileSize(size);
            //设置文件所属用户信息
            fileInfo.setfUser(user.getUsername());
            //设置文件上传时间信息
            fileInfo.setUpDateTime(xingUtils.getTime());
            //设置格式化后的文件大小信息
            fileInfo.setStringFileSize(xingUtils.formatFileSize(size));
            //设置文件名信息
            fileInfo.setFileName(filename);
            //转换为流
//            InputStream is = value.getInputStream();
            //计算md5
//            String md5 = DigestUtils.md5DigestAsHex(is);
            //判断目录下是否有重名的文件
            for (File file1 : fileList) {
                while (file1.getName().equals(filename)){
                    filename=xingUtils.renameDate(filename);
                }
            }
            //设置文件的md5信息
//            fileInfo.setFileKey(md5);
            //将文件信息插入数据库
            fileInfoDao.insertInfo(xingUtils.fileType(fileInfo));
            //上传文件,以后用md5来标记文件,以后用来减少重复
            try {
                value.transferTo(new File(path,filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
            request.getSession().setAttribute(key,"成功上传!");
        }
        return "redirect:/main";
    }

    /**
     * 中转页面
     * @return
     */
    @RequestMapping(path = "/transfer")
    public String transfer(HttpServletRequest request){
        String transfer = request.getParameter("transfer");
        //跳转到添加页面
        if("add".equals(transfer)){
            return "handle/upload";
        }
        if("setting".equals(transfer)){
            return "setting";
        }
        return "redirect:/main";
    }

    /**
     * 单个文件下载
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(path = "/download")
    public String downLoadFile(HttpServletRequest request,HttpServletResponse response) throws IOException {
        List<String> idList=new ArrayList<>();
        String fid = request.getParameter("fid");
        idList.add(fid);
        List<String> byId = fileInfoDao.findById(idList);
        String filename = byId.get(0);
        //调用下载方法
        down(filename,request,response);
        return "redirect:/main";
    }

    /**
     * 批量下载
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(path = "/downloads")
    public String downLoadFiles(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //获取选中的ID封装为Map
        Map<String, String[]> map = request.getParameterMap();
        //将Map转换为List集合
        List<String> idList = xingUtils.ValueToList(map);
        if(idList.size()<=0){
            return "redirect:/main";
        }
        //根据fid查找文件名,并返回集合 注:不是真实路径
        List<String> filenameList=fileInfoDao.findById(idList);
        //封装文件的真实路径名
        List<String> filePathnameList=new ArrayList<>();
        for (String s : filenameList) {
            filePathnameList.add(getUserFolder(request)+s);
        }
        //判断如果是一个文件,就不压缩,直接下载
        if(filePathnameList.size()==1){
            down(filenameList.get(0),request,response);
            return "redirect:/main";
        }
        //压缩文件名
        String zipName=filenameList.get(0).substring(0,filenameList.get(0).length()-4)+"等多个文件.zip";
        //压缩后文件的真实路径
        String zipPathName=getUserFolder(request)+zipName;
        //压缩文件
        xingUtils.createZipFile(filePathnameList, zipPathName);
        //下载压缩后的文件
        down(zipName,request,response);
        //删除压缩后的文件
        new File(zipPathName).delete();
        return "redirect:/main";
    }

    /**
     * 下载文件的方法
     * @param filename  文件名
     * @param request
     * @param response
     * @throws IOException
     */
    public void down(String filename,HttpServletRequest request,HttpServletResponse response) throws IOException {
        //设置编码
        response.setContentType("application/x-rar-compressed;charset=UTF-8");
        //输入流对象
        FileInputStream file;
        //输出流对象
        ServletOutputStream sos;
        //创建域对象
        ServletContext servletContext = request.getServletContext();
        //建立输入流,把文件加载进内存
        file=new FileInputStream(getUserFolder(request)+filename);
        //根据文件后缀名来判断文件类型
        String mimeType = servletContext.getMimeType(filename);
        filename = DownLoadUtils.getContentDisposition(filename, request);
        //设置响应头
        response.setHeader("context-type",mimeType);
        response.setHeader("content-disposition",filename);
        //建立输出流
        sos=response.getOutputStream();
        byte[] bytes=new byte[1024*8];
        int len=0;
        while((len=file.read(bytes))!=-1){
            sos.write(bytes,0,len);
        }
        if(file!=null){
            file.close();
        }
        if(sos!=null){
            sos.close();
        }
    }

    /**
     * 单个删除到回收站
     * @param request
     * @return
     */
    @RequestMapping(path = "/recycle")
    public String recycle(HttpServletRequest request){
        //获得文件ID
        String fid = request.getParameter("fid");
        //标记删除到回收站
        fileInfoDao.recycle(xingUtils.getTime(),Integer.valueOf(fid));
        return "redirect:/main";
    }

    /**
     * 批量删除到回收站
     * @param request
     * @throws IOException
     */
    @RequestMapping(path = "/batchRecycle")
    public String batchRecycle(HttpServletRequest request){
        Map<String, String[]> map = request.getParameterMap();
        List<String> idList = xingUtils.ValueToList(map);
        if(idList.size()>0){
            //批量删除
            fileInfoDao.batchRecycle(xingUtils.getTime(),idList);
        }
        return "redirect:/main";
    }

    /**
     * 单个恢复
     * @param request
     * @return
     */
    @RequestMapping(path = "/recover")
    public String recover(HttpServletRequest request) {
        //获得文件ID
        String fid = request.getParameter("fid");
        //标记删除到回收站
        fileInfoDao.recover(Integer.valueOf(fid));
        return "redirect:/recycle";
    }

    /**
     * 批量恢复
     * @param request
     * @throws IOException
     */
    @RequestMapping(path = "/batchRecover")
    public String batchRecover(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        List<String> idList = xingUtils.ValueToList(map);
        if(idList.size()>0){
            fileInfoDao.batchRecover(idList);
        }
        return "redirect:/recycle";
    }

    /**
     * 单个删除
     * @param request
     * @return
     */
    @RequestMapping(path = "/delete")
    public String delete(HttpServletRequest request) throws IOException {
        //获得文件ID
        String fid = request.getParameter("fid");
        //删除文件
        fileInfoDao.delete(Integer.valueOf(fid));
        return "redirect:/recycle";
    }

    /**
     * 批量删除
     * @param request
     * @throws IOException
     */
    @RequestMapping(path = "/batchDelete")
    public String batchDelete(HttpServletRequest request) throws IOException {
        Map<String, String[]> map = request.getParameterMap();
        List<String> idList = xingUtils.ValueToList(map);
        if(idList.size()>0){
            fileInfoDao.batchDelete(idList);
        }
        return "redirect:/recycle";
    }

    /**
     * 根据条件模糊查找
     * @param request
     * @return
     */
    @RequestMapping(path = "/search")
    public String search(HttpServletRequest request){
        String username = ((User) request.getSession().getAttribute("user")).getUsername();
        String type = request.getParameter("type");
        String search = request.getParameter("name");
        search=(search==null)?"":search;
        List<FileInfo> fileList=new ArrayList<>();
        if(type==null){
            fileList=fileInfoDao.search(username,search);
            request.getSession().setAttribute("fileList",fileList);
            return "main";
        }else{
            type="."+type;
            if(video.equals(type)){
                fileList=fileInfoDao.searchByVideo(username);
            }else if(music.equals(type)) {
                fileList=fileInfoDao.searchByMusic(username);
            }else if(doc.equals(type)){
                fileList=fileInfoDao.searchByDoc(username);
            }else if(img.equals(type)){
                fileList=fileInfoDao.searchByImg(username);
            }
            request.getSession().setAttribute("fileList",fileList);
            return "main";
        }
    }


    /**
     * 获取用户文件夹
     * @param request
     * @return
     */
        public String getUserFolder(HttpServletRequest request){
            user = (User) request.getSession().getAttribute("user");
            return "G:\\UpLoad\\"+user.getUsername()+"\\";
        }

    }
