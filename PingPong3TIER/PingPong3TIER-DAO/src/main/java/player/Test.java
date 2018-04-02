package player;

import java.sql.SQLException;

public class Test {
	
	public static void main(String[] args) throws SQLException{
		PlayerDAO p = new PlayerDAO();
		//p.insert("Mihai Popescu", "25");
		//System.out.println("Hello");
		p.selectAll();
		//p.delete("1");
		//p.delete("2");
	}

}
