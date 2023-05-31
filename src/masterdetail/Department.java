/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package masterdetail;

/**
 *
 * @author Zeyad.Alaa
 */
public class Department {
    private int id;
    private String name;
    private int employees_number;
    private Section section;
    
    public Department(int id, String name,int employees_number, Section section) {
        this.id = id;
        this.name = name;
        this.section = section;
        this.employees_number = employees_number;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getEmployees_number() {
        return employees_number;
    }

    public void setEmployees_number(int employees_number) {
        this.employees_number = employees_number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    
}
