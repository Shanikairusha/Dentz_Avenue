/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Darkness
 */
public class Password {
      private static MessageDigest md;
      
      public String passwordEncord(String password) throws NoSuchAlgorithmException{
         
          try {
               md = MessageDigest.getInstance("MD5");
          } catch (Exception e) {
          }
          
           md.reset();
        byte[] digested = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<digested.length;i++){
            sb.append(Integer.toHexString(0xff & digested[i]));            
        }            
          return sb.toString();
      }

    
}
