

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Start
 */
@WebServlet("/Start")
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Start() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Serving GET " + request.getRequestURI() + "?" + request.getQueryString());
		
		// Redirect client to Start on Startup/YorkBank
		if (request.getRequestURI().equals(request.getContextPath() + "/Startup/YorkBank")) { 
			response.sendRedirect(request.getContextPath() + "/Start");
			return;
		};
		
		request.getRequestDispatcher("/UI.jspx").forward(request, response);
		
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Serving POST " + request.getRequestURI() + "?" + request.getQueryString());
		response.setContentType("text/html");
		
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		//Set the default values
		double principle = Double.parseDouble(this.getServletContext().getInitParameter("principle")); 
		double amortization = Double.parseDouble(this.getServletContext().getInitParameter("amortization"));
		double interest = Double.parseDouble(this.getServletContext().getInitParameter("interest"));
		
		if (request.getParameter("principle") != null && request.getParameter("principle").length() > 0 ) { 
			principle = Double.parseDouble(request.getParameter("principle")); //Get from query string
		} else {
			principle = (double) (session.getAttribute("principle") != null ? session.getAttribute("principle") : principle);
		}
		
		if (request.getParameter("amortization") != null && request.getParameter("amortization").length() > 0 ) { 
			amortization = Double.parseDouble(request.getParameter("amortization")); 
		} else {
			amortization = (double) (session.getAttribute("amortization") != null ? session.getAttribute("amortization") : amortization);
		}
		
		interest = request.getParameter("interest") != null && request.getParameter("interest").length() > 0 ? Double.parseDouble(request.getParameter("interest")) : interest;
		
		//Do the magic
		double monthlyPayment = (interest * principle) / (1 - Math.pow(1 + interest,-amortization));
		
		String htmlOut =  
		"<html>" +
		"<head>" +
		"<title> Monthly Payment </title>" +
		"<link rel=\"stylesheet\" href=\"/mCalc/res/mc.css\" type=\"text/css\" title=\"cse4413\" media=\"screen, print\"/>" +
		"</head>" +
		"<body>" +
			"<form target='' method='GET'>" +
				"<FIELDSET>" +
					"<LEGEND>Mortgage Calc</LEGEND>" +
					"<label for=\"principle\" accesskey=\"p\">Principle</label>" +
					"<input disabled type=\"text\" name=\"principle\" id=\"cash\" value=\"" + principle + "\"/>" +
					"<br/>" +
					"<label for=\"interest\" accesskey=\"i\">Interest</label>" +
					"<input disabled type=\"text\" name=\"interest\" id=\"interest\" value=\"" + interest + "\"/>" +
					"<br/>" +
					"<label for=\"amortization\" accesskey=\"a\">Amortization</label>" +
					"<div class=\"radio\">" +
						"<input disabled " + (amortization == 20 ? "checked" : "") + " type=\"radio\" id=\"amort20\" name=\"amortization\" value=\"20\"/><label for=\"amort20\">20</label>" +
						"<input disabled " + (amortization == 25 ? "checked" : "") + " type=\"radio\" id=\"amort25\" name=\"amortization\" value=\"25\"/><label for=\"amort25\">25</label>" +
						"<input disabled " + (amortization == 30 ? "checked" : "") + " type=\"radio\" id=\"amort30\" name=\"amortization\" value=\"30\"/><label for=\"amort30\">30</label>" +
					"</div>" +
					"<br/>" +
					"<label for=\"monthylpayment\" accesskey=\"m\">Monthly Payment</label>" +
					"<input disabled type=\"text\" name=\"monthlypayment\" id=\"monthlypayment\" value=\"" + String.format("%.2f", monthlyPayment) + "\"/>" +
					"<br/>" +
					"<input type=\"submit\" name=\"restart\" value=\"Restart\"/>" +
				"</FIELDSET>" +
			"</form>" +
		"</body>" +
		"</html>";
		
		out.println(htmlOut);
		
		//Store values in session for future use
		session.setAttribute("principle", principle);
		session.setAttribute("amortization", amortization);
	}

}
