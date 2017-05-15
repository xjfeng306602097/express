package com.express.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.express.model.*;
import com.express.service.*;
import com.express.util.ExcelUtil;
import com.express.util.ImageCodeUtil;
import com.express.util.PropertyUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
	OverDueExpressService overDueExpressService;
	@Autowired
	SendMailService sendMailService;
	// @Autowired
	// ExcelService excelService;

	/**
	 * 登录页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String toIndex() {
		return "admin/userlogin";
	}

	/**
	 * 管理员用户查询快件
	 * 
	 * @param express
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getExpress", method = RequestMethod.POST)
	public List<Express> getExpress(@RequestBody Express express, HttpServletResponse response) throws IOException {

		// List<Express> expresses=new ArrayList<>();
		// expresses=expressService.queryExpressInfo(new Express());
		// HSSFWorkbook hssfWorkbook=excelService.exportExpress(expresses);
		// ExcelUtil.ExportExcel(hssfWorkbook,response);
		if (express.getStatus().equals("all") && express.getStatus() != null) {
			express.setStatus(null);
		}
		List<Express> expressList = new ArrayList<>();
		expressList = expressService.queryExpressInfo(express);
		// if (expressList.size()>0) {
		// for (int i = 0; i < expressList.size(); i++) {
		// ExpressExpose expressExpose = new ExpressExpose();
		// expressExpose.setExpress(expressList.get(i));
		// ExpressShelf expressShelf = new ExpressShelf();
		// expressShelf.setExpress(expressList.get(i));
		// expressShelf = expressShelfService.queryShelfByParams(expressShelf);
		// if (expressShelf!=null) {
		// expressExpose.setShelfId(expressShelf.getShelfId());
		// }
		// expressExposes.add(expressExpose);
		// }
		// }
		return expressList;
	}
	// @RequestMapping(value = "/export",method = RequestMethod.POST)
	// public void export(@RequestBody Express express,HttpServletResponse
	// response) throws IOException{
	// List<Express> expresses=new ArrayList<>();
	// expresses=expressService.queryExpressInfo(new Express());
	// HSSFWorkbook hssfWorkbook=excelService.exportExpress(expresses);
	// ExcelUtil.ExportExcel(hssfWorkbook,response);
	// }

	/**
	 * 快件维护页面查询货柜信息
	 * 
	 * @param express
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getShelf", method = RequestMethod.POST)
	public Object getShelf(@RequestBody Express express) {
		List<ExpressShelf> expressShelves = new ArrayList<>();
		ExpressShelf expressShelf = new ExpressShelf();
		JSONObject object = new JSONObject();
		OverDueExpress overDueExpress = new OverDueExpress();
		String status = express.getStatus();
		// 根据快件状态判断弹框显示内容
		switch (status) {
		case "O":
			overDueExpress.setExpress(express);
			overDueExpress = overDueExpressService.queryShelfByParams(overDueExpress);
			if (overDueExpress.getExpress() != null) {
				object.put("location", "该快件放置于过期货柜中");
				// 是否显示放入货柜的按钮的标识
				object.put("canput", false);

			}
			break;
		case "E":
			expressShelf.setExpress(express);
			expressShelves = expressShelfService.queryShelfListByParams(expressShelf);
			if (expressShelves.size() > 0) {
				expressShelf = expressShelves.get(0);
				object.put("location", "该快件放置于" + expressShelf.getShelfId() + "货柜中");
				object.put("canput", false);
			}
			break;
		case "N":
			object.put("location", "该快件未放入货柜中");
			object.put("canput", true);
			break;
		case "S":
			object.put("location", "该快件已领取");
			object.put("canput", false);
			break;
		}

		return object;
	}

	/**
	 * 快件维护页面新增货柜
	 * 
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createShelf", method = RequestMethod.POST)
	public JSONArray createShelf() {
		ExpressShelf expressShelf = new ExpressShelf();
		expressShelf.setShelfStatus("N");
		expressShelfService.createExpressShelf(expressShelf);
		List<ExpressShelf> expressShelves = new ArrayList<>();
		JSONArray jsonArray = new JSONArray();
		expressShelves = expressShelfService.queryShelfListByParams(new ExpressShelf());
		jsonArray.addAll(expressShelves);
		return jsonArray;
	}

	/**
	 * 货柜维护页面查询货柜列表
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getShelfList", method = RequestMethod.POST)
	public JSONArray getShelfList(@RequestBody JSONObject object) throws Exception {
		Map shelf = (Map) object.get("shelf");
		String type = (String) object.get("type");
		Long id = null;
		Date createDate = null;
		JSONArray jsonArray = new JSONArray();
		if (type.equals("today")) {
			String status = (String) object.get("status");
			if (status.equals("all")) {
				status = null;
			}
			ExpressShelf expressShelf;
			expressShelf = new ExpressShelf();
			shelf = (Map) object.get("shelf");
			if (shelf.get("shelfId") != null && !shelf.get("shelfId").equals("")) {
				id = Long.parseLong((String) shelf.get("shelfId"));
			}
			if (shelf.get("createDate") != null && !shelf.get("createDate").equals("")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String dstr = (String) shelf.get("createDate");
				createDate = sdf.parse(dstr);
			}
			expressShelf.setShelfId(id);
			expressShelf.setCreateDate(createDate);
			expressShelf.setShelfStatus(status);
			List<ExpressShelf> expressShelves = new ArrayList<>();
			expressShelves = expressShelfService.queryShelfListByParams(expressShelf);
			jsonArray.addAll(expressShelves);
		} else {
			OverDueExpress overDueExpress;
			overDueExpress = new OverDueExpress();
			shelf = (Map) object.get("shelf");
			if (shelf.get("shelfId") != null && !shelf.get("shelfId").equals("")) {
				id = Long.parseLong((String) shelf.get("shelfId"));
			}
			if (shelf.get("createDate") != null && !shelf.get("createDate").equals("")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String dstr = (String) shelf.get("createDate");
				createDate = sdf.parse(dstr);
			}
			overDueExpress.setOverDueShelfId(id);
			overDueExpress.setCreateDate(createDate);
			overDueExpress.setStatus((String) shelf.get("shelfStatus"));
			List<OverDueExpress> overDueExpresses = new ArrayList<>();
			overDueExpresses = overDueExpressService.queryShelfListByParams(overDueExpress);
			jsonArray.addAll(overDueExpresses);
		}

		return jsonArray;
	}

	@ResponseBody
	@RequestMapping(value = "/removeExpress", method = RequestMethod.POST)
	public List<ExpressShelf> removeExpress(@RequestBody ExpressShelf expressShelf) {
		expressShelfService.removeExpress(expressShelf);
		List<ExpressShelf> expressShelves = new ArrayList<>();
		expressShelves = expressShelfService.queryShelfListByParams(new ExpressShelf());
		return expressShelves;
	}

	/**
	 * 放入货柜
	 * 
	 * @param express
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/putIntoShelf", method = RequestMethod.POST)
	public ExpressShelf getShelfList(@RequestBody Express express) {
		ExpressShelf expressShelf = expressShelfService.queryUnusedShelf();
		if (expressShelf != null) {
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
	public LoginResult login(@RequestBody JSONObject params, HttpServletRequest request,HttpServletResponse response) throws IOException {
		LoginResult loginResult = new LoginResult();
		HttpSession session = request.getSession();
		String userId = (String) params.get("userId");
		String password = DigestUtils
				.md5DigestAsHex((params.get("password") + PropertyUtil.getProperty("Salt")).getBytes());
		User result = userService.getUserById(userId);
		if (result != null) {
			String code=(String) params.get("imageCode");
			String sessionCode=(String) request.getSession().getAttribute("code");
			if(code.equals(sessionCode)) {
				if (result.getPassword().equals(password)) {
					User user = new User();
					user.setUserId(userId);
					user.setPassword(password);
					session.setMaxInactiveInterval(20 * 60); // 20分钟
					session.setAttribute("user", user);
					loginResult.setLoginStatus("success");
					loginResult.setMessage("success");
					return loginResult;
				} else {
					loginResult.setLoginStatus("errorpassword");
					loginResult.setMessage(PropertyUtil.getProperty("PleaseCheckYourPassword"));
					return loginResult;
				}
			}else {
				loginResult.setLoginStatus("errorImageCode");
				loginResult.setMessage(PropertyUtil.getProperty("PleaseCheckYourImageCode"));
				return loginResult;
			}
		} else {
			loginResult.setLoginStatus("erroruserid");
			loginResult.setMessage(PropertyUtil.getProperty("PleaseCheckYourUserId"));
			return loginResult;
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		return "redirect:loginIndex";
	}

	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			return "admin/sap";
		} else {
			return "redirect:loginIndex";
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createUser(@RequestBody User user) throws IOException {
		User userNew = new User();
		userNew.setUserName(user.getUserName());
		userNew.setUserId(user.getUserId());
		userNew.setPassword(
				DigestUtils.md5DigestAsHex((user.getPassword() + PropertyUtil.getProperty("LoginSalt")).getBytes()));
		userService.createUser(userNew);
		return "";
	}

	@ResponseBody
	@RequestMapping(value = "/createExpress", method = RequestMethod.POST)
	public List<Express> createUser(@RequestBody Express express) throws IOException {
		express.setStatus("N");
		expressService.createExpress(express);
		// userNew.setUserName(user.getUserName());
		// userNew.setUserId(user.getUserId());
		// userNew.setPassword(DigestUtils.md5DigestAsHex((user.getPassword()+PropertyUtil.getProperty("LoginSalt")).getBytes()));
		// userService.createUser(userNew);
		List<Express> list = new ArrayList<>();
		list.add(express);
		return list;
	}

	/**
	 * 更新快件信息
	 * 
	 * @param express
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/updateExpress", method = RequestMethod.POST)
	public List<Express> updateUser(@RequestBody Express express) throws IOException {
		expressService.updateExpress(express);
		List<Express> list = new ArrayList<>();
		list.add(express);
		return list;
	}

	/**
	 * 更新快件信息
	 * 
	 * @param express
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	public Object notification(@RequestBody Express express) throws IOException {
		String verificationCode = DigestUtils
				.md5DigestAsHex((express.getVerificationCode() + PropertyUtil.getProperty("Salt")).getBytes())
				.substring(0, 6); // 获取验证码
		express.setVerificationCode(verificationCode);
		JSONObject object = new JSONObject();
		try {
			sendMailService.sendVertificationCodeByEmail(express);
		} catch (IOException e) {
			e.printStackTrace();
			object.put("result", "error");
			return object;
		}
		object.put("result", "success");
		return object;
	}

	@RequestMapping(value = "/loginIndex", method = RequestMethod.GET)
	public String toLoginIndex() {
		return "admin/index";
	}

	@RequestMapping(value = "/downloadExcel/{params}/params", method = RequestMethod.GET)
	public String downloadExcel(@PathVariable("params") String params, HttpServletRequest request,
			HttpServletResponse response)
			throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException, NoSuchMethodException, SecurityException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Express List");
		HSSFRow row = null;
		row = sheet.createRow(0);
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 设置居中
		String[] titleArray = { "ID", PropertyUtil.getProperty("ExpressNo").trim(),
				PropertyUtil.getProperty("FromDate").trim(), PropertyUtil.getProperty("ArriveDate").trim(),
				PropertyUtil.getProperty("ReceiveDate").trim(), PropertyUtil.getProperty("Company").trim(),
				PropertyUtil.getProperty("Consignee").trim(), PropertyUtil.getProperty("Contact").trim(),
				PropertyUtil.getProperty("Verificationcode").trim(), PropertyUtil.getProperty("AddressSource").trim(),
				PropertyUtil.getProperty("AddressDest").trim(), PropertyUtil.getProperty("ExpressStatus").trim(),
				PropertyUtil.getProperty("EmailAddress_title").trim() };
		List<String> titles = Arrays.asList(titleArray);
		ExcelUtil.setTitle(titles, row, style); // 生成标题
		JSONObject obj = JSON.parseObject(params);// 设置首行单元格标题
		Express express = JSONObject.toJavaObject(obj, Express.class);
		if (express.getStatus().equals("all") && express.getStatus() != null) {
			express.setStatus(null);
		}
		List<Express> expressList = expressService.queryExpressInfo(express);
		for (int i = 0; i < expressList.size(); i++) {
			row = sheet.createRow((int) i + 1);
			ExcelUtil.setRowContentByAnnotation(row, expressList.get(i));
		}
		String fileName = "ExcelDownload";
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workbook.write(os);
		byte[] content = os.toByteArray();
		ByteArrayInputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}
		}
		return null;
	}

	/**
	 * 生成图片验证码
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@RequestMapping(value="/getImageCode",method = RequestMethod.GET)
	public void getImage(HttpServletResponse response,HttpServletRequest request)throws IOException{
		// 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
		//禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		ImageCodeUtil vCode = new ImageCodeUtil(100,30,5,10);
		HttpSession session=request.getSession();
		session.setAttribute("code", vCode.getCode());
		vCode.write(response.getOutputStream());
		response.flushBuffer();
	}
}
