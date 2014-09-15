

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
		System.out.println("Serving " + request.getRequestURI() + "?" + request.getQueryString());
		
		// Redirect client to Start on Startup/YorkBank
		if (request.getRequestURI().equals(request.getContextPath() + "/Startup/YorkBank")) { 
			response.sendRedirect(request.getContextPath() + "/Start");
		};
		
		HttpSession session = request.getSession();
		
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		
		out.println("Mortgage Calculator");
		
		
		double principle, amortization = 0.0;
		
		if (request.getParameter("principle") != null) { 
			principle = Double.parseDouble(request.getParameter("principle")); //Get from query string
		} else {
			principle = (double) (session.getAttribute("principle") != null ?  
					session.getAttribute("principle") :  //Try session storage first
						Double.parseDouble(this.getServletContext().getInitParameter("principle"))); //Default value
		}
		
		if (request.getParameter("amortization") != null) { 
			amortization = Double.parseDouble(request.getParameter("amortization")); 
		} else {
			amortization = (double) (session.getAttribute("amortization") != null ? 
					session.getAttribute("amortization") : 
						Double.parseDouble(this.getServletContext().getInitParameter("amortization")));
		}
		
		double interest = request.getParameter("interest") != null ? 
				Double.parseDouble(request.getParameter("interest")) : 
					Double.parseDouble(this.getServletContext().getInitParameter("interest"));
		
		//Do the magic
		double monthlyPayment = (interest * principle) / (1 - Math.pow(1 + interest,-amortization));
		
		//Print output
		out.println("Principle\t:\t$" + principle);
		out.println("Amortization\t:\t$" + amortization);
		out.println("Interest\t:\t$" + interest);
		out.printf("Monthly payment\t:\t$%.2f", monthlyPayment);
		
		//Store values in session for future use
		session.setAttribute("principle", principle);
		session.setAttribute("amortization", amortization);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
