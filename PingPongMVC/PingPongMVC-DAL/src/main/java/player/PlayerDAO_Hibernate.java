package player;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class PlayerDAO_Hibernate implements PlayerDAO {
	
	SessionFactory factory;
	Session session;
	
	public PlayerDAO_Hibernate() {
		
	}
	
	public ArrayList<PlayerObj> selectAll(){
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(PlayerObj.class).buildSessionFactory();
		ArrayList<PlayerObj> list=new ArrayList<PlayerObj>();
		session=factory.getCurrentSession();
		try {
			session.beginTransaction();
			list=(ArrayList<PlayerObj>) session.createQuery("from PlayerObj").getResultList();
		}finally {
			factory.close(); 
		}
		return list;
	}
	
	public PlayerObj selectByID(int ID) {
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(PlayerObj.class).buildSessionFactory();
		PlayerObj player=null;
		session=factory.getCurrentSession();
		try {
			session.beginTransaction();
			player=session.get(PlayerObj.class, ID);
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
		return player;
	}
	
	public int insert(String name,int age,int available,String userName,String password,float account,String type) {
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(PlayerObj.class).buildSessionFactory();
		int ID = 0;
		session=factory.getCurrentSession();
		try {
			PlayerObj player=new PlayerObj(name,age,available,userName,password,account,type);
			session.beginTransaction();
			ID=(Integer) session.save(player);
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
		return ID;
	}
	
	public void update(int ID,String name,int age,int available,String userName,String password,float account) {
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(PlayerObj.class).buildSessionFactory();
		PlayerObj player=null;
		session=factory.getCurrentSession();
		try {
			session.beginTransaction();
			player=session.get(PlayerObj.class, ID);
			if(name==null || name=="") {
				player.setName(player.getName());
			}
			else {
				player.setName(name);
			}
			if(age<0) {
				player.setAge(player.getAge());
			}
			else {
				player.setAge(age);
			}
			if(available!=0 && available!=1) {
				player.setAvailable(player.getAvailable());
			}
			else {
				player.setAvailable(available);
			}
			if(userName==null || userName=="") {
				player.setUserName(player.getUserName());
			}
			else {
				player.setUserName(userName);
			}
			if(password==null || password=="") {
				player.setPassword(player.getPassword());
			}
			else {
				player.setPassword(password);
			}
			if(account<0) {
				player.setAccount(player.getAccount());
			}
			else {
				player.setAccount(account);
			}
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
	}
	
	public void delete(int ID) {
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(PlayerObj.class).buildSessionFactory();
		PlayerObj player=null;
		session=factory.getCurrentSession();
		try {
			session.beginTransaction();
			player=session.get(PlayerObj.class, ID);
			session.delete(player);
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
	}

}
