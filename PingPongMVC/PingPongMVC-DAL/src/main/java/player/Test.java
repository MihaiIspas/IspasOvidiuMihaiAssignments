package player;

import java.sql.SQLException;
import java.util.ArrayList;

public class Test {

	public static void main(String[] args) throws SQLException {
		PlayerDAO_JDBC playerDAOJDBC=new PlayerDAO_JDBC();
		PlayerDAO_Hibernate playerDAOHibernate=new PlayerDAO_Hibernate();
		/*ArrayList<PlayerObj> list=new ArrayList<PlayerObj>();
		
		list=playerDAOJDBC.selectAll();
		
		for(PlayerObj p:list) {
			System.out.println(p.toString());
		}*/
		
		//playerDAOJDBC.update(10, "", -1, -1, "martinv", "martinv",-1);
		
		//int ID=playerDAOHibernate.insert("Ardelean Constantin", 25, 0, "costa", "costa", 0, "P");
		//System.out.println(ID+"-----------------");
		
		/*PlayerObj p=playerDAOHibernate.selectByID(20);
		System.out.println(p.toString());*/
		
		/*ArrayList<PlayerObj> list1=new ArrayList<PlayerObj>();
		list1=playerDAOHibernate.selectAll();
		for(PlayerObj p:list1) {
			System.out.println(p.toString());
		}*/
		
		//playerDAOHibernate.update(10, "Martin Victor", -1, -1, "", "",-1);
		//playerDAOHibernate.delete(20);

	}

}
