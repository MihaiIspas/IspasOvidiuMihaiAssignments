package match;

import java.sql.SQLException;
import java.util.LinkedHashMap;

public interface MatchDAOInterface {
	
	public LinkedHashMap<String,String[]> selectAll() throws SQLException;
	public String[] selectByID(String ID) throws SQLException;
	public String insert(String score,String name,String status,String tournamentID,String player1ID,String player2ID) throws SQLException;
	public void update(String ID,String score,String name,String status,String tournamentID,String player1ID,String player2ID) throws SQLException;
	public void delete(String ID) throws SQLException;
	
}
