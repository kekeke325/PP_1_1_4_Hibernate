package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSession()) {
            Transaction transaction = session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS user (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age INT)";

            NativeQuery<User> query = session.createNativeQuery(sql, User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSession()) {
            Transaction transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS user";

            NativeQuery<User> query = session.createNativeQuery(sql, User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            User user = new User();

            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);

            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Session session = Util.getSession()) {
            Query<User> query = session.createQuery("from User", User.class);
            userList = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;

        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            String sql = "TRUNCATE TABLE user";

            NativeQuery<User> query = session.createNativeQuery(sql, User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
