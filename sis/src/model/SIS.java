package model;

import java.io.FileWriter;
import java.io.StringWriter;
import java.security.InvalidParameterException;
import java.sql.SQLException;
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

	// Constants defining sort by methods
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

	// Expose sort by methods as an array of k,v pairs
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

	private String sanitizeSortBy(String s) {
		for (Map<String, String> option : SORT_BY_OPTIONS)
			if (option.get("key").equals(s))
				return s;
		throw new InvalidParameterException("Invalid sort method received");
	}

	private double sanitizeGPA(String gpa) {
		double r = Double.parseDouble(gpa);
		if (r < 0.0 || r > 8.0)
			throw new InvalidParameterException(
					"GPA must be between 0.0 and 8.0");
		return r;
	}

	private String sanitizeName(String namePrefix) {
		if (namePrefix.length() < 1)
			throw new InvalidParameterException("Name prefix must not be empty");
		return namePrefix;
	}

	public List<StudentBean> retrieve(String namePrefix, String gpa, String s)
			throws SQLException {
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

	public void export(String namePrefix, String minGPA, String sort,
			String filename) throws Exception {
		System.out.println("REACH");
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
