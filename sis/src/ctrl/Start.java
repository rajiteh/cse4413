package ctrl;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.*;


/**
 * Servlet implementation class Start
 */
@WebServlet("/Start")
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String MODEL_KEY = "com.sis.ctrl.Start.SIS";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Start() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init();
		try {
			config.getServletContext().setAttribute(MODEL_KEY, new SIS());
		} catch (NamingException e) {
			e.printStackTrace();
			throw new ServletException("Unable to create database connection");
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getAttribute("sortBy") == null) request.setAttribute("sortBy" , SIS.SORT_BY_DEFAULT.get("key"));
		request.setAttribute("sortByOptions", SIS.SORT_BY_OPTIONS);
		request.getRequestDispatcher("/Form.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		

		SIS model = (SIS) request.getServletContext().getAttribute(MODEL_KEY);
		
		if (request.getParameter("report") != null || request.getParameter("generate") != null) {
			String namePrefix = request.getParameter("prefix");
			String gpa = request.getParameter("gpa");
			String sortBy = request.getParameter("sortby");
			try {
				request.setAttribute("prefix", namePrefix);
				request.setAttribute("minGPA", gpa);
				request.setAttribute("sortBy", sortBy);
				request.setAttribute("results", model.retrieve(namePrefix, gpa, sortBy));
			} catch (Exception e) {
				request.setAttribute("error", e.getMessage());
				e.printStackTrace();
			}
		}
		
		doGet(request,response);
	}

}
