package model;

public class StudentBean {
	
	private String name;
	private String major;
	private int courses;
	private double gpa;

	public StudentBean(String name, String major, int courses, double gpa) {
		super();
		this.name = name;
		this.major = major;
		this.courses = courses;
		this.gpa = gpa;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the major
	 */
	public String getMajor() {
		return major;
	}

	/**
	 * @param major the major to set
	 */
	public void setMajor(String major) {
		this.major = major;
	}

	/**
	 * @return the courses
	 */
	public int getCourses() {
		return courses;
	}

	/**
	 * @param courses the courses to set
	 */
	public void setCourses(int courses) {
		this.courses = courses;
	}

	/**
	 * @return the gpa
	 */
	public double getGpa() {
		return gpa;
	}

	/**
	 * @param gpa the gpa to set
	 */
	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

}
