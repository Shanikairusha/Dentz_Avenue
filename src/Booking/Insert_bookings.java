/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Booking;

import Database.DB_conn;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author SHA
 */
public class Insert_bookings {
    
        
    
    public void Insert_tempbookings(Booking bk){
        
        try {
            DB_conn.initFirbase();
        } catch (IOException ex) {
            Logger.getLogger(Insert_bookings.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // TODO add your handling code here:
        //Establish connection to the firebase Database and give location to the saving node
        
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Dentz/Booking");
        
        
        //JOptionPane.showMessageDialog(null, bk.getName());
        
      //Upload and save and create selected data record in the database for mor details with completeion check
            //https://firebase.google.com/docs/database/admin/save-data#java_8
            
            
          myRef.push().setValue(bk, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                if(de!=null){
                    JOptionPane.showMessageDialog(null, dr);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Data Saved Succesfully");
                }
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    public static void main(String arg[]){
        Booking bk = new Booking();
        Insert_bookings bkks = new Insert_bookings();
        bkks.Insert_tempbookings(bk);
        
    }
}
