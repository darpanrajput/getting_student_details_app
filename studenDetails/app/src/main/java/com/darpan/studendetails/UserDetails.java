
package com.darpan.studendetails;


public class UserDetails {
    private String Branch;
    private String Contact;
    private String Email;
    private String Name;
    private String RollNumber;
    private String Semester;
    private String Year;

    public UserDetails() {
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "Branch='" + Branch + '\'' +
                ", Contact='" + Contact + '\'' +
                ", Email='" + Email + '\'' +
                ", Name='" + Name + '\'' +
                ", RollNumber='" + RollNumber + '\'' +
                ", Semester='" + Semester + '\'' +
                ", Year='" + Year + '\'' +
                '}';
    }

    public UserDetails(String branch, String contact, String email, String name, String rollNumber, String semester, String year) {
        Branch = branch;
        Contact = contact;
        Email = email;
        Name = name;
        RollNumber = rollNumber;
        Semester = semester;
        Year = year;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRollNumber() {
        return RollNumber;
    }

    public void setRollNumber(String rollNumber) {
        RollNumber = rollNumber;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }
}
