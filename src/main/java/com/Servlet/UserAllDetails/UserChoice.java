package com.Servlet.UserAllDetails;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class UserChoice extends HttpServlet {

	private static final String String = null;
	static String filePath = "C:\\Users\\SKTS_Admin_02\\eclipse-workspace\\Tomcat\\UserChoice1\\Properties_Files\\DBDetails.properties";
	String ExcelPath = "C:\\Users\\SKTS_Admin_02\\Desktop\\01-05-2024\\Desktop Files\\Demo.xlsx";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// DataBase Properties

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

		PrintWriter printWriter = response.getWriter(); // Printing the data for Server Area

		try {
			Class.forName(dbDriver);
			connection = DriverManager.getConnection(url, userName, passWord);
			preparedStatement = connection.prepareStatement("select * from userChoice");

			ResultSet resultSet = preparedStatement.executeQuery();

			ResultSetMetaData resultMetaData = resultSet.getMetaData(); // Returns the number of columns in the result
																		// set.

			int totalColumn = resultMetaData.getColumnCount();

			int rollNo = Integer.parseInt(request.getParameter("rollNo"));

			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Data"); // Excel Sheet Creation inserted the Data from DataBase

			Row headerRow = sheet.createRow(0); // Heading of the Columns in Excel Sheet

			for (int i = 1; i <= totalColumn; i++) {
				headerRow.createCell(i - 1).setCellValue(resultMetaData.getColumnName(i));
			}
			int rowNum = 1;
			while (resultSet.next()) {
				Row row = sheet.createRow(rowNum++);
				for (int i = 1; i <= totalColumn; i++) {
					row.createCell(i - 1).setCellValue(resultSet.getString(i));

				}
				if (rowNum >= rollNo) {
					break;
				}

			}
			FileOutputStream outputStream = new FileOutputStream(ExcelPath);
			workbook.write(outputStream);

			// ----------------------------------------------------

			printWriter.print("<table width=60% border=1>");
			printWriter.print("<caption> USER DETAILS </caption>");

			for (int i = 1; i <= totalColumn; i++) {

				printWriter.print("<th>" + resultMetaData.getColumnName(i) + "</th>");

			}
			while (resultSet.next()) {
				printWriter.print("<tr><td>" + resultSet.getInt(1) + "</td><td>" + resultSet.getString(2) + "</td><td>"
						+ resultSet.getString(3) + "</td><td>" + resultSet.getString(4) + "</td></tr>");
			}

			printWriter.print("</table>");

			printWriter.print("<td><a href='Download=\"+resultSet.getInt(1)+\"'> Download </a></td>");

		} catch (Exception e) {
			printWriter.print("Error for the Data Base");
		} finally {
			printWriter.close();

		}

	}

}
