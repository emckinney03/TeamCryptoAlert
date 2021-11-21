package datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @since J2SE-1.8
 CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,    
  userName VARCHAR(30) NOT NULL,   
  password VARCHAR(30) NOT NULL,
  email VARCHAR(30) NOT NULL, 
  PRIMARY KEY (id));
 */
@Entity
@Table(name = "users")
public class User {

   @ID  // primary key
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id") // specify the column name. Without it, it will use method name
   private Integer id;

   @Column(name = "userName")
   private String userName;
   
   @Column(name = "userPass")
   private String userPass;

@Column(name = "userEmail")
   private String userEmail;



   public User() {
	  super();
   }

   public User(Integer id, String userName, String userPass, String userEmail) {
      this.id = id;
      this.userName = userName;
      this.userPass = userPass;
      this.userEmail = userEmail;
   }

   public User(String userName, String userPass, String userEmail) {
      this.userName = userName;
      this.userPass = userPass;
      this.userEmail = userEmail;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String username) {
      this.userName = username;
   }
   
   public String getUserPass() {
      return userPass;
   }

   public void setUserPass(String userPass) {
      this.userPass = userPass;
   }

   public String getuserEmail(){
      return userEmail;
   }
   public void setUserEmail (String userEmail){
      this.userEmail = userEmail;
   }

   @Override
   public String toString() {
      return "User: " + this.id + ", " + this.userName + ", " + this.userPass + ", "+ this.userEmail;
   }
}