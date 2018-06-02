package set;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import match.MatchObj;

public class SetDAO_Hibernate implements SetDAO {
	
	SessionFactory factory;
	Session session;
	
	public SetDAO_Hibernate() {
		
	}
	
	public ArrayList<SetObj> selectAll(){
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(SetObj.class).buildSessionFactory();
		ArrayList<SetObj> list=new ArrayList<SetObj>();
		session=factory.getCurrentSession();
		try {
			session.beginTransaction();
			list=(ArrayList<SetObj>) session.createQuery("from SetObj").getResultList();
		}finally {
			factory.close(); 
		}
		return list;
	}
	
	public SetObj selectByID(int ID) {
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(SetObj.class).buildSessionFactory();
		SetObj set=null;
		session=factory.getCurrentSession();
		try {
			session.beginTransaction();
			set=session.get(SetObj.class, ID);
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
		return set;
	}
	
	public ArrayList<SetObj> selectByMatchID(Integer matchID){
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(SetObj.class).buildSessionFactory();
		ArrayList<SetObj> list=new ArrayList<SetObj>();
		session=factory.getCurrentSession();
		try {
			session.beginTransaction();
			list=(ArrayList<SetObj>) session.createQuery("from SetObj s where s.matchID="+Integer.toString(matchID)).getResultList();
		}finally {
			factory.close(); 
		}
		return list;
	}
	
	public int insert(String name,String score,String status,Integer matchID,int scoreLimit) {
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(SetObj.class).buildSessionFactory();
		int ID=0;
		session=factory.getCurrentSession();
		try {
			SetObj set=new SetObj(name,score,status,matchID,scoreLimit);
			session.beginTransaction();
			ID=(Integer) session.save(set);
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
		return ID;
	}
	
	public void update(int ID,String score,String name,String status,Integer matchID,int scoreLimit) {
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(SetObj.class).buildSessionFactory();
		SetObj set=null;
		session=factory.getCurrentSession();
		try {
			session.beginTransaction();
			set=session.get(SetObj.class, ID);
			if(name==null || name=="") {
				set.setName(set.getName());
			}
			else {
				set.setName(name);
			}
			if(score==null || score=="") {
				set.setScore(set.getScore());
			}
			else {
				set.setScore(score);
			}
			if(status==null || status=="") {
				set.setStatus(set.getStatus());
			}
			else {
				set.setStatus(status);
			}
			if(matchID==-1) {
				set.setMatchID(set.getMatchID());
			}
			else {
				set.setMatchID(matchID);
			}
			if(scoreLimit==-1) {
				set.setScoreLimit(set.getScoreLimit());
			}
			else {
				set.setScoreLimit(scoreLimit);
			}
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
	}
	
	public void delete(int ID) {
		factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(SetObj.class).buildSessionFactory();
		SetObj set=null;
		session=factory.getCurrentSession();
		try {
			session.beginTransaction();
			set=session.get(SetObj.class, ID);
			session.delete(set);
			session.getTransaction().commit();
		}finally {
			factory.close();
		}
	}

}
