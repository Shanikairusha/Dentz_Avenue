/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Users.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import org.apache.commons.logging.Log;

/**
 *
 * @author SHA
 */
public class Login_check {
    
   
        //private boolean classBoolean = false;
        boolean check =false;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Dentz/Users");
        ArrayList<User> allusers = new ArrayList<User>(); 
        
    
    
    public boolean  Login_Check(String username,String password){
        
          int result=0,n;
          
               readData(new FirbaseCallback() {
                @Override
                public void onCallback(ArrayList<User> alldt) {
                     System.out.println(alldt.size());
                   for(User alldwn:alldt){
                       
                       if(username.equals(alldwn.getUserName()) && password.equals(alldwn.getPassword())){
                           //classBoolean=true;
                           LgChkd();
                       }
                   }
                }
            });
          
           
            System.out.println(check);
            
          return check;
            
    }
    public void LgChkd(){
        check=true;
    }
    
    public void readData(FirbaseCallback firbaseCallback){
         myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                  
                 Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                // size= DataSnapshot.getChildrenCount();
                  for(DataSnapshot child:children){
                      User usr=child.getValue(User.class);
                      allusers.add(usr);                     
                  }
                  firbaseCallback.onCallback(allusers);
                      
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                  System.out.println("The read failed: " + databaseError.getCode());
                }
              });
    }
    public interface FirbaseCallback {
        void onCallback(ArrayList<User> alldt);
    }
    
    
}
