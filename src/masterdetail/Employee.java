/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package masterdetail;

import java.sql.Date;

/**
 *
 * @author Zeyad.Alaa
 */
public class Employee {
    private int id;
    private String first_name;
    private String last_name;
    private Date DOB;
    private String email;
    private int department_id;
    private Department department;

    public Employee(int id, String first_name, String last_name, Date DOB, String email,int department_id, Department department) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.DOB = DOB;
        this.email = email;
        this.department_id = department_id;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Date getDOB() {
        return DOB
;    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    
}
