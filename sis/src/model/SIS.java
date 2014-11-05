package model;

import java.io.FileWriter;
import java.io.StringWriter;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;

@SuppressWarnings("serial")
public class SIS {

	
	/**
	 * A key value pair of a sort by method. 
	 * "key" denotes the column name to be searched in the database.
	 * "value" denotes the human friendly name for the sorting method.
	 */
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

	/**
	 * Default sort by method 
	 */
	public static final Map<String, String> SORT_BY_DEFAULT = SORT_BY_NONE;


	/**
	 * Expose enabled sort by methods as an array 
	 */
	public static ArrayList<Map<String, String>> SORT_BY_OPTIONS = new ArrayList<Map<String, String>>() {
		{
			add(SORT_BY_LAST_NAME);
			add(SORT_BY_MAJOR);
			add(SORT_BY_NUMBER_OF_COURSES);
			add(SORT_BY_GPA);
			add(SORT_BY_NONE);
		}
	};
	
	public static final String NAME_PATTERN = "^[a-zA-Z0-9]*$";
	public static final String NAME_PATTERN_ERROR_MSG = "Must be alphanumeric";
	public static final String GPA_PATTERN = "^([0-9]+(\\.[0-9]+)?)$";
	public static final String GPA_PATTERN_ERROR_MSG = "Must be a valid positive number";
	public static final double MIN_GPA = 0.0;
	public static final double MAX_GPA = 9.0;
	private static final int FLAG_LIKE_PREFIX = 1;
	private static final int FLAG_LIKE_SUFFIX = 2;
	

	private StudentDAO studentDAO;

	public StudentDAO getStudentDAO() {
		return studentDAO;
	}

	public SIS() throws NamingException {
		studentDAO = new StudentDAO();
	}

	/**
	 * Validates a given string against a String regex pattern or raises InvalidParameterException
	 * @param str string to be tested
	 * @param pattern regular expression pattern 
	 * @param error error message to be thrown with InvalidParameterException
	 * @return validated input string
	 */
	private String regexValidate(String str, String pattern, String error) {
		str = str.trim();
		if (str.matches(pattern)) return str;
		else throw new InvalidParameterException(error);
	}
	
	/**
	 * Validates the existence of a given sort by method in the enabled sorting methods specified by SORT_BY_OPTIONS list. Raises InvalidParameterException on not found.
	 * @param s sorting method
	 * @return validated sorting method
	 */
	private String sanitizeSortBy(String s) {
		for (Map<String, String> option : SORT_BY_OPTIONS)
			if (option.get("key").equals(s))
				return s;
		throw new InvalidParameterException("Invalid sort method received");
	}

	private double sanitizeGPA(String gpa) {
		gpa = regexValidate(gpa, GPA_PATTERN, GPA_PATTERN_ERROR_MSG);
		double r = Double.parseDouble(gpa);
		if (r < MIN_GPA || r > MAX_GPA) throw new InvalidParameterException("Must be between " + MIN_GPA + " and " + MAX_GPA);
		else return r;
	}

	private String sanitizeName(String name) {
		return regexValidate(name, NAME_PATTERN, NAME_PATTERN_ERROR_MSG);
	}
	
	/**
	 * Given a string appends one of prefix or suffix operators indicated by a FLAG_LIKE_* for use with a SQL LIKE query 
	 * @param src source string
	 * @param like_flag one of FLAG_LIKE_*
	 * @return source string with like_flag operation performed
	 */
	private String howDoYouLIKEit(String src, int like_flag) {
		switch (like_flag) {
        case FLAG_LIKE_PREFIX:  src = src + "%";
                 break;
        case FLAG_LIKE_SUFFIX:  src = "%" + src;
                 break;
        default:
        	throw new InvalidParameterException("Illegal FLAG_LIKE");
		}
		return src;
	}

	public List<StudentBean> retrieve(String namePrefix, String gpa, String s)
			throws Exception {
		
		String sanePrefix = null;
		double saneGPA = 0.0;
		try {
			sanePrefix = howDoYouLIKEit(sanitizeName(namePrefix), FLAG_LIKE_PREFIX);	
		} catch (Exception e) { 
			throw new Exception("Name Prefix : " + e.getMessage());
		}
		
		try {
			saneGPA = sanitizeGPA(gpa);	
		} catch (Exception e) { 
			throw new Exception("Min GPA : " + e.getMessage());
		}
		
		String saneSortBy = sanitizeSortBy(s);
		List<StudentBean> resultSet;

		if (saneSortBy.equals(SORT_BY_NONE.get("key"))) {
			resultSet = studentDAO.retrieve(sanePrefix, saneGPA);
		} else {
			resultSet = studentDAO.retrieve(sanePrefix, saneGPA, saneSortBy);
		}
		return resultSet;
	}

	public void export(String namePrefix, String minGPA, String sort,
			String filename) throws Exception {
		List<StudentBean> result = retrieve(namePrefix, minGPA, sort);
		ListWrapper listWrapper = new ListWrapper(namePrefix, sanitizeGPA(minGPA), sort, result);
		StringWriter sWriter = null;
		FileWriter fWriter = null;
		try {
			JAXBContext jaxbCtx = JAXBContext.newInstance(listWrapper
					.getClass());
			Marshaller marshaller = jaxbCtx.createMarshaller();
			
			marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION,
					"SIS.xsd");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

			sWriter = new StringWriter();
			sWriter.write("<?xml version='1.0'?>");
			sWriter.write("\n");
			sWriter.write("<?xml-stylesheet type='text/xsl' href='SIS.xsl' ?>");
			sWriter.write("\n");
			marshaller.marshal(listWrapper, new StreamResult(sWriter));

			fWriter = new FileWriter(filename);
			fWriter.write(sWriter.toString());

		} catch (Exception e) {
			throw e;
		} finally {
			if (fWriter != null)
				fWriter.close();
			if (sWriter != null)
				sWriter.close();
		}

	}
}
