package com.cmpe281.project.process;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.cmpe281.project.beans.MobileBean;
import com.cmpe281.project.connection.DatabaseConnection;
import com.cmpe281.project.persistent.MobileProductPersistent;


public class ProductProcess {
	public List<MobileBean> getProductList() {
		Connection connection = DatabaseConnection.getConnection();
		List<MobileBean> productList = null;
		
		try {
			if (!connection.isClosed()) {
				MobileProductPersistent productPersistent = new MobileProductPersistent();
		
					productList = productPersistent.getMobileList(connection);
			} else {
				System.out.println("Connection problem");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection.closeConnection(connection);
		}
		return productList;
	}
}
