package tournament;

import java.sql.Date;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import player.PlayerObj;

public class TournamentDAO_Hibernate implements TournamentDAO {
	
	SessionFactory factory;
	Session session;
	
	public TournamentDAO_Hibernate() {
		
	}
	
	public ArrayList<TournamentObj> selectAll(){
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(TournamentObj.class).buildSessionFactory();
		ArrayList<TournamentObj> list=new ArrayList<TournamentObj>();
		session=factory.getCurrentSession();
		try {
			session.beginTransaction();
			list=(ArrayList<TournamentObj>) session.createQuery("from TournamentObj").getResultList();
		}finally {
			factory.close(); 
		}
		return list;
	}
	
	public TournamentObj selectByID(int ID) {
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(TournamentObj.class).buildSessionFactory();
		TournamentObj tour=null;
		session=factory.getCurrentSession();
		try {
			session.beginTransaction();
			tour=session.get(TournamentObj.class, ID);
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
		return tour;
	}
	
	public int insert(String name,String status,int nrPlayers,float price,Date startDate) {
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(TournamentObj.class).buildSessionFactory();
		int ID=0;
		session=factory.getCurrentSession();
		try {
			TournamentObj tour=new TournamentObj(name,status,nrPlayers,price,startDate);
			session.beginTransaction();
			ID=(Integer) session.save(tour);
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
		return ID;
	}
	
	public void update(int ID,String name,String status,int nrPlayers,float price,Date date) {
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(TournamentObj.class).buildSessionFactory();
		TournamentObj tour=null;
		session=factory.getCurrentSession();
		try {
			session.beginTransaction();
			tour=session.get(TournamentObj.class, ID);
			if(name==null || name=="") {
				tour.setName(tour.getName());
			}
			else {
				tour.setName(name);
			}
			if(status==null || status=="") {
				tour.setStatus(tour.getStatus());
			}
			else {
				tour.setStatus(status);
			}
			if(nrPlayers==-1) {
				tour.setNumberPlayers(tour.getNumberPlayers());
			}
			else {
				tour.setNumberPlayers(nrPlayers);
			}
			if(price==-1) {
				tour.setPrice(tour.getPrice());
			}
			else {
				tour.setPrice(price);
			}
			if(date==null) {
				tour.setStartDate(tour.getStartDate());
			}
			else {
				tour.setStartDate(date);
			}
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
	}
	
	public void delete(int ID) {
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(TournamentObj.class).buildSessionFactory();
		TournamentObj tour=null;
		session=factory.getCurrentSession();
		try {
			session.beginTransaction();
			tour=session.get(TournamentObj.class, ID);
			session.delete(tour);
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
	}

}
