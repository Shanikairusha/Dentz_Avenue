/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Darkness
 */
public class Email {
    
    

   private Pattern pattern;
   private Matcher matcher;

   private static final String EMAIL_ADDRESS_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

   public Email(){
    pattern = Pattern.compile(EMAIL_ADDRESS_PATTERN);
   }

   public boolean validate(final String emailId){ 
    matcher = pattern.matcher(emailId);
    return matcher.matches();

   }
  
}
    

