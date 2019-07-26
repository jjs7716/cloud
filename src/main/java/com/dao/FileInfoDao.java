package com.dao;

import com.domain.FileInfo;
import com.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileInfoDao {
    /**
     * 根据用户名查找他的文件
     * @param user
     * @return
     */
    List<FileInfo> findByProperty(User user);

    /**
     * 显示回收站文件
     * @param user
     * @return
     */
    List<FileInfo> findByRecycle(User user);


    /**
     * 插入文件信息
     * @param fileInfo
     */
    void insertInfo(FileInfo fileInfo);

    /**
     * 根据Id查找文件
     * @param
     * @return
     */
    List<String> findById(List<String> list);

    /**
     * 单个删除到回收站
     * 并设置删除时间
     * @param fid
     */
    void recycle(@Param("deleteTime") String deleteTime, @Param("fid")int fid);

    /**
     * 批量删除到回收站
     * 注:多个参数,要加@Param注解,指定和哪个参数对应,注解里的值要和SQL语句里的参数相同
     * 例:@Param("deleteTime") 和SQL里的#{deleteTime}要相同
     * 注:如果是list集合,要和for each里的collection属性对应
     * 例:@Param("list")和collection="list"
     * @param list
     */
    void batchRecycle(@Param("deleteTime") String deleteTime, @Param("list") List<String> list);

    /**
     * 单个还原
     * @param id
     */
    void recover(int id);

    /**
     * 批量还原
     * @param list
     */
    void batchRecover(List<String> list);

    /**
     * 单个彻底删除
     * @param id
     */
    void delete(int id);

    /**
     * 批量彻底删除
     * @param list
     */
    void batchDelete(List<String> list);

    /**
     * 条件搜索
     * @param username
     * @param searchName
     * @return
     */
    List<FileInfo> search(@Param("username") String username,@Param("searchName") String searchName);


    List<FileInfo> searchByVideo(String username);

    List<FileInfo> searchByMusic(String username);

    List<FileInfo> searchByDoc(String username);

    List<FileInfo> searchByImg(String username);




    Integer getDeleteDay(FileInfo fileInfo);

}
