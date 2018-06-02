package player;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlayerDAO {
	
	public ArrayList<PlayerObj> selectAll() throws SQLException;
	public PlayerObj selectByID(int ID) throws SQLException;
	public int insert(String name,int age,int available,String userName,String password,float account,String type) throws SQLException;
	public void update(int ID,String name,int age,int available,String userName,String password,float account) throws SQLException;
	public void delete(int ID) throws SQLException;

}
