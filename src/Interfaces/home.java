/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Booking.Booking;
import Database.DB_conn;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author MaC
 */
public class home extends javax.swing.JFrame {

    /**
     * Creates new form home
     */
    int xMouse;
    int yMouse;
    private String log_name;
    private String user_role;
    public home() {
//        setExtendedState(home.MAXIMIZED_BOTH);
        initComponents();
        setBackground(new Color(0,0,0,0));
       
        try {
            DB_conn.initFirbase();
        } catch (IOException ex) {
            Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
        }        
        TableLoad(); 
        tbl_today.removeColumn(tbl_today.getColumnModel().getColumn(4));
        tbl_today.removeColumn(tbl_today.getColumnModel().getColumn(4));
        
        //get current system's date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
        LocalDateTime now = LocalDateTime.now();
        lbl_datetime.setText(now.format(dtf));  
        
        
    }
    

    public  home(String log_name){
        initComponents();
        setBackground(new Color(0,0,0,0));
        setExtendedState(home.MAXIMIZED_BOTH);
        
        //set login person's name
        this.log_name = log_name;
        lbl_logname.setText(log_name); 
        
        TableLoad(); 
        tbl_today.removeColumn(tbl_today.getColumnModel().getColumn(4));
        tbl_today.removeColumn(tbl_today.getColumnModel().getColumn(4));
      
           //get current system's date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
        LocalDateTime now = LocalDateTime.now();
        lbl_datetime.setText(now.format(dtf));  
    }
     public  home(String log_name,String jobr){
        initComponents();
        this.user_role = jobr;
        this.log_name = log_name;
        
        setBackground(new Color(0,0,0,0));
        setExtendedState(home.MAXIMIZED_BOTH);
        
        //set login person's name        
        lbl_logname.setText(log_name); 
        
       if (jobr.equals("User") || jobr.equals("Doctor")) {
            btnSettings.setVisible(false);
        } else {
            btnSettings.setVisible(true);
        }
        
        TableLoad(); 
        tbl_today.removeColumn(tbl_today.getColumnModel().getColumn(4));
        tbl_today.removeColumn(tbl_today.getColumnModel().getColumn(4));
      
           //get current system's date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
        LocalDateTime now = LocalDateTime.now();
        lbl_datetime.setText(now.format(dtf));  
    }
    
    
   private void TableLoad() {
        // ----------------------------------------------------------------------------------------------------------------

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Dentz/Booking");
        ArrayList<Booking> allBookings = new ArrayList<Booking>();
        DefaultTableModel book_model = (DefaultTableModel) tbl_today.getModel();
        book_model.setRowCount(0);
        Object rowdata[] = new Object[9];       
// ----------------------------------------------------------------------------------------------------------------

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                // book_model.setRowCount(0);
                allBookings.clear();

                Booking books = dataSnapshot.getValue(Booking.class);
                allBookings.add(books);
                books.setBookID(dataSnapshot.getKey());

                    rowdata[0] = allBookings.get(0).getName();
                    rowdata[1] = allBookings.get(0).getReason();
                    rowdata[2] = allBookings.get(0).getBokingtTime();
                    rowdata[3] = allBookings.get(0).getPhnNm();
                    rowdata[4] = allBookings.get(0).getBookID();
                    rowdata[5] = allBookings.get(0).getPID();
                      book_model.addRow(rowdata);                                          
                tableSort();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                 Booking books = dataSnapshot.getValue(Booking.class);
                // books.setBookID(dataSnapshot.getKey());
                String key = dataSnapshot.getKey();
                updateTable(key, books);
                tableSort();
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Booking books = dataSnapshot.getValue(Booking.class);
                DeleteRecordTable(dataSnapshot.getKey());
                tableSort();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        // ----------------------------------------------------------------------------------------------------------------
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                book_model.setRowCount(0);
                allBookings.clear();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                // size= DataSnapshot.getChildrenCount();
                for (DataSnapshot child : children) {
                    Booking books = child.getValue(Booking.class);
                    books.setBookID(child.getKey());
                    allBookings.add(books);                 
                }  
// ----------------------------------------------------------------------------------------------------------------
                    LocalDate now1 = LocalDate.now(); //get Local System date
                    Date today = java.sql.Date.valueOf(now1); // Convert LocalDate type Variable into Date type Variable
                    
                System.out.println(today.getClass().getSimpleName());
                
                for (int i = 0; i < allBookings.size(); i++) {
                    // JOptionPane.showMessageDialog(null, allBookings.get(i).getDocNote());
                    rowdata[0] = allBookings.get(i).getName();
                    rowdata[1] = allBookings.get(i).getReason();
                    rowdata[2] = allBookings.get(i).getBokingtTime();
                    rowdata[3] = allBookings.get(i).getPhnNm();
                    rowdata[4] = allBookings.get(i).getBookID();
                    rowdata[5] = allBookings.get(i).getPID();
                 
                    //type casting.... String to Date
                     LocalDate cladate = LocalDate.parse(allBookings.get(i).getDate());
                     java.util.Date date = java.sql.Date.valueOf(cladate); //date type
                  
                    if(date.equals(today)){                     
                        book_model.addRow(rowdata);
                    }                    
                }
                allBookings.clear();
                tableSort();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        // ----------------------------------------------------------------------------------------------------------------
    }
   private void tableSort() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tbl_today.getModel());
        tbl_today.setRowSorter(sorter);
        ArrayList list = new ArrayList();
        list.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
    }  

   private void updateTable(String BookID, Booking updatedBook) {
       DefaultTableModel book_model = (DefaultTableModel) tbl_today.getModel();
        LocalDate now1 = LocalDate.now(); //get Local System date
        Date today = java.sql.Date.valueOf(now1);
        int size = book_model.getRowCount();
        Object rowdata[] = new Object[9];
        for (int i = 0; i < size; i++) {
            if ((book_model.getValueAt(i, 4)).equals(BookID)) {
                
                 LocalDate cladate = LocalDate.parse(updatedBook.getDate());
                 java.util.Date date = java.sql.Date.valueOf(cladate); //date type            
                  
                   if(date.equals(today)){                     
                         book_model.setValueAt(updatedBook.getName(), i, 0);
                            book_model.setValueAt(updatedBook.getReason(), i, 1);
                            book_model.setValueAt(updatedBook.getBokingtTime(), i, 2);
                            book_model.setValueAt(updatedBook.getPhnNm(), i, 3);
                            book_model.setValueAt(BookID, i, 4); 
                            book_model.setValueAt(updatedBook.getPID(), i, 5);
                    }  
                   else{
                       book_model.removeRow(i);
                   }         
            }
           
        }
         if(updatedBook.getDate().equals(today)){
                    rowdata[0] = updatedBook.getName();
                    rowdata[1] = updatedBook.getReason();
                    rowdata[2] = updatedBook.getBokingtTime();
                    rowdata[3] = updatedBook.getPhnNm();
                    rowdata[4] = updatedBook.getBookID();
                    rowdata[5] = updatedBook.getPID();
                      book_model.addRow(rowdata);                                          
                tableSort();
            }
    }
   private void DeleteRecordTable(String BookID) {
        DefaultTableModel book_model = (DefaultTableModel) tbl_today.getModel();
        int size = book_model.getRowCount();
        
//         LocalDate now1 = LocalDate.now(); //get Local System date
//         Date today = java.sql.Date.valueOf(now1); // Convert LocalDate type Variable into Date type Variable
//         
//         LocalDate cladate = LocalDate.parse(updatedBook.getDate());
//         java.util.Date date = java.sql.Date.valueOf(cladate); //date type
//         
//         if(date.equals(today)){      
        for (int i = 0; i < size; i++) {
            if ((book_model.getValueAt(i, 4)).equals(BookID)) {
                book_model.removeRow(i);

            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnMinimize = new javax.swing.JLabel();
        btnClose = new javax.swing.JLabel();
        MoveBar = new javax.swing.JLabel();
        btnMaximize = new javax.swing.JLabel();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        btnPatient = new javax.swing.JPanel();
        lblPatient = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_today = new javax.swing.JTable();
        btnBooking = new javax.swing.JPanel();
        lblPatient1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnSettings = new javax.swing.JPanel();
        lblPatient2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnBilling = new javax.swing.JPanel();
        lblPatient3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        kButton1 = new keeptoo.KButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbl_logname = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lbl_datetime = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow"));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setMinimumSize(new java.awt.Dimension(1280, 720));
        setUndecorated(true);
        setSize(new java.awt.Dimension(0, 0));

        jPanel1.setBackground(new java.awt.Color(48, 67, 82));

        btnMinimize.setBackground(new java.awt.Color(48, 67, 82));
        btnMinimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mini.png"))); // NOI18N
        btnMinimize.setOpaque(true);
        btnMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimizeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMinimizeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMinimizeMouseExited(evt);
            }
        });

        btnClose.setBackground(new java.awt.Color(48, 67, 82));
        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/close.png"))); // NOI18N
        btnClose.setOpaque(true);
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCloseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCloseMouseExited(evt);
            }
        });

        MoveBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                MoveBarMouseDragged(evt);
            }
        });
        MoveBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                MoveBarMousePressed(evt);
            }
        });

        btnMaximize.setBackground(new java.awt.Color(48, 67, 82));
        btnMaximize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-maximize-window-27.png"))); // NOI18N
        btnMaximize.setOpaque(true);
        btnMaximize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMaximizeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMaximizeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMaximizeMouseExited(evt);
            }
        });

        kGradientPanel2.setkBorderRadius(25);
        kGradientPanel2.setkEndColor(new java.awt.Color(215, 210, 204));
        kGradientPanel2.setkStartColor(new java.awt.Color(215, 210, 204));
        kGradientPanel2.setOpaque(false);
        kGradientPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                kGradientPanel2MouseEntered(evt);
            }
        });

        btnPatient.setBackground(new java.awt.Color(48, 67, 82));
        btnPatient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPatientMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPatientMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPatientMouseExited(evt);
            }
        });
        btnPatient.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPatient.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        lblPatient.setForeground(new java.awt.Color(255, 255, 255));
        lblPatient.setText("PATIENT'S RECORDS");
        btnPatient.add(lblPatient, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 150, 30));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-fever-100.png"))); // NOI18N
        btnPatient.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        tbl_today.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        tbl_today.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Patient Name", "Reason to Visit", "Time", "Phone Number", "ID", "PID"
            }
        ));
        tbl_today.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbl_today.setFocusable(false);
        tbl_today.setRowHeight(25);
        tbl_today.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbl_today.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbl_today);

        btnBooking.setBackground(new java.awt.Color(48, 67, 82));
        btnBooking.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBookingMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBookingMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBookingMouseExited(evt);
            }
        });
        btnBooking.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPatient1.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        lblPatient1.setForeground(new java.awt.Color(255, 255, 255));
        lblPatient1.setText("BOOKING");
        btnBooking.add(lblPatient1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 80, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-ticket-100.png"))); // NOI18N
        btnBooking.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, 90));

        btnSettings.setBackground(new java.awt.Color(48, 67, 82));
        btnSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSettingsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSettingsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSettingsMouseExited(evt);
            }
        });
        btnSettings.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPatient2.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        lblPatient2.setForeground(new java.awt.Color(255, 255, 255));
        lblPatient2.setText("SETTINGS");
        btnSettings.add(lblPatient2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 80, 30));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-settings-100 (1).png"))); // NOI18N
        btnSettings.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 110, 100));

        btnBilling.setBackground(new java.awt.Color(48, 67, 82));
        btnBilling.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBillingMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBillingMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBillingMouseExited(evt);
            }
        });
        btnBilling.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPatient3.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        lblPatient3.setForeground(new java.awt.Color(255, 255, 255));
        lblPatient3.setText("BILLING");
        btnBilling.add(lblPatient3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 80, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-cash-receipt-100.png"))); // NOI18N
        btnBilling.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 110, -1));

        kButton1.setText("Register");
        kButton1.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 18)); // NOI18N
        kButton1.setkAllowGradient(false);
        kButton1.setkBackGroundColor(new java.awt.Color(48, 67, 82));
        kButton1.setkHoverColor(new java.awt.Color(63, 87, 107));
        kButton1.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        kButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton1ActionPerformed(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-home-100.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Impact", 0, 60)); // NOI18N
        jLabel5.setText("MENU");

        lbl_logname.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbl_logname.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_user_male_30px_1.png"))); // NOI18N
        lbl_logname.setText("Name");

        jPanel2.setOpaque(false);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel7.setText("Date:-");

        lbl_datetime.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_datetime, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_datetime, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_refresh_26px.png"))); // NOI18N
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(32, 32, 32))
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                                        .addGap(110, 110, 110)
                                        .addComponent(jLabel5))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 373, Short.MAX_VALUE)
                                .addComponent(btnSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57)
                                .addComponent(btnPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57)
                                .addComponent(btnBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57))
                            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_logname)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBilling, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29))))
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBilling, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(lbl_logname)
                        .addGap(2, 2, 2)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
                .addGap(73, 73, 73)
                .addComponent(kButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(MoveBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMinimize)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMaximize)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClose))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(kGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1595, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(MoveBar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnClose)
                        .addComponent(btnMinimize)
                        .addComponent(btnMaximize)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1020, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPatientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPatientMouseClicked
        patientRecords pr = new patientRecords(log_name,user_role,user_role);
        pr.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnPatientMouseClicked

    private void btnPatientMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPatientMouseEntered
        btnPatient.setBackground(new Color(63, 87, 107));        
    }//GEN-LAST:event_btnPatientMouseEntered

    private void btnPatientMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPatientMouseExited
        btnPatient.setBackground(new Color(48, 67, 82));        
    }//GEN-LAST:event_btnPatientMouseExited

    private void MoveBarMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MoveBarMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_MoveBarMouseDragged

    private void MoveBarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MoveBarMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_MoveBarMousePressed

    private void btnMinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizeMouseClicked
        //login log = new login();
        this.setState(home.ICONIFIED);
    }//GEN-LAST:event_btnMinimizeMouseClicked

    private void btnCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseClicked
        int result = JOptionPane.showConfirmDialog(this, "Are You Sure You Want To Cancel?",null,JOptionPane.YES_NO_OPTION);
        
        if(result == 0){
            System.exit(0);
        }else{
            System.out.println(result);            
        }
    }//GEN-LAST:event_btnCloseMouseClicked

    private void btnMaximizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMaximizeMouseClicked
        setExtendedState(home.MAXIMIZED_BOTH); 
    }//GEN-LAST:event_btnMaximizeMouseClicked

    private void btnCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseEntered
        btnClose.setBackground(new Color(63, 87, 107));
    }//GEN-LAST:event_btnCloseMouseEntered

    private void btnCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseExited
        btnClose.setBackground(new Color(48,67,82));
    }//GEN-LAST:event_btnCloseMouseExited

    private void btnMaximizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMaximizeMouseEntered
        btnMaximize.setBackground(new Color(63, 87, 107));       
    }//GEN-LAST:event_btnMaximizeMouseEntered

    private void btnMaximizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMaximizeMouseExited
         btnMaximize.setBackground(new Color(48,67,82));
    }//GEN-LAST:event_btnMaximizeMouseExited

    private void btnMinimizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizeMouseEntered
        btnMinimize.setBackground(new Color(63, 87, 107));        
    }//GEN-LAST:event_btnMinimizeMouseEntered

    private void btnMinimizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizeMouseExited
        btnMinimize.setBackground(new Color(48,67,82));
    }//GEN-LAST:event_btnMinimizeMouseExited

    private void kGradientPanel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kGradientPanel2MouseEntered
        kGradientPanel2.setkStartColor(new Color(224, 224, 224));
    }//GEN-LAST:event_kGradientPanel2MouseEntered

    private void btnBookingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBookingMouseClicked
        Booking_main_test booking = new Booking_main_test(log_name,user_role);
        booking.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnBookingMouseClicked

    private void btnBookingMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBookingMouseEntered
        btnBooking.setBackground(new Color(63, 87, 107)); 
    }//GEN-LAST:event_btnBookingMouseEntered

    private void btnBookingMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBookingMouseExited
        btnBooking.setBackground(new Color(48, 67, 82)); 
    }//GEN-LAST:event_btnBookingMouseExited

    private void btnSettingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSettingsMouseClicked
        Setting st = new Setting(log_name,user_role);
        st.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnSettingsMouseClicked

    private void btnSettingsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSettingsMouseEntered
        btnSettings.setBackground(new Color(63, 87, 107)); 
    }//GEN-LAST:event_btnSettingsMouseEntered

    private void btnSettingsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSettingsMouseExited
        btnSettings.setBackground(new Color(48, 67, 82)); 
    }//GEN-LAST:event_btnSettingsMouseExited

    private void btnBillingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBillingMouseClicked
        // TODO add your handling code here:
String Mes="<html>" +
                "    <h3>Go to Billing</h3>" +
                "    <p>You are trying to go to billing <b>without selecting a appointment/patient</b>are you sure?</p><br>" +
                "    <p>Click \"Go to Billing \" to go to invoice without selecting  </p><br>" +
                "    <p>Click \"Return\" to go back and select a appointment/patient </p>" +
                "</html>";
        int w = 180;
        Object[] options = { "Go to Billing", "Return" };
        int rowindex=-1;
        DefaultTableModel home_model = (DefaultTableModel) tbl_today.getModel();
        
        if( tbl_today.getSelectionModel().isSelectionEmpty()){
            
        }else{
            rowindex = tbl_today.getRowSorter().convertRowIndexToModel(tbl_today.getSelectedRow());
        }
         
//         System.out.println(rowindex);
               
             if(rowindex<0){
                       
                        int  n = JOptionPane.showOptionDialog(null, String.format(Mes, w, w), "Connect or Update?",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, // do not use a custom Icon
                            options, // the titles of buttons
                            options[0]); // default button title
                        if(n==0){
                                new Invoice(log_name,user_role).setVisible(true);
                                this.setVisible(false);
                        }
                        if(n==1){
                            return;
                        }
                    }
             if(rowindex>0){
                 
                        String PID = home_model.getValueAt(rowindex,5).toString();
                        String Name = home_model.getValueAt(rowindex,0).toString();
                              new Invoice(Name,PID,log_name,user_role).setVisible(true);
                              this.setVisible(false);
             }
        
    }//GEN-LAST:event_btnBillingMouseClicked

    private void btnBillingMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBillingMouseEntered
        btnBilling.setBackground(new Color(63, 87, 107)); 
    }//GEN-LAST:event_btnBillingMouseEntered

    private void btnBillingMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBillingMouseExited
        btnBilling.setBackground(new Color(48, 67, 82)); 
    }//GEN-LAST:event_btnBillingMouseExited

    private void kButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton1ActionPerformed
        DefaultTableModel home_model = (DefaultTableModel) tbl_today.getModel();
        // int rowindex = tbl_Mainbooking.getSelectedRow();
        int rowindex = tbl_today.getRowSorter().convertRowIndexToModel(tbl_today.getSelectedRow());
        
        String PID = home_model.getValueAt(rowindex,5).toString();
        String Name = home_model.getValueAt(rowindex,0).toString();
        String PhnNm = home_model.getValueAt(rowindex,3).toString();
        String BID = home_model.getValueAt(rowindex,4).toString();
        if(PID.equals( "Not Registered")){
             new patientRecords(PID,Name,PhnNm,BID ).setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(null, "Patient is Already Registered");
                  
        }
       
        
    }//GEN-LAST:event_kButton1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        TableLoad();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel MoveBar;
    private javax.swing.JPanel btnBilling;
    private javax.swing.JPanel btnBooking;
    private javax.swing.JLabel btnClose;
    private javax.swing.JLabel btnMaximize;
    private javax.swing.JLabel btnMinimize;
    private javax.swing.JPanel btnPatient;
    private javax.swing.JPanel btnSettings;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private keeptoo.KButton kButton1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private javax.swing.JLabel lblPatient;
    private javax.swing.JLabel lblPatient1;
    private javax.swing.JLabel lblPatient2;
    private javax.swing.JLabel lblPatient3;
    private javax.swing.JLabel lbl_datetime;
    private javax.swing.JLabel lbl_logname;
    private javax.swing.JTable tbl_today;
    // End of variables declaration//GEN-END:variables
}
