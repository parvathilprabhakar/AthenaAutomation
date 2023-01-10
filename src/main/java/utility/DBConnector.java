package utility;

import java.sql.*;
import java.util.ArrayList;

public class DBConnector {
	Connection con;
	ReadPropFile db = new ReadPropFile();

	public DBConnector() throws SQLException, ClassNotFoundException {

		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(
					"jdbc:mysql://"+db.getDBData().getProperty("HostName")+":3306/"+db.getDBData().getProperty("DefaultSchema")+"?useSSL=false",
				db.getDBData().getProperty("Username"), db.getDBData().getProperty("Password"));
	}

	public ResultSet executeQuery(String query) throws SQLException {
		System.out.println(query);
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		return rs;
	}
	public ArrayList getSingleColumnData(ResultSet rs) throws SQLException {
		ArrayList<String> colData = new ArrayList<>();
		while(rs.next()) {
			colData.add(rs.getString(1));
		}
		return colData;
	}
	
	public  void selectAllFromCourses() throws SQLException {
		executeQuery(db.getQuery().getProperty("SelectAllFromCourses"));
	}
	public  void selectAllFromOffers() throws SQLException {
		executeQuery(db.getQuery().getProperty("SelectAllFromOffers"));
	}
	public  void SelectFromCourseEnrollment_Uid(String uid) throws SQLException {
		executeQuery(db.getQuery().getProperty("SelectFromCourseEnrollment_Uid").replace("<Replace>", uid));
	}
	public  void SelectFromOffers_UserID(String userId) throws SQLException {
		executeQuery(db.getQuery().getProperty("SelectFromOffers_UserID").replace("<Replace>", userId));
	}
	public  void SelectFromPaymentDetails_UserID(String userId) throws SQLException {
		executeQuery(db.getQuery().getProperty("SelectFromPaymentDetails_UserID").replace("<Replace>", userId));
	}
	public  String Select_IsClaimed_FromCourseEnrolment_UID_CID(String uid, String cid) throws SQLException {
		ResultSet rs = executeQuery(db.getQuery().getProperty("Select_IsClaimed_FromCourseEnrolment_UID_CID").replace("<Replace_uid>", uid).replace("<Replace_cid>", cid));
		String value = "";
		while (rs.next()) {
			value = rs.getString("is_Claimed");
		}
		return value; //returns only a single value
	}
}
