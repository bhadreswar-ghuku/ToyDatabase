/*
* Here toy database is implemented using files in Java
* File StudentRecord.db for students records
* similarly InternshipOfferRecord.db for internship offer records
* */

package cs1504;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ToyDatabase {

    private int userInput = -1;
    private String studentRecordFileName = "StudentRecord.db";
    private String internshipOfferRecordFileName = "InternshipOfferRecord.db";
    private ArrayList<Student> studentList = new ArrayList<>();
    private ArrayList<InternshipOffer> offerList = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private String directory = null;
    private boolean isDatabaseOpen = false;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ToyDatabase toyDatabase = new ToyDatabase();
        toyDatabase.run();
    }

    private void run() throws IOException, ClassNotFoundException {
        System.out.println("Welcome to Toy Database for Summer Internship Placement");
        while(this.userInput != 10) {
            showOptions();
            this.userInput = this.scanner.nextInt();
            if(validateInput()) {
                if(this.userInput == 1) {
                    openDatabase();
                    this.isDatabaseOpen = true;
                } else if(this.userInput == 2) {
                    if(!this.isDatabaseOpen) {
                        System.out.println("Database isn't opened, sorry!");
                        continue;
                    }
                    enterNewStudentRecord();
                } else if(this.userInput == 3) {
                    if(!this.isDatabaseOpen) {
                        System.out.println("Database isn't opened, sorry!");
                        continue;
                    }
                    enterNewInternshipOfferRecord();
                } else if(this.userInput == 4) {
                    if(!this.isDatabaseOpen) {
                        System.out.println("Database isn't opened, sorry!");
                        continue;
                    }
                    System.out.print("Enter student name : ");
                    ArrayList<String> companyList = getCompanyList(this.scanner.next().trim());
                    if(companyList == null) {
                        System.out.println("Null");
                    } else if(companyList.size() == 0) {
                        System.out.println("Company List Empty");
                    } else {
                        System.out.println("Company List :");
                        for(int i = 0; i < companyList.size(); i++) {
                            System.out.println(companyList.get(i));
                        }
                    }
                } else if(this.userInput == 5) {
                    if(!this.isDatabaseOpen) {
                        System.out.println("Database isn't opened, sorry!");
                        continue;
                    }
                    System.out.print("Enter company name : ");
                    ArrayList<Student> students = getSelectedStudents(this.scanner.next().trim());
                    if(students == null) {
                        System.out.println("Null");
                    } else if(students.size() == 0) {
                        System.out.println("Empty student record");
                    } else {
                        System.out.println("Student Record :");
                        for(int i = 0; i < students.size(); i++) {
                            System.out.println(students.get(i).getName() + "\t"
                                    + students.get(i).getRollNumber() + "\t"
                                    + students.get(i).getBatch());
                        }
                    }
                } else if(this.userInput == 6) {
                    if(!this.isDatabaseOpen) {
                        System.out.println("Database isn't opened, sorry!");
                        continue;
                    }
                    System.out.println("Average Salary : " + getAverageSalaryOffered());
                } else if(this.userInput == 7) {
                    if(!this.isDatabaseOpen) {
                        System.out.println("Database isn't opened, sorry!");
                        continue;
                    }
                    System.out.println("Total number of students : " + getTotalStudents());
                } else if(this.userInput == 8) {
                    if(!this.isDatabaseOpen) {
                        System.out.println("Database isn't opened, sorry!");
                        continue;
                    }
                    System.out.println("Total number of students with offers : " + getTotalStudentsWithOffer());
                } else if(this.userInput == 9) {
                    if(!this.isDatabaseOpen) {
                        System.out.println("Database isn't opened, can't perform close!");
                        continue;
                    }
                    System.out.println("Closing the database : thank you!");
                    closeDatabase();
                    this.isDatabaseOpen = false;
                } else {
                    if(this.isDatabaseOpen) {
                        System.out.println("Please close the database first!");
                        continue;
                    }
                    System.out.println("Exiting...");
                    break;
                }
            } else {
                System.out.println("Wrong Input, Please verify");
            }
        }
    }


    private void showOptions() {

        System.out.println("1 : Open database");
        System.out.println("2 : Enter new record for student");
        System.out.println("3 : Enter new offer record for internship");
        System.out.println("4 : Search company names by student");
        System.out.println("5 : Search student list selected by company");
        System.out.println("6 : Query average salary");
        System.out.println("7 : Query total number of students");
        System.out.println("8 : Query total students with offers");
        System.out.println("9 : Close database");
        System.out.println("10 : Exit");
        System.out.print("Enter choice : ");
    }

    private boolean validateInput() {

        if(this.userInput >= 1 && this.userInput <= 10) return true;
        return false;
    }

    private void openDatabase() throws IOException, ClassNotFoundException {
        System.out.print("Enter directory : ");
        openDatabase(this.scanner.next().trim());
    }

    private void openDatabase(String dir) throws IOException, ClassNotFoundException {
        File file = new File(dir);
        if(!file.exists()) {
            System.out.println("The directory " + dir + " doesn't exist");
        }
        if(file.mkdir()) {
            System.out.println("The directory " + dir + " is created");
        }
        try {
            this.directory = dir;
            readData(dir);
        } catch (Exception e) {
            System.out.println("Reading data error : " + e.getMessage());
        }
    }
    private void readData(String dir) throws IOException, ClassNotFoundException {
        this.studentList.clear();
        this.offerList.clear();

        String fileName = dir + "/" + this.studentRecordFileName;
        File studentFile = new File(fileName);
        if(!studentFile.exists()) {
            studentFile.createNewFile();
        } else {
            FileInputStream fin = new FileInputStream(fileName);
            if(fin != null) {
                ObjectInputStream ois = new ObjectInputStream(fin);
                Object tempObject;
                while((tempObject = ois.readObject()) != null) {
                    this.studentList.add((Student) tempObject);
                }
                ois.close();
            }
            fin.close();
        }



        fileName = dir + "/" + this.internshipOfferRecordFileName;
        File offerFile = new File(fileName);
        if(!offerFile.exists()) {
            offerFile.createNewFile();
        } else {

            FileInputStream fin = new FileInputStream(fileName);
            if(fin != null) {
                ObjectInputStream ois = new ObjectInputStream(fin);
                Object tempObject;
                while((tempObject = ois.readObject()) != null) {
                    this.offerList.add((InternshipOffer) tempObject);
                }
                ois.close();
            }
            fin.close();
        }

    }
    private void enterNewStudentRecord() {
        String studentName, rollNumber, batchId;
        System.out.print("Enter name of the student : ");
        studentName = this.scanner.next();
        System.out.print("Enter roll number of the student : ");
        rollNumber = this.scanner.next();
        System.out.print("Enter batch id : ");
        batchId = this.scanner.next();
        this.studentList.add(new Student(studentName, rollNumber, batchId));
    }
    private void enterNewInternshipOfferRecord() {
        String rollNumber, companyName, accept;
        Integer salary;
        boolean status;
        System.out.print("Enter roll number of the student : ");
        rollNumber = this.scanner.next();
        System.out.print("Enter company name : ");
        companyName = this.scanner.next();
        System.out.print("Enter salary : ");
        salary = this.scanner.nextInt();
        System.out.print("Got the offer yes/no : ");
        accept = this.scanner.next();

        if(accept.trim().equalsIgnoreCase("yes")) {
            status = true;
        } else {
            status = false;
        }
        this.offerList.add(new InternshipOffer(rollNumber, companyName, salary, status));
    }
    private ArrayList<String> getCompanyList(String studentName) {
        ArrayList<String> companyList = new ArrayList<>();
        String rollNumber = null;
        for(int i = 0; i < studentList.size(); i++) {
            if(studentList.get(i).getName().equalsIgnoreCase(studentName)) {
                rollNumber = studentList.get(i).getRollNumber();
                break;
            }
        }
        if(rollNumber == null) {
            return null;
        }
        for(int i = 0; i < this.offerList.size(); i++) {
            if(this.offerList.get(i).getStudentRollNumber().equalsIgnoreCase(rollNumber)) {
                companyList.add(this.offerList.get(i).getCompanyName());
            }
        }
        return  companyList;
    }
    private ArrayList<Student> getSelectedStudents(String companyName) {
        ArrayList<String> rollNumbers = new ArrayList<>();
        for(int i = 0; i < this.offerList.size(); i++) {
            if(this.offerList.get(i).getCompanyName().equalsIgnoreCase(companyName)) {
                if(this.offerList.get(i).getStatus()) {
                    rollNumbers.add(this.offerList.get(i).getStudentRollNumber());
                }
            }
        }
        if(rollNumbers.size() == 0) {
            return null;
        }
        ArrayList<Student> selectedStudents = new ArrayList<>();
        for(int i = 0; i < rollNumbers.size(); i++) {
            for(int j = 0; j < this.studentList.size(); j++) {
                if(rollNumbers.get(i).equalsIgnoreCase(this.studentList.get(j).getRollNumber())) {
                    selectedStudents.add(this.studentList.get(i));
                }
            }
        }
        return selectedStudents;
    }
    private double getAverageSalaryOffered() {
        double salary = 0;
        for(int i = 0; i < this.offerList.size(); i++) {
            salary += this.offerList.get(i).getSalaryOffered();
        }
        return salary / (double) this.offerList.size();
    }
    private Integer getTotalStudents() {
        return this.studentList.size();
    }
    private Integer getTotalStudentsWithOffer() {
        Map<String, Integer> studentMap = new HashMap<>();
        for(int i = 0; i < this.offerList.size(); i++) {
            if(this.offerList.get(i).getStatus()) {
                studentMap.put(this.offerList.get(i).getStudentRollNumber(), 1);
            }
        }
        return studentMap.size();
    }
    private void closeDatabase() throws IOException {
        try {
            FileOutputStream fout = new FileOutputStream(this.directory + "/" + this.studentRecordFileName);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            for(int i = 0; i < this.studentList.size(); i++) {
                oos.writeObject(this.studentList.get(i));
            }
            fout.close();
            oos.close();
            System.out.println(studentList.size() + " student records stored to db");
            fout = new FileOutputStream(this.directory + "/" + this.internshipOfferRecordFileName);
            oos = new ObjectOutputStream(fout);
            for(int i = 0; i < this.offerList.size(); i++) {
                oos.writeObject(this.offerList.get(i));
            }
            fout.close();
            oos.close();
            System.out.println(this.offerList.size() + " internship records stored to db");
        } catch (Exception e) {
            System.out.println("closing database error : " + e.getMessage());
        }
    }
}
