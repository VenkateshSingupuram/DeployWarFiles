package com.Demo1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DemoDownload extends HttpServlet {
	Statement statement;
	Connection connection = null;
	String filePath = "C:\\Users\\SKTS_Admin_02\\eclipse-workspace\\Tomcat\\UserChoice1\\Properties_Files\\DBDetails.properties";
	PrintWriter printWriter;
	Workbook workbook;
	ResultSet resultSet;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			doDownload(request, response);
			closingStatements();
		} catch (ClassNotFoundException | ServletException | IOException | SQLException e) {
			printWriter.print("Error Of the GOGET Method , Could you Plz Rectify the Errro...........");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			doDownload(request, response);   // Calling the Method
			closingStatements();
		} catch (ClassNotFoundException | ServletException | IOException | SQLException e) {
			printWriter.print("Error in doPost Method Check Once Clearly..............");
		}
	}

	protected void doDownload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException, ClassNotFoundException {
		File file = new File(filePath);
		FileInputStream fileInput = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(fileInput);
		String dbDriver = properties.getProperty("dbDriver");
		String url = properties.getProperty("url");
		String userName = properties.getProperty("userName");
		String passWord = properties.getProperty("passWord");

		Class.forName(dbDriver);
		connection = DriverManager.getConnection(url, userName, passWord);
		statement = connection.createStatement(1005, 1007);    // It will Moving into Exact Index
 
		int pageNumber = Integer.parseInt(request.getParameter("pageno")); // Getting the request from user.
		int startIndex = (pageNumber - 1) * 10;

		// Why I am Write Below the Query My Requriement table data 10

		resultSet = statement.executeQuery("SELECT * FROM userchoice LIMIT 10 OFFSET "+ startIndex);
 //   
		// Create workbook and sheet

		workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("User Details");

		// This is table headers

		ResultSetMetaData metaData = resultSet.getMetaData();
		int columnCount = metaData.getColumnCount();
		Row headerRow = sheet.createRow(0);
		for (int i = 1; i <= columnCount; i++) {
			headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
		}

		// Write table data
		int rowCount = 1;
		while (resultSet.next()) {
			Row row = sheet.createRow(rowCount++);
			for (int i = 1; i <= columnCount; i++) {
				row.createCell(i - 1).setCellValue(resultSet.getString(i));
			}
		}

		// Write workbook to response output stream
		 response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.setHeader("Content-Disposition", "attachment; filename=userdetails.xlsx");
	        workbook.write(response.getOutputStream());


	}

	public void closingStatements() {
		try {
			workbook.close();
			resultSet.close();
			printWriter.close();
			statement.close();
			connection.close();

		} catch (Exception e) {
			printWriter.print("Exception for Closing Statment Because not Closing ,Properlly.");
		}
	}
}
