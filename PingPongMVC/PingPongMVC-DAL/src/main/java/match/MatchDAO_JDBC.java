package match;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MatchDAO_JDBC implements MatchDAO {
	
	Connection connection=null;
	PreparedStatement statement;
	ResultSet rs=null;
	
	String selectAllQuery="select * from `game`";
	String selectByIDQuery="select * from `game` where `ID`=?";
	String selectByTournamentIDQuery="select * from `game` where `TournamentID`=?";
	String insertString="insert into `game` (`Name`,`Score`,`Status`,`TournamentID`) values (?,?,?,?)";
	String updateString="update `game` set `Score`=?,`Name`=?,`Status`=?,`TournamentID`=?,`Player1ID`=?,`Player2ID`=?,`WinnerID`=? where `ID`=?";
	String deleteString="delete from `game` where `ID`=?";
	
	public MatchDAO_JDBC() {
		try {
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/pingpong?autoReconnect=true&useSSL=false", "root", "password");
		}
		catch(Exception e) {
			e.getMessage();
		}
	}
	
	public ArrayList<MatchObj> selectAll() throws SQLException {
		ArrayList<MatchObj> list=new ArrayList<MatchObj>();
		statement=connection.prepareStatement(selectAllQuery);
		rs=statement.executeQuery();
		while(rs.next()) {
			int id=rs.getInt("ID");
			String name=rs.getString("Name");
			String score=rs.getString("Score");
			String status=rs.getString("Status");
			Integer tournamentID=rs.getInt("TournamentID");
			Integer player1ID=rs.getInt("Player1ID");
			Integer player2ID=rs.getInt("Player2ID");
			Integer winnerID=rs.getInt("WinnerID");
			MatchObj match=new MatchObj(id,name,score,status,tournamentID,player1ID,player2ID,winnerID);
			list.add(match);
		}
		rs.close();
		statement.close();
		return list;
	}
	
	public MatchObj selectByID(int ID) throws SQLException {
		MatchObj match=null;
		statement=connection.prepareStatement(selectByIDQuery);
		statement.setInt(1, ID);
		rs=statement.executeQuery();
		if(rs.next()) {
			String name=rs.getString("Name");
			String score=rs.getString("Score");
			String status=rs.getString("Status");
			Integer tournamentID=rs.getInt("TournamentID");
			Integer player1ID=rs.getInt("Player1ID");
			Integer player2ID=rs.getInt("Player2ID");
			Integer winnerID=rs.getInt("WinnerID");
			match=new MatchObj(ID,name,score,status,tournamentID,player1ID,player2ID,winnerID);
		}
		rs.close();
		statement.close();
		return match;
	}
	
	public ArrayList<MatchObj> selectByTournamentID(Integer tournamentID) throws SQLException {
		ArrayList<MatchObj> list=new ArrayList<MatchObj>();
		statement=connection.prepareStatement(selectByTournamentIDQuery);
		statement.setInt(1, tournamentID);
		rs=statement.executeQuery();
		while(rs.next()) {
			Integer player1ID=null,player2ID=null,winnerID=null;
			int id=rs.getInt("ID");
			String name=rs.getString("Name");
			String score=rs.getString("Score");
			String status=rs.getString("Status");
			//System.out.println(rs.getInt("Player1ID")+" p1");
			//System.out.println(rs.getInt("Player2ID")+" p2");
			//System.out.println(rs.getInt("WinnerID")+" w");
			if(rs.getInt("Player1ID")!=0) {
				player1ID=rs.getInt("Player1ID");
			}
			if(rs.getInt("Player2ID")!=0) {
				player2ID=rs.getInt("Player2ID");
			}
			if(rs.getInt("WinnerID")!=0) {
				winnerID=rs.getInt("WinnerID");
			}
			MatchObj match=new MatchObj(id,name,score,status,tournamentID,player1ID,player2ID,winnerID);
			list.add(match);
		}
		rs.close();
		statement.close();
		return list;
	}
	
	public int insert(String name,String score,String status,Integer tournamentID) throws SQLException {
		int ID = 0;
		statement=connection.prepareStatement(insertString,Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, name);
		statement.setString(2, score);
		statement.setString(3, status);
		statement.setInt(4, tournamentID);
		statement.executeUpdate();
		ResultSet keys=statement.getGeneratedKeys();
		if(keys.next()) {
			ID=keys.getInt(1);
		}
		statement.close();
		return ID;
	}
	
	public void update(int ID,String score,String name,String status,Integer tournamentID,Integer player1ID,Integer player2ID,Integer winnerID) throws SQLException {
		statement=connection.prepareStatement(selectByIDQuery);
		statement.setInt(1, ID);
		rs=statement.executeQuery();
		MatchObj match=null;
		if(rs.next()) {
			String score1=rs.getString("Score");
			String name1=rs.getString("Name");
			String status1=rs.getString("Status");
			Integer tournamentID1=rs.getInt("TournamentID");
			Integer player1ID1=rs.getInt("Player1ID");
			Integer player2ID1=rs.getInt("Player2ID");
			Integer winnerID1=rs.getInt("WinnerID");
			match=new MatchObj(ID,name1,score1,status1,tournamentID1,player1ID1,player2ID1,winnerID1);
		}
		rs.close();
		statement.close();
		if(score==null || score=="") {
			score=match.getScore();
		}
		if(name==null || name=="") {
			name=match.getName();
		}
		
		if(status==null || status=="") {
			status=match.getStatus();
		}
		
		if(tournamentID==-1) {
			tournamentID=match.getTournamentID();
		}
		
		if(player1ID==-1) {
			player1ID=match.getPlayer1ID();
		}
		
		if(player2ID==-1) {
			player2ID=match.getPlayer2ID();
		}
		
		if(winnerID==-1) {
			winnerID=match.getWinnerID();
		}
		
		statement=connection.prepareStatement(updateString);
		statement.setString(1, score);
		statement.setString(2, name);
		statement.setString(3, status);
		if(tournamentID==0) {	
			statement.setString(4, null);
		}
		else {
			statement.setInt(4, tournamentID);
		}
		if(player1ID==0) {	
			statement.setString(5, null);
		}
		else {
			statement.setInt(5, player1ID);
		}
		if(player2ID==0) {	
			statement.setString(6, null);
		}
		else {
			statement.setInt(6, player2ID);
		}
		if(winnerID==0) {	
			statement.setString(7, null);
		}
		else {
			statement.setInt(7, winnerID);
		}
		statement.setInt(8, ID);
		statement.executeUpdate();
		statement.close();
	}
	
	public void delete(int ID) throws SQLException {
		statement=connection.prepareStatement(deleteString);
		statement.setInt(1, ID);
		statement.executeUpdate();
	}

}
