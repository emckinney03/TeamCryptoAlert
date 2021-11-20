/**
 */
package util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import datamodel.User;
import datamodel.Note;

/**
 * @since JavaSE-1.8
 */
public class UtilDB {
   static SessionFactory sessionFactory = null;

   public static SessionFactory getSessionFactory() {
      if (sessionFactory != null) {
         return sessionFactory;
      }
      Configuration configuration = new Configuration().configure();
      StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
      sessionFactory = configuration.buildSessionFactory(builder.build());
      return sessionFactory;
   }

   public static List<User> listUsers() {
      List<User> resultList = new ArrayList<User>();

      Session session = getSessionFactory().openSession();
      Transaction tx = null;  // each process needs transaction and commit the changes in DB.

      try {
         tx = session.beginTransaction();
         List<?> users = session.createQuery("FROM User").list();
         for (Iterator<?> iterator = users.iterator(); iterator.hasNext();) {
            User user = (User) iterator.next();
            resultList.add(user);
         }
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return resultList;
   }

   public static List<User> listUsers(String keyword) {
      List<User> resultList = new ArrayList<User>();

      Session session = getSessionFactory().openSession();
      Transaction tx = null;

      try {
         tx = session.beginTransaction();
         // System.out.println((User)session.get(User.class, 1)); // use "get" to fetch data
         // Query q = session.createQuery("FROM User");
         List<?> users = session.createQuery("FROM User").list();
         for (Iterator<?> iterator = users.iterator(); iterator.hasNext();) {
            User user = (User) iterator.next();
            if (user.getUserName().startsWith(keyword)) {
               resultList.add(user);
            }
         }
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return resultList;
   }

   public static List<User> lookupUser(String userName) {
      List<User> resultList = new ArrayList<User>();

      Session session = getSessionFactory().openSession();
      Transaction tx = null;

      try {
         tx = session.beginTransaction();
         System.out.println((User)session.get(User.class, 1)); // use "get" to fetch data
         // Query q = session.createQuery("FROM User");
         List<?> users = session.createQuery("FROM User").list();
         for (Iterator<?> iterator = users.iterator(); iterator.hasNext();) {
            User user = (User) iterator.next();
            if (user.getUserName().equals(userName)) {
               resultList.add(user);
            }
         }
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return resultList;
   }

   public static void createUser(String userName, String password) {
      Session session = getSessionFactory().openSession();
      Transaction tx = null;
      try {
         tx = session.beginTransaction();
         session.save(new User(userName, password));
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
   }
   
   public static List<Note> listNotes(String userName) {
      List<Note> resultList = new ArrayList<Note>();

      Session session = getSessionFactory().openSession();
      Transaction tx = null;

      try {
         tx = session.beginTransaction();
         // System.out.println((User)session.get(User.class, 1)); // use "get" to fetch data
         // Query q = session.createQuery("FROM User");
         List<?> notes = session.createQuery("FROM Note").list();
         for (Iterator<?> iterator = notes.iterator(); iterator.hasNext();) {
            Note note = (Note) iterator.next();
            if (note.getOwnerID().equals(userName)) {
               resultList.add(note);
            }
         }
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return resultList;
	}
   
	public static void createNote(String ownerID, String label, String note) {
	   if (!(ownerID == null || label == null || note == null)) {
		   Session session = getSessionFactory().openSession();
		   Transaction tx = null;
		   try {
			   tx = session.beginTransaction();
			   session.save(new Note(ownerID, label, note));
			   tx.commit();
		   } catch (HibernateException e) {
			   if (tx != null)
				   tx.rollback();
			   e.printStackTrace();
		   } finally {
			   session.close();
		   }
	   }
	}
   
   // Main code for method modified from CodeJava.net (https://www.codejava.net/frameworks/hibernate/hibernate-basics-3-ways-to-delete-an-entity-from-the-datastore)
   public static void deleteNote(int noteID) {
	   Session session = getSessionFactory().openSession();
	   Transaction tx = null;
	   try {
		   tx = session.beginTransaction();
		   Serializable id = noteID;
		   Object persistentInstance = session.load(Note.class, id);
		   if (persistentInstance != null) {
			   System.out.println("Deleting... " + persistentInstance.toString());
			   session.delete(persistentInstance);
			   tx.commit();
		   }
	   } catch (HibernateException e) {
		   if (tx != null)
			   tx.rollback();
		   e.printStackTrace();
	   } finally {
		   session.close();
	   }
	}
}

