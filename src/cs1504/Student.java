package cs1504;

import java.io.Serializable;

public class Student implements Serializable{
    private String name;
    private String rollNumber;
    private String batch;

    public Student(String name, String rollNumber, String batch) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.batch = batch;
    }
    public String getName() {
        return this.name;
    }
    public String getRollNumber() {
        return this.rollNumber;
    }

    public String getBatch() {
        return batch;
    }
}
