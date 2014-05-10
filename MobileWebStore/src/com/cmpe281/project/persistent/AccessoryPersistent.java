package com.cmpe281.project.persistent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cmpe281.project.beans.AccessoriesBean;
import com.cmpe281.project.connection.DatabaseConnection;

public class AccessoryPersistent {
private final String MOBILE_ACCESSORY = " SELECT * FROM accessories WHERE MOBILE_ID = ? ";

	public AccessoriesBean getAccessories(Connection connection, int mobileId) {
	AccessoriesBean accessoriesBean = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	try {
		statement = connection.prepareStatement(MOBILE_ACCESSORY);
		statement.setInt(1, mobileId);
		resultSet = statement.executeQuery();
		if (resultSet.next()) {
			accessoriesBean = new AccessoriesBean();
			accessoriesBean.setAccessoryId(resultSet.getInt(1));
			accessoriesBean.setMobileId(resultSet.getInt(2));
			accessoriesBean.setCover(resultSet.getInt(3));
			accessoriesBean.setHeadSet(resultSet.getInt(4));
			accessoriesBean.setScreenGuard(resultSet.getInt(5));
			accessoriesBean.setDataCable(resultSet.getInt(6));
			
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		DatabaseConnection.closeConnection(statement, resultSet);
	}
	return accessoriesBean;
}
}
