package entities;

import java.util.ArrayList;
import java.util.UUID;

public class ArticleObj {
	
	String ID;
	String title;
	String abstr;
	String author;
	String body;
	ArrayList<ArticleObj> related;
	
	public ArticleObj() {
		
	}

	public ArticleObj(String title, String abstr, String author, String body, ArrayList<ArticleObj> related) {
		this.title = title;
		this.abstr = abstr;
		this.author = author;
		this.body = body;
		this.related = related;
		this.ID=UUID.randomUUID().toString();
	}
	
	public ArticleObj(String title, String abstr, String author, String body) {
		this.title = title;
		this.abstr = abstr;
		this.author = author;
		this.body = body;
		this.ID=UUID.randomUUID().toString();
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID(String ID) {
		this.ID=ID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAbstr() {
		return abstr;
	}

	public void setAbstr(String abstr) {
		this.abstr = abstr;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public ArrayList<ArticleObj> getRelated() {
		return related;
	}

	public void setRelated(ArrayList<ArticleObj> related) {
		this.related = related;
	}

	@Override
	public String toString() {
		return "ArticleObj [title=" + title + ", abstr=" + abstr + ", author=" + author + ", body=" + body + "]";
	}
	
}
