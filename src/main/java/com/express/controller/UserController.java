package com.express.controller;

import com.express.model.Express;
import com.express.model.ExpressShelf;
import com.express.model.User;
import com.express.service.ExpressService;
import com.express.service.ExpressShelfService;
import com.express.service.UserService;
import com.express.util.PropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ExpressService expressService;
    @Autowired
    ExpressShelfService expressShelfService;

    /**
     * 登录页面
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String toIndex(){
        return "admin/userlogin";
    }

    /**
     * 管理员用户查询快件
     * @param express
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getExpress",method = RequestMethod.POST)
    public List<Express> getExpress(@RequestBody Express express){
        List<Express> expressList = expressService.queryExpressInfo(express.getContact(), express.getExpressNo(),express.getStatus());
        return  expressList;
    }
    @ResponseBody
    @RequestMapping(value = "/getShelf",method = RequestMethod.POST)
    public ExpressShelf getExpress(@RequestBody String code){
        return  new ExpressShelf();
    }
    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestBody User params, HttpServletRequest request) throws IOException{
        String passwordParams=params.getPassword();
        // User user=userService.getUserById(params.getUserId());
        // if(user.getPassword().equals(DigestUtils.md5DigestAsHex((passwordParams.getBytes()+ PropertyUtil.getProperty("LoginSalt")).getBytes()))
        //         &&user.getUserId().equals(params.getUserId())){
        //     return "success";
        // } else {
        //     return "fail";
        // }
        return "success";
    }
    @RequestMapping(value = "/redirect",method = RequestMethod.GET)
    public String index(){
        return "admin/sap";
    }
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public String createUser(@RequestBody User user) throws IOException{
        User userNew=new User();
        userNew.setUserName(user.getUserName());
        userNew.setUserId(user.getUserId());
        userNew.setPassword(DigestUtils.md5DigestAsHex((user.getPassword()+PropertyUtil.getProperty("LoginSalt")).getBytes()));
        userService.createUser(userNew);
        return "";
    }
    @ResponseBody
    @RequestMapping(value = "/createExpress",method = RequestMethod.POST)
    public List<Express> createUser(@RequestBody Express express) throws IOException{
        expressService.createExpress(express);
        // userNew.setUserName(user.getUserName());
        // userNew.setUserId(user.getUserId());
        // userNew.setPassword(DigestUtils.md5DigestAsHex((user.getPassword()+PropertyUtil.getProperty("LoginSalt")).getBytes()));
        // userService.createUser(userNew);
        List<Express> list=new ArrayList<>();
        list.add(express);
        return list;
    }

    /**
     * 更新快件信息
     * @param express
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/updateExpress",method = RequestMethod.POST)
    public List<Express> updateUser(@RequestBody Express express) throws IOException{
        expressService.updateExpress(express);
        List<Express> list=new ArrayList<>();
        list.add(express);
        return list;
    }
}
