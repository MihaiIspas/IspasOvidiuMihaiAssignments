package tournament;

import java.sql.SQLException;
import java.util.HashMap;

public interface TournamentDAOInterface {
	
	public HashMap<String,String[]> selectAll() throws SQLException;
	public String[] selectByID(String ID) throws SQLException;
	public String insert(String name,String status) throws SQLException;
	public void update(String ID,String name,String status) throws SQLException;
	public void delete(String ID) throws SQLException;

}
