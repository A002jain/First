package dbservices;
import ch.qos.logback.core.ConsoleAppender;
import crudinterface.Crud;
import hibernateutility.HibernateUtility;

import recordTables.BankDetails;
import recordTables.FamilyDetails;
import recordTables.PersonalDetails;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
public class CrudService implements Crud {

    private  static Logger logger;
    public Session session;
    private ValidationService validateData;
    private InputData inputData;
    private int id,salary, bankAcct, fatherAge,familyId;
    private String phoneNo, emailId, fatherName, bankName, employeeName;
    public CrudService(){
        try {
            setLogger();
            session= HibernateUtility.getSessionFactory().openSession();

            validateData =new ValidationService();
            inputData=new InputData();
            logger.info("Connected to Database");
        }catch (Exception e){
            logger.warn("Exception:"+e);
        }
    }

    public void create() {
        logger.info("Create Data");
        Transaction transaction=null;
        getData();
        if(validate()) {
            try {
                transaction=session.beginTransaction();
                insertDataInTable();
                transaction.commit();
                logger.info("Data Saved to DB");
            } catch (Exception e) {
                logger.warn("Exception:"+e);
                if(transaction!=null){
                    transaction.rollback();
                }
            }
        }else{
            logger.warn("Invalid Input Data");
        }
    }

    public void display() {
        logger.info("Display");
        try{
            List<PersonalDetails> personalDetailsList=session.createQuery("from PersonalDetails",PersonalDetails.class).list();
            logger.info("Total Records: "+personalDetailsList.size());
            for(PersonalDetails personalDetails:personalDetailsList) {
                System.out.print(" Name: " + personalDetails.getName());
                System.out.print(" Emp Id: " + personalDetails.getEmployeeId());
                System.out.print(" Phone No.: " + personalDetails.getPhoneNo());
                System.out.print(" Email Id: " + personalDetails.getEmailId());
                System.out.print(" Salary: " + personalDetails.getSalary());

                System.out.print(" Father Name: " + personalDetails.getFamilyDetails().getFatherName());
                System.out.print(" Father Age: " + personalDetails.getFamilyDetails().getFatherAge());
                System.out.print(" Family Id: " + personalDetails.getFamilyDetails().getFamilyId());

                System.out.print(" Bank Name: "+ personalDetails.getBankDetails().getBankName());
                System.out.println(" Acct No: "+ personalDetails.getBankDetails().getAcctNo());
            }
        }catch (Exception e){
            logger.warn("Exception"+e);
        }
    }

    public void delete() {
        logger.info("Delete Record");
        Transaction transaction=null;
        PersonalDetails  personalDetails;
        int id=inputData.intInput("Enter Id: ");
        try{

            transaction=session.beginTransaction();
            personalDetails=session.find(PersonalDetails.class,id);
            session.getTransaction().commit();

            // session.delete(personalDetails);
            transaction.commit();
            logger.info("Deleted Record Id:"+id+" Name:"+personalDetails.getName());
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            logger.warn("Exception"+e);
        }
    }

    public void update() {
        logger.info("Update Record");
        Transaction  transaction=null;
        PersonalDetails personalDetails;
        id=inputData.intInput("Enter ID :");
        try {
            phoneNo = inputData.stringInput("PhoneNo. :");
            emailId = inputData.stringInput("EmailId :").toUpperCase();
            salary = inputData.intInput("Salary :");
            if(validateData.phoneNumberValidation(phoneNo) && validateData.emailIdValidation(emailId) ) {
                transaction=session.beginTransaction();
                personalDetails=session.get(PersonalDetails.class,id);
                personalDetails.setPhoneNo(phoneNo);
                personalDetails.setSalary(salary);
                personalDetails.setEmailId(emailId);
                session.saveOrUpdate(personalDetails);
                transaction.commit();
                logger.info("Record Updated");
            }else{
                logger.info("Invalid Input Data");
            }
        }catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            logger.warn("Exception"+e);
        }
    }

    public void close(){
        try {
            logger.info("Closing Connection");
            session.close();
            HibernateUtility.shutDown();
        }catch (Exception e){
            logger.warn("Exception"+e);
        }
    }

    private void insertDataInTable(){
        FamilyDetails familyDetails=new FamilyDetails();
        BankDetails bankDetails=new BankDetails();

        familyDetails.setFamilyId(familyId);
        familyDetails.setFatherAge(fatherAge);
        familyDetails.setFatherName(fatherName);

        bankDetails.setAcctNo(bankAcct);
        bankDetails.setBankName(bankName);

        PersonalDetails personalDetails=new PersonalDetails(bankDetails,familyDetails);
        personalDetails.setEmployeeId(id);
        personalDetails.setName(employeeName);
        personalDetails.setPhoneNo(phoneNo);
        personalDetails.setEmailId(emailId);
        personalDetails.setSalary(salary);
        bankDetails.setPersonalDetails(personalDetails);
        familyDetails.setPersonalDetails(personalDetails);

        session.saveOrUpdate(personalDetails);
    }

    private boolean validate(){
            return (validateData.nameValidation(employeeName) && validateData.nameValidation(fatherName) &&
                    validateData.ageValidation(fatherAge) && validateData.phoneNumberValidation(phoneNo) &&
                    validateData.emailIdValidation(emailId) && validateData.checkNegative(id) &&
                    validateData.checkNegative(familyId) && validateData.checkNegative(salary) &&
                    validateData.checkNegative(bankAcct));
    }

    private void getData(){
      id=inputData.intInput("Enter Id: ");
      employeeName =inputData.stringInput("Name: ");
      phoneNo=inputData.stringInput("Phone No.: ");
      emailId=inputData.stringInput("EmailId: ").toUpperCase();
      salary=inputData.intInput("Salary: ");

      fatherName=inputData.stringInput("Father Name: ");
      fatherAge=inputData.intInput("Father Age: ");
      familyId=inputData.intInput("Family Id: ");

      bankAcct=inputData.intInput("Acct No.: ");
      bankName=inputData.stringInput("Bank Name: ");
    }

    public void search(){
        try {
           // Criteria criteria = session.createCriteria(PersonalDetails.class);
           // criteria.add(Restrictions.gt("id", 0));
            String inp=inputData.stringInput("Enter <Property name>:<Value>");
            String[] data=inp.split(":");
            String data1=data[0].trim();
            String data2=data[1].trim();
            CriteriaBuilder criteriaBuilder=session.getCriteriaBuilder();
            CriteriaQuery<PersonalDetails> criteriaQuery =criteriaBuilder.createQuery(PersonalDetails.class);
            Root<PersonalDetails> root=criteriaQuery.from(PersonalDetails.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.<String>get(data1),data2));
            List<PersonalDetails> personalDetailsList=session.createQuery(criteriaQuery).list();
            for(PersonalDetails personalDetails : personalDetailsList) {
                System.out.print(" Name: " + personalDetails.getName());
                System.out.print(" Emp Id: " + personalDetails.getEmployeeId());
                System.out.print(" Phone No.: " + personalDetails.getPhoneNo());
                System.out.print(" Email Id: " + personalDetails.getEmailId());
                System.out.print(" Salary: " + personalDetails.getSalary());

                System.out.print(" Father Name: " + personalDetails.getFamilyDetails().getFatherName());
                System.out.print(" Father Age: " + personalDetails.getFamilyDetails().getFatherAge());
                System.out.print(" Family Id: " + personalDetails.getFamilyDetails().getFamilyId());

                System.out.print(" Bank Name: " + personalDetails.getBankDetails().getBankName());
                System.out.print(" Acct No: " + personalDetails.getBankDetails().getAcctNo());
            }
        }catch (Exception e){
            logger.info("Search Error:"+e);
            System.exit(0);
        }
    }

    private static void setLogger(){
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        PatternLayoutEncoder ple = new PatternLayoutEncoder();
        ple.setPattern("%date %level [%thread] %logger{10} [%file:%line] %msg%n");
        ple.setContext(lc);
        ple.start();
        FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
        String logFile = "new.log";
        fileAppender.setFile(logFile);
        fileAppender.setEncoder(ple);
        fileAppender.setContext(lc);
        fileAppender.start();
        logger= (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(fileAppender);
        logger.setLevel(Level.ALL);
        logger.setAdditive(false);
    }
}
