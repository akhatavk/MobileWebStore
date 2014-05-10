package com.cmpe281.project.persistent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.cmpe281.project.beans.CartBean;
import com.cmpe281.project.beans.CartItemsBean;
import com.cmpe281.project.connection.DatabaseConnection;

public class OrderPersistent {
	private final String ORDER_ID = " SELECT ORDER_ID FROM ORDER_DETAILS WHERE (ORDER_STATUS = 'FALSE' OR ORDER_STATUS = 'false') AND USER_ID = ? ";
	private final String CREATE_ORDER = " INSERT INTO  ORDER_DETAILS(USER_ID,ORDER_STATUS) VALUES (?, 'FALSE') ";

	private final String GET_ORDER = " SELECT ORDER_DETAILS.ORDER_ID AS ORDER_ID, ORDER_DETAILS.USER_ID AS USER_ID, ORDER_ITEMS.ORDER_ITEM_ID AS ORDER_ITEM_ID, ORDER_ITEMS.PRODUCT_ID AS PRODUCT_ID,CAST(ORDER_ITEMS.quantity AS UNSIGNED) AS QUANTITY,CAST(PRODUCT.price AS DECIMAL(10,2)) AS PRICE, "
			+ " CAST(ORDER_ITEMS.quantity AS UNSIGNED) * CAST(PRODUCT.price AS DECIMAL(10,2)) AS AMOUNT, PRODUCT.brandName,PRODUCT.mobileName,PRODUCT.quantity,PRODUCT.price,PRODUCT.operatingSystem "
			+ " FROM ORDER_DETAILS INNER JOIN ORDER_ITEMS ON ORDER_DETAILS.ORDER_ID = ORDER_ITEMS.ORDER_ID INNER JOIN PRODUCT ON ORDER_ITEMS.PRODUCT_ID = PRODUCT.ROW_ID "
			+ " WHERE (ORDER_DETAILS.ORDER_STATUS = 'FALSE' OR ORDER_DETAILS.ORDER_STATUS = 'FALSE') AND ORDER_DETAILS.USER_ID = ?";
	
	private final String UPDATE_ORDER_STATUS = " UPDATE ORDER_DETAILS SET ORDER_STATUS = 'TRUE' WHERE ORDER_ID = ? ";
	public int getOrderId(Connection connection, int userId) {
		int orderId = -1;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(ORDER_ID);
			
			statement.setInt(1, userId);
			System.out.println(statement);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				orderId = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection.closeConnection(statement, resultSet);
		}
		return orderId;
	}

	public int createOrder(Connection connection, CartBean cartBean) {
		int orderId = -1;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(CREATE_ORDER, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, cartBean.getUserId());
			System.out.println(statement);
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			while (resultSet.next()) {
				orderId = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection.closeConnection(statement, resultSet);
		}
		return orderId;
	}

	public List<CartItemsBean> getCartItems(Connection connection,int userId) {
		List<CartItemsBean> cartItemsList = new ArrayList<CartItemsBean>();
		CartItemsBean cartItemsBean = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(GET_ORDER);
			//statement.setInt(1, 2);
			statement.setInt(1, userId);
			System.out.println(statement);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				/*
				 * " SELECT ORDER_DETAILS.ORDER_ID AS ORDER_ID,
				 * ORDER_DETAILS.USER_ID AS USER_ID, ORDER_ITEMS.ORDER_ITEM_ID
				 * AS ORDER_ITEM_ID, ORDER_ITEMS.PRODUCT_ID AS PRODUCT_ID,
				 * CAST(ORDER_ITEMS.quantity AS UNSIGNED) AS QUANTITY,
				 * CAST(PRODUCT.COLUMN3 AS DECIMAL(4,2)) AS PRICE, " + "
				 * CAST(ORDER_ITEMS.quantity AS UNSIGNED) * CAST(PRODUCT.COLUMN3
				 * AS DECIMAL(4,2)) AS AMOUNT,
				 * PRODUCT.ORDER_STATUS,PRODUCT.COLUMN2,PRODUCT
				 * .COLUMN3,PRODUCT.COLUMN4
				 * ,PRODUCT.COLUMN5,PRODUCT.COLUMN6,PRODUCT.COLUMN7 " +
				 * " FROM ORDER_DETAILS INNER J" + "
				 */cartItemsBean = new CartItemsBean();
				cartItemsBean.setOrderId(resultSet.getInt("ORDER_ID"));
				cartItemsBean.setUserId(resultSet.getInt("USER_ID"));
				cartItemsBean.setOrderItemId(resultSet.getInt("ORDER_ITEM_ID"));
				cartItemsBean.setProductId(resultSet.getInt("PRODUCT_ID"));
				cartItemsBean.setQuantity(resultSet.getInt("QUANTITY"));
				cartItemsBean.setPrice(resultSet.getFloat("PRICE"));
				cartItemsBean.setAmount(resultSet.getFloat("AMOUNT"));
				cartItemsBean.setBrandName(resultSet.getString("brandName"));
				cartItemsBean.setMobileName(resultSet.getString("mobileName"));
				cartItemsBean.setPrice(resultSet.getString("price"));
				cartItemsBean.setOperatingSystem(resultSet.getString("operatingSystem"));		
				
				
				cartItemsList.add(cartItemsBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection.closeConnection(statement, resultSet);
		}
		return cartItemsList;
	}
	
	public boolean updateOrderDetails(Connection connection, int orderId){
		boolean isSuccess = true;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(UPDATE_ORDER_STATUS);
			statement.setInt(1, orderId);
			System.out.println(statement);
			statement.executeUpdate();
			isSuccess = true;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection.closeConnection(statement, null);
		}
		return isSuccess;

	}
}
