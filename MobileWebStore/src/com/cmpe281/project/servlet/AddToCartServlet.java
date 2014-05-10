package com.cmpe281.project.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.cmpe281.project.beans.CartBean;
import com.cmpe281.project.beans.MobileBean;
import com.cmpe281.project.process.CartProcess;

public class AddToCartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In Add to cart servlet");
		int itemId = Integer.parseInt((String) request.getParameter("itemId"));
		int cartQuantity = Integer.parseInt((String) request.getParameter("quantityCart"));
		if(request.getSession()==null)
			System.out.println("sESSION IS null");
		int userId = (Integer) request.getSession().getAttribute("userId");

		CartBean cartBean = new CartBean();
		cartBean.setItemId(itemId);
		cartBean.setPurchased(false);
		cartBean.setQuantity(cartQuantity);
		cartBean.setUserId(userId);
	

		boolean isAdded = new CartProcess().addToCart(cartBean);
		System.out.println("Item Added = " + isAdded);
		List<MobileBean> productList = (List<MobileBean>) request.getSession().getAttribute("productlist");

		if(isAdded) {
			request.setAttribute("itemAdded", "Item added to cart");
		} else {
			request.setAttribute("itemAdded", "Error!! Item not added to cart!");
		}
		
		String url = null;
	
			url = "/mobile.jsp";
			if (isAdded) {
				List<MobileBean> mobileList = (List<MobileBean>) (List<MobileBean>) productList;
				for (MobileBean mobileBean : productList) {
					if (itemId == mobileBean.getMobileId()) {
						mobileBean.setQuantity(mobileBean.getQuantity() - cartQuantity);
					}
				}
			}
		request.getSession().setAttribute("productlist", productList);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
		dispatcher.forward(request, response);

	}
}
