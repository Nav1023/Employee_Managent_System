package com.example.soc_macmini_15.sqlitepractice.Model;

public class Employee {

    private long empId;
    private String firstName;
    private String lastName;
    private String gender;
    private String hireDate;
    private String dept;

    public Employee() {
    }

    public Employee(long empId, String firstName, String lastName, String gender, String hireDate, String dept) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.hireDate = hireDate;
        this.dept = dept;
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String toString() {
        return "Emp id: " + getEmpId() + "\n" + "Name: " + getFirstName()+ " " + getLastName() + " \n"+"Gender: "+getGender()
               +"\n" + "Hire Date: " + getHireDate() + "\n" + "Department: " + getDept();
    }

}
