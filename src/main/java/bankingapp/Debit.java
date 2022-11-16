package bankingapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class Debit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	
	
		Double debit = Double.parseDouble(request.getParameter("debit"));
		
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
		if(balance<debit)
		{
			
			out.print("<b>Insufficient Balance </b>");
			RequestDispatcher rd = request.getRequestDispatcher("account.jsp");
			rd.include(request, response);
			
		}
		else {
			
		double newbalance = balance-debit;
		out.print("<h3> Available Balance : $"+(balance-debit)+"</h3>");
		PreparedStatement ps1 = con.prepareStatement("update AccountHolder set balance = ? where Account_number = ?");
		ps1.setDouble(1, newbalance);
		ps1.setString(2, accno);
		int row = ps1.executeUpdate();
		System.out.println(row+" row is updated");
		
		out.print("<html><head><link rel='stylesheet' href='style.css'></head><body>");
		out.print("<form action = 'account.jsp' class='debit'>");
		out.print("<h3>Please take your Money Now..$ "+debit+"</h3>");
    	out.print("<input type='submit' value= ' Continue 'id='continue'>");
    	out.print("</form>");
    	out.print("<form action = 'home.jsp' class='debit'>");
    	out.print("<input type='submit' value= ' Home 'id='continue'>");
    	out.print("</form>");
    	out.print("</body></html>");
		}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
