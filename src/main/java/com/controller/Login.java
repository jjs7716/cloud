package com.controller;

import com.dao.FileInfoDao;
import com.dao.UserDao;
import com.domain.FileInfo;
import com.domain.User;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.CaptchaUtil;
import utils.XingUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping(path="")
public class Login {

    @Autowired
    private User user;

    @Autowired
    private UserDao userDao;

    @Autowired
    private FileInfoDao fileInfoDao;

    @Autowired
    private XingUtils xingUtils;


    /**
     * 主页面,显示用户所有未标记删除文件
     * @param session
     * @return
     */
    @RequestMapping(path = "/main")
    public String main(HttpSession session){
        user = (User) session.getAttribute("user");
        if(user==null){
            return "redirect:/index.jsp";
        }
        List<FileInfo> fileList = fileInfoDao.findByProperty(user);
        session.setAttribute("fileList",fileList);
        return "main";
    }

    /**
     * 显示回收站已标记的用户文件
     * @param session
     * @return
     * @throws ParseException
     */
    @RequestMapping(path = "/recycle")
    public String recycle(HttpSession session) throws ParseException {
        //声明回收站保存时间
        Integer saveDay=30;
        user = (User) session.getAttribute("user");
        //获取用户在回收站的所有文件
        List<FileInfo> fileList = fileInfoDao.findByRecycle(user);
        //新建一个集合来存储最后要显示的文件列表
        List<FileInfo> residueList=new ArrayList<>();
        for (FileInfo fileInfo : fileList) {
            //遍历回收站每个文件剩余清空时间
            Integer residueDay=saveDay-xingUtils.deleteDay(fileInfo.getDeleteTime(),xingUtils.getTime());
            if(residueDay<0){
                //如果剩余天数小于0,就删除文件
                fileInfoDao.delete(fileInfo.getFid());
            }else{
                //如果剩余天数大于0,就设置最后清空时间
                fileInfo.setDeleteDay(residueDay);
                //把文件信息添加到要显示的文件列表
                residueList.add(fileInfo);
            }
        }
        //将文件列表发送出去
        session.setAttribute("recycleList",residueList);
        return "handle/recycle";
    }

    /**
     * 登录验证
     * @param request
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IOException
     */
    @RequestMapping(path = "/login")
    public String loginCheck(HttpServletRequest request,HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, IOException {
        String code = String.valueOf(request.getSession().getAttribute("loginCode"));
        request.getSession().removeAttribute("loginCode");
        Map<String, String[]> map = request.getParameterMap();
        String error1="验证码错误!";
        String error2="用户名或密码错误!";
        BeanUtils.populate(user,map);
        if(!code.equalsIgnoreCase(user.getLoginCode())){
            request.getSession().setAttribute("error",error1);
            return "redirect:/index.jsp";
        }
        if(userDao.checkUser(user).size()==0){
            request.getSession().setAttribute("error",error2);
            return "redirect:/index.jsp";
        }
        else{
            request.getSession().setAttribute("user",user);
            return "redirect:/main";
        }
    }

    /**
     * 检查用户名是否存在
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(path = "/checkUser")
    public void checkUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //必须设置response编码
        response.setContentType("text/text;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        user.setUsername(username);
        //检查username是否存在
        User cuser = userDao.checkUsername(user);
        if (cuser == null) {
            response.getWriter().write("true");
        } else {
            response.getWriter().write("false");
        }
    }

    /**
     * 注册用户
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(path = "/regist")
    public String regist(HttpServletRequest request,HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        user.setUsername(username);
        user.setPassword(password);
        userDao.insert(user);
        request.getSession().setAttribute("user",user);
        return "redirect:/main";
    }

    /**
     * 退出清空session,返回主页
     * @param request
     * @return
     */
    @RequestMapping(path = "/exit")
    public String exit(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "redirect:/index.jsp";
    }

    /**
     * 设置页面,修改密码
     * @return
     */
    @RequestMapping(path = "/setting")
    public void setting(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setContentType("text/text;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        user = (User) request.getSession().getAttribute("user");
        String password = user.getPassword();
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        if(password.equals(oldPassword)){
            user.setPassword(newPassword);
            userDao.updatePassword(user);
            response.getWriter().write("true");
        }
        else{
            response.getWriter().write("false");
        }
    }


    /**
     * 验证码模块
     * @param request
     * @param response
     * @throws Exception
     */

    @ResponseBody
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public void validateCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = CaptchaUtil.createImage();
        //将验证码存入Session
        request.getSession(true).setAttribute("loginCode",objs[0]);
        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "jpeg", os);
        os.flush();
    }
}
