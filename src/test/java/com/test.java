package com;

import com.dao.FileInfoDao;
import com.dao.UserDao;
import com.domain.FileInfo;
import com.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utils.XingUtils;

import java.io.File;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:ApplicationContext.xml")
public class test {

    @Autowired
    private UserDao userDao;

    @Autowired
    private User user;

    @Autowired
    private FileInfo fileInfo;

    @Autowired
    private FileInfoDao fileInfoDao;

    @Autowired
    private XingUtils xingUtils;
    @Test
    public void test1()  {
        String a="nihao";
        String b=new String(a);
        System.out.println(b);
        System.out.println();
    }
    @Test
    public void test2()  {

    }


    @Test
    public void test3() throws IOException {
        File file=new File("H:\\Users\\Administrator\\Downloads\\G%3A%5CUpLoad%5Cadmin%5C%5Bjikekaifa.com%5D11%E7%A7%8D%E5%AE%89%E5%8D%93%E4%BC%A0%E6%84%9F%E5%99%A8%E4%BD%BF%E7%94%A8%E6%BA%90%E7%A0%81%E7%AD%89.zip");
        file.delete();

    }
}
