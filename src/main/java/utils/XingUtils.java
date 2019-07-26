package utils;

import com.domain.FileInfo;
import com.domain.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class XingUtils {

    @Autowired
    private Format format;


    /**
     * 重命名文件
     * 格式为原文件名+当前时间+后缀
     * @param fileName 重复的文件名
     * @return  返回加入当前时间的新文件名
     */
    public String renameDate(String fileName){
        // 获得.的位置,确定扩展名
        int index = fileName.lastIndexOf(".");
        // 扩展名前的文件名
        String start;
        // 扩展名
        String end="";
        if(index==-1){
            start=fileName;
        }else{
            start=fileName.substring(0,index);
            end=fileName.substring(index,fileName.length());
        }
        fileName=start+new SimpleDateFormat("_yyyyMMdd_HHmmss").format(new Date())+end;
        return fileName;
    }
        /**
         * 将Map集合中的value值转换为List集合
         * @param map 要转换的map集合
         * @return  value值的list集合
         */
        public List<String> ValueToList(Map map){
            List<String> list=new ArrayList<>();
            Set<Map.Entry<String, String[]>> entries = map.entrySet();
            for (Map.Entry<String, String[]> entry : entries) {
                for (String s : entry.getValue()) {
                    list.add(s);
                }
            }
            return list;
        }

        /**
         * 获得当前时间   格式例:2019-01-01 11:11:11
         * @return
         */
        public String getTime(){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        }

    /**
     * 计算两个时间差
     * @param   start   开始时间
     * @param   end     结束时间
     * @return
     * @throws ParseException
     */
    public Integer deleteDay(String start,String end) throws ParseException {
        //设置转换的日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //开始时间
        Date startDate = sdf.parse(start);
        //结束时间
        Date endDate = sdf.parse(end);
        //得到相差的天数 betweenDate
        int betweenDate = (int) ((endDate.getTime() - startDate.getTime())/(60*60*24*1000));
        return betweenDate;
    }

    /**
     * 转换文件大小
     * @param fileSize
     * @return
     */
    public String formatFileSize(long fileSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileSize == 0) {
            return wrongSize;
        }
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 压缩文件
     * @param fileList      要压缩的文件名的真实路径列表
     * @param zipPathName      压缩后的文件名的真实路径
     * @return
     */
    public  String createZipFile(List<String> fileList, String zipPathName) {
        if(fileList == null || fileList.size() == 0){
            return null;
        }
        //构建压缩文件File
        File zipFile = new File(zipPathName);
        //初期化ZIP流
        ZipOutputStream out = null;
        try{
            //构建ZIP流对象
            out = new ZipOutputStream(new FileOutputStream(zipFile));
            //循环处理传过来的集合
            for(int i = 0; i < fileList.size(); i++){
                //获取目标文件
                File inFile = new File(fileList.get(i));
                if(inFile.exists()){
                    //定义ZipEntry对象
                    ZipEntry entry = new ZipEntry(inFile.getName());
                    //赋予ZIP流对象属性
                    out.putNextEntry(entry);
                    int len = 0 ;
                    //缓冲
                    byte[] buffer = new byte[1024];
                    //构建FileInputStream流对象
                    FileInputStream fis;
                    fis = new FileInputStream(inFile);
                    while ((len = fis.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                        out.flush();
                    }
                    //关闭closeEntry
                    out.closeEntry();
                    //关闭FileInputStream
                    fis.close();
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                //最后关闭ZIP流
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return zipPathName;
    }

    /**
     * 根据后缀判断文件格式并存入类中
     * @param fileInfo
     * @return
     */
    public FileInfo fileType(FileInfo fileInfo){
        String filename=fileInfo.getFileName();
        int t=0;
        //判断文件类型
        for (String s : format.getVideoList()) {
            if(filename.endsWith(s)){
                fileInfo.setType(".video");
                t++;
                break;
            }
        }
        if (t==0) {
            for (String s : format.getMusicList()) {
                if (filename.endsWith(s)) {
                    fileInfo.setType(".music");
                    t++;
                    break;
                }
            }
        }
        if(t==0){
            for (String s : format.getImgList()) {
                if(filename.endsWith(s)){
                    fileInfo.setType(".img");
                    t++;
                    break;
                }
            }
        }
        if(t==0){
            for (String s : format.getDocList()) {
                if(filename.endsWith(s)){
                    fileInfo.setType(".doc");
                    t++;
                    break;
                }
            }
        }
        if(t==0){
            fileInfo.setType(".other");
        }
        return fileInfo;
    }
}
