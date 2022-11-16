package bankingapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.x.protobuf.MysqlxCrud.Collection;


public class OpenAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String PIN = request.getParameter("PIN");
		String bdate = request.getParameter("bdate");
		String dlnum = request.getParameter("dlnum");
		String que1 = request.getParameter("que1");
		String que2 = request.getParameter("que2");
		String que3 = request.getParameter("que3");
		String que4 = request.getParameter("que4");
		String ans1 = request.getParameter("ans1");
		String ans2 = request.getParameter("ans2");
		String ans3 = request.getParameter("ans3");
		String ans4 = request.getParameter("ans4");
		String balance = request.getParameter("balance");
		
		LocalDate date = LocalDate.parse(bdate);

  
        int day = date.getDayOfMonth();

        int year =date.getYear();
        String year1 = String.valueOf(year);
    
        String y =year1.substring(1,2);
        String dl = dlnum.substring(2, 6);
        
        if(PIN.length()==4)
        {
        	try {
        	ServletContext sc = getServletContext();
        	Connection con = (Connection) sc.getAttribute("connection");
        	
        	PreparedStatement ps = con.prepareStatement("insert into AccountHolder values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        	
        	//set account number 
            
            String accno = dl + day + y;
            ps.setString(1, null);
            ps.setString(2, name);
            ps.setString(3, type);
            ps.setString(4,bdate);
            ps.setString(5, dlnum);
            ps.setString(6, accno);
            ps.setString(7, PIN);
            ps.setString(8, que1);
            ps.setString(9,ans1);
            ps.setString(10, que2);
            ps.setString(11,ans2);
            ps.setString(12, que3);
            ps.setString(13,ans3);
            ps.setString(14, que4);
            ps.setString(15,ans4);
            ps.setString(16, balance);
            int row = ps.executeUpdate();
            System.out.println(row + " inserted successfully..");
            out.print("<html><head><link rel='stylesheet' href='style.css'></head><body>");
            out.print("<div class='debit'><h3> Successfully opened Account </h3>");
            out.println("<br><b>Account Holder Name : </b>"+name);
            out.println("<br><b>Type of Account : </b>"+type);
            out.println("<br><b>Account Number : </b>"+accno);
            out.print("<br><b>Registered PIN : </b>"+PIN);
            out.print("<br><b>Birth Date : </b>"+bdate);
            out.print("<br><b>Available Balance : </b>"+balance+"</div>");
            
            out.print("<br><br><form action = 'account.jsp' id = 'debit'>");
            out.print("<input type = 'submit' value = 'Get into Your Account' id='next'>");
            out.print("</form>");
            out.print("</body></html>");
        	}catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        	
        }
        else
        {
        	out.print("<html><head><link rel='stylesheet' href='style.css'></head><body>");
        	out.print("<b>Your PIN must be Four Digits</b>");
        	RequestDispatcher rd = request.getRequestDispatcher("openaccount.jsp");
        	rd.include(request, response);
        	out.print("</body></html>");
        }
       
    
    


    
	}

}
