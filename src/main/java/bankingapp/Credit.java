package bankingapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Credit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	
		Double credit = Double.parseDouble(request.getParameter("credit"));
		
		ServletContext sc = getServletContext();
		Connection con = (Connection) sc.getAttribute("connection");
		String accno= (String) sc.getAttribute("accno");
		try {
		PreparedStatement ps = con.prepareStatement("select balance from AccountHolder where Account_number=?");
		ps.setString(1, accno);
	
		ResultSet rs= ps.executeQuery();
		while(rs.next())
		{
		double balance = rs.getDouble(1);
			
		double newbalance = balance+credit;
		out.print("<h2>Available Balance : $"+(balance+credit)+"</h2>");
		PreparedStatement ps1 = con.prepareStatement("update AccountHolder set balance = ? where Account_number = ?");
		ps1.setDouble(1, newbalance);
		ps1.setString(2, accno);
		int row = ps1.executeUpdate();
		System.out.println(row+" row is updated");
		
		out.print("<html><head><link rel='stylesheet' href='style.css'></head><body>");
		out.print("<form action = 'account.jsp' class='debit'>");
		out.print("<h3>Your Money is credited into your account..$ "+credit+"</h3>");
    	out.print("<br><br><input type='submit' value= 'Continue' id='next'>");
    	out.print("</form>");
    	out.print("<form action = 'home.jsp'  class='debit'>");
    	out.print("<input type='submit' value= 'Home' id='next'>");
    	out.print("</form>");
    	out.print("</body></html>");
		
		}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
