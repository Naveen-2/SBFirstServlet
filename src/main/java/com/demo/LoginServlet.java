package com.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
		description = "Login Servlet Testing",
		urlPatterns = {"/LoginServlet"},
		initParams = {
				@WebInitParam(name = "user", value = "Naveen"),
				@WebInitParam(name = "password", value= "QWE!@#qwe123")
		}
)

public class LoginServlet extends HttpServlet {
	 private static final String FIRST_NAME_PATTERN="^[A-Z]{1}[a-zA-Z]{2}[a-zA-z0-9]*";
	 private static final String PASSWORD_PATTERN="^(?=.*[@#$%^&+=])(?=.*[0-9])(?=.*[A-Z]).{8,}$";
	 
	 @Override
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		
		
		String userID = getServletConfig().getInitParameter("user");
		String password = getServletConfig().getInitParameter("password");
		
//		boolean checkFirstName = checkFirstName(request, response, validateFirstName(userID));
//		boolean checkPassword = checkPassword(request, response, validatePassword(pwd));
		
		if(validateFirstName(user)) {
			PrintWriter out = response.getWriter();
			if(validatePassword(pwd)) {
				if(userID.equals(user) && password.equals(pwd)) {
					request.setAttribute("user", user);
					request.getRequestDispatcher("LoginSuccess.jsp").forward(request, response);
				} else {
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
					
					out.println("<font color = red><br>User Name & Password are not matching.");
					rd.include(request, response);
				}
			} else {
				out.println("<font color = red><br>Password is invalid.");
			}
			
		} else {
			PrintWriter out = response.getWriter();
			out.println("<font color = red><br>User Name is invalid.");
		}
		
//		if(userID.equals(user) && password.equals(pwd)) {
//			request.setAttribute("user", user);
//			request.getRequestDispatcher("LoginSuccess.jsp").forward(request, response);
//		} else {
//			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
//			PrintWriter out = response.getWriter();
//			out.println("<font color = red><br>User Name & Password are invalid.");
//			rd.include(request, response);
//		}
	}
	 private boolean checkPassword(HttpServletRequest req, HttpServletResponse resp, boolean validatePassword) throws IOException {
	        if(validatePassword == false){
	            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
	            PrintWriter out = resp.getWriter();
	            out.println("<font color=red>Invalid Password: <br>Rule 1: minimum 8 Characters - <br>Rule 2: Should have at least 1 UpperCase- <br>Rule 3: Should have at least 1 numeric number in the password - <br>Rule 4: Has exactly 1 Special Character</font>");
	            return false;
	        }
	        return true;
	    }


	    private boolean validatePassword(String pwd) {
	        Pattern check = Pattern.compile(PASSWORD_PATTERN);
	        boolean value = check.matcher(pwd).matches();
	        return value;
	    }

	    private boolean checkFirstName(HttpServletRequest request, HttpServletResponse response, boolean validateFirstName) throws IOException, ServletException {
	        if (validateFirstName==false){
	            RequestDispatcher rd=getServletContext().getRequestDispatcher("/login.jsp");
	            PrintWriter out=response.getWriter();
	            out.println("<font color=red>User name is Incorrect</font>");
	            rd.include(request,response);
	            return false;
	        }
	        return true;
	    }

	    private boolean validateFirstName(String firstName) {

	        Pattern check= Pattern.compile(FIRST_NAME_PATTERN);
	        boolean value=check.matcher(firstName).matches();
	        return value;
	    }
}
