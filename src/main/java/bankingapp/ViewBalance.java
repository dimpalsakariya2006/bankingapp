package bankingapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ViewBalance extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		ServletContext sc = getServletContext();
		Connection con = (Connection) sc.getAttribute("connection");
		String accno= (String) sc.getAttribute("accno");
		try {
		PreparedStatement ps = con.prepareStatement("select balance,Account_number,Account_Holder_name from AccountHolder where Account_number=?");
		ps.setString(1, accno);
	
		ResultSet rs= ps.executeQuery();
		while(rs.next())
		{
		double balance = rs.getDouble(1);
		
		out.print("<html><head><link rel='stylesheet' href='style.css'></head><body>");
		out.print("<br><br>");
		out.print("<p style='margin-left:40%'><b> Account Holder name : </b>"+rs.getString(3));
		out.print("<br><b>Account number : </b>"+rs.getString(2));
		out.print("<br><b>Avalilable Balance : </b>"+"$ "+balance +"</p>");
		out.print("<form action = 'account.jsp' id='debit'>");
    	out.print("<input type='submit' value= 'Continue' id='continue'>");
    	out.print("</form>");
    	out.print("<form action = 'home.jsp' id='debit'>");
    	out.print("<input type='submit' value= 'Home' id='continue'>");
    	out.print("</form>");
    	out.print("</body></html>");
    	
		}
	}catch(Exception e)
		{
		e.printStackTrace();
		}
	}

}
