package de.laliluna.example;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class InitSessionFactory {

    private static SessionFactory sessionFactory;

    private InitSessionFactory() {
    }

    static {
        final Configuration cfg = new Configuration();
        cfg.configure("/hibernate.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();
    }

    public static SessionFactory getInstance() {
        return sessionFactory;
    }

}
