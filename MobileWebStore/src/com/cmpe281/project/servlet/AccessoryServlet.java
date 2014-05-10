package com.cmpe281.project.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cmpe281.project.beans.AccessoriesBean;
import com.cmpe281.project.beans.MobileBean;

import com.cmpe281.project.process.AccessoryProcess;


public class AccessoryServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int mobileId = Integer.parseInt((String) request.getParameter("itemId"));
		
		AccessoriesBean accessoriesBean = null;
		AccessoryProcess accessoryProcess = new AccessoryProcess();
		accessoriesBean = accessoryProcess.getAccessories(mobileId);
		

		
		
		List<MobileBean> productList = (List<MobileBean>) request.getSession().getAttribute("productlist");
		

		for (MobileBean mobileBean : productList) {
			if (mobileId == mobileBean.getMobileId()) {
				request.getSession().setAttribute("mobileName", mobileBean.getBrandName() + " " + mobileBean.getMobileName());
				break;
			}
		}
	
		request.getSession().setAttribute("accessory", accessoriesBean); 
		String url = "/showAccessory.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);
		

	
	}
}
