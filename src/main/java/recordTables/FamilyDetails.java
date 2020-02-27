package recordTables;

import javax.persistence.*;
@Entity
@Table(name="familydetails")
public class FamilyDetails {
    @Id
    @Column(name="familyid" ,nullable = false)
    private int familyId;
    @Column(name="fathername",nullable = false)
    private String fatherName;
    @Column(name="fatherage",nullable = false)
    private int fatherAge;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="empid")
    private PersonalDetails personalDetails;

    public PersonalDetails getPersonalDetails() { return personalDetails; }

    public void setPersonalDetails(PersonalDetails personalDetails) { this.personalDetails = personalDetails; }

    public int getFatherAge() { return fatherAge; }

    public void setFatherName(String fatherName) { this.fatherName = fatherName; }

    public String getFatherName() { return fatherName; }

    public void setFatherAge(int fatherAge) { this.fatherAge = fatherAge; }

    public void setFamilyId(int familyId) { this.familyId = familyId; }

    public int getFamilyId() { return familyId; }

}

