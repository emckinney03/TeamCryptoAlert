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
  PRIMARY KEY (id));
 */
@Entity
@Table(name = "users")
public class User {

   @Id  // primary key
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id") // specify the column name. Without it, it will use method name
   private Integer id;

   @Column(name = "userName")
   private String userName;
   
   @Column(name = "userPass")
   private String userPass;

   public User() {
	  super();
   }

   public User(Integer id, String userName, String userPass) {
      this.id = id;
      this.userName = userName;
      this.userPass = userPass;
   }

   public User(String userName, String userPass) {
      this.userName = userName;
      this.userPass = userPass;
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

   @Override
   public String toString() {
      return "User: " + this.id + ", " + this.userName + ", " + this.userPass;
   }
}