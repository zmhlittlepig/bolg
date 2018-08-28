package action;

import bean.Users;
import factory.ServiceFactory;
import service.UserService;
import utils.FileUtil;
import utils.MailUtil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import com.alibaba.fastjson.JSON;

/**
 * Created with IntelliJ IDEA. Description: User: panyiwen Date: 2018-07-24
 * Time: 下午7:09
 */
@WebServlet("/user")
@MultipartConfig(maxFileSize=5765004,fileSizeThreshold=1000)
public class UserServlet extends BaseServlet {
	private UserService userService = (UserService) ServiceFactory.getInstance("UserService");


	//登陆
	public Object login(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;

		try {

			String uid = request.getParameter("username");
			String upasswd = request.getParameter("password");
			String remember = request.getParameter("remember");
			/*System.out.println(uid);
			System.out.println(upasswd);*/

			Users user = userService.getUserByLoginAndPass(uid, upasswd);

			HttpSession session = request.getSession();
			if (user != null) {
				if (user.getUserStatus() == -1) {

					System.out.println("禁用用户");
					response.getWriter().print(-1);

				} else {

					if(user.getUserStatus() == 1) {
						System.out.println("管理员登陆");
						response.getWriter().print(1);
					}else {
						System.out.println("普通用户登陆");
						response.getWriter().print(0);
					}

					session.setAttribute("user", user);

				}


			} else {
				response.getWriter().print(10);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return uri;
	}


	//注册
	public Object register(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;

		try {

			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");

			String username = request.getParameter("username");
			String nickname = request.getParameter("nickname");
			String password = request.getParameter("password");

			Users users = new Users();
			Date date = new Date();

			users.setUserLogin(username);
			users.setUserNicename(nickname);
			users.setUserPass(password);
			users.setDisplayName(nickname);
			users.setUserRegistered(date);

			boolean is2 = userService.saveUser(users);
			if (is2) {
				response.getWriter().print(1);
			} else {
				response.getWriter().print(2);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return uri;
	}


	//用户名重复校验
	public void checkUsername(HttpServletRequest request, HttpServletResponse response) {

		try {

			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");

			// 1.检测是否存在
			String name = request.getParameter("username");
			int length = name.length();
			//System.out.println(length);

			boolean isExist = userService.checkUserName(name);

			// 2.通知页面，到底有还是没有
			if (isExist) {

				response.getWriter().print(1);

			} else if(!isExist){

				response.getWriter().print(0);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	//修改密码
	public Object ChangePassword(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;

		try {

			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");

			String oldpassword = request.getParameter("oldpassword");
			String newpassword = request.getParameter("newpassword");
			String rnewpassword = request.getParameter("rnewpassword");
			Users users = (Users) request.getSession().getAttribute("user");

			if(rnewpassword.equals(newpassword)) {

				if(oldpassword.equals(users.getUserPass())) {
					boolean is = userService.updateUserPasswd(users.getUserLogin(), oldpassword, newpassword);
					if(is) {
						response.getWriter().print(1);;

						HttpSession session = request.getSession(false);
						session.removeAttribute("user");
					}else {
						response.getWriter().print(0);
					}

				}else {
					response.getWriter().print(0);
				}
			}else {
				response.getWriter().print(0);
			}




		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return uri;

	}


	//修改用户信息
	public Object ChangeMessage(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;

		try {

			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");

			Users users = (Users) request.getSession().getAttribute("user");

			String userNicename = request.getParameter("userNicename");
			String userEmail = request.getParameter("userEmail");
			String displayName = request.getParameter("displayName");
			boolean is = false;
			if(users != null) {

				users.setDisplayName(displayName);
				users.setUserNicename(userNicename);
				users.setUserEmail(userEmail);

				is = userService.updateUser(users);
			}

			if(is) {
				request.getSession().setAttribute("user", users);
				response.getWriter().print(1);
			}else {
				response.getWriter().print(0);
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return uri;
	}


	/*//获取用户信息
	public Object getUserMessage(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;

		try {

			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");

			Users users = (Users) request.getSession().getAttribute("user");

			System.out.println(users.toString());
			String s = JSON.toJSONString(users);
			response.getWriter().write(s);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return uri;
	}
	*/

	//修改用户图片
	public Object UpdateUserPic(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;

		try {

			Users users = (Users) request.getSession().getAttribute("user");
			String dir = "/photo";

			Part part = request.getPart("file");
			String header=part.getHeader("Content-Disposition");
			String fileName = FileUtil.getFileName(header);
			String[] split = fileName.split("\\.");
			String newFileName = UUID.randomUUID().toString().replace("-", "") + "." + split[1];
			System.out.println(newFileName);
			//System.out.println(dir+File.separator+fileName);
			part.write("E:\\upload\\image"+File.separator+newFileName);

			String pic = dir + "/" + newFileName;

			boolean is = userService.updateUserPic(pic, users.getUserLogin());

			if(is) {
				users.setPic(pic);
				request.getSession().setAttribute("user", users);
				String s = JSON.toJSONString(users);
				response.getWriter().write(s);
			}



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return uri;
	}


	//发送邮箱验证码
	public Object setEmail(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;
		try {

			String email = request.getParameter("email");

			int s = (int)((Math.random() * 9 + 1) * 100000 );
			String captcha = s + "";

			Map<String, String> map = new HashMap<>();
			map.put(email, captcha);
			request.getSession().setAttribute("emailAndcaptcha", map);
			request.getSession().setAttribute("YZTime", new Date());

			if(email != null && captcha != null) {
				MailUtil.send_mail(email, captcha);
				response.getWriter().print(1);
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return uri;
	}


	//检验验证码是否正确
	public Object checkYzm(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;

		try {
			String captcha = request.getParameter("captcha");
			String email = request.getParameter("email");
			String password = request.getParameter("password");

			//System.out.println(captcha + "***" + email + "*****" + password);

			Date time1 = (Date) request.getSession().getAttribute("YZTime");
			Map<String, String> map = (Map<String, String>) request.getSession().getAttribute("emailAndcaptcha");
			Date time2 = new Date();

			long time = time2.getTime() - time1.getTime();
			String b1 = map.get(email);
			/*System.out.println(time);
			System.out.println(b1);*/

			if(captcha.equals(b1)) {
				if((time / 1000) <= 30) {
					if(password.length() >= 8) {
						userService.updatePwdByEmail(email, password);
						response.getWriter().print(1);
					}
				}
			}else {
				response.getWriter().print(0);
			}



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return uri;
	}


	//判断是否登陆
	public Object checkISlogin(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;

		try {

			Users users = (Users) request.getSession().getAttribute("user");

			if(users != null) {
				response.getWriter().print(users.getUserStatus()+1);
			}else {
				response.getWriter().print(0);
			}


		} catch (IOException e) {
			e.printStackTrace();
		}

		return uri;
	}


	//退出登录
	public Object logOff(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;

		try {


			request.getSession().removeAttribute("user");
			Users users = (Users) request.getSession().getAttribute("user");

			if(users == null) {
				response.getWriter().print(1);
			}else {
				response.getWriter().print(0);
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return uri;
	}


	//获取当前登陆的用户信息
	public Object getNowUser(HttpServletRequest request, HttpServletResponse response) {
		Object uri = null;

		try {

			Users users = (Users) request.getSession().getAttribute("user");
			if(users != null) {
				String s = JSON.toJSONString(users);
				System.out.println("ssssssssss" + s);
				response.getWriter().write(s);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return uri;
	}
}