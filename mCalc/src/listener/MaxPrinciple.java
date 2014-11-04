package listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Application Lifecycle Listener implementation class MaxPrinciple
 *
 */
@WebListener
public class MaxPrinciple implements HttpSessionAttributeListener {

	public static final String ATTRIBUTE_PRINCIPLE_MAX = "maxPrinciple";
	public static final String ATTRIBUTE_PRINCIPLE_CURRENT = "principle";
	
    /**
     * Default constructor. 
     */
    public MaxPrinciple() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent arg0)  { 
    	try {
    		if (arg0.getName().equals("principle")) {
        		setMaxPrinciple(arg0);
        	};
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent arg0)  { 
    	try {
    		if (arg0.getName().equals("principle")) {
        		setMaxPrinciple(arg0);
        	};
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    }
	
    
    private void setMaxPrinciple(HttpSessionBindingEvent ev) {
    	double principle, max;
    	principle = Double.parseDouble((String) ev.getSession().getAttribute(ATTRIBUTE_PRINCIPLE_CURRENT));
    	try {
    		max = Double.parseDouble((String) ev.getSession().getServletContext().getAttribute(ATTRIBUTE_PRINCIPLE_MAX));
    	} catch (NullPointerException e) {
    		max = 0.00;
    	}
    	
    	if (principle > max) ev.getSession().getServletContext().setAttribute(ATTRIBUTE_PRINCIPLE_MAX, String.valueOf(principle));
    	System.out.println(String.format("Curr_Prin: %f\nCurr_Max: %f\n", principle, max));
    }

	
}
