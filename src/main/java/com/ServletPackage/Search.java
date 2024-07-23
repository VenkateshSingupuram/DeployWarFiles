package com.ServletPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Search extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String rollNo = request.getParameter("rollNo");
		try {
		Class.forName("org.postgresql.Driver");
		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ServletProgramDataBase","postgres","postgres");
	PreparedStatement preparedStatement = 	connection.prepareStatement("select * from userChoice where userId = ?");
	
	preparedStatement.setString(1, rollNo);
	out.println("<table width =75% border=1>");
	out.println("<caption> Student Details : </caption>");
	
	ResultSet resultSet = preparedStatement.executeQuery();
	
	ResultSetMetaData rsmd = resultSet.getMetaData();
	
	int totalColumn =rsmd.getColumnCount();
	out.print("<tr>");
	for(int i=1;i<totalColumn;i++) {
		out.print("<th>"+rsmd.getColumnName(i)+"</th>");
		
	}
	out.print("</tr>");
	while(resultSet.next()) {
		out.print("<tr><td>"+resultSet.getInt(1)+"</td><td>"+resultSet.getString(2)+"</td><td>"+resultSet.getString(3)+"</td><td>"+resultSet.getString(4)+"</td></tr>");
		
	}
	out.print("</table>");
	
		}
		catch (Exception e) {
        
			e.printStackTrace();
}
	}

}
