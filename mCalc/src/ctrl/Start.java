package ctrl;


import java.io.IOException;
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
		
		if (request.getParameter("ajax") != null) { 
			String msg = "<select id='ajaxSelect' onchange='updateInterest(this.value);'>"; 
			msg += "<option value='3.5'>" + "3.5 from RBC" + "</option>"; 
			msg += "<option value='3.2'>" + "3.2 from ING" + "</option>"; 
			msg += "<option value='3.1'>" + "3.1 from TD" + "</option>";
			msg += "</select>"; 
			response.getWriter().print(msg);
			return;
		} 
		
		
		// Redirect client to Start on Startup/YorkBank
		if (request.getRequestURI().equals(request.getContextPath() + "/Startup/YorkBank")) { 
			response.sendRedirect(request.getContextPath() + "/Start");
			return;
		};
		HttpSession session = request.getSession();
		request.setAttribute("interest", session.getAttribute("interest"));
		request.setAttribute("amortization", session.getAttribute("amortization"));
		request.setAttribute("principle", session.getAttribute("principle"));
		
		request.getRequestDispatcher("/UI.jspx").forward(request, response);
		
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Serving POST " + request.getRequestURI() + "?" + request.getQueryString());
		response.setContentType("text/html");
		String monthlyPayment, jspTarget, principle, amortization, interest;
		
		HttpSession session = request.getSession();
		Mortgage mrtg = (Mortgage) this.getServletContext().getAttribute("model");
		
		
		//Set the default values
		principle = request.getParameter("principle");
		amortization = request.getParameter("amortization");
		interest = request.getParameter("interest");
		
		if (principle == null || principle.length() == 0) {
			if ((principle = (String) session.getAttribute("principle")) == null) {
				principle = this.getServletContext().getInitParameter("principle"); 
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
		request.setAttribute("principle", principle);
		
		try {
			monthlyPayment = String.format("%.2f", mrtg.computePayment(principle, amortization, interest));
			request.setAttribute("monthlyPayment", monthlyPayment);
			jspTarget = "/Result.jspx";
			
			//Store session on valid input ONLY
			session.setAttribute("interest", interest);
			session.setAttribute("amortization", amortization);
			session.setAttribute("principle", principle);
			
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
