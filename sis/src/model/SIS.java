package model;

import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

@SuppressWarnings("serial")
public class SIS {

	//Constants defining sort by methods
	private static final Map<String, String> SORT_BY_LAST_NAME = new HashMap<String, String>() {
		{
			put("key", "surname");
			put("value", "Last Name");
		}
	};
	private static final Map<String, String> SORT_BY_MAJOR = new HashMap<String, String>() {
		{
			put("key", "major");
			put("value", "Major");
		}
	};
	private static final Map<String, String> SORT_BY_NUMBER_OF_COURSES = new HashMap<String, String>() {
		{
			put("key", "courses");
			put("value", "Number of Courses");
		}
	};
	private static final Map<String, String> SORT_BY_GPA = new HashMap<String, String>() {
		{
			put("key", "gpa");
			put("value", "GPA");
		}
	};
	private static final Map<String, String> SORT_BY_NONE = new HashMap<String, String>() {
		{
			put("key", "none");
			put("value", "Select One");
		}
	};
	

	public static final Map<String, String> SORT_BY_DEFAULT = SORT_BY_NONE;
	
	//Expose sort by methods as an array of k,v pairs
	public static ArrayList<Map<String, String>> SORT_BY_OPTIONS = new ArrayList<Map<String, String>>() {
		{
			add(SORT_BY_LAST_NAME);
			add(SORT_BY_MAJOR);
			add(SORT_BY_NUMBER_OF_COURSES);
			add(SORT_BY_GPA);
			add(SORT_BY_NONE);
		}
	};
	
	
	private StudentDAO studentDAO;

	
	public StudentDAO getStudentDAO() {
		return studentDAO;
	}

	public SIS() throws NamingException {
		studentDAO = new StudentDAO();
	}
	
    public List<StudentBean> retrieve(String namePrefix, String gpa, String s) throws SQLException {
    	String sanePrefix = sanitizeName(namePrefix);
    	double saneGpa = sanitizeGPA(gpa);
    	String saneSortBy = sanitizeSortBy(s);
    	List<StudentBean> resultSet;
    	
    	if (saneSortBy.equals(SORT_BY_NONE.get("key"))) {
    		resultSet = studentDAO.retrieve(sanePrefix, saneGpa);
    	} else {
    		resultSet = studentDAO.retrieve(sanePrefix, saneGpa, saneSortBy);
    	}
		return resultSet;
    }

	private String sanitizeSortBy(String s) {
		for(Map<String,String> option : SORT_BY_OPTIONS)
			if (option.get("key").equals(s)) return s;
		throw new InvalidParameterException("Invalid sort method received");
	}

	private double sanitizeGPA(String gpa) {
		double r = Double.parseDouble(gpa);
		if (r < 0.0 || r > 8.0) throw new InvalidParameterException("GPA must be between 0.0 and 8.0");
		return r;
	}

	private String sanitizeName(String namePrefix) {
		if (namePrefix.length() < 1) throw new InvalidParameterException("Name prefix must not be empty");
		
		return namePrefix;
	}

}
