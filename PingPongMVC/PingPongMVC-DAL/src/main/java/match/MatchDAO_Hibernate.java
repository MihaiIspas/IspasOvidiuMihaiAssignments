package match;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import tournament.TournamentObj;

public class MatchDAO_Hibernate implements MatchDAO {
	
	SessionFactory factory;
	Session session;
	
	public MatchDAO_Hibernate() {
		
	}
	
	public ArrayList<MatchObj> selectAll(){
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(MatchObj.class).buildSessionFactory();
		ArrayList<MatchObj> list=new ArrayList<MatchObj>();
		session=factory.getCurrentSession();
		try {
			session.beginTransaction();
			list=(ArrayList<MatchObj>) session.createQuery("from MatchObj").getResultList();
		}finally {
			factory.close(); 
		}
		return list;
	}
	
	public MatchObj selectByID(int ID) {
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(MatchObj.class).buildSessionFactory();
		MatchObj match=null;
		session=factory.getCurrentSession();
		try {
			session.beginTransaction();
			match=session.get(MatchObj.class, ID);
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
		return match;
	}
	
	public ArrayList<MatchObj> selectByTournamentID(Integer tournamentID){
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(MatchObj.class).buildSessionFactory();
		ArrayList<MatchObj> list=new ArrayList<MatchObj>();
		session=factory.getCurrentSession();
		try {
			session.beginTransaction();
			list=(ArrayList<MatchObj>) session.createQuery("from MatchObj m where m.tournamentID="+Integer.toString(tournamentID)).getResultList();
		}finally {
			factory.close(); 
		}
		return list;
	}
	
	public int insert(String name,String score,String status,Integer tournamentID) {
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(MatchObj.class).buildSessionFactory();
		int ID=0;
		session=factory.getCurrentSession();
		try {
			MatchObj match=new MatchObj(name,score,status,tournamentID,null,null);
			session.beginTransaction();
			ID=(Integer) session.save(match);
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
		return ID;
	}
	
	public void update(int ID,String score,String name,String status,Integer tournamentID,Integer player1ID,Integer player2ID,Integer winnerID) {
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(MatchObj.class).buildSessionFactory();
		MatchObj match=null;
		session=factory.getCurrentSession();
		try {
			session.beginTransaction();
			match=session.get(MatchObj.class, ID);
			if(name==null || name=="") {
				match.setName(match.getName());
			}
			else {
				match.setName(name);
			}
			if(score==null || score=="") {
				match.setScore(match.getScore());
			}
			else {
				match.setScore(score);
			}
			if(status==null || status=="") {
				match.setStatus(match.getStatus());
			}
			else {
				match.setStatus(status);
			}
			if(tournamentID==-1) {
				match.setTournamentID(match.getTournamentID());
			}
			else {
				match.setTournamentID(tournamentID);
			}
			if(player1ID==-1) {
				match.setPlayer1ID(match.getPlayer1ID());
			}
			else {
				match.setPlayer1ID(player1ID);
			}
			if(player2ID==-1) {
				match.setPlayer2ID(match.getPlayer2ID());
			}
			else {
				match.setPlayer2ID(player2ID);
			}
			if(winnerID==-1) {
				match.setWinnerID(match.getWinnerID());
			}
			else {
				match.setWinnerID(winnerID);
			}
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
	}
	
	public void delete(int ID) {
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(MatchObj.class).buildSessionFactory();
		MatchObj match=null;
		session=factory.getCurrentSession();
		try {
			session.beginTransaction();
			match=session.get(MatchObj.class, ID);
			session.delete(match);
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
	}

}
