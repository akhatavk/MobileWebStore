package com.cmpe281.project.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cmpe281.project.beans.MobileBean;

import com.cmpe281.project.beans.UserBean;
import com.cmpe281.project.process.LoginUserProcess;
import com.cmpe281.project.process.ProductProcess;


public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		ProductProcess productProcess = new ProductProcess();
		List<MobileBean> productList = null;
		String emailAdress = request.getParameter("emailAddress");
		String password = request.getParameter("password");
		UserBean userBean = null;
		if( ! (emailAdress == null || emailAdress.trim().equals("") || password == null || password.trim().equals("")) ){
			UserBean bean = new UserBean();
			bean.setEmail(emailAdress);
			bean.setUserPassword(password);
			LoginUserProcess loginProcess = new LoginUserProcess();
			userBean = loginProcess.loginUser(bean);
		System.out.println("*************"+userBean.getUserId());
			if(userBean != null){
				System.out.println("Session Set");
				session.setAttribute("userId", userBean.getUserId());
				session.setAttribute("userBean", userBean);
			}
		}
		String url = "/mobile.jsp";
		if(userBean == null){
			url = "/login.jsp";
		} 
		productList = productProcess.getProductList();
		List<MobileBean> mobileList = (List<MobileBean>) (List<?>) productList;

	

		session.setAttribute("productlist", productList);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}
}
