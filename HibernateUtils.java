package com.sunlong.hibernate02.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


/**
 * @Auther: SolarL
 * @Date: 2019/7/26
 * @Description: com.sunlong.hibernate01
 * @version: 1.0
 */
public class HibernateUtils {

    private static final Configuration cfg;
    private static final SessionFactory sf;

    static {
        cfg = new Configuration().configure("com/sunlong/hibernate02/hibernate.cfg.xml");
        sf = cfg.buildSessionFactory();
    }

    public static Session openSession() {
        return sf.openSession();
    }

    public static Session getCurrentSession(){
        return sf.getCurrentSession();
    }
}
