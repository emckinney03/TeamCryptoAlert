package datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @since J2SE-1.8
 CREATE TABLE notes (
  id INT NOT NULL AUTO_INCREMENT, 
  ownerID VARCHAR(30) NOT NULL,
  label VARCHAR(30) NOT NULL,   
  note TEXT NOT NULL,   
  PRIMARY KEY (id));
 */
@Entity
@Table(name = "notes")
public class Note {

   @Id  // primary key
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id") // specify the column name. Without it, it will use method name
   private Integer id;
   
   @Column(name = "ownerID") // specify the column name. Without it, it will use method name
   private String ownerID;

   @Column(name = "label")
   private String label;
   
   @Column(name = "contents")
   private String contents;

   public Note() {
	  super();
   }

   public Note(Integer id, String ownerID, String label, String contents) {
      this.id = id;
      this.label = label;
      this.contents = contents;
   }

   public Note(String ownerID, String label, String contents) {
	  this.ownerID = ownerID;
      this.label = label;
      this.contents = contents;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }
   
   public String getOwnerID() {
      return ownerID;
   }

   public void setOwnerID(String userName) {
      this.ownerID = userName;
   }

   public String getLabel() {
      return label;
   }

   public void setLabel(String label) {
      this.label = label;
   }
   
   public String getContents() {
      return contents;
   }

   public void setContents(String contents) {
      this.contents = contents;
   }

   @Override
   public String toString() {
      return "Note: " + this.id + ", " + this.ownerID + ", " + this.label + ", " + this.contents;
   }
}