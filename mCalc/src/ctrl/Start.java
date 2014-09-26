package ctrl;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Mortgage;

/**
 * Servlet implementation class Start
 */
@WebServlet(urlPatterns = { "/Start", "/Start/*"})
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
		HttpSession session = request.getSession();
		request.setAttribute("interest", session.getAttribute("interest"));
		request.setAttribute("amortization", session.getAttribute("amortization"));
		request.setAttribute("principal", session.getAttribute("principal"));
		
		request.getRequestDispatcher("/UI.jspx").forward(request, response);
		
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Serving POST " + request.getRequestURI() + "?" + request.getQueryString());
		response.setContentType("text/html");
		String monthlyPayment, jspTarget, principal, amortization, interest;
		
		HttpSession session = request.getSession();
		Mortgage mrtg = (Mortgage) this.getServletContext().getAttribute("model");
		
		
		//Set the default values
		principal = request.getParameter("principal");
		amortization = request.getParameter("amortization");
		interest = request.getParameter("interest");
		
		if (principal == null || principal.length() == 0) {
			if ((principal = (String) session.getAttribute("principal")) == null) {
				principal = this.getServletContext().getInitParameter("principal"); 
			}
		}
		
		if (amortization == null || amortization.length() == 0) {
			if ((amortization = (String) session.getAttribute("amortization")) == null) {
				amortization = this.getServletContext().getInitParameter("amortization"); 
			}
		}
		
		if (interest == null || interest.length() == 0) {
			interest = this.getServletContext().getInitParameter("interest");
		}
		
		request.setAttribute("interest", interest);
		request.setAttribute("amortization", amortization);
		request.setAttribute("principal", principal);
		
		try {
			monthlyPayment = String.format("%.2f", mrtg.computePayment(principal, amortization, interest));
			request.setAttribute("monthlyPayment", monthlyPayment);
			jspTarget = "/Result.jspx";
			
			//Store session on valid input ONLY
			session.setAttribute("interest", interest);
			session.setAttribute("amortization", amortization);
			session.setAttribute("principal", principal);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			jspTarget = "/UI.jspx";
		}
		
		request.getRequestDispatcher(jspTarget).forward(request, response);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		this.getServletContext().setAttribute("model", new Mortgage());
	}

}
