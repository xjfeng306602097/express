package com.express.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.express.model.Express;
import com.express.model.ExpressShelf;
import com.express.model.LoginResult;
import com.express.model.User;
import com.express.service.ExpressService;
import com.express.service.ExpressShelfService;
import com.express.service.SendMailService;
import com.express.service.UserService;
import com.express.util.PropertyUtil;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ExpressService expressService;
    @Autowired
    ExpressShelfService expressShelfService;
    @Autowired
    SendMailService sendMailService;

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
        List<Express> expressList =new ArrayList<>();
        expressList  = expressService.queryExpressInfo(express);
        // if (expressList.size()>0) {
        //     for (int i = 0; i < expressList.size(); i++) {
        //         ExpressExpose expressExpose = new ExpressExpose();
        //         expressExpose.setExpress(expressList.get(i));
        //         ExpressShelf expressShelf = new ExpressShelf();
        //         expressShelf.setExpress(expressList.get(i));
        //         expressShelf = expressShelfService.queryShelfByParams(expressShelf);
        //         if (expressShelf!=null) {
        //             expressExpose.setShelfId(expressShelf.getShelfId());
        //         }
        //         expressExposes.add(expressExpose);
        //     }
        // }
        return expressList;
    }

    /**
     * 快件维护页面查询货柜信息
     * @param express
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getShelf",method = RequestMethod.POST)
    public ExpressShelf getShelf(@RequestBody Express express){
        List<ExpressShelf> expressShelves = new ArrayList<>();
        ExpressShelf expressShelf=new ExpressShelf();
        expressShelf.setExpress(express);
        expressShelves=expressShelfService.queryShelfListByParams(expressShelf);
        if (expressShelves.size()>0) {
            expressShelf=expressShelves.get(0);
        }
        return expressShelf;
    }

    /**
     * 货柜维护页面查询货柜列表
     * @param expressShelf
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getShelfList",method = RequestMethod.POST)
    public List<ExpressShelf> getShelfList(@RequestBody ExpressShelf expressShelf){
        List<ExpressShelf> expressShelves=new ArrayList<>();
        expressShelves=expressShelfService.queryShelfListByParams(expressShelf);
        return expressShelves;
    }
    @ResponseBody
    @RequestMapping(value = "/removeExpress",method = RequestMethod.POST)
    public List<ExpressShelf> removeExpress(@RequestBody ExpressShelf expressShelf){
        expressShelfService.removeExpress(expressShelf);
        List<ExpressShelf> expressShelves =new ArrayList<>();
        expressShelves=expressShelfService.queryShelfListByParams(new ExpressShelf());
        return expressShelves;
    }

    /**
     * 放入货柜
     * @param express
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/putIntoShelf",method = RequestMethod.POST)
    public ExpressShelf getShelfList(@RequestBody Express express){
        ExpressShelf expressShelf =expressShelfService.queryUnusedShelf();
        if (expressShelf!=null) {
            expressShelf.setExpress(express);
            expressShelf.setShelfStatus("E");
            expressShelf.setCreateDate(new Date());
            expressShelfService.updateExpressShelf(expressShelf);
            express.setStatus("E");
            expressService.updateExpress(express);
        }
        return expressShelf;
    }
    @ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public LoginResult login(@RequestBody User params, HttpServletRequest request) throws IOException {
		LoginResult loginResult = new LoginResult();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			String userId = user.getUserId();
			String password = user.getPassword();
			User result = userService.getUserById(userId);
			if (result.getPassword().equals(password)) {
				loginResult.setLoginStatus("success");
				loginResult.setMessage("success");
				return loginResult;
			} else {
				loginResult.setLoginStatus("error");
				loginResult.setMessage("${PleaseCheckYourPassword}");
				return loginResult;
			}
		} else {
			String userId = params.getUserId();
			String password = DigestUtils
					.md5DigestAsHex((params.getPassword() + PropertyUtil.getProperty("Salt")).getBytes());
			User result = userService.getUserById(userId);
			if (result.getPassword().equals(password)) {
				params.setPassword(password);
				session.setMaxInactiveInterval(20*60); // 20分钟
				session.setAttribute("user", params);
				loginResult.setLoginStatus("success");
				loginResult.setMessage("success");
				return loginResult;
			} else {
				loginResult.setLoginStatus("error");
				loginResult.setMessage("${PleaseCheckYourPassword}");
				return loginResult;
			}
		}
	}
    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null ){
			return "admin/sap";
		} else {
			return "redirect:loginIndex";
		}
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
    /**
     * 更新快件信息
     * @param express
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/sendMail",method = RequestMethod.POST)
    public Object notification(@RequestBody Express express) throws IOException{
        String verificationCode = DigestUtils
                .md5DigestAsHex((express.getVerificationCode() + PropertyUtil.getProperty("Salt")).getBytes())
                .substring(0, 6); // 获取验证码
        express.setVerificationCode(verificationCode);
        JSONObject object=new JSONObject();
        try {
            sendMailService.sendVertificationCodeByEmail(express);
        } catch (IOException e) {
            e.printStackTrace();
            object.put("result","error");
            return object;
        }
        object.put("result","success");
        return object;
    }
    
    @RequestMapping(value = "/loginIndex", method = RequestMethod.GET)
	public String toLoginIndex() {
		return "admin/index";
	}
}
