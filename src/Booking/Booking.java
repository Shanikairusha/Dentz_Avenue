/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Booking;

import java.sql.Time;

/**
 *
 * @author SHA
 */
public class Booking {
    private String PID;
    private String Name;
    private int PhnNm;
    private String Date;
    private String BokingtTime;
    private String Reason;
    private String BookID;
    private String DocNote;

    public String getDocNote() {
        return DocNote;
    }

    public void setDocNote(String DocNote) {
        this.DocNote = DocNote;
    }
    

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String BookID) {
        this.BookID = BookID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Booking( int phnNm, String Date, String BokingtTime, String Reason) {
        //this.NIC = NIC;
        this.PhnNm = phnNm;
        this.Date = Date;
        this.BokingtTime = BokingtTime;
        this.Reason = Reason;
    }

    public Booking() {
    }

    public String getPID() {
        return PID;
    }

    public int getPhnNm() {
        return PhnNm;
    }

    public String getDate() {
        return Date;
    }

    public String getBokingtTime() {
        return BokingtTime;
    }

    public String getReason() {
        return Reason;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public void setPhnNm(int PhnNm) {
        this.PhnNm = PhnNm;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setBokingtTime(String BokingtTime) {
        this.BokingtTime = BokingtTime;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }
    
    
    
    
}
