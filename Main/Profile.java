package Main;

import Admin.AdminDashboard;
import User.UserDashboard;
import java.awt.Color;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Profile extends javax.swing.JDialog {
    
    Connection con = null;
    PreparedStatement ps1=null, ps2=null;
    ResultSet rs1=null, rs2=null;
    DefaultTableModel model;
    String id;
    UserDashboard usr;
    AdminDashboard adm;
    
    private Profile(JFrame jFrame, boolean b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Profile(java.awt.Frame parent, boolean modal, String id) throws ClassNotFoundException, SQLException {
        super(parent, modal);
        initComponents();
        this.setTitle("Profile");
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Emporium", "root", "avrodeep1411");
        this.id = id;
        if (id.startsWith("usr")) {
            getUserDetails();
            usr = (UserDashboard) parent;
        }
        else {
            getAdminDetails();
            adm = (AdminDashboard) parent;
        }
    }
    
    private void getUserDetails () throws SQLException {
        ps1 = con.prepareStatement("SELECT * FROM UserProfile WHERE usr_id=?");
        ps2 = con.prepareStatement("SELECT * FROM UsrLgn WHERE usr_id=?");
        setDetails();
    }
    
    private void getAdminDetails () throws SQLException {
        ps1 = con.prepareStatement("SELECT * FROM AdminProfile WHERE adm_id=?");
        ps2 = con.prepareStatement("SELECT * FROM AdmLgn WHERE adm_id=?");
        setDetails();
    }
    
    private void setDetails () throws SQLException {
        ps1.setString(1, id);
        ps2.setString(1, id);
        rs1 = ps1.executeQuery();
        rs2 = ps2.executeQuery();
        rs1.next();
        rs2.next();
        jTextField1.setText(rs1.getString("fname"));
        jTextField2.setText(rs1.getString("lname"));
        jTextField3.setText(rs1.getString("email"));
        jTextField4.setText(rs2.getString("fpans"));
        jPasswordField1.setText(rs2.getString("pswrd"));
        jComboBox1.setSelectedItem(rs2.getString("fpques"));
    }
    
    private void updateUser() {
        try {
            ps1 = con.prepareStatement("UPDATE UserProfile SET fname=?,lname=?,email=? WHERE usr_id=?");
            ps1.setString(1, jTextField1.getText().trim());
            ps1.setString(2, jTextField2.getText().trim());
            ps1.setString(3, jTextField3.getText().trim());
            ps1.setString(4, id);
            ps1.executeUpdate();
            ps2 = con.prepareStatement("UPDATE UsrLgn SET pswrd=?,fpques=?,fpans=? WHERE usr_id=?");
            ps2.setString(1, jPasswordField1.getText().trim());
            ps2.setString(2, jComboBox1.getSelectedItem().toString());
            ps2.setString(3, jTextField4.getText());
            ps2.setString(4, id);
            ps2.executeUpdate();
            getUserDetails();
            usr.execute(id);
            JOptionPane.showMessageDialog(this, "Profile Update Successful!");
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void updateAdmin() {
        try {
            ps1 = con.prepareStatement("UPDATE AdminProfile SET fname=?,lname=?,email=? WHERE adm_id=?");
            ps1.setString(1, jTextField1.getText().trim());
            ps1.setString(2, jTextField2.getText().trim());
            ps1.setString(3, jTextField3.getText().trim());
            ps1.setString(4, id);
            ps1.executeUpdate();
            ps2 = con.prepareStatement("UPDATE AdmLgn SET pswrd=?,fpques=?,fpans=? WHERE adm_id=?");
            ps2.setString(1, jPasswordField1.getText().trim());
            ps2.setString(2, jComboBox1.getSelectedItem().toString());
            ps2.setString(3, jTextField4.getText());
            ps2.setString(4, id);
            ps2.executeUpdate();
            getAdminDetails();
            adm.execute(id);
            JOptionPane.showMessageDialog(this, "Profile Update Successful!");
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel18 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Baskerville Old Face", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("First Name :");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, 40));

        jTextField1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, 270, 40));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Baskerville Old Face", 0, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 102));
        jLabel13.setText("Last Name :");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, -1, 40));

        jTextField2.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 240, 270, 40));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Baskerville Old Face", 0, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 102));
        jLabel14.setText("Email ID :");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, -1, 40));

        jTextField3.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 310, 270, 40));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Baskerville Old Face", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("Password :");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, -1, 40));

        jPasswordField1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        getContentPane().add(jPasswordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 380, 240, 40));

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Baskerville Old Face", 0, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 102));
        jLabel18.setText("Security Question :");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, -1, 40));

        jComboBox1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Select One --", "How are you?", "What is your nickname?", "What is your pet's name?", "Favourite Sport?", "Date-of-Birth?" }));
        getContentPane().add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 450, 270, 40));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Baskerville Old Face", 0, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 102));
        jLabel17.setText("Security Answer :");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 520, -1, 40));

        jTextField4.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 520, 270, 40));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Script MT Bold", 1, 48)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("My Profile");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, -1, -1));

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("O");
        jLabel7.setOpaque(true);
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 380, 40, 40));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Trebuchet MS", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Save Changes");
        jLabel9.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 2, true)));
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.setOpaque(true);
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel9MouseExited(evt);
            }
        });
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 620, 150, 70));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/dialogs (2).jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseEntered
        jLabel9.setForeground(Color.WHITE);
        jLabel9.setBackground(Color.decode("#000066"));
    }//GEN-LAST:event_jLabel9MouseEntered

    private void jLabel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseExited
        jLabel9.setForeground(Color.decode("#000066"));
        jLabel9.setBackground(Color.WHITE);
    }//GEN-LAST:event_jLabel9MouseExited

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        if (jLabel7.getText().equals("O")) {   
            jLabel7.setText(Character.toString('\u00d8'));
            jLabel7.setForeground(Color.BLACK);
            jLabel7.setBackground(Color.WHITE);
            jPasswordField1.setEchoChar((char)0);
        }
        else {
            jLabel7.setText(Character.toString('O'));
            jLabel7.setBackground(Color.BLACK);
            jLabel7.setForeground(Color.WHITE);
            jLabel7.setBorder(null);
            jPasswordField1.setEchoChar('\u2022');
        }
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        int check;
        CheckEntries chk = new CheckEntries();
        check = chk.checkFname(jTextField1.getText().trim(), this);
        if (check == 0) {
            jTextField1.grabFocus();
            return;
        }
        check = chk.checkLname(jTextField2.getText().trim(), this);
        if (check == 0) {
            jTextField2.grabFocus();
            return;
        }
        check = chk.checkEmail(jTextField3.getText().trim(), this);
        if (check == 0) {
            jTextField3.grabFocus();
            return;
        }
        check = chk.checkPswrd(jPasswordField1.getText().trim(), this);
        if (check == 0) {
            jPasswordField1.grabFocus();
            return;
        }
        if (jComboBox1.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Security Question not selected.", "Warning!", JOptionPane.WARNING_MESSAGE);
            jComboBox1.grabFocus();
            return;
        }
        check = chk.checkSans(jTextField4.getText().trim(), this);
        if (check == 0) {
            jTextField4.grabFocus();
            return;
        }
        int yes = JOptionPane.showConfirmDialog(this, "Are you sure you want to Update Profile Data?", "Save Changes?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (yes == JOptionPane.NO_OPTION)
            return;
        if (id.startsWith("usr"))
            updateUser();
        else
            updateAdmin();
    }//GEN-LAST:event_jLabel9MouseClicked

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Profile dialog = new Profile(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
