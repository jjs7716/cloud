package com.domain;

import org.springframework.stereotype.Repository;

@Repository
public class FileInfo {
    private Integer fid;
    private String fileName;
    private Long fileSize;
    private String fUser;
    private String upDateTime;
    private String fileKey;
    private String folder;
    private String stringFileSize;
    private String deleteTime="";
    private Integer deleteDay=0;
    private String type;


    @Override
    public String toString() {
        return "FileInfo{" +
                "fid=" + fid +
                ", fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", fUser='" + fUser + '\'' +
                ", upDateTime='" + upDateTime + '\'' +
                ", fileKey='" + fileKey + '\'' +
                ", folder='" + folder + '\'' +
                ", stringFileSize='" + stringFileSize + '\'' +
                ", deleteTime='" + deleteTime + '\'' +
                ", deleteDay=" + deleteDay +
                ", type='" + type + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Integer getDeleteDay() {
        return deleteDay;
    }

    public void setDeleteDay(Integer deleteDay) {
        this.deleteDay = deleteDay;
    }


    public String getUpDateTime() {
        return upDateTime;
    }

    public void setUpDateTime(String upDateTime) {
        this.upDateTime = upDateTime;
    }

    public String getStringFileSize() {
        return stringFileSize;
    }

    public void setStringFileSize(String stringFileSize) {
        this.stringFileSize = stringFileSize;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getfUser() {
        return fUser;
    }

    public void setfUser(String fUser) {
        this.fUser = fUser;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }
}
