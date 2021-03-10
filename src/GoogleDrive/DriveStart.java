/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GoogleDrive;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.auth.oauth2.Credential;
import GoogleDrive.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.Drive.Files;
//import com.google.api.services.drive.model.DriveList;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.*;

import com.google.api.services.drive.model.FileList;
import java.io.ByteArrayOutputStream;
//import java.io.File;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JOptionPane;
//import GoogleDrive_allClass.File;

//import GoogleDrive.

/**
 *
 * @author SHA
 */
public class DriveStart {
    
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    //private static FileDataStoreFactory DATA_STORE_FACTORY  ;
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials. file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = DriveStart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
    
    public static String Uploader(String Name,String Path,String type) throws GeneralSecurityException, IOException{
        
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
 //----------------------------------------------------------------------------------------------------------------        
        
        
 //----------------------------------------------------------------------------------------------------------------        
         
        File fileMetadata = new File();
            fileMetadata.setName(Name);
            java.io.File filePath = new java.io.File(Path);
            fileMetadata.setParents(Collections.singletonList("1htI0Q5exC3xlGrNkfJDhtL238AfPD7AW"));
            FileContent mediaContent = new FileContent(type, filePath);
            
            
             File file = service.files().create(fileMetadata,mediaContent)
                .setFields("id, parents")
                .execute();
             JOptionPane.showMessageDialog(null, Name+"Succesfully Uploaded");
              return (file.getId());
        
        
    }
    public static String FIleCreater() throws GeneralSecurityException, IOException{
        
         final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
 //----------------------------------------------------------------------------------------------------------------        
            File fileMetadata2 = new File();
            fileMetadata2.setName("Patient-X-Ray-Images");
            fileMetadata2.setMimeType("application/vnd.google-apps.folder");

            File file2 = service.files().create(fileMetadata2)
                .setFields("id")
                .execute();
            String File_ID = file2.getId();
            
           return(File_ID);
    
    }
    public static void Downloader() throws GeneralSecurityException, IOException{
         final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        
        String fileId = "1N1vP7vCCVWgs-N5NuyK1GabcCbPP_UPS";
        FileOutputStream outputStream = new FileOutputStream("gg");
        service.files().get(fileId)
            .executeMediaAndDownloadTo(outputStream);
       String surl = "https://drive.google.com/uc?export=download&id=1N1vP7vCCVWgs-N5NuyK1GabcCbPP_UPS"; 
       
    }
    
    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        
        System.out.println(FIleCreater());
//        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//
//        // Print the names and IDs for up to 10 files.
//        FileList result = service.files().list()
//                .setPageSize(10)
//                .setFields("nextPageToken, files(id, name)")
//                .execute();
//        List<com.google.api.services.drive.model.File> files = result.getFiles();
//        if (files == null || files.isEmpty()) {
//            System.out.println("No files found.");
//        } else {
//            System.out.println("Files:");
//            for (com.google.api.services.drive.model.File file : files) {
//                System.out.printf("%s (%s)\n", file.getName(), file.getId());
//            }
//        }
        //Uploader();
            //Drive driveService = GoogleDriveUtils.
  //       Downloader();
   }
    
}
