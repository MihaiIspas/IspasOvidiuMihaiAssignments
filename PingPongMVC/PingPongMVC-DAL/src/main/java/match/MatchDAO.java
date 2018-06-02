package match;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MatchDAO {
	
	public ArrayList<MatchObj> selectAll() throws SQLException;
	public MatchObj selectByID(int ID) throws SQLException;
	public ArrayList<MatchObj> selectByTournamentID(Integer tournamentID) throws SQLException;
	public int insert(String name,String score,String status,Integer tournamentID) throws SQLException;
	public void update(int ID,String score,String name,String status,Integer tournamentID,Integer player1ID,Integer player2ID,Integer winnerID) throws SQLException;
	public void delete(int ID) throws SQLException;

}
