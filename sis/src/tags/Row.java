package tags;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;

import java.io.*;

public class Row extends SimpleTagSupport {
	private static final double GPA_TO_COMPARE = 5.0;
	private static final char RIGHT_ARROW = 0x2af8; 
	private static final char LEFT_ARROW = 0x2af7; 
	private static final char BULLET = 0x2b24;
	private static final String COLOR_INCREASING = "blue";
	private static final String COLOR_DECREASING = "red";

	private String name;
	private String major;
	private int courses;
	private double gpa;

	public void doTag() throws JspException, IOException {
		JspWriter os = this.getJspContext().getOut();
		os.write("<td> " + name + " </td> <td> " + major + " </td> <td> "
				+ courses + "</td> <td> " + gpa + "</td> <td>"
				+ generateIndicator(gpa) + "</td>");
	}

	private String generateIndicator(double g) {
		char charToAppend;
		String color;
		boolean isIncrease = g > GPA_TO_COMPARE ? true : false;
		
		if (isIncrease) {
			charToAppend = RIGHT_ARROW;
			color = COLOR_INCREASING;
		} else {
			charToAppend = LEFT_ARROW;
			color = COLOR_DECREASING;
		}
		
		double idx = Math.round(Math.abs((GPA_TO_COMPARE * 10) - (g * 10)));
		StringBuffer sb = new StringBuffer();
		while (idx >= 1) {
			sb.append(charToAppend);
			idx -= 1;
		}

		if (isIncrease) sb.append(BULLET);
		else sb.insert(0, BULLET);

		return "<span style='color: " + color + "'>" + sb.toString()
				+ "</span>";
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param major
	 *            the major to set
	 */
	public void setMajor(String major) {
		this.major = major;
	}

	/**
	 * @param course
	 *            the course to set
	 */
	public void setCourses(int courses) {
		this.courses = courses;
	}

	/**
	 * @param gpa
	 *            the gpa to set
	 */
	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

}