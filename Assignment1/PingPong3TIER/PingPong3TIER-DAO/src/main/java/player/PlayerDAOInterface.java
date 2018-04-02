package player;

import java.sql.SQLException;
import java.util.LinkedHashMap;

public interface PlayerDAOInterface {
	
	public LinkedHashMap<String,String[]> selectAll() throws SQLException;
	public String[] selectByID(String ID) throws SQLException;
	public void insert(String name,String age,String available,String userName,String password,String type) throws SQLException;
	public void update(String ID,String name,String age,String available,String userName,String password) throws SQLException;
	public void delete(String ID) throws SQLException;

}
