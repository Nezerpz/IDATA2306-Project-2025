package no.ntnu.rentalroulette;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionUtil {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(EntityManagerFactory factory) {
        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    public void closeSession(Session session) {
        session.close();
    }

    public void closeSessionFactory() {
        sessionFactory.close();
    }

    public void save(Object object) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.save(object);
        transaction.commit();
        closeSession(session);
    }

    public <T> List<T> getAll(Class<T> clazz) {
        Session session = getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> rootEntry = cq.from(clazz);
        CriteriaQuery<T> all = cq.select(rootEntry);

        TypedQuery<T> allQuery = session.createQuery(all);
        closeSession(session);
        return allQuery.getResultList();
    }

    public Object get(Class<?> clazz, int id) {
        Session session = getSession();
        Object object = session.get(clazz, id);
        closeSession(session);
        return object;
    }

    public void update(Object object) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.update(object);
        transaction.commit();
        closeSession(session);
    }

    public void delete(Object object) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(object);
        transaction.commit();
        closeSession(session);
    }
}
