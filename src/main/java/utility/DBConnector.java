package utility;

import java.sql.*;

public class DBConnector {
	Connection con;
	ReadPropFile db = new ReadPropFile();

	public DBConnector() throws SQLException, ClassNotFoundException {

		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(
				"jdbc:mysql://"+db.getDBData().getProperty("HostName")+":3306/"+db.getDBData().getProperty("DefaultSchema"),
				db.getDBData().getProperty("Username"), db.getDBData().getProperty("Password"));
	}

	public ResultSet executeQuery(String query) throws SQLException {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			return rs;
	}
}
