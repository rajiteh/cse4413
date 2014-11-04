package filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class Throttle
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST
		}
					, urlPatterns = {"/Start", "/Start/*", "/"})
public class Throttle implements Filter {
	private static final long THROTTLE_PERIOD = 5000; //in ms
    /**
     * Default constructor. 
     */
    public Throttle() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		final String THROTTLE_VALUE_KEY = "throttledSince";
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		if (!(session.isNew() || session.getAttribute(THROTTLE_VALUE_KEY) == null)) {
			long timeSinceLastRequest = System.currentTimeMillis() - (long) session.getAttribute(THROTTLE_VALUE_KEY);
			if (timeSinceLastRequest < THROTTLE_PERIOD) {
				//Set view context
				request.setAttribute("timeToWait", (THROTTLE_PERIOD - timeSinceLastRequest) / 1000 + 1); //one to offset the round
				request.getRequestDispatcher("/throttled.jspx").forward(request, response);
				return; //Cancel the chain
			} else {
				session.setAttribute(THROTTLE_VALUE_KEY, null); //clear throttling
			}
		} 
		session.setAttribute(THROTTLE_VALUE_KEY, session.getLastAccessedTime());
		chain.doFilter(request, response);	
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
