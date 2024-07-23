
package com.Servlet;

import java.io.IOException;

import com.Servlet.DataBaseConnections.DataBase_Connections;
import com.Servlet.UserDetails.UserDetails;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GetServlet extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		

	 int userId =Integer.parseInt(request.getParameter("userId"));
	 
	 DataBase_Connections databasecon = new DataBase_Connections();
	 
	 UserDetails userDetails  =  databasecon.getServlet(userId);
	 
	 request.setAttribute("go", userDetails);
	 
	 
	 RequestDispatcher requestforjspfiles =request.getRequestDispatcher("usermessage.jsp");
	 requestforjspfiles.forward(request, response);
	 
	 
	}




}
