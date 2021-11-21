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
public class Currencies {

   @Id  // primary key
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "currencyID") // specify the column name. Without it, it will use method name
   private Integer currencyID;

   @Column(name = "currencyName")
   private String currencyName;
 
   public Currencies() {
	  super();
   }

   public Currencies(Integer currencyID, String currencyName) {
      this.currencyID = currencyID;
      this.currencyName = currencyName;
   }


   public Integer getCurrencyID() {
      return currencyID;
   }

   public void setCurrencyID(Integer currencyID) {
      this.currencyID = currencyID;
   }

   public String getcurrencyName() {
      return currencyName;
   }

   public void setCurrencyName(String currencyName) {
      this.currencyName = currencyName;
   }
  

   @Override
   public String toString() {
      return "Currencies: " + this.currencyID + ", " + this.currencyName;
   }
}
