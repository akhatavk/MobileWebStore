package com.cmpe281.project.persistent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cmpe281.project.beans.MobileBean;
import com.cmpe281.project.connection.DatabaseConnection;

public class MobileProductPersistent {
	
	private final String MOBILE_LIST = " SELECT ROW_ID,brandName,mobileName,quantity,price,operatingSystem FROM PRODUCT";

	private final String UPDATE_PRODUCT_QUANTITY = " UPDATE PRODUCT AS PROD INNER JOIN ORDER_ITEMS  AS ITEM ON PROD.ROW_ID = ITEM.PRODUCT_ID  SET PROD.quantity = CAST( (CAST(PROD.quantity AS UNSIGNED) - CAST(ITEM.quantity AS UNSIGNED)) AS CHAR(100)) WHERE ORDER_ID = ? ";

	public List<MobileBean> getMobileList(Connection connection) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<MobileBean> mobileList = new ArrayList<MobileBean>();
		MobileBean mobileBean = null;
		try {
			statement = connection.prepareStatement(MOBILE_LIST);
			//statement.setInt(1, 2);
			System.out.println(statement);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				mobileBean = new MobileBean();
				mobileBean.setMobileId(resultSet.getInt("ROW_ID"));
				mobileBean.setBrandName(resultSet.getString("brandName"));
				mobileBean.setMobileName(resultSet.getString("mobileName"));
				mobileBean.setPrice(Float.parseFloat(resultSet.getString("quantity")));
				mobileBean.setQuantity(Integer.parseInt(resultSet.getString("price")));
				mobileBean.setOperatingSystem(resultSet.getString("operatingSystem"));
				mobileList.add(mobileBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection.closeConnection(statement, resultSet);
		}
		List<MobileBean> productList = mobileList;
		return productList;
	}

	
	
	
	
	public boolean updateQuantity(Connection connection, int orderId){
		boolean isSuccess = true;
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(UPDATE_PRODUCT_QUANTITY);
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
