package recordTables;

import javax.persistence.*;

@Entity
@Table(name="bankdetails")
public class BankDetails {
    @Id
    @Column(name="bankacct",nullable = false)
    private int acctNo;
    @Column(name="bankname",nullable = false)
    private String bankName;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="empid")
    private PersonalDetails personalDetails;

    public PersonalDetails getPersonalDetails() { return personalDetails; }

    public void setPersonalDetails(PersonalDetails personalDetails) { this.personalDetails = personalDetails; }

    public void setAcctNo(int acctNo) { this.acctNo = acctNo; }

    public void setBankName(String bankName) { this.bankName = bankName; }

    public int getAcctNo() { return acctNo; }

    public String getBankName() { return bankName; }

}
