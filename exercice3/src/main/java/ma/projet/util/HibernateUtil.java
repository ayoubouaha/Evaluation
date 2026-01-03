package ma.projet.util;

import java.io.IOException;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import ma.projet.beans.Personne;
import ma.projet.beans.Homme;
import ma.projet.beans.Femme;
import ma.projet.beans.Mariage;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties props = new Properties();
                props.load(HibernateUtil.class.getClassLoader().getResourceAsStream("application.properties"));

                Configuration configuration = new Configuration();
                configuration.setProperty(Environment.DIALECT, props.getProperty("hibernate.dialect"));
                configuration.setProperty(Environment.HBM2DDL_AUTO, props.getProperty("hibernate.hbm2ddl.auto"));
                configuration.setProperty(Environment.SHOW_SQL, props.getProperty("hibernate.show_sql", "true"));
                configuration.setProperty(Environment.FORMAT_SQL, props.getProperty("hibernate.format_sql", "true"));
                configuration.setProperty(Environment.DRIVER, props.getProperty("db.driver"));
                configuration.setProperty(Environment.URL, props.getProperty("db.url"));
                configuration.setProperty(Environment.USER, props.getProperty("db.username"));
                configuration.setProperty(Environment.PASS, props.getProperty("db.password"));

                configuration.addAnnotatedClass(Personne.class);
                configuration.addAnnotatedClass(Homme.class);
                configuration.addAnnotatedClass(Femme.class);
                configuration.addAnnotatedClass(Mariage.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return sessionFactory;
    }
}
