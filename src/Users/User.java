/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Users;

import java.sql.Time;

/**
 *
 * @author SHA
 */
public class User {
    
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    private String Name;
    private String NIC;
    private String DOB;
    private String Email;
    private int MobileNm;
    private String Adress;
    private String UserName;
    private String Password;
    private String JobRole;

    public String getName() {
        return Name;
    }

    public String getNIC() {
        return NIC;
    }

    public String getDOB() {
        return DOB;
    }

    public String getEmail() {
        return Email;
    }

    public int getMobileNm() {
        return MobileNm;
    }

    public String getAdress() {
        return Adress;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassword() {
        return Password;
    }

    public String getJobRole() {
        return JobRole;
    }

    public User(String uid, String Name, String NIC,  String Email, int MobileNm, String DOB, String Adress, String UserName, String Password, String JobRole) {
        this.Name = Name;
        this.NIC = NIC;
        this.Email = Email;
        this.MobileNm = MobileNm;
        this.Adress = Adress;
        this.UserName = UserName;
        this.Password = Password;
        this.JobRole = JobRole;
        this.DOB = DOB;
        this.uid = uid;
    }

    public User() {
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setMobileNm(int MobileNm) {
        this.MobileNm = MobileNm;
    }

    public void setAdress(String Adress) {
        this.Adress = Adress;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public void setJobRole(String JobRole) {
        this.JobRole = JobRole;
    }
    
    
}

