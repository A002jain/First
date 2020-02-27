package recordTables;

import javax.persistence.*;

@Entity
@Table(name="personaldetails")
public class PersonalDetails {
    @Id
    @Column(name="empid",nullable = false)
    private int employeeId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email_id", nullable = false)
    private String emailId;
    @Column(name = "phone_no", nullable = false)
    private String phoneNo;
    @Column(name = "salary", nullable = false)
    private int salary;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "personalDetails")
    private BankDetails bankDetails;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "personalDetails")
    private FamilyDetails familyDetails;

    public PersonalDetails(){}
    public PersonalDetails(BankDetails bankDetails,FamilyDetails familyDetails){
        this.setBankDetails(bankDetails);
        this.setFamilyDetails(familyDetails);
    }

    public void setFamilyDetails(FamilyDetails familyDetails) {
        this.familyDetails = familyDetails;
    }

    public void setBankDetails(BankDetails bankDetails) {
        this.bankDetails = bankDetails;
    }

    public BankDetails getBankDetails() {
        return bankDetails;
    }

    public FamilyDetails getFamilyDetails() {
        return familyDetails;
    }

    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getSalary() {
        return salary;
    }

    public String getName() {
        return name;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getEmailId() { return emailId; }

    public String getPhoneNo() { return phoneNo; }

    public void setEmailId(String emailId) { this.emailId = emailId; }

    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
}
