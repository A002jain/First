package hibernateutility;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.jetbrains.annotations.NotNull;
import recordTables.BankDetails;
import recordTables.FamilyDetails;
import recordTables.PersonalDetails;

import java.io.FileInputStream;
import java.util.Properties;

public class HibernateUtility {
    private static HibernateUtility hibernateUtility;
    private static SessionFactory sessionFactory;
   /* private HibernateUtility(){
        try{
                StandardServiceRegistry standardServiceRegistry=new StandardServiceRegistryBuilder().configure().build();
                MetadataSources   metadataSources=new MetadataSources(standardServiceRegistry);
                Metadata metadata=metadataSources.getMetadataBuilder().build();
                sessionFactory=metadata.getSessionFactoryBuilder().build();
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/
   private HibernateUtility() {
       try {
           Configuration configuration= new Configuration() ;
           FileInputStream fileInputStream = new FileInputStream("hibernate.properties");
           Properties property = new Properties();
           property.load(fileInputStream);
           configuration.setProperties(property);
           configuration.addAnnotatedClass(PersonalDetails.class);
           configuration.addAnnotatedClass(BankDetails.class);
           configuration.addAnnotatedClass(FamilyDetails.class);

           ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                   .applySettings(configuration.getProperties()).build();
           sessionFactory = configuration.buildSessionFactory(serviceRegistry);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

   public static SessionFactory getSessionFactory() {
        if(sessionFactory==null)
            hibernateUtility=new HibernateUtility();
        return hibernateUtility.sessionFactory;
    }

    public static void shutDown(){
        sessionFactory.close();
    }

    public static Transaction getTransaction(@NotNull Session session){
        return session.beginTransaction();
    }

}
