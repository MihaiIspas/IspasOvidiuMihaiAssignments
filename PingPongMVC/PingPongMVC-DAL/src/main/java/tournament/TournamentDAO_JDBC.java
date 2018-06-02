package tournament;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TournamentDAO_JDBC implements TournamentDAO {
	
	Connection connection=null;
	PreparedStatement statement;
	ResultSet rs=null;
	
	String selectAllQuery="select * from `tournament`";
	String selectByIDQuery="select * from `tournament` where `ID`=?";
	String insertString="insert into `tournament` (`Name`,`Status`,`NumberPlayers`,`Price`,`Start_Date`) values (?,?,?,?,?)";
	String updateString="update `tournament` set `Name`=?, `Status`=?, `NumberPlayers`=?, `Price`=?, `Start_Date`=? where `ID`=?";
	String deleteString="delete from `tournament` where `ID`=?";
	
	public TournamentDAO_JDBC() {
		try {
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/pingpong?autoReconnect=true&useSSL=false", "root", "password");
		}
		catch(Exception e) {
			e.getMessage();
		}
	}
	
	public ArrayList<TournamentObj> selectAll() throws SQLException {
		ArrayList<TournamentObj> list=new ArrayList<TournamentObj>();
		statement=connection.prepareStatement(selectAllQuery);
		rs=statement.executeQuery();
		while(rs.next()) {
			//System.out.println(rs.getInt("ID"));
			int id=rs.getInt("ID");
			//System.out.println(id);
			String name=rs.getString("Name");
			String status=rs.getString("Status");
			int nrPlayers=rs.getInt("NumberPlayers");
			float price=rs.getFloat("Price");
			Date date=rs.getDate("Start_Date");
			TournamentObj tour=new TournamentObj(id,name,status,nrPlayers,price,date);
			list.add(tour);
		}
		rs.close();
		statement.close();
		return list;
	}
	
	public TournamentObj selectByID(int ID) throws SQLException {
		TournamentObj tour=null;
		statement=connection.prepareStatement(selectByIDQuery);
		statement.setInt(1, ID);
		rs=statement.executeQuery();
		if(rs.next()) {
			String name=rs.getString("Name");
			String status=rs.getString("Status");
			int nrPlayers=rs.getInt("NumberPlayers");
			float price=rs.getFloat("Price");
			Date date=rs.getDate("Start_Date");
			tour=new TournamentObj(ID,name,status,nrPlayers,price,date);
		}
		rs.close();
		statement.close();
		return tour;
	}
	
	public int insert(String name,String status,int nrPlayers,float price,Date startDate) throws SQLException {
		int ID=0;
		statement=connection.prepareStatement(insertString,Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, name);
		statement.setString(2, status);
		statement.setInt(3, nrPlayers);
		statement.setFloat(4, price);
		statement.setDate(5, startDate);
		statement.executeUpdate();
		ResultSet keys=statement.getGeneratedKeys();
		if(keys.next()) {
			ID=keys.getInt(1);
		}
		statement.close();
		return ID;
	}
	
	public void update(int ID,String name,String status,int nrPlayers,float price,Date date) throws SQLException {
		statement=connection.prepareStatement(selectByIDQuery);
		statement.setInt(1, ID);
		rs=statement.executeQuery();
		TournamentObj tour=null;
		if(rs.next()) {
			String name1=rs.getString("Name");
			String status1=rs.getString("Status");
			int nrPlayers1=rs.getInt("NumberPlayers");
			float price1=rs.getFloat("Price");
			Date date1=rs.getDate("StartDate");
			tour=new TournamentObj(ID,name1,status1,nrPlayers1,price1,date1);
		}
		rs.close();
		statement.close();
		
		if(name==null || name=="") {
			name=tour.getName();
		}
		
		if(status==null || status=="") {
			status=tour.getStatus();
		}
		
		if(nrPlayers==-1) {
			nrPlayers=tour.getNumberPlayers();
		}
		
		if(price==-1) {
			price=tour.getPrice();
		}
		if(date==null) {
			date=tour.getStartDate();
		}
		
		statement=connection.prepareStatement(updateString);
		statement.setString(1, name);
		statement.setString(2, status);
		statement.setInt(3, nrPlayers);
		statement.setFloat(4, price);
		statement.setDate(5, date);
		statement.setInt(6, ID);
		statement.executeUpdate();
		statement.close();
	}
	
	public void delete(int ID) throws SQLException {
		statement=connection.prepareStatement(deleteString);
		statement.setInt(1, ID);
		statement.executeUpdate();
	}

}
