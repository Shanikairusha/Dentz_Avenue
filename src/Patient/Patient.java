/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Patient;

/**
 *
 * @author SHA
 */
public class Patient {
    
    private String FullName;
    private String NIC;
    private String DOB;
    private String Address;
    private String Country;
    private int MobileNm;
    private int TelNm;
    private String Email;
    private String PID;

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
    private String DrugAllergies;
    private String CurrentMedicine;
    private String MedicalHistory;
    private String DoctorsNote;

    public String getFullName() {
        return FullName;
    }

    public String getNIC() {
        return NIC;
    }

    public String getDOB() {
        return DOB;
    }

    public String getAddress() {
        return Address;
    }

    public String getCountry() {
        return Country;
    }

    public int getMobileNm() {
        return MobileNm;
    }

    public int getTelNm() {
        return TelNm;
    }

    public String getDrugAllergies() {
        return DrugAllergies;
    }

    public String getCurrentMedicine() {
        return CurrentMedicine;
    }

    public String getMedicalHistory() {
        return MedicalHistory;
    }

    public String getDoctorsNote() {
        return DoctorsNote;
    }

    public Patient(String FullName, String NIC, String DOB, String Address, String Country, int MobileNm, int TelNm, String DrugAllergies, String CurrentMedicine, String MedicalHistory, String DoctorsNote) {
        this.FullName = FullName;
        this.NIC = NIC;
        this.DOB = DOB;
        this.Address = Address;
        this.Country = Country;
        this.MobileNm = MobileNm;
        this.TelNm = TelNm;
        this.DrugAllergies = DrugAllergies;
        this.CurrentMedicine = CurrentMedicine;
        this.MedicalHistory = MedicalHistory;
        this.DoctorsNote = DoctorsNote;
    }

    public Patient() {
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public void setMobileNm(int MobileNm) {
        this.MobileNm = MobileNm;
    }

    public void setTelNm(int TelNm) {
        this.TelNm = TelNm;
    }

    public void setDrugAllergies(String DrugAllergies) {
        this.DrugAllergies = DrugAllergies;
    }

    public void setCurrentMedicine(String CurrentMedicine) {
        this.CurrentMedicine = CurrentMedicine;
    }

    public void setMedicalHistory(String MedicalHistory) {
        this.MedicalHistory = MedicalHistory;
    }

    public void setDoctorsNote(String DoctorsNote) {
        this.DoctorsNote = DoctorsNote;
    }
    
}
