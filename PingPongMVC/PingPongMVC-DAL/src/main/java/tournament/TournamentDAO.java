package tournament;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public interface TournamentDAO {
	
	public ArrayList<TournamentObj> selectAll() throws SQLException;
	public TournamentObj selectByID(int ID) throws SQLException;
	public int insert(String name,String status,int nrPlayers,float price,Date startDate) throws SQLException;
	public void update(int ID,String name,String status,int nrPlayers,float price,Date date) throws SQLException;
	public void delete(int ID) throws SQLException;

}
