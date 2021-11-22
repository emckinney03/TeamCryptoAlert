package datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @since J2SE-1.8
 CREATE TABLE follows (
  followID INT NOT NULL AUTO_INCREMENT,    
  currencyID INT NOT NULL,   
  userID INT NOT NULL,
  PRIMARY KEY (followID) FOREIGN KEY (userID) 
        REFERENCES users(userID),
    FOREIGN KEY (currencyID) 
        REFERENCES currencies(currencyID));
 */
@Entity
@Table(name = "follows")
public class Follow {

   @Id  // primary key
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "followID") // specify the column name. Without it, it will use method name
   private Integer followID;

   @Column(name = "currencyID")
   private int currencyID;
   
   @Column(name = "userID")
   private int userID;


   public Follow() {
	  super();
   }

   public Follow(Integer followID, Integer currencyID, Integer userID) {
      this.followID = followID;
      this.currencyID = currencyID;
      this.userID = userID;
   }

   public Follow(Integer currencyID, Integer userID) {
      this.currencyID = currencyID;
      this.userID = userID;
   }

   public Integer getfollowID() {
      return followID;
   }

   public void setFollowID(Integer followID) {
      this.followID = followID;
   }

   public Integer getCurrencyID() {
      return currencyID;
   }

   public void setCurrencyID(Integer currencyID) {
      this.currencyID = currencyID;
   }
   
   public Integer getUserID() {
      return userID;
   }

   public void setUserID(Integer userID) {
      this.userID = userID;
   }


   @Override
   public String toString() {
      return "Follow: " + this.followID + ", " + this.currencyID + ", " + this.userID;
   }
}
