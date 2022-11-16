package bankingapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Account extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String accno = request.getParameter("accno");
		String PIN = request.getParameter("PIN");
		ServletContext sc = getServletContext();
		Connection con = (Connection) sc.getAttribute("connection");
		sc.setAttribute("accno", accno);
		try {
			PreparedStatement ps = con.prepareStatement("select PIN,Question1,Answer1,Question2,Answer2,Question3,Answer3,Question4,Answer4 from AccountHolder where Account_number=?");
			ps.setString(1, accno);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				String PIN1 = rs.getString(1);
				if(PIN.equals(PIN1))
                {
                	out.print("<h2 >Successfully Entered into your account</h2>");
                	out.print("<div class = 'quepage'><p><br><b>Account Number : "+accno+"</b><br></p>");
                	
                	//store all security questions in list
                	List<String> questions = new ArrayList<String>();
                	questions.add(rs.getString(2));
                	questions.add(rs.getString(4));
                	questions.add(rs.getString(6));
                	questions.add(rs.getString(8));
                	System.out.println(questions);  
                	
                	//store all security answers in list
                	List<String> answers = new ArrayList<String>();
                	answers.add(rs.getString(3));
                	answers.add(rs.getString(5));
                	answers.add(rs.getString(7));
                	answers.add(rs.getString(9));
                	System.out.println(answers);
                	
                	Random r = new Random();
                	int i = r.nextInt(4);
                	System.out.println(i);
                	
                	String que = questions.get(i);
                	System.out.println(que);
                	
                	String ans = answers.get(i);
                	System.out.println(ans);
                	
                	sc.setAttribute("ans", ans);
    
                	out.print("<html><head><link rel='stylesheet' href='style.css'></head><body>");
                	out.print("<form action = 'anscheck.jsp'>");
                	out.print("<p><b>Secutity Question : </b>"+que+"</p>");
                	out.print("<label for='Answer' id='ans'><b>  Answer : </b></label><input type='text' name = 'ans1' id='ans1'><br></div>");
                	out.print("<br><br><input type='submit'id='next1' value= 'Next'>");
                	out.print("</form>");
                	out.print("</body></html>");
                		
                } 
				else
				{
					out.print("<html><head><link rel='stylesheet' href='style.css'></head><body>");
					out.print("<b>Please Enter correct Account Number and PIN</b>");
                	RequestDispatcher rd = request.getRequestDispatcher("account.jsp");
                	rd.include(request, response);
                	out.print("</body></html>");
					
				}
			}
			
			
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
