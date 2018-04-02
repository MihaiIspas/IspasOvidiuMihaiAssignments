package match;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class MatchDAO implements MatchDAOInterface {

	Connection connection=null;
	PreparedStatement statement;
	ResultSet rs=null;
	
	String selectAllQuery="select * from `game`";
	String selectByIDQuery="select * from `game` where `ID`=?";
	String selectByTournamentIDQuery="select * from `game` where `TournamentID`=?";
	String selectByNameQuery="select `ID` from `game` where `Name`=?";
	String selectSetIDQuery="select `CurrentSetID` from `game` where `ID`=?";
	String insertString="insert into `game` (`Score`,`Name`,`Status`,`TournamentID`,`Player1ID`,`Player2ID`) values (?,?,?,?,?,?)";
	String updateString="update `game` set `Score`=?,`Name`=?,`Status`=?,`TournamentID`=?,`Player1ID`=?,`Player2ID`=? where `ID`=?";
	String updateWinnerString="update `game` set `WinnerID`=? where `ID`=?";
	String resetPlayerIDs="update `game` set `Player1ID`=?,`Player2ID`=? where `ID`=?";
	String deleteString="delete from `game` where `ID`=?";
	
	public MatchDAO() {
		try {
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/pingpong?autoReconnect=true&useSSL=false", "root", "password");
		}
		catch(Exception e) {
			e.getMessage();
		}
	}
	
	public LinkedHashMap<String,String[]> selectAll() throws SQLException {
		LinkedHashMap<String,String[]> map = new LinkedHashMap<String,String[]>();
		statement=connection.prepareStatement(selectAllQuery);
		rs=statement.executeQuery();
		while(rs.next()) {
			String[] auxStr = new String[6];
			auxStr[0]=rs.getString("Score");
			auxStr[1]=rs.getString("Name");
			auxStr[2]=rs.getString("Status");
			auxStr[3]=rs.getString("TournamentID");
			auxStr[4]=rs.getString("Player1ID");
			auxStr[5]=rs.getString("Player2ID");
			map.put(rs.getString("ID"), auxStr);
		}
		rs.close();
		statement.close();
		return map;
	}
	
	public String[] selectByID(String ID) throws SQLException {
		String[] str = new String[7];
		statement=connection.prepareStatement(selectByIDQuery);
		statement.setString(1, ID);
		rs=statement.executeQuery();
		if(rs.next()) {
			str[0]=ID;
			str[1]=rs.getString("Score");
			str[2]=rs.getString("Name");
			str[3]=rs.getString("Status");
			str[4]=rs.getString("TournamentID");
			str[5]=rs.getString("Player1ID");
			str[6]=rs.getString("Player2ID");
		}
		rs.close();
		statement.close();
		return str;
	}
	
	public LinkedHashMap<String,String[]> selectByTournamentID(String tournamentID) throws SQLException {
		LinkedHashMap<String,String[]> map = new LinkedHashMap<String,String[]>();
		statement=connection.prepareStatement(selectByTournamentIDQuery);
		statement.setString(1, tournamentID);
		rs=statement.executeQuery();
		//System.out.println("Hello");
		while(rs.next()) {
			String[] str = new String[6];
			String id=rs.getString("ID");
			//System.out.println(rs.getString("ID"));
			str[0]=rs.getString("Score");
			//System.out.println(str[0]);
			str[1]=rs.getString("Name");
			//System.out.println(str[1]);
			str[2]=rs.getString("Status");
			//System.out.println(str[2]);
			str[3]=tournamentID;
			//System.out.println(str[3]);
			//System.out.println("Hello");
			str[4]=rs.getString("Player1ID");
			str[5]=rs.getString("Player2ID");
			map.put(id, str);
		}
		rs.close();
		statement.close();
		return map;
	}
	
	public String insert(String score,String name,String status,String tournamentID,String player1ID,String player2ID) throws SQLException {
		String ID="";
		statement=connection.prepareStatement(insertString);
		statement.setString(1, score);
		statement.setString(2, name);
		statement.setString(3, status);
		statement.setString(4, tournamentID);
		statement.setString(5, player1ID);
		statement.setString(6, player2ID);
		statement.executeUpdate();
		statement.setString(1, name);
		statement.close();
		statement=connection.prepareStatement(selectByNameQuery);
		statement.setString(1, name);
		rs=statement.executeQuery();
		if(rs.next()) {
			ID=rs.getString("ID");
		}
		statement.close();
		rs.close();
		return ID;
	}
	
	public void updateWinner(String ID,String winnerID) throws SQLException {
		statement=connection.prepareStatement(updateWinnerString);
		statement.setString(1, winnerID);
		statement.setString(2, ID);
		statement.executeUpdate();
		statement.close();
	}
	
	public void update(String ID,String score,String name,String status,String tournamentID,String player1ID,String player2ID) throws SQLException {
		String[] str = new String[6];
		statement=connection.prepareStatement(selectByIDQuery);
		statement.setString(1, ID);
		rs=statement.executeQuery();
		if(rs.next()) {
			str[0]=rs.getString("Score");
			str[1]=rs.getString("Name");
			str[2]=rs.getString("Status");
			str[3]=rs.getString("TournamentID");
			str[4]=rs.getString("Player1ID");
			str[5]=rs.getString("Player2ID");
		}
		rs.close();
		
		if(score==null || score=="") {
			score=str[0];
		}
		if(name==null || name=="") {
			name=str[1];
		}
		
		if(status==null || status=="") {
			status=str[2];
		}
		
		if(tournamentID==null || tournamentID=="") {
			tournamentID=str[3];
		}
		
		if(player1ID==null || player1ID=="") {
			player1ID=str[4];
		}
		
		if(player2ID==null || player2ID=="") {
			player2ID=str[5];
		}
		
		statement=connection.prepareStatement(updateString);
		statement.setString(1, score);
		statement.setString(2, name);
		statement.setString(3, status);
		statement.setString(4, tournamentID);
		statement.setString(5, player1ID);
		statement.setString(6, player2ID);
		statement.setString(7, ID);
		statement.executeUpdate();
		statement.close();
	}
	
	public void resetPlayerIDs(String ID) throws SQLException {
		statement=connection.prepareStatement(resetPlayerIDs);
		statement.setString(1, null);
		statement.setString(2, null);
		statement.setString(3, ID);
		statement.executeUpdate();
		statement.close();
	}
	
	public void delete(String ID) throws SQLException {
		statement=connection.prepareStatement(deleteString);
		statement.setString(1, ID);
		statement.executeUpdate();
	}
}
