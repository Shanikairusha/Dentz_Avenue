/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Booking.Booking;
import Booking.Insert_bookings;
import Database.DB_conn;
import Users.User;
import javafx.event.ActionEvent;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.awt.Color;
import java.awt.List;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.callback.ConfirmationCallback;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author SHA
 */
public class Booking_main_test extends javax.swing.JFrame {

    Boolean passres = false;
    public volatile boolean shutdown;
    /**
     * Creates new form Booking_main_test
     */
    int xMouse;
    int yMouse;

    public Booking_main_test() {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
//        try {
//            DB_conn.initFirbase();
//        } catch (IOException ex) {
//            Logger.getLogger(Insert_bookings.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        bookingDateChooser.setMinSelectableDate(date);
        TableLoad();
        // ----------------------------------------------------------------------------------------------------------------
        lblBookID.hide();
        lblbkID.hide();
        jLabel1.hide();
        hidecolumn();
        // setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    private String log_name;
    private String user_role;

    public Booking_main_test(String log_name, String jobr) {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        // ----------------------------------------------------------------------------------------------------------------

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        bookingDateChooser.setMinSelectableDate(date);
        TableLoad();
        // ----------------------------------------------------------------------------------------------------------------
        lblBookID.hide();
        lblbkID.hide();
        jLabel1.hide();
        hidecolumn();

        this.user_role = jobr;
        this.log_name = log_name;
        lbl_logname.setText(log_name);
    }
    public Booking_main_test(String PID){
          initComponents();
        setBackground(new Color(0, 0, 0, 0));
        // ----------------------------------------------------------------------------------------------------------------

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        bookingDateChooser.setMinSelectableDate(date);
        TableLoad();
        // ----------------------------------------------------------------------------------------------------------------
        lblBookID.hide();
        lblbkID.hide();
        jLabel1.hide();
        hidecolumn();
        
        
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRefDel = database.getReference("Dentz/Booking");
            DefaultTableModel book_model = (DefaultTableModel) tbl_Mainbooking.getModel();
             int size = book_model.getRowCount();
             
            myRefDel.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                   

                    for (int i = 0; i < size; i++) {
                        if ((book_model.getValueAt(i, 5)).equals(PID)) {
                            //JOptionPane.showMessageDialog(null, book_model.getValueAt(i, 6));
                           myRefDel.child(book_model.getValueAt(i, 6).toString()).removeValueAsync(); 
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    throw new UnsupportedOperationException("Not supported yet."); // To change body of generated
                    // methods, choose Tools | Templates.
                }
            });
        
    }
    public Booking_main_test(String PID, String MobileNm, String Name, int pass, String log_name,String usr_Job) {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        this.log_name = log_name;
        this.user_role=usr_Job;
        lbl_logname.setText(log_name);
        // ----------------------------------------------------------------------------------------------------------------

        lblBookID.hide();
        lblbkID.hide();
        jLabel1.hide();
        TableLoad();
        String html2 = "<html>" + "    <h3 >" + "        What do you want to with patient:- " + Name + "    </h3>"
                + "    <div >"
                + "    <p>If you want to connect newly registered patient with his existing first account "
                + "        please click <b>\"Connect with a booking\"</b></p><br>"
                + "        <p>If you want to: </p><ol type=\"1\">" + "            <li>Update Patient's Booking</li>"
                + "            <li>Update or Create Doctors note for the appointment</li>"
                + "            <li>Create new booking for a registered patient</li><br>"
                + "      <p> Please click <b>\"Update or create booking\"</b></p>" + "    </div>";

        int w = 180;

        // ----------------------------------------------------------------------------------------------------------------
        Object[] options = {"Connect with a Booking", "Update or Create Booking", "Cancel"};

        int n = JOptionPane.showOptionDialog(null, String.format(html2, w, w), "Connect or Update?",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, // do not use a custom Icon
                options, // the titles of buttons
                options[0]); // default button title

        // ----------------------------------------------------------------------------------------------------------------
        if (n == 0) {
            txtPID.setText(PID);
            txtPhn.setText(MobileNm);
            txtName.setText(Name);
            btnBookUpdate.setText("Connect");
            flash();
            passres = true;
            jLabel1.show();
        }
        if (n == 1) {
            txtPID.setText(PID);
            txtPhn.setText(MobileNm);
            txtName.setText(Name);            
            txtSearch.setText(PID);
            passres = false;
            searchFilter(PID);
        }
        if (n == 2) {
            System.exit(0);
        }

        // ----------------------------------------------------------------------------------------------------------------
        hidecolumn();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel2 = new keeptoo.KGradientPanel();
        jLabel1 = new javax.swing.JLabel();
        btnClose = new javax.swing.JLabel();
        btnMinimize = new javax.swing.JLabel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Mainbooking = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtPhn = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtReason = new javax.swing.JTextArea();
        time_booktime = new com.github.lgooddatepicker.components.TimePicker();
        jSeparator1 = new javax.swing.JSeparator();
        bookingDateChooser = new com.toedter.calendar.JCalendar();
        lblbkID = new javax.swing.JLabel();
        lblBookID = new javax.swing.JLabel();
        txtPID = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDocNote = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnLoad = new keeptoo.KButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnAdd2 = new keeptoo.KButton();
        btnNewBook = new keeptoo.KButton();
        btnBookUpdate = new keeptoo.KButton();
        btnBookDelete = new keeptoo.KButton();
        btnSearch = new keeptoo.KButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbl_logname = new javax.swing.JLabel();
        lblBack = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(780, 520));
        setUndecorated(true);
        setSize(new java.awt.Dimension(0, 0));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kGradientPanel2.setkBorderRadius(25);
        kGradientPanel2.setkEndColor(new java.awt.Color(164, 57, 49));
        kGradientPanel2.setkStartColor(new java.awt.Color(30, 67, 80));
        kGradientPanel2.setOpaque(false);
        kGradientPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                kGradientPanel2MouseDragged(evt);
            }
        });
        kGradientPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                kGradientPanel2MousePressed(evt);
            }
        });
        kGradientPanel2.setLayout(null);

        jLabel1.setText("You are in Connection mode Click here to go to the normal mode.....");
        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        kGradientPanel2.add(jLabel1);
        jLabel1.setBounds(540, 20, 480, 50);

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/close.png"))); // NOI18N
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCloseMouseClicked(evt);
            }
        });
        kGradientPanel2.add(btnClose);
        btnClose.setBounds(1260, 10, 27, 27);

        btnMinimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mini.png"))); // NOI18N
        btnMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimizeMouseClicked(evt);
            }
        });
        kGradientPanel2.add(btnMinimize);
        btnMinimize.setBounds(1230, 10, 27, 27);

        kGradientPanel1.setkBorderRadius(25);
        kGradientPanel1.setkEndColor(new java.awt.Color(240, 240, 240));
        kGradientPanel1.setkStartColor(new java.awt.Color(240, 240, 240));
        kGradientPanel1.setOpaque(false);
        kGradientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_Mainbooking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Date", "Time", "Name", "Phone Number", "Reason ", "PID", "Childkey", "Doctor Note"
            }
        ));
        tbl_Mainbooking.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_MainbookingMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_Mainbooking);

        kGradientPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 120, 620, 220));

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        kGradientPanel1.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 70, 260, 30));

        txtName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNameFocusGained(evt);
            }
        });
        kGradientPanel1.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 320, 260, 30));

        txtPhn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhnKeyTyped(evt);
            }
        });
        kGradientPanel1.add(txtPhn, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 370, 260, 30));

        txtReason.setColumns(20);
        txtReason.setRows(5);
        jScrollPane2.setViewportView(txtReason);

        kGradientPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 420, 260, 70));
        kGradientPanel1.add(time_booktime, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 510, 270, -1));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        kGradientPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 40, 30, 620));

        bookingDateChooser.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                bookingDateChooserVetoableChange(evt);
            }
        });
        kGradientPanel1.add(bookingDateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 380, 200));

        lblbkID.setText("Booking ID");
        kGradientPanel1.add(lblbkID, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 110, 20));
        kGradientPanel1.add(lblBookID, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 220, 230, 20));

        txtPID.setEditable(false);
        kGradientPanel1.add(txtPID, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 270, 260, 30));

        txtDocNote.setColumns(20);
        txtDocNote.setRows(5);
        jScrollPane3.setViewportView(txtDocNote);

        kGradientPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 470, 530, 130));

        jLabel10.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        jLabel10.setText("Doctor's Note");
        kGradientPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 440, -1, -1));

        jLabel12.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        jLabel12.setText("Name");
        kGradientPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, -1, -1));

        jLabel13.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        jLabel13.setText("Phone Number");
        kGradientPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, -1, -1));

        jLabel14.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        jLabel14.setText("Reason to visit");
        kGradientPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, -1, -1));

        btnLoad.setText("Refresh");
        btnLoad.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 18)); // NOI18N
        btnLoad.setkAllowGradient(false);
        btnLoad.setkBackGroundColor(new java.awt.Color(48, 67, 82));
        btnLoad.setkHoverColor(new java.awt.Color(63, 87, 107));
        btnLoad.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btnLoad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoadMouseClicked(evt);
            }
        });
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadActionPerformed(evt);
            }
        });
        kGradientPanel1.add(btnLoad, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 80, 130, 30));

        jLabel15.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        jLabel15.setText("PID");
        kGradientPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, -1, -1));

        jLabel16.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        jLabel16.setText("Time");
        kGradientPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, -1, -1));

        btnAdd2.setText("Go to Patient");
        btnAdd2.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 18)); // NOI18N
        btnAdd2.setkAllowGradient(false);
        btnAdd2.setkBackGroundColor(new java.awt.Color(48, 67, 82));
        btnAdd2.setkHoverColor(new java.awt.Color(63, 87, 107));
        btnAdd2.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btnAdd2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAdd2MouseClicked(evt);
            }
        });
        btnAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd2ActionPerformed(evt);
            }
        });
        kGradientPanel1.add(btnAdd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 350, 140, 30));

        btnNewBook.setText("Create a New Booking");
        btnNewBook.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 18)); // NOI18N
        btnNewBook.setkAllowGradient(false);
        btnNewBook.setkBackGroundColor(new java.awt.Color(48, 67, 82));
        btnNewBook.setkHoverColor(new java.awt.Color(63, 87, 107));
        btnNewBook.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btnNewBook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNewBookMouseClicked(evt);
            }
        });
        btnNewBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewBookActionPerformed(evt);
            }
        });
        kGradientPanel1.add(btnNewBook, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 580, 210, 40));

        btnBookUpdate.setText("Update");
        btnBookUpdate.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 18)); // NOI18N
        btnBookUpdate.setkAllowGradient(false);
        btnBookUpdate.setkAllowTab(true);
        btnBookUpdate.setkBackGroundColor(new java.awt.Color(48, 67, 82));
        btnBookUpdate.setkHoverColor(new java.awt.Color(63, 87, 107));
        btnBookUpdate.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btnBookUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBookUpdateMouseClicked(evt);
            }
        });
        btnBookUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookUpdateActionPerformed(evt);
            }
        });
        kGradientPanel1.add(btnBookUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 580, 120, 40));

        btnBookDelete.setText("Delete");
        btnBookDelete.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 18)); // NOI18N
        btnBookDelete.setkAllowGradient(false);
        btnBookDelete.setkBackGroundColor(new java.awt.Color(48, 67, 82));
        btnBookDelete.setkHoverColor(new java.awt.Color(63, 87, 107));
        btnBookDelete.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btnBookDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBookDeleteMouseClicked(evt);
            }
        });
        btnBookDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookDeleteActionPerformed(evt);
            }
        });
        kGradientPanel1.add(btnBookDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 580, 100, 40));

        btnSearch.setText("Search");
        btnSearch.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 18)); // NOI18N
        btnSearch.setkAllowGradient(false);
        btnSearch.setkBackGroundColor(new java.awt.Color(48, 67, 82));
        btnSearch.setkHoverColor(new java.awt.Color(63, 87, 107));
        btnSearch.setkHoverForeGround(new java.awt.Color(255, 255, 255));
        btnSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchMouseClicked(evt);
            }
        });
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        kGradientPanel1.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 70, 70, 30));

        kGradientPanel2.add(kGradientPanel1);
        kGradientPanel1.setBounds(10, 80, 1280, 680);

        jLabel2.setText("Booking");
        jLabel2.setFont(new java.awt.Font("Impact", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        kGradientPanel2.add(jLabel2);
        jLabel2.setBounds(230, 20, 121, 45);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-ticket-70.png"))); // NOI18N
        kGradientPanel2.add(jLabel8);
        jLabel8.setBounds(140, 0, 70, 70);

        lbl_logname.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_user_male_30px_1.png"))); // NOI18N
        lbl_logname.setText("Name");
        lbl_logname.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbl_logname.setForeground(new java.awt.Color(255, 255, 255));
        kGradientPanel2.add(lbl_logname);
        lbl_logname.setBounds(1070, 40, 120, 30);

        lblBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-go-back-70.png"))); // NOI18N
        lblBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBackMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblBackMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblBackMouseExited(evt);
            }
        });
        kGradientPanel2.add(lblBack);
        lblBack.setBounds(30, 10, 70, 60);

        getContentPane().add(kGradientPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 770));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        passres = false;
        shutdown = true;
        clear();
        jLabel1.hide();
        btnBookUpdate.hide(); 
        btnBookUpdate.setkBackGroundColor(Color.getHSBColor(206, 41,32)); 
        btnBookUpdate.show();
        btnBookUpdate.setText("Update");

    }//GEN-LAST:event_jLabel1MouseClicked

    private void kGradientPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kGradientPanel2MousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_kGradientPanel2MousePressed

    private void kGradientPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kGradientPanel2MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_kGradientPanel2MouseDragged
    ImageIcon iconEntered = new ImageIcon("img/icons8-go-back-702.png");
    ImageIcon iconExited = new ImageIcon("img/icons8-go-back-70.png");
    private void lblBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBackMouseClicked
        home hm = new home(log_name, user_role);
        hm.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_lblBackMouseClicked

    private void lblBackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBackMouseEntered
        lblBack.setIcon(iconEntered);
    }//GEN-LAST:event_lblBackMouseEntered

    private void lblBackMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBackMouseExited
        lblBack.setIcon(iconExited);
    }//GEN-LAST:event_lblBackMouseExited

    private void btnSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMouseClicked
        // TODO add your handling code here:
        searchFilter(txtSearch.getText());
    }//GEN-LAST:event_btnSearchMouseClicked

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtPhnKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhnKeyTyped
        // TODO add your handling code here:
        if(txtPhn.getText().length()>=10){
           evt.consume();
           JOptionPane.showMessageDialog(this, "Enter Valid Phone Number");
           txtPhn.setText("");
        }
            
    }//GEN-LAST:event_txtPhnKeyTyped

    private void formWindowActivated(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:

    }// GEN-LAST:event_formWindowActivated

    private void searchFilter(String search) {

        TableRowSorter sorter = new TableRowSorter(tbl_Mainbooking.getModel());

        sorter.setRowFilter(RowFilter.regexFilter("(?i).*\\Q" + search + "\\E.*"));

        tbl_Mainbooking.setRowSorter(sorter);

        if (tbl_Mainbooking.getRowCount() == 0) {
            sorter.setRowFilter(null);
            //JOptionPane.showMessageDialog(null, "No result");
        }

    }

    private void flash() {

        final long timeInterval = 700;
        shutdown = false;
        Runnable rm = new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (!shutdown) {

                    if (count % 2 == 0) {
                        jLabel1.setForeground(Color.red);
                    } else {
                        jLabel1.setForeground(Color.white);
                    }
                    count++;

                    try {
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread threadflsh = new Thread(rm);
        threadflsh.start();

    }

    private void tableSort() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tbl_Mainbooking.getModel());
        tbl_Mainbooking.setRowSorter(sorter);
        ArrayList list = new ArrayList();
        list.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
    }

    private void bookingDateChooserVetoableChange(java.beans.PropertyChangeEvent evt)
            throws java.beans.PropertyVetoException {// GEN-FIRST:event_bookingDateChooserVetoableChange
        // TODO add your handling code here:
    }// GEN-LAST:event_bookingDateChooserVetoableChange

    private void txtNameFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_txtNameFocusGained
        // TODO add your handling code here:

    }// GEN-LAST:event_txtNameFocusGained

    private void formWindowOpened(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:\

    }// GEN-LAST:event_formWindowOpened

    private void updateTable(String BookID, Booking updatedBook) {
        DefaultTableModel book_model = (DefaultTableModel) tbl_Mainbooking.getModel();

        int size = book_model.getRowCount();

        for (int i = 0; i < size; i++) {
            if ((book_model.getValueAt(i, 6)).equals(BookID)) {
                book_model.setValueAt(updatedBook.getDate(), i, 0);
                book_model.setValueAt(updatedBook.getBokingtTime(), i, 1);
                book_model.setValueAt(updatedBook.getName(), i, 2);
                book_model.setValueAt(updatedBook.getPhnNm(), i, 3);
                book_model.setValueAt(updatedBook.getReason(), i, 4);
                book_model.setValueAt(updatedBook.getPID(), i, 5);
                book_model.setValueAt(updatedBook.getBookID(), i, 6);
                book_model.setValueAt(updatedBook.getDocNote(), i, 7);
            }
        }

    }

    private void DeleteRecordTable(String BookID) {
        DefaultTableModel book_model = (DefaultTableModel) tbl_Mainbooking.getModel();

        int size = book_model.getRowCount();

        for (int i = 0; i < size; i++) {
            if ((book_model.getValueAt(i, 6)).equals(BookID)) {
                book_model.removeRow(i);

            }
        }
    }

    private void TableLoad() {
        // ----------------------------------------------------------------------------------------------------------------

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Dentz/Booking");
        ArrayList<Booking> allBookings = new ArrayList<Booking>();
        DefaultTableModel book_model = (DefaultTableModel) tbl_Mainbooking.getModel();
        book_model.setRowCount(0);
        Object rowdata[] = new Object[9];
        // ----------------------------------------------------------------------------------------------------------------

        // ----------------------------------------------------------------------------------------------------------------
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                // book_model.setRowCount(0);
                allBookings.clear();

                Booking books = dataSnapshot.getValue(Booking.class);
                allBookings.add(books);
                books.setBookID(dataSnapshot.getKey());

                rowdata[0] = allBookings.get(0).getDate();
                rowdata[1] = allBookings.get(0).getBokingtTime();
                rowdata[2] = allBookings.get(0).getName();
                rowdata[3] = allBookings.get(0).getPhnNm();
                rowdata[4] = allBookings.get(0).getReason();
                rowdata[5] = allBookings.get(0).getPID();
                rowdata[6] = allBookings.get(0).getBookID();
                rowdata[7] = allBookings.get(0).getDocNote();
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
                    // JOptionPane.showMessageDialog(null, child.getKey());

                }

                // ----------------------------------------------------------------------------------------------------------------
                for (int i = 0; i < allBookings.size(); i++) {
                    // JOptionPane.showMessageDialog(null, allBookings.get(i).getDocNote());
                    rowdata[0] = allBookings.get(i).getDate();
                    rowdata[1] = allBookings.get(i).getBokingtTime();
                    rowdata[2] = allBookings.get(i).getName();
                    rowdata[3] = allBookings.get(i).getPhnNm();
                    rowdata[4] = allBookings.get(i).getReason();
                    rowdata[5] = allBookings.get(i).getPID();
                    rowdata[6] = allBookings.get(i).getBookID();
                    rowdata[7] = allBookings.get(i).getDocNote();

                    book_model.addRow(rowdata);

                }

                // JOptionPane.showMessageDialog(null,
                // tbl_Mainbooking.getModel().getValueAt(0,6));
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

    private void hidecolumn() {

        TableColumn tcol = tbl_Mainbooking.getColumnModel().getColumn(6);
        tbl_Mainbooking.removeColumn(tcol);

    }

    private void tbl_MainbookingMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tbl_MainbookingMouseClicked
        // ----------------------------------------------------------------------------------------------------------------

        
        DefaultTableModel book_model = (DefaultTableModel) tbl_Mainbooking.getModel();
        // int rowindex = tbl_Mainbooking.getSelectedRow();
        int rowindex = tbl_Mainbooking.getRowSorter().convertRowIndexToModel(tbl_Mainbooking.getSelectedRow());
        String Dt = book_model.getValueAt(rowindex, 0).toString();
        LocalDate cladate = LocalDate.parse(Dt);
        java.util.Date date = java.sql.Date.valueOf(cladate);
        // ----------------------------------------------------------------------------------------------------------------

        if (passres == false) {
            clear();
            txtName.setText(book_model.getValueAt(rowindex, 2).toString());
            txtPhn.setText(book_model.getValueAt(rowindex, 3).toString());
            txtReason.setText(book_model.getValueAt(rowindex, 4).toString());
            time_booktime.setTime(LocalTime.parse(book_model.getValueAt(rowindex, 1).toString()));
            bookingDateChooser.setDate(date);
            lblBookID.setText(book_model.getValueAt(rowindex, 6).toString());
            txtDocNote.setText(book_model.getValueAt(rowindex, 7).toString());
            txtPID.setText(book_model.getValueAt(rowindex, 5).toString());
            

        }

        // ----------------------------------------------------------------------------------------------------------------
        if (passres == true) {
            // txtName.setText(book_model.getValueAt(rowindex, 2).toString());
            // txtPhn.setText(book_model.getValueAt(rowindex, 3).toString());
            txtReason.setText(book_model.getValueAt(rowindex, 4).toString());
            time_booktime.setTime(LocalTime.parse(book_model.getValueAt(rowindex, 1).toString()));
            bookingDateChooser.setDate(date);
            lblBookID.setText(book_model.getValueAt(rowindex, 6).toString());
            txtDocNote.setText(book_model.getValueAt(rowindex, 7).toString());
            
                 if(!book_model.getValueAt(rowindex, 5).toString().equals("Not Registered")){
                     btnBookUpdate.hide();
                      btnBookUpdate.setkBackGroundColor(Color.GRAY);
                     btnBookUpdate.show();
    
               }else{
                    btnBookUpdate.hide(); 
                    btnBookUpdate.setkBackGroundColor(Color.getHSBColor(206, 41,32)); 
                    btnBookUpdate.show();
                 }
                 
        }

    }
   
    private void clear() {
        txtDocNote.setText(null);
        txtName.setText(null);
        txtPhn.setText(null);
        txtReason.setText(null);
        // time_booktime.setText(null);
        // bookingDateChooser.setDate(null);
        txtPID.setText(null);
        txtDocNote.setText(null);
    }

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        String search = txtSearch.getText().toLowerCase();
        searchFilter(search);
    }// GEN-LAST:event_txtSearchKeyReleased

    private void MoveBarMouseDragged(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_MoveBarMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);

    }// GEN-LAST:event_MoveBarMouseDragged

    private void MoveBarMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_MoveBarMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }// GEN-LAST:event_MoveBarMousePressed

    private void btnMinimizeMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnMinimizeMouseClicked
        // login log = new login();
        this.setState(patientRecords.ICONIFIED);
    }// GEN-LAST:event_btnMinimizeMouseClicked

    private void btnCloseMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnCloseMouseClicked
        int result = JOptionPane.showConfirmDialog(this, "Are You Sure You Want To Cancel?", null, JOptionPane.YES_NO_OPTION);

        if (result == 0) {
            this.dispose();
            home hm = new home(log_name, user_role);
            hm.setVisible(true);
        } else {
            System.out.println(result);

        }
    }// GEN-LAST:event_btnCloseMouseClicked

    private void btnLoadMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnLoadMouseClicked

        TableLoad();

    }// GEN-LAST:event_btnLoadMouseClicked

    private void btnAdd2MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnAdd2MouseClicked
        // TODO add your handling code here:
         DefaultTableModel book_model = (DefaultTableModel) tbl_Mainbooking.getModel();
         
         if(tbl_Mainbooking.getSelectionModel().isSelectionEmpty()){
             JOptionPane.showMessageDialog(null, "Please select a booking first");
         }else{
             String Name= txtName.getText();
             String PhnNm = txtPhn.getText();
             String PID= txtPID.getText();
             
              new patientRecords(PID,Name,PhnNm,PID ).setVisible(true);
         }
        
    }// GEN-LAST:event_btnAdd2MouseClicked

    private void btnLoadActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLoadActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnLoadActionPerformed

    private void btnAdd2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAdd2ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnAdd2ActionPerformed

    private void btnNewBookMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnNewBookMouseClicked

        // ----------------------------------------------------------------------------------------------------------------
        String nic;
        Booking new_bookings = new Booking();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String thedate = dateFormat.format(bookingDateChooser.getDate());
        if (txtPID.getText().isEmpty()) {
            nic = "Not Registered";
        } else {
            nic = txtPID.getText();
        }

        // ----------------------------------------------------------------------------------------------------------------
        new_bookings.setDate(thedate);
        new_bookings.setName(txtName.getText());
        new_bookings.setPhnNm(Integer.valueOf(txtPhn.getText()));
        new_bookings.setReason(txtReason.getText());
        new_bookings.setBokingtTime(time_booktime.getTime().toString());
        new_bookings.setPID(nic);
        new_bookings.setDocNote(txtDocNote.getText());

        // ----------------------------------------------------------------------------------------------------------------
        // inrtBoks.Insert_tempbookings(new_bookings);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Dentz/Booking");
        // ----------------------------------------------------------------------------------------------------------------

        clear();

        // ----------------------------------------------------------------------------------------------------------------
        myRef.push().setValue(new_bookings, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                if (de != null) {
                    JOptionPane.showMessageDialog(null, dr);
                } else {
                    JOptionPane.showMessageDialog(null, "Data Saved Succesfully");
                }
                throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods,
                // choose Tools | Templates.
            }
        });
        // ----------------------------------------------------------------------------------------------------------------

    }// GEN-LAST:event_btnNewBookMouseClicked

    private void btnNewBookActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNewBookActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnNewBookActionPerformed

    private void btnBookUpdateMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnBookUpdateMouseClicked
        String nic;
        Booking new_bookings = new Booking();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String thedate = dateFormat.format(bookingDateChooser.getDate());
        if (txtPID.getText().isEmpty()) {
            nic = "Not Registered";
        } else {
            nic = txtPID.getText();
        }
        // ----------------------------------------------------------------------------------------------------------------
        new_bookings.setDate(thedate);
        new_bookings.setName(txtName.getText());
        new_bookings.setPhnNm(Integer.valueOf(txtPhn.getText()));
        new_bookings.setReason(txtReason.getText());
        new_bookings.setBokingtTime(time_booktime.getTime().toString());
        new_bookings.setPID(nic);
        new_bookings.setDocNote(txtDocNote.getText());

        // ----------------------------------------------------------------------------------------------------------------
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Dentz/Booking");

        // ----------------------------------------------------------------------------------------------------------------
        clear();

        // ----------------------------------------------------------------------------------------------------------------
        Map<String, Object> BookUpdates = new HashMap<>();
        BookUpdates.put(lblBookID.getText(), new_bookings);

        myRef.updateChildrenAsync(BookUpdates);
        
        btnBookUpdate.setText("Update");

    }// GEN-LAST:event_btnBookUpdateMouseClicked

    private void btnBookUpdateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnBookUpdateActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnBookUpdateActionPerformed

    private void btnBookDeleteMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_btnBookDeleteMouseClicked
        int result = JOptionPane.showConfirmDialog(null, "Are you sure?", "Do you want to delete",
                JOptionPane.YES_NO_CANCEL_OPTION);

        if (result == 0) {

            // ----------------------------------------------------------------------------------------------------------------
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Dentz/Booking");

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    myRef.child(lblBookID.getText()).removeValueAsync();
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    throw new UnsupportedOperationException("Not supported yet."); // To change body of generated
                    // methods, choose Tools | Templates.
                }
            });
            // ----------------------------------------------------------------------------------------------------------------

            // ----------------------------------------------------------------------------------------------------------------
            clear();

            // ----------------------------------------------------------------------------------------------------------------
            JOptionPane.showMessageDialog(null, "Succesfully Deleted");

        }
    }// GEN-LAST:event_btnBookDeleteMouseClicked

    private void btnBookDeleteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnBookDeleteActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_btnBookDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
        
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Booking_main_test.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Booking_main_test.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Booking_main_test.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Booking_main_test.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Booking_main_test().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JCalendar bookingDateChooser;
    private keeptoo.KButton btnAdd2;
    private keeptoo.KButton btnBookDelete;
    private keeptoo.KButton btnBookUpdate;
    private javax.swing.JLabel btnClose;
    private keeptoo.KButton btnLoad;
    private javax.swing.JLabel btnMinimize;
    private keeptoo.KButton btnNewBook;
    private keeptoo.KButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private javax.swing.JLabel lblBack;
    private javax.swing.JLabel lblBookID;
    private javax.swing.JLabel lbl_logname;
    private javax.swing.JLabel lblbkID;
    private javax.swing.JTable tbl_Mainbooking;
    private com.github.lgooddatepicker.components.TimePicker time_booktime;
    private javax.swing.JTextArea txtDocNote;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPID;
    private javax.swing.JTextField txtPhn;
    private javax.swing.JTextArea txtReason;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
