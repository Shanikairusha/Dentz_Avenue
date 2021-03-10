/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validation;

import Database.DB_conn;
import Interfaces.login;
import Users.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Darkness
 */
public class Database_check {
    
   public ArrayList<User> allusers1= new ArrayList<User>();
    public void check_username(){
       
       try {
           DB_conn.initFirbase();
       } catch (IOException ex) {
           Logger.getLogger(Database_check.class.getName()).log(Level.SEVERE, null, ex);
       }         
           
        
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Dentz/Users");
        
         myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                  
                 Iterable<DataSnapshot> children = dataSnapshot.getChildren();
               
                  for(DataSnapshot child:children){
                      User usr=child.getValue(User.class);
                      allusers1.add(usr);      
                      System.out.println(allusers1.size()); 
                  }                              
                }                
                @Override
                public void onCancelled(DatabaseError databaseError) {
                  System.out.println("The read failed: " + databaseError.getCode());
                }
              });
              
        
    }
    public static void main(String[] args) {
        Database_check dbc = new Database_check();
         
               
        
    }

    public Database_check() {
        check_username();
    }
   
}
