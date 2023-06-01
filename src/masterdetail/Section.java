/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package masterdetail;

/**
 *
 * @author Zeyad.Alaa
 */
public class Section {

    private int id;
    private String SectionName;

    public Section(int id, String SectionName) {
        this.id = id;
        this.SectionName = SectionName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSectionName() {
        return SectionName;
    }

    public void setSectionName(String SectionName) {
        this.SectionName = SectionName;
    }
    
    
    
}
