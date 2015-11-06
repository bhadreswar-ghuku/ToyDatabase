package cs1504;

import java.io.Serializable;

public class InternshipOffer implements Serializable{
    private String studentRollNumber;
    private String companyName;
    private Integer salaryOffered;
    private boolean status;

    public InternshipOffer(String studentRollNumber, String companyName,
                           int salaryOffered, boolean status) {
        this.studentRollNumber = studentRollNumber;
        this.companyName = companyName;
        this.salaryOffered = salaryOffered;
        this.status = status;
    }
    public String getStudentRollNumber() {
        return this.studentRollNumber;
    }
    public String getCompanyName() {
        return this.companyName;
    }
    public boolean getStatus() {
        return status;
    }
    public Integer getSalaryOffered() {
        return salaryOffered;
    }
}
