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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Darkness
 */
public class Setting extends javax.swing.JFrame {

    /**
     * Creates new form Setting
     */
    int xMouse;
    int yMouse;
    String selected_key;

    public Setting() {
//         setExtendedState(Setting.MAXIMIZED_BOTH);
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        // hide the 9th column in tbl_setting table

        tbl_setting.removeColumn(tbl_setting.getColumnModel().getColumn(8));
//        tbl_setting.getColumnModel().getColumn(8).setMaxWidth(0);
//        tbl_setting.getColumnModel().getColumn(8).setMinWidth(0);
//        tbl_setting.getColumnModel().getColumn(8).setWidth(0);

//        try {
//            DB_conn.initFirbase();
//        } catch (IOException ex) {
//            Logger.getLogger(Insert_bookings.class.getName()).log(Level.SEVERE, null, ex);
//        }
        tableload();
    }
    private String log_name;
    private String user_role;

    public Setting(String log_name, String jobrole) {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        setExtendedState(Setting.MAXIMIZED_BOTH);

        // hide the 9th column in tbl_setting table        
        tbl_setting.removeColumn(tbl_setting.getColumnModel().getColumn(8));
        tableload();

        this.user_role = jobrole;
        this.log_name = log_name;
        lbl_logname.setText(log_name);
    }

    private void tableload() {
        // ----------------------------------------------------------------------------------------------------------------

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Dentz/Users");
        ArrayList<User> allusers = new ArrayList<User>();
        DefaultTableModel user_model = (DefaultTableModel) tbl_setting.getModel();
        user_model.setRowCount(0);
        Object rowdata[] = new Object[9];
        // ----------------------------------------------------------------------------------------------------------------
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                // book_model.setRowCount(0);
                allusers.clear();

                User books = dataSnapshot.getValue(User.class);
                books.setUid(dataSnapshot.getKey());
                allusers.add(books);

                rowdata[0] = allusers.get(0).getJobRole();
                rowdata[1] = allusers.get(0).getName();
                rowdata[2] = allusers.get(0).getNIC();
                rowdata[3] = allusers.get(0).getDOB();
                rowdata[4] = allusers.get(0).getEmail();
                rowdata[5] = allusers.get(0).getMobileNm();
                rowdata[6] = allusers.get(0).getUserName();
                rowdata[7] = allusers.get(0).getAdress();
                rowdata[8] = allusers.get(0).getUid();
                user_model.addRow(rowdata);
//                tableSort();
            }
//

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {

                User books = dataSnapshot.getValue(User.class);
                String key = dataSnapshot.getKey();
                updateTable(key, books); // send update details with the key for fill the table
//                tableSort();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                User books = dataSnapshot.getValue(User.class);
                DeleteRecordTable(dataSnapshot.getKey());
//                tableSort();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
//        // ----------------------------------------------------------------------------------------------------------------
//
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user_model.setRowCount(0);
                allusers.clear();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                // size= DataSnapshot.getChildrenCount();
                for (DataSnapshot child : children) {
                    User books = child.getValue(User.class);
                    books.setUid(child.getKey());
                    allusers.add(books);
                    // JOptionPane.showMessageDialog(null, child.getKey());
                }
                // ----------------------------------------------------------------------------------------------------------------

                for (int i = 0; i < allusers.size(); i++) {
                    // JOptionPane.showMessageDialog(null, allBookings.get(i).getDocNote());
                    rowdata[0] = allusers.get(i).getJobRole();
                    rowdata[1] = allusers.get(i).getName();
                    rowdata[2] = allusers.get(i).getNIC();
                    rowdata[3] = allusers.get(i).getDOB();
                    rowdata[4] = allusers.get(i).getEmail();
                    rowdata[5] = allusers.get(i).getMobileNm();
                    rowdata[6] = allusers.get(i).getUserName();
                    rowdata[7] = allusers.get(i).getAdress();
                    rowdata[8] = allusers.get(i).getUid();
                    user_model.addRow(rowdata);
                }
                // JOptionPane.showMessageDialog(null,
                // tbl_Mainbooking.getModel().getValueAt(0,6));
                allusers.clear();
//               tableSort();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
//  ---------------------------------------------------------------------------------------------------------------        
    }

    private void DeleteRecordTable(String BookID) {
        DefaultTableModel user_model = (DefaultTableModel) tbl_setting.getModel();

        int size = user_model.getRowCount();

        for (int i = 0; i < size; i++) {
            if ((user_model.getValueAt(i, 8)).equals(BookID)) {
                user_model.removeRow(i);
            }
        }
    }

    private void updateTable(String BookID, User updatedBook) {
        DefaultTableModel user_model = (DefaultTableModel) tbl_setting.getModel();

        int size = user_model.getRowCount();

        for (int i = 0; i < size; i++) {
            if ((user_model.getValueAt(i, 8)).equals(BookID)) {
                user_model.setValueAt(updatedBook.getJobRole(), i, 0);
                user_model.setValueAt(updatedBook.getName(), i, 1);
                user_model.setValueAt(updatedBook.getNIC(), i, 2);
                user_model.setValueAt(updatedBook.getDOB(), i, 3);
                user_model.setValueAt(updatedBook.getEmail(), i, 4);
                user_model.setValueAt(updatedBook.getMobileNm(), i, 5);
                user_model.setValueAt(updatedBook.getUserName(), i, 6);
                user_model.setValueAt(updatedBook.getAdress(), i, 7);
                user_model.setValueAt(BookID, i, 8);
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

        kGradientPanel3 = new keeptoo.KGradientPanel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        btnHome = new javax.swing.JPanel();
        lblPatient = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnadduser = new javax.swing.JPanel();
        lblPatient5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btndeleteuser = new javax.swing.JPanel();
        lblPatient10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnedit = new javax.swing.JPanel();
        lblPatient6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnHome1 = new javax.swing.JPanel();
        lblPatient1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_setting = new javax.swing.JTable();
        btnClose = new javax.swing.JLabel();
        btnMinimize = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbl_logname = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        kGradientPanel3.setkBorderRadius(25);
        kGradientPanel3.setkEndColor(new java.awt.Color(40, 62, 81));
        kGradientPanel3.setkStartColor(new java.awt.Color(57, 90, 119));
        kGradientPanel3.setOpaque(false);
        kGradientPanel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                kGradientPanel3MouseDragged(evt);
            }
        });
        kGradientPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                kGradientPanel3MousePressed(evt);
            }
        });

        kGradientPanel1.setkBorderRadius(35);
        kGradientPanel1.setkEndColor(new java.awt.Color(240, 240, 240));
        kGradientPanel1.setkStartColor(new java.awt.Color(240, 240, 240));
        kGradientPanel1.setOpaque(false);

        btnHome.setBackground(new java.awt.Color(40, 62, 81));
        btnHome.setPreferredSize(new java.awt.Dimension(133, 133));
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHomeMouseExited(evt);
            }
        });
        btnHome.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPatient.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        lblPatient.setForeground(new java.awt.Color(255, 255, 255));
        lblPatient.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPatient.setText("HOME");
        btnHome.add(lblPatient, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 130, 30));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_home_50px_3.png"))); // NOI18N
        btnHome.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 90, 80));

        btnadduser.setBackground(new java.awt.Color(40, 62, 81));
        btnadduser.setPreferredSize(new java.awt.Dimension(133, 133));
        btnadduser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnadduserMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnadduserMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnadduserMouseExited(evt);
            }
        });
        btnadduser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPatient5.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        lblPatient5.setForeground(new java.awt.Color(255, 255, 255));
        lblPatient5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPatient5.setText("ADD USERS");
        btnadduser.add(lblPatient5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 130, 30));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_user_male_60px_4.png"))); // NOI18N
        btnadduser.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 90, 80));

        btndeleteuser.setBackground(new java.awt.Color(40, 62, 81));
        btndeleteuser.setPreferredSize(new java.awt.Dimension(133, 133));
        btndeleteuser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndeleteuserMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btndeleteuserMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btndeleteuserMouseExited(evt);
            }
        });
        btndeleteuser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPatient10.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        lblPatient10.setForeground(new java.awt.Color(255, 255, 255));
        lblPatient10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPatient10.setText("DELETE USERS");
        btndeleteuser.add(lblPatient10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 130, 30));

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_denied_60px.png"))); // NOI18N
        btndeleteuser.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 90, 80));

        btnedit.setBackground(new java.awt.Color(40, 62, 81));
        btnedit.setPreferredSize(new java.awt.Dimension(133, 133));
        btnedit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btneditMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btneditMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btneditMouseExited(evt);
            }
        });
        btnedit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPatient6.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        lblPatient6.setForeground(new java.awt.Color(255, 255, 255));
        lblPatient6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPatient6.setText("EDIT PROFILE");
        btnedit.add(lblPatient6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 130, 30));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_registration_52px_1.png"))); // NOI18N
        btnedit.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 90, 80));

        btnHome1.setBackground(new java.awt.Color(40, 62, 81));
        btnHome1.setPreferredSize(new java.awt.Dimension(133, 133));
        btnHome1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHome1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHome1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHome1MouseExited(evt);
            }
        });
        btnHome1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPatient1.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        lblPatient1.setForeground(new java.awt.Color(255, 255, 255));
        lblPatient1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPatient1.setText("VIEW INVOICE");
        btnHome1.add(lblPatient1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 130, 30));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_bill_60px_1.png"))); // NOI18N
        btnHome1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 90, 90));

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnadduser, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btndeleteuser, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnedit, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnHome1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnedit, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnHome1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addComponent(btndeleteuser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnadduser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(28, 28, 28))
        );

        kGradientPanel2.setkBorderRadius(25);
        kGradientPanel2.setkEndColor(new java.awt.Color(240, 240, 240));
        kGradientPanel2.setkStartColor(new java.awt.Color(240, 240, 240));
        kGradientPanel2.setOpaque(false);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel5.setText("Registerd Users: ");

        jScrollPane1.setBorder(null);
        jScrollPane1.setOpaque(false);

        tbl_setting.setBackground(new java.awt.Color(240, 240, 240));
        tbl_setting.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tbl_setting.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Job Role", "Name", "NIC", "DOB", "E-Mail", "Mobile", "Username", "Address", "UID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_setting.setFocusable(false);
        tbl_setting.setGridColor(new java.awt.Color(240, 240, 240));
        tbl_setting.setRowHeight(20);
        tbl_setting.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbl_setting.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbl_setting);

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1))
                .addGap(28, 28, 28))
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/close.png"))); // NOI18N
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCloseMouseClicked(evt);
            }
        });

        btnMinimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/mini.png"))); // NOI18N
        btnMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimizeMouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Impact", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Settings");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-settings-80.png"))); // NOI18N

        lbl_logname.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbl_logname.setForeground(new java.awt.Color(255, 255, 255));
        lbl_logname.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_user_male_30px_1.png"))); // NOI18N
        lbl_logname.setText("Name");

        javax.swing.GroupLayout kGradientPanel3Layout = new javax.swing.GroupLayout(kGradientPanel3);
        kGradientPanel3.setLayout(kGradientPanel3Layout);
        kGradientPanel3Layout.setHorizontalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel8)
                .addGap(40, 40, 40)
                .addComponent(jLabel2)
                .addGroup(kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 994, Short.MAX_VALUE)
                        .addComponent(btnMinimize)
                        .addGap(0, 0, 0)
                        .addComponent(btnClose)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_logname, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1360, Short.MAX_VALUE)
                    .addComponent(kGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1360, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        kGradientPanel3Layout.setVerticalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(kGradientPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2))
                    .addGroup(kGradientPanel3Layout.createSequentialGroup()
                        .addGroup(kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnMinimize)
                            .addComponent(btnClose))
                        .addGap(18, 18, 18)
                        .addComponent(lbl_logname)))
                .addGap(10, 10, 10)
                .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(kGradientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1380, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1380, 685));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
        home home = new home(log_name,user_role);
        home.setVisible(true);
        this.setVisible(false);

    }//GEN-LAST:event_btnHomeMouseClicked

    private void btnHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseEntered
        btnHome.setBackground(new Color(63, 87, 107));
    }//GEN-LAST:event_btnHomeMouseEntered

    private void btnHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseExited
        btnHome.setBackground(new Color(48, 67, 82));
    }//GEN-LAST:event_btnHomeMouseExited

    private void btnadduserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnadduserMouseClicked
        user_reg register = new user_reg();
        register.setVisible(true);
//       this.setVisible(false);
    }//GEN-LAST:event_btnadduserMouseClicked

    private void btnadduserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnadduserMouseEntered
        btnadduser.setBackground(new Color(63, 87, 107));
    }//GEN-LAST:event_btnadduserMouseEntered

    private void btnadduserMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnadduserMouseExited
        btnadduser.setBackground(new Color(48, 67, 82));
    }//GEN-LAST:event_btnadduserMouseExited

    private void btneditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btneditMouseClicked

        int row = -1;
        row = tbl_setting.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "You need to Select a User");
        } else {

            String value = tbl_setting.getModel().getValueAt(row, 8).toString(); //get key value of selected row

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Dentz/Users");
            ArrayList<User> allusers = new ArrayList<User>();

            Object rowdata[] = new Object[9];
            // ----------------------------------------------------------------------------------------------------------------
            myRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
//        // ----------------------------------------------------------------------------------------------------------------
//
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    allusers.clear();
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    // size= DataSnapshot.getChildrenCount();
                    for (DataSnapshot child : children) {
                        User books = child.getValue(User.class);
                        if (value == (child.getKey())) {
                            books.setUid(child.getKey());
                            System.out.println(child.getKey());
                            user_reg userregform = new user_reg(books);// send ritrive data to *user_reg* form
                            userregform.setVisible(true);
                        }
                        allusers.add(books);

                        // JOptionPane.showMessageDialog(null, child.getKey());
                    }
                    allusers.clear();
//               tableSort();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }
//  ---------------------------------------------------------------------------------------------------------------        

    }//GEN-LAST:event_btneditMouseClicked

    private void btneditMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btneditMouseEntered
        btnedit.setBackground(new Color(63, 87, 107));
    }//GEN-LAST:event_btneditMouseEntered

    private void btneditMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btneditMouseExited
        btnedit.setBackground(new Color(48, 67, 82));
    }//GEN-LAST:event_btneditMouseExited

    private void btndeleteuserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndeleteuserMouseClicked

        int row = -1;
        row = tbl_setting.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "You need to Select a User");
        } else {
            int result = JOptionPane.showConfirmDialog(this, "Are You Sure You want to Delete this User?", "Delete User", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            if (result == 1) {

            } else {
                String value = tbl_setting.getModel().getValueAt(row, 8).toString(); //get key value of selected row
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Dentz/Users");

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot ds) {
                        myRef.child(value).removeValueAsync();      // remove node that related to x value
                    }

                    @Override
                    public void onCancelled(DatabaseError de) {
                        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated
                        // methods, choose Tools | Templates.
                    }
                });
            }
        }


    }//GEN-LAST:event_btndeleteuserMouseClicked

    private void btndeleteuserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndeleteuserMouseEntered
        btndeleteuser.setBackground(new Color(63, 87, 107));
    }//GEN-LAST:event_btndeleteuserMouseEntered

    private void btndeleteuserMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndeleteuserMouseExited
        btndeleteuser.setBackground(new Color(48, 67, 82));
    }//GEN-LAST:event_btndeleteuserMouseExited

    private void btnCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseClicked
        int result = JOptionPane.showConfirmDialog(this, "Are You Sure You Want To Cancel?", null, JOptionPane.YES_NO_OPTION);

        if (result == 0) {
            this.dispose();
            home hm = new home(log_name, user_role);
            hm.setVisible(true);
        } else {
            System.out.println(result);

        }
    }//GEN-LAST:event_btnCloseMouseClicked

    private void btnMinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizeMouseClicked
        this.setState(Setting.ICONIFIED);
    }//GEN-LAST:event_btnMinimizeMouseClicked

    private void kGradientPanel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kGradientPanel3MousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_kGradientPanel3MousePressed

    private void kGradientPanel3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kGradientPanel3MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_kGradientPanel3MouseDragged

    private void btnHome1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHome1MouseClicked
         ViewInvoice invoice = new ViewInvoice();
         invoice.setVisible(true);
    }//GEN-LAST:event_btnHome1MouseClicked

    private void btnHome1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHome1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHome1MouseEntered

    private void btnHome1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHome1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHome1MouseExited
     
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
            java.util.logging.Logger.getLogger(Setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Setting().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnClose;
    private javax.swing.JPanel btnHome;
    private javax.swing.JPanel btnHome1;
    private javax.swing.JLabel btnMinimize;
    private javax.swing.JPanel btnadduser;
    private javax.swing.JPanel btndeleteuser;
    private javax.swing.JPanel btnedit;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private keeptoo.KGradientPanel kGradientPanel3;
    private javax.swing.JLabel lblPatient;
    private javax.swing.JLabel lblPatient1;
    private javax.swing.JLabel lblPatient10;
    private javax.swing.JLabel lblPatient5;
    private javax.swing.JLabel lblPatient6;
    private javax.swing.JLabel lbl_logname;
    private javax.swing.JTable tbl_setting;
    // End of variables declaration//GEN-END:variables
}
