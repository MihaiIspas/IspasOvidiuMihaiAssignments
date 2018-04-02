package set;

import java.sql.SQLException;
import java.util.LinkedHashMap;

public interface SetDAOInterface {
	
	public LinkedHashMap<String,String[]> selectAll() throws SQLException;
	public String[] selectByID(String ID) throws SQLException;
	public LinkedHashMap<String,String[]> selectByMatchID(String matchID) throws SQLException;
	public void insert(String score,String status,String matchID,String scoreLimit,String name) throws SQLException;
	public void update(String ID,String score,String status,String matchID,String scoreLimit,String name) throws SQLException;
	public void delete(String ID) throws SQLException;

}
