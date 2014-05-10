package com.cmpe281.project.process;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cmpe281.project.beans.AccessoriesBean;

import com.cmpe281.project.connection.DatabaseConnection;
import com.cmpe281.project.persistent.AccessoryPersistent;


public class AccessoryProcess {
	

	public AccessoriesBean getAccessories( int mobileId) {
		AccessoriesBean accessoriesBean = null;
		Connection connection = DatabaseConnection.getConnection();

		try {
			if (!connection.isClosed()) {
				accessoriesBean = new AccessoryPersistent().getAccessories(connection, mobileId);
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
		return accessoriesBean;
	}
}
