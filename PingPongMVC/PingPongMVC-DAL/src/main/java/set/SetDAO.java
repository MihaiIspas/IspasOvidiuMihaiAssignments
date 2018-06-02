package set;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SetDAO {
	
	public ArrayList<SetObj> selectAll() throws SQLException;
	public SetObj selectByID(int ID) throws SQLException;
	public ArrayList<SetObj> selectByMatchID(Integer matchID) throws SQLException;
	public int insert(String name,String score,String status,Integer matchID,int scoreLimit) throws SQLException;
	public void update(int ID,String name,String score,String status,Integer matchID,int scoreLimit) throws SQLException;
	public void delete(int ID) throws SQLException;

}
