package com.Servlet.DataBaseConnections;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.System.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.LookAndFeel;

import org.apache.catalina.connector.Response;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

import com.Servlet.UserDetails.UserDetails;

public class DataBase_Connections {
	static String filePath = "C:\\Users\\SKTS_Admin_02\\eclipse-workspace\\Tomcat\\LoginUserDetails\\PropertiesFile\\DataBaseDetails.properties";
	static String loggersPath = "C:\\Users\\SKTS_Admin_02\\eclipse-workspace\\Tomcat\\LoginUserDetails\\Loggers.properties";

	private static org.apache.log4j.Logger loggers = LogManager.getLogger(DataBase_Connections.class);

	static {
		try {
			PropertyConfigurator.configure(loggersPath);
		} catch (Exception e) {

			loggers.debug("Error of the Catch Block .........");
		}
	}  

	public UserDetails getServlet(int userId) throws IOException {

		File file = new File(filePath);
		FileInputStream fileInput = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(fileInput);
		String dbDriver = properties.getProperty("dbDriver");
		String url = properties.getProperty("url");
		String userName = properties.getProperty("userName");
		String passWord = properties.getProperty("passWord");
		Connection connection = null;
		PreparedStatement preparedStatement = null;
	

		UserDetails userDetails = new UserDetails(); // UserDetails Object.
		try {

			Class.forName(dbDriver);
			connection = DriverManager.getConnection(url, userName, passWord);
			preparedStatement = connection.prepareStatement("select * from userlogin where userId =" + userId);
			
		
			ResultSet resultSet = preparedStatement.executeQuery();
    
			ResultSetMetaData resultForTable =resultSet.getMetaData();
			int totalColumn = resultForTable.getColumnCount();
		
			if (resultSet.next()) {
				userDetails.setUserId(resultSet.getInt("userId"));
				userDetails.setUserName(resultSet.getString("userName"));
				userDetails.setLocation(resultSet.getString("userLocation"));
				userDetails.setProfessional(resultSet.getString("userProfessional"));
			}
   loggers.info("</table>");
		} catch (Exception e) {
			loggers.error("Error Occurred in the Code");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {

				loggers.warn("SQL Exception Occurred , Connection not Closed Properly");
			}
		}
		return userDetails;
	}
}
