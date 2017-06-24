package com.Entity;
import java.util.ArrayList;
import java.util.Date;


/**
 * 图书评论类
 * @author Zen Johnny
 * @version 1.0 
 * @since 2016-12-13
 */

public class BookComment {

	private String  isbn;        //ISBN号(主键)
    private String  readerId;    //借阅人ID 
    private Integer Grade;       //图书评级(5星评级制) 
    private Date    commentDate; //评论时间
    private String  content;     //评论内容(有且仅能有一条评论)
    
  /**
   * Construction Method
   * @author Zen Johnny
   * @param isbn
   * @param readerId
   * @param grade
   * @param content
   */
    public BookComment(String isbn, String readerId, int grade,Date commentDate, String content) {
		super();
		this.isbn = isbn;
		this.readerId = readerId;
		Grade = grade;
		this.commentDate = commentDate;
		this.content = content;
	} 
    public BookComment() {
		super();
		this.isbn = null;
		this.readerId = null;
		this.Grade = 0;
		this.commentDate = null;
		this.content = null;
	}
    
/**
 * 方法   
 * @return isbn
 */
    public String getIsbn() {
		return isbn;
	}
    public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
    public String getReaderId() {
		return readerId;
	}
    public void setReaderId(String readerId) {
		this.readerId = readerId;
	}
	
    public int getGrade() {
		return Grade;
	}
    public void setGrade(int grade) {
		Grade = grade;
	}
    public Date getCommentDate() {
		return commentDate;
	}
	public void setgetCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
    public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}   
}
