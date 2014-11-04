package model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sisReport")
public class ListWrapper {
	@XmlAttribute(name="namePrefix")
	private String namePrefix;
	@XmlAttribute(name="minGPA")
	private double minGPA;
	@XmlAttribute(name="orderBy")
	private String orderBy;
    @XmlElement(name="student")
	private List<StudentBean> list;
	
	public ListWrapper(String namePrefix, double minGPA, String orderBy,
			List<StudentBean> list) {
		this.namePrefix = namePrefix;
		this.minGPA = minGPA;
		this.orderBy = orderBy;
		this.list = list;
	}
	
	public ListWrapper() {};

}
