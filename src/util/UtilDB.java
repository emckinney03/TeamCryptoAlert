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
//import datamodel.Note;
import datamodel.Follow;
import datamodel.Currency;

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
   
   public static List<Follow> listFollow() {
	      List<Follow> resultList = new ArrayList<Follow>();

	      Session session = getSessionFactory().openSession();
	      Transaction tx = null;  // each process needs transaction and commit the changes in DB.

	      try {
	         tx = session.beginTransaction();
	         List<?> follows = session.createQuery("FROM Follow").list();
	         for (Iterator<?> iterator = follows.iterator(); iterator.hasNext();) {
	            Follow follow = (Follow) iterator.next();
	            resultList.add(follow);
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
               System.out.println(" TESTING " + user.getUserName());
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

   public static void createUser(String username, String password, String email) {
      Session session = getSessionFactory().openSession();
      Transaction tx = null;
      try {
         tx = session.beginTransaction();
         session.save(new User(username, password, email));
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
   }
   public static void createFollow(Integer currencyID, Integer userID) {
	      Session session = getSessionFactory().openSession();
	      Transaction tx = null;
	      try {
	         tx = session.beginTransaction();
	         session.save(new Follow(currencyID, userID));
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx != null)
	            tx.rollback();
	         e.printStackTrace();
	      } finally {
	         session.close();
	      }
	   }
	   
   public static void createCurrency(String currencyName) {
      Session session = getSessionFactory().openSession();
      Transaction tx = null;
      try {
         tx = session.beginTransaction();
         session.save(new Currency(currencyName));
         tx.commit();
      } catch (HibernateException e) {
         if (tx != null)
            tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
   }
   
   
   public static void deleteFollows(Integer id) {
	   Session session = getSessionFactory().openSession();
	   Transaction tx = null;
	   String hql = "delete from Follow where userID= " + id; 
	   session.createQuery(hql).executeUpdate();
//	   try {
//           session.beginTransaction();
//
//           Follow follow = (Follow) session.get(Follow.class, id);
//
//           session.delete(follow);
//
//           session.getTransaction().commit();
//       }
//       catch (HibernateException e) {
//           e.printStackTrace();
//           session.getTransaction().rollback();
//       }
   }
   
   public static void deleteUser(Integer id) {
	   Session session = getSessionFactory().openSession();
	   Transaction tx = null;
	   String hql = "delete from User where userID= " + id; 
	   session.createQuery(hql).executeUpdate();
   }
   
   public static List<Currency> lookupCurrency(String currencyName) {
	   List<Currency> resultList = new ArrayList<Currency>();

	   Session session = getSessionFactory().openSession();
	   Transaction tx = null;

	   try {
	      tx = session.beginTransaction();
	      List<?> currencies = session.createQuery("FROM Currency").list();
	      for (Iterator<?> iterator = currencies.iterator(); iterator.hasNext();) {
	         Currency currency = (Currency) iterator.next();
	         if (currency.getCurrencyName().equals(currencyName)) {
	            resultList.add(currency);
	         }
	      }
	      tx.commit();
	   } 
	   catch (HibernateException e) {
	      if (tx != null)
	         tx.rollback();
	      e.printStackTrace();
	   } 
	   finally {
	      session.close();
	   }
	   return resultList;
	}
   
   public static List<Currency> listCurrencies() {
	   List<Currency> resultList = new ArrayList<Currency>();

	   Session session = getSessionFactory().openSession();
	   Transaction tx = null;

	   try {
	      tx = session.beginTransaction();
	      List<?> currencies = session.createQuery("FROM Currency").list();
	      for (Iterator<?> iterator = currencies.iterator(); iterator.hasNext();) {
	         Currency currency = (Currency) iterator.next();
	         resultList.add(currency);
	      }
	      tx.commit();
	   } 
	   catch (HibernateException e) {
	      if (tx != null)
	         tx.rollback();
	      e.printStackTrace();
	   } 
	   finally {
	      session.close();
	   }
	   return resultList;
	}
   
//   public static List<Note> listNotes(String userName) {
//      List<Note> resultList = new ArrayList<Note>();
//
//      Session session = getSessionFactory().openSession();
//      Transaction tx = null;
//
//      try {
//         tx = session.beginTransaction();
//         // System.out.println((User)session.get(User.class, 1)); // use "get" to fetch data
//         // Query q = session.createQuery("FROM User");
//         List<?> notes = session.createQuery("FROM Note").list();
//         for (Iterator<?> iterator = notes.iterator(); iterator.hasNext();) {
//            Note note = (Note) iterator.next();
//            if (note.getOwnerID().equals(userName)) {
//               resultList.add(note);
//            }
//         }
//         tx.commit();
//      } catch (HibernateException e) {
//         if (tx != null)
//            tx.rollback();
//         e.printStackTrace();
//      } finally {
//         session.close();
//      }
//      return resultList;
//	}
   
//	public static void createNote(String ownerID, String label, String note) {
//	   if (!(ownerID == null || label == null || note == null)) {
//		   Session session = getSessionFactory().openSession();
//		   Transaction tx = null;
//		   try {
//			   tx = session.beginTransaction();
//			   session.save(new Note(ownerID, label, note));
//			   tx.commit();
//		   } catch (HibernateException e) {
//			   if (tx != null)
//				   tx.rollback();
//			   e.printStackTrace();
//		   } finally {
//			   session.close();
//		   }
//	   }
//	}
   
   // Main code for method modified from CodeJava.net (https://www.codejava.net/frameworks/hibernate/hibernate-basics-3-ways-to-delete-an-entity-from-the-datastore)
//   public static void deleteNote(int noteID) {
//	   Session session = getSessionFactory().openSession();
//	   Transaction tx = null;
//	   try {
//		   tx = session.beginTransaction();
//		   Serializable id = noteID;
//		   Object persistentInstance = session.load(Note.class, id);
//		   if (persistentInstance != null) {
//			   System.out.println("Deleting... " + persistentInstance.toString());
//			   session.delete(persistentInstance);
//			   tx.commit();
//		   }
//	   } catch (HibernateException e) {
//		   if (tx != null)
//			   tx.rollback();
//		   e.printStackTrace();
//	   } finally {
//		   session.close();
//	   }
//	}
}

