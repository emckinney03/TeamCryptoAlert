package datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @since J2SE-1.8
 CREATE TABLE currencies (
  currencyID INT NOT NULL AUTO_INCREMENT,    
  currencyName VARCHAR(30) NOT NULL,   
  PRIMARY KEY (currencyID));
 */
@Entity
@Table(name = "currencies")
public class Currency {

   @Id  // primary key
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "currencyID") // specify the column name. Without it, it will use method name
   private Integer currencyID;

   @Column(name = "currencyName")
   private String currencyName;
   
   @Column(name = "currencyPrice")
   private Integer currencyPrice;
 
   public Currency() {
	  super();
   }

   public Currency(Integer currencyID, String currencyName, Integer currencyPrice) {
      this.currencyID = currencyID;
      this.currencyName = currencyName;
      this.currencyPrice = currencyPrice;
   }


   public Integer getCurrencyID() {
      return currencyID;
   }

   public void setCurrencyID(Integer currencyID) {
      this.currencyID = currencyID;
   }

   public String getCurrencyName() {
      return currencyName;
   }

   public void setCurrencyName(String currencyName) {
      this.currencyName = currencyName;
   }
   
   public Integer getCurrencyPrice() {
	      return currencyID;
	   }

   public void setCurrencyPrice(Integer currencyPrice) {
      this.currencyPrice = currencyPrice;
   }
  

   @Override
   public String toString() {
      return "Currency: " + this.currencyID + ", " + this.currencyName;
   }
}
