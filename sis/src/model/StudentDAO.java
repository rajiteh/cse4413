package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class StudentDAO {
	private static final String DATASOURCE_CONTEXT = "java:/comp/env/jdbc/EECS";
	private static final String SCHEMA = "roumani";
	private static final String FLAG_NO_SORT = "none";
	
	private DataSource dataSource;
	

	public StudentDAO() throws NamingException {
		Context initialContext = new InitialContext();
		dataSource = (DataSource) initialContext.lookup(DATASOURCE_CONTEXT);
	}

	public List<StudentBean> retrieve(String namePrefix, double gpa)
			throws SQLException {
		return retrieve(namePrefix, gpa, FLAG_NO_SORT);
	}

	public List<StudentBean> retrieve(String namePrefix, double gpa,
			String sortBy) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		List<StudentBean> retval = new ArrayList<StudentBean>();
		
		boolean sorting = sortBy.equals(FLAG_NO_SORT) ? false : true;
		String query = "SELECT surname, givenname, major, courses, GPA FROM SIS WHERE gpa >=? AND surname LIKE ?";
		query += (sorting ? " ORDER BY " + sortBy : "");

		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setDouble(1, gpa);
			preparedStatement.setString(2, namePrefix + "%");
			
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String fullName = resultSet.getString(1) + ", " + resultSet.getString(2);
				String major = resultSet.getString(3);
				int courses = resultSet.getInt(4);
				double gradepointavg = resultSet.getDouble(5);				
				retval.add(new StudentBean(fullName , major, courses, gradepointavg));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			//Make sure we release the connection.
			if (connection != null)
				connection.close();
		}

		return retval;

	}

	private Connection getConnection() throws SQLException {
		Connection c = dataSource.getConnection();
		c.createStatement().executeUpdate("set schema " + SCHEMA);
		return c;

	}
}
