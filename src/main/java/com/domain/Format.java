package com.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class Format {
    private List<String> videoList;
    private List<String> imgList;
    private List<String> docList;
    private List<String> musicList;
//    Properties properties=new Properties();
//    InputStream is= Format.class.getClassLoader().getResourceAsStream("format.properties");
    public List<String> getVideoList() {
        videoList=new ArrayList<>();
        videoList.add("mkv");
        videoList.add("mp4");
        videoList.add("3gp");
        videoList.add("rmvb");
        videoList.add("avi");
        videoList.add("flv");
        videoList.add("wmv");
        videoList.add("mov");
        videoList.add("mpeg");
        videoList.add("mpg");
        videoList.add("rm");
        return videoList;
    }

    public List<String> getImgList() {
        imgList=new ArrayList<>();
        imgList.add("jpg");
        imgList.add("jpeg");
        imgList.add("bmp");
        imgList.add("png");
        imgList.add("tif");
        imgList.add("gif");
        imgList.add("pcx");
        imgList.add("tga");
        imgList.add("exif");
        imgList.add("psd");
        imgList.add("fpx");
        imgList.add("svg");
        imgList.add("dwg");
        return imgList;
    }

    public List<String> getDocList() {
        docList=new ArrayList<>();
        docList.add("doc");
        docList.add("docx");
        docList.add("xls");
        docList.add("ppt");
        docList.add("txt");
        docList.add("md");
        return docList;
    }

    public List<String> getMusicList() {
        musicList=new ArrayList<>();
        musicList.add("mp3");
        musicList.add("wma");
        musicList.add("flac");
        musicList.add("aac");
        musicList.add("mmf");
        musicList.add("amr");
        musicList.add("m4a");
        musicList.add("wav");
        musicList.add("ogg");
        musicList.add("m4r");
        return musicList;
    }

}
