package de.laliluna.example;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class TestExample {

    private static Logger log = LoggerFactory.getLogger(TestExample.class);

    public static void main(String[] args) {
        /* clean tables */
//        clean();
        /* simple create example */
//        createHoney();
        /* relation example */
//        createRelation();
        /* delete example */
//        delete();
        /* update example */
//        update();
        /* query example */
//        query();
        /* query by name */
        query("Modern style");
    }

    private static Honey createHoney() {
        Honey forestHoney = new Honey();
        forestHoney.setName("forest honey");
        forestHoney.setTaste("very sweet");
        Session session = InitSessionFactory.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        session.save(forestHoney);
        tx.commit();
        session.close();
        return forestHoney;
    }

    private static void update() {
        Honey honey = createHoney();
        Session session = InitSessionFactory.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        honey.setName("Modern style");
        session.update(honey);
        tx.commit();
        session.close();
    }

    private static void delete() {
        Honey honey = createHoney();
        Session session = InitSessionFactory.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(honey);
        tx.commit();
        session.close();
    }

    private static void clean() {
        Session session = InitSessionFactory.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        session.createQuery("delete from Bee").executeUpdate();
        session.createQuery("delete from Honey").executeUpdate();
        tx.commit();
        session.close();
    }

    private static void createRelation() {
        Session session = InitSessionFactory.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Honey honey = new Honey();
        honey.setName("country honey");
        honey.setTaste("Delicious");
        session.save(honey);
        Bee bee = new Bee("Sebastian");
        session.save(bee);
        /* create the relation on both sides */
        bee.setHoney(honey);
        honey.getBees().add(bee);
        tx.commit();
        session.close();
    }

    private static void query() {
        Session session = InitSessionFactory.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        List<Honey> honeys = session.createQuery("select h from Honey as h").list();
        for (Iterator iter = honeys.iterator(); iter.hasNext(); ) {
            Honey element = (Honey) iter.next();
            log.debug(element.toString());
        }
        tx.commit();
        session.close();
    }

    private static void query(String name) {
        Session session = InitSessionFactory.getInstance().openSession();
        Transaction tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(Honey.class);
        if (!name.equals("") && name != null) {
            criteria.add(Restrictions.eq("name", name));
        }
        List<Honey> honeys = criteria.list();

        honeys.forEach(honey -> {
            log.info(honey.toString());
        });
        tx.commit();
        session.close();
    }

}
