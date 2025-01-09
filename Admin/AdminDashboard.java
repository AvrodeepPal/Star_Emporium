package Admin;

import Emporium.CheckBooks;
import Emporium.BookRegister;
import Main.CheckEntries;
import Main.Loading;
import Main.Profile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.Vector;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class AdminDashboard extends javax.swing.JFrame {

    public Loading load = null;
    Connection con = null;
    PreparedStatement ps1 = null, ps2 = null;
    ResultSet rs1 = null, rs2 = null;
    DefaultTableModel model;
    String admid;
    private boolean menu = true; // true for expanded, false for collapsed

    public AdminDashboard() throws ClassNotFoundException, SQLException {
        initComponents();
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Emporium", "root", "avrodeep1411");
        hideAllPanels();
        jPanel4.setVisible(true);
    }

    public void execute(String admid) throws SQLException {
        this.admid = admid;
        loginInfo();
    }

    private void fadeout() {
        for (double i = 1.0; i > 0.0; i -= 0.1) {
            this.setOpacity(Float.parseFloat("" + i));
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void collapseMenubar() {
        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    for (int i = 350; i > 100; i--) {
                        Thread.sleep(1);
                        jPanel2.setSize(i, 900);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };
        th.start();
        menu = false;
        jPanel3.setVisible(false);
    }

    private void expandMenubar() {
        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    for (int i = 100; i <= 350; i++) {
                        Thread.sleep(1);
                        jPanel2.setSize(i, 900);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };
        th.start();
        menu = true;
        jPanel3.setVisible(true);
    }

    private void hideAllPanels() {
        jPanel4.setVisible(false);
        jPanel5.setVisible(false);
        jPanel6.setVisible(false);
        jPanel7.setVisible(false);
    }

    private void loginInfo() throws SQLException {
        ps1 = con.prepareStatement("SELECT * FROM AdminProfile WHERE adm_id=?");
        ps1.setString(1, admid);
        rs1 = ps1.executeQuery();
        rs1.next();
        jLabel4.setText("Welcome, " + rs1.getString("fname") + " " + rs1.getString("lname"));
    }

    private void refreshTable1() throws SQLException {
        ps1 = con.prepareStatement("SELECT * FROM UserProfile;");
        rs1 = ps1.executeQuery();
        ResultSetMetaData rsmd = rs1.getMetaData();
        int n = rsmd.getColumnCount();
        model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        while (rs1.next()) {
            ps2 = con.prepareStatement("SELECT * FROM UsrLgn WHERE usr_id=?;");
            ps2.setString(1, rs1.getString("usr_id"));
            rs2 = ps2.executeQuery();
            rs2.next();
            Vector v = new Vector();
            for (int i = 1; i <= n; i++) { // i=0 is table header
                v.add(rs1.getString(1));
                v.add(rs1.getString(2));
                v.add(rs1.getString(3));
                v.add(rs1.getString(4));
                v.add(rs1.getInt(5));
                v.add(rs2.getString(3));
                v.add(rs2.getString(4));
            }
            model.addRow(v);
        }
        model = (DefaultTableModel) jTable1.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        jTable1.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter("usr"));
    }

    public void refreshTable2() throws SQLException {
        ps1 = con.prepareStatement("SELECT * FROM AdminProfile;");
        rs1 = ps1.executeQuery();
        ResultSetMetaData rsmd = rs1.getMetaData();
        int n = rsmd.getColumnCount();
        model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
        while (rs1.next()) {
            ps2 = con.prepareStatement("SELECT * FROM AdmLgn WHERE adm_id=?;");
            ps2.setString(1, rs1.getString("adm_id"));
            rs2 = ps2.executeQuery();
            rs2.next();
            Vector v = new Vector();
            for (int i = 1; i <= n; i++) { // i=0 is table header
                v.add(rs1.getString(1));
                v.add(rs1.getString(2));
                v.add(rs1.getString(3));
                v.add(rs1.getString(4));
                v.add(rs2.getString(3));
                v.add(rs2.getString(4));
            }
            model.addRow(v);
        }
        model = (DefaultTableModel) jTable2.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        jTable2.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter("adm"));
    }

    public void refreshTable3() throws SQLException {
        ps1 = con.prepareStatement("SELECT * FROM Empo;");
        rs1 = ps1.executeQuery();
        ResultSetMetaData rsmd = rs1.getMetaData();
        int n = rsmd.getColumnCount();
        model = (DefaultTableModel) jTable3.getModel();
        model.setRowCount(0);
        while (rs1.next()) {
            Vector v = new Vector();
            for (int i = 1; i <= n; i++) { // i=0 is table header
                v.add(rs1.getString(1));
                v.add(rs1.getString(2));
                v.add(rs1.getString(3));
                v.add(rs1.getString(4));
                v.add(rs1.getInt(5));
            }
            model.addRow(v);
        }
        model = (DefaultTableModel) jTable3.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        jTable3.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter("bk"));
    }

    private void clearAll1() throws SQLException {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jComboBox1.setSelectedIndex(0);
        jTable1.clearSelection();
    }

    private void clearAll2() {
        jTextField8.setText("");
        jTextField9.setText("");
        jTextField10.setText("");
        jTextField11.setText("");
        jTextField12.setText("");
        jTextField13.setText("");
        jComboBox2.setSelectedIndex(0);
        jTable2.clearSelection();
    }

    private void clearAll3() {
        jTextField14.setText("");
        jTextField15.setText("");
        jTextField16.setText("");
        jTextField18.setText("");
        jTextField17.setText("");
        jTextField19.setText("");
        jTable3.clearSelection();
    }

    private void manageUsersPanel() throws SQLException {
        refreshTable1();
        clearAll1();
    }

    private void manageAdminsPanel() throws SQLException {
        refreshTable2();
        clearAll2();
    }

    private void manageEmporiumPanel() throws SQLException {
        refreshTable3();
        clearAll3();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel44 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/logo.jpg"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 170, 170));

        jLabel16.setBackground(new java.awt.Color(255, 0, 0));
        jLabel16.setFont(new java.awt.Font("Segoe UI Emoji", 1, 48)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("X");
        jLabel16.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 0), 2));
        jLabel16.setOpaque(true);
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(1350, 50, 90, 60));

        jLabel28.setFont(new java.awt.Font("Segoe Script", 3, 48)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 0));
        jLabel28.setText("Star Emporium");
        jPanel1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, -1, -1));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Version 1.48");
        jPanel1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 170, -1, -1));

        jLabel30.setFont(new java.awt.Font("Papyrus", 1, 24)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 204, 255));
        jLabel30.setText("The Ultimate eBook Store for Learners!!!");
        jPanel1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 120, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 220));

        jPanel2.setBackground(new java.awt.Color(255, 255, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 102));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/arrow-left-s-fill.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.setOpaque(true);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 0, 30, 900));

        jPanel3.setBackground(new java.awt.Color(255, 255, 102));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/profilepic.jpg"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, -1));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Username");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 220, 180));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 350, 10));
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 223, 350, 10));

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/menu-fold-fill.png"))); // NOI18N
        jLabel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 40, 40));

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Menu");
        jLabel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, 180, 40));

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/home-4-line.png"))); // NOI18N
        jLabel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 40, 40));

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Home");
        jLabel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 330, 180, 40));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/user-settings-line.png"))); // NOI18N
        jLabel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 40, 40));

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Times New Roman", 0, 22)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Manage Users");
        jLabel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 380, 180, 40));

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/admin-line.png"))); // NOI18N
        jLabel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, 40, 40));

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("Times New Roman", 0, 22)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Manage Admins");
        jLabel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 430, 180, 40));

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/book-2-line.png"))); // NOI18N
        jLabel23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, 40, 40));

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Times New Roman", 0, 22)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Manage Emporium");
        jLabel24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel24MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 480, 180, 40));

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logout-box-line.png"))); // NOI18N
        jLabel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel25MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 570, 40, 40));

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Logout");
        jLabel26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 570, 180, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 350, 900));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setText("Home Panel");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, 170, 100));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/adminbg (1).jpg"))); // NOI18N
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(-250, -400, -1, -1));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, -1, -1));

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setFont(new java.awt.Font("Segoe Print", 1, 72)); // NOI18N
        jLabel27.setText("Manage Users");
        jPanel5.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, -1, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel5.setText("User ID :");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, -1, -1));

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jPanel5.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 190, 250, 40));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel8.setText("First Name :");
        jPanel5.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, -1, -1));

        jTextField2.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jPanel5.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, 250, 40));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel10.setText("Last Name :");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 330, -1, -1));

        jTextField3.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jPanel5.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 360, 250, 40));

        jLabel32.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel32.setText("Email ID :");
        jPanel5.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 250, -1, -1));

        jTextField4.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jPanel5.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 280, 250, 40));

        jLabel33.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel33.setText("Balance :");
        jPanel5.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 330, -1, -1));

        jTextField5.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jPanel5.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 360, 250, 40));

        jLabel34.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel34.setText("Security Answer :");
        jPanel5.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 330, -1, -1));

        jTextField6.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jPanel5.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 360, 250, 40));

        jLabel36.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel36.setText("Search :");
        jPanel5.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 60, -1, -1));

        jTextField7.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField7KeyReleased(evt);
            }
        });
        jPanel5.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 90, 280, 40));

        jLabel35.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel35.setText("Security Question :");
        jPanel5.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 250, -1, -1));

        jComboBox1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Select One --", "How are you?", "What is your nickname?", "What is your pet's name?", "Favourite Sport?", "Date-of-Birth?" }));
        jPanel5.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 280, 250, 40));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "User ID", "First Name", "Last Name", "Email", "Balance", "Security Question", "Security Answer"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 430, 940, 170));

        jLabel37.setBackground(new java.awt.Color(255, 255, 255));
        jLabel37.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 0, 51));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("Update");
        jLabel37.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true)));
        jLabel37.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel37.setOpaque(true);
        jLabel37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel37MouseClicked(evt);
            }
        });
        jPanel5.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 180, 110, 50));

        jLabel38.setBackground(new java.awt.Color(255, 255, 255));
        jLabel38.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 0, 51));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Delete");
        jLabel38.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true)));
        jLabel38.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel38.setOpaque(true);
        jLabel38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel38MouseClicked(evt);
            }
        });
        jPanel5.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 260, 110, 50));

        jLabel39.setBackground(new java.awt.Color(255, 255, 255));
        jLabel39.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 0, 51));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Clear");
        jLabel39.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true)));
        jLabel39.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel39.setOpaque(true);
        jLabel39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel39MouseClicked(evt);
            }
        });
        jPanel5.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(1270, 340, 110, 50));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/adminbg (1).jpg"))); // NOI18N
        jPanel5.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(-250, -400, -1, -1));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, -1, -1));

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setFont(new java.awt.Font("Segoe Print", 1, 72)); // NOI18N
        jLabel31.setText("Manage Admins");
        jPanel6.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, -1, -1));

        jLabel40.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel40.setText("Admin ID :");
        jPanel6.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, -1, -1));

        jTextField8.setEditable(false);
        jTextField8.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jPanel6.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 190, 250, 40));

        jLabel41.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel41.setText("First Name :");
        jPanel6.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, -1, -1));

        jTextField9.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jPanel6.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, 250, 40));

        jLabel42.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel42.setText("Last Name :");
        jPanel6.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 330, -1, -1));

        jTextField10.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jPanel6.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 360, 250, 40));

        jLabel43.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel43.setText("Security Question :");
        jPanel6.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 250, -1, -1));

        jComboBox2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Select One --", "How are you?", "What is your nickname?", "What is your pet's name?", "Favourite Sport?", "Date-of-Birth?" }));
        jPanel6.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 280, 250, 40));

        jLabel44.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel44.setText("Security Answer :");
        jPanel6.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 330, -1, -1));

        jTextField12.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jPanel6.add(jTextField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 360, 250, 40));

        jLabel45.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel45.setText("Email ID :");
        jPanel6.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 330, -1, -1));

        jTextField11.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jPanel6.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 360, 250, 40));

        jLabel46.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel46.setText("Search :");
        jPanel6.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 60, -1, -1));

        jTextField13.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jTextField13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField13KeyReleased(evt);
            }
        });
        jPanel6.add(jTextField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 90, 280, 40));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Admin ID", "First Name", "Last Name", "Email", "Security Question", "Security Answer"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTable2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jPanel6.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 430, 940, 170));

        jLabel48.setBackground(new java.awt.Color(255, 255, 255));
        jLabel48.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(255, 0, 51));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("Register");
        jLabel48.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true)));
        jLabel48.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel48.setOpaque(true);
        jLabel48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel48MouseClicked(evt);
            }
        });
        jPanel6.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 200, 140, 70));

        jLabel49.setBackground(new java.awt.Color(255, 255, 255));
        jLabel49.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 0, 51));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("Delete");
        jLabel49.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true)));
        jLabel49.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel49.setOpaque(true);
        jLabel49.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel49MouseClicked(evt);
            }
        });
        jPanel6.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 200, 110, 50));

        jLabel50.setBackground(new java.awt.Color(255, 255, 255));
        jLabel50.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 0, 51));
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("Clear");
        jLabel50.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true)));
        jLabel50.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel50.setOpaque(true);
        jLabel50.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel50MouseClicked(evt);
            }
        });
        jPanel6.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 280, 110, 50));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/adminbg (1).jpg"))); // NOI18N
        jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(-250, -400, -1, -1));

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, -1, -1));

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Segoe Print", 1, 72)); // NOI18N
        jLabel12.setText("Manage Emporium");
        jPanel7.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, -1, -1));

        jLabel47.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel47.setText("Book ID :");
        jPanel7.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, -1, -1));

        jTextField14.setEditable(false);
        jTextField14.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jPanel7.add(jTextField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 190, 250, 40));

        jLabel51.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel51.setText("Book Name :");
        jPanel7.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, -1, -1));

        jTextField15.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jPanel7.add(jTextField15, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, 320, 40));

        jLabel52.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel52.setText("Author :");
        jPanel7.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 330, -1, -1));

        jTextField16.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jPanel7.add(jTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 360, 300, 40));

        jLabel53.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel53.setText("Subject :");
        jPanel7.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 330, -1, -1));

        jTextField18.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jPanel7.add(jTextField18, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 360, 290, 40));

        jLabel54.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel54.setText("Price :");
        jPanel7.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 250, -1, -1));

        jTextField17.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jPanel7.add(jTextField17, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 280, 120, 40));

        jLabel55.setBackground(new java.awt.Color(255, 255, 255));
        jLabel55.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(255, 0, 51));
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("Add New Book");
        jLabel55.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true)));
        jLabel55.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel55.setOpaque(true);
        jLabel55.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel55MouseClicked(evt);
            }
        });
        jPanel7.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 220, 200, 70));

        jLabel56.setBackground(new java.awt.Color(255, 255, 255));
        jLabel56.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 0, 51));
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("Delete");
        jLabel56.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true)));
        jLabel56.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel56.setOpaque(true);
        jLabel56.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel56MouseClicked(evt);
            }
        });
        jPanel7.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 270, 110, 50));

        jLabel57.setBackground(new java.awt.Color(255, 255, 255));
        jLabel57.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 0, 51));
        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel57.setText("Clear");
        jLabel57.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true)));
        jLabel57.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel57.setOpaque(true);
        jLabel57.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel57MouseClicked(evt);
            }
        });
        jPanel7.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 350, 110, 50));

        jLabel58.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel58.setText("Search :");
        jPanel7.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 80, -1, -1));

        jTextField19.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jTextField19.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField19KeyReleased(evt);
            }
        });
        jPanel7.add(jTextField19, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 110, 280, 40));

        jLabel59.setBackground(new java.awt.Color(255, 255, 255));
        jLabel59.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 0, 51));
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setText("Update");
        jLabel59.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true)));
        jLabel59.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel59.setOpaque(true);
        jLabel59.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel59MouseClicked(evt);
            }
        });
        jPanel7.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 190, 110, 50));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book ID", "Book Name", "Author", "Subject", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTable3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable3.getTableHeader().setReorderingAllowed(false);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jPanel7.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 430, 940, 170));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/adminbg (1).jpg"))); // NOI18N
        jPanel7.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(-250, -400, -1, -1));

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        int yes = JOptionPane.showConfirmDialog(this, "Are you sure you want to Exit?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (yes == JOptionPane.YES_OPTION) {
            fadeout();
            System.exit(0);
        }
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        collapseMenubar();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        if (menu)
            collapseMenubar();
        else
            expandMenubar();
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        if (!menu) {
            expandMenubar();
            return;
        }
        hideAllPanels();
        jPanel4.setVisible(true);
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        if (!menu) {
            expandMenubar();
            return;
        }
        hideAllPanels();
        jPanel5.setVisible(true);
        try {
            manageUsersPanel();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel19MouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        if (!menu) {
            expandMenubar();
            return;
        }
        hideAllPanels();
        jPanel6.setVisible(true);
        try {
            manageAdminsPanel();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        if (!menu) {
            expandMenubar();
            return;
        }
        hideAllPanels();
        jPanel7.setVisible(true);
        try {
            manageEmporiumPanel();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel23MouseClicked

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked
        int yes = JOptionPane.showConfirmDialog(this, "Are you sure you want to Logout?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (yes == JOptionPane.YES_OPTION) {
            fadeout();
            AdminLogin admlgn = new AdminLogin();
            admlgn.setVisible(true);
            admlgn.load = load;
            load.hideAllPanels();
            load.jPanel3.setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_jLabel25MouseClicked

    private void jTextField7KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyReleased
        String s = jTextField7.getText().trim();
        try {
            if (s.isEmpty()) {
                refreshTable1();
                return;
            }
            model = (DefaultTableModel) jTable1.getModel();
            TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
            jTable1.setRowSorter(trs);
            trs.setRowFilter(RowFilter.regexFilter(s));
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextField7KeyReleased

    private void jLabel37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel37MouseClicked
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please Select the Row to be Updated from the Table");
            return;
        }
        int check;
        CheckEntries chk = new CheckEntries();
        check = chk.checkFname(jTextField2.getText().trim(), this);
        if (check == 0) {
            jTextField2.grabFocus();
            return;
        }
        check = chk.checkLname(jTextField3.getText().trim(), this);
        if (check == 0) {
            jTextField3.grabFocus();
            return;
        }
        check = chk.checkEmail(jTextField4.getText().trim(), this);
        if (check == 0) {
            jTextField4.grabFocus();
            return;
        }
        check = chk.checkBalance(jTextField5.getText().trim(), this);
        if (check == 0) {
            jTextField5.grabFocus();
            return;
        }
        if (jComboBox1.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Security Question not selected.", "Warning!", JOptionPane.WARNING_MESSAGE);
            jComboBox1.grabFocus();
            return;
        }
        check = chk.checkSans(jTextField6.getText().trim(), this);
        if (check == 0) {
            jTextField6.grabFocus();
            return;
        }
        try {
            ps1 = con.prepareStatement("UPDATE UserProfile SET fname=?,lname=?,email=?,balance=? WHERE usr_id=?");
            ps1.setString(1, jTextField2.getText().trim());
            ps1.setString(2, jTextField3.getText().trim());
            ps1.setString(3, jTextField4.getText().trim().toLowerCase());
            ps1.setInt(4, Integer.parseInt(jTextField5.getText().trim()));
            ps1.setString(5, jTextField1.getText());
            ps1.executeUpdate();
            ps2 = con.prepareStatement("UPDATE UsrLgn SET fpques=?,fpans=? WHERE usr_id=?");
            ps2.setString(1, jComboBox1.getSelectedItem().toString());
            ps2.setString(2, jTextField6.getText().trim());
            ps2.setString(3, jTextField1.getText());
            ps2.executeUpdate();
            JOptionPane.showMessageDialog(this, "User ID : " + jTextField1.getText() + "\nUser Data Update Successful!");
            refreshTable1();
            clearAll1();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel37MouseClicked

    private void jLabel38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel38MouseClicked
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please Select the Row to be Deleted from the Table");
            return;
        }
        int yes = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete?", "Delete User", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (yes == JOptionPane.NO_OPTION) {
            return;
        }
        String s = jTextField1.getText();
        try {
            ps1 = con.prepareStatement("DELETE FROM UserProfile WHERE usr_id=?;");
            ps1.setString(1, s);
            ps1.executeUpdate();
            ps2 = con.prepareStatement("DELETE FROM UsrLgn WHERE usr_id=?;");
            ps2.setString(1, s);
            ps2.executeUpdate();
            ps1 = con.prepareStatement("DELETE FROM Carts WHERE usr_id=?;");
            ps1.setString(1, s);
            ps1.executeUpdate();
            ps2 = con.prepareStatement("DELETE FROM AllOrders WHERE usr_id=?;");
            ps2.setString(1, s);
            ps2.executeUpdate();
            JOptionPane.showMessageDialog(this, "User ID : " + s + " has been successfully deleted.");
            refreshTable1();
            clearAll1();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel38MouseClicked

    private void jLabel39MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MouseClicked
        try {
            clearAll1();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel39MouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        for (double i = 0.1; i <= 1.0; i += 0.1) {
            this.setOpacity(Float.parseFloat("" + i));
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_formWindowOpened

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int row = jTable1.getSelectedRow();
        model = (DefaultTableModel) jTable1.getModel();
        jTextField1.setText(model.getValueAt(row, 0).toString());
        jTextField2.setText(model.getValueAt(row, 1).toString());
        jTextField3.setText(model.getValueAt(row, 2).toString());
        jTextField4.setText(model.getValueAt(row, 3).toString());
        jTextField5.setText(model.getValueAt(row, 4).toString());
        jComboBox1.setSelectedItem(model.getValueAt(row, 5));
        jTextField6.setText(model.getValueAt(row, 6).toString());
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField13KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField13KeyReleased
        String s = jTextField13.getText().trim();
        try {
            if (s.isEmpty()) {
                refreshTable2();
                return;
            }
            model = (DefaultTableModel) jTable2.getModel();
            TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
            jTable2.setRowSorter(trs);
            trs.setRowFilter(RowFilter.regexFilter(s));
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextField13KeyReleased

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int row = jTable2.getSelectedRow();
        model = (DefaultTableModel) jTable2.getModel();
        jTextField8.setText(model.getValueAt(row, 0).toString());
        jTextField9.setText(model.getValueAt(row, 1).toString());
        jTextField10.setText(model.getValueAt(row, 2).toString());
        jTextField11.setText(model.getValueAt(row, 3).toString());
        jTextField12.setText(model.getValueAt(row, 5).toString());
        jComboBox2.setSelectedItem(model.getValueAt(row, 4));
    }//GEN-LAST:event_jTable2MouseClicked

    private void jLabel48MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseClicked
        AdminRegister obj = new AdminRegister();
        obj.setVisible(true);
        obj.adm = this;
    }//GEN-LAST:event_jLabel48MouseClicked

    private void jLabel49MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel49MouseClicked
        int row = jTable2.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please Select the Row to be Deleted from the Table");
            return;
        }
        if (jTable1.getRowCount() == 1) {
            JOptionPane.showMessageDialog(this, "Atleast one admin is mandatory.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int yes = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete?", "Delete Admin", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (yes == JOptionPane.NO_OPTION) {
            return;
        }
        String s = jTextField8.getText();
        try {
            ps1 = con.prepareStatement("DELETE FROM AdminProfile WHERE adm_id=?;");
            ps1.setString(1, s);
            ps1.executeUpdate();
            ps2 = con.prepareStatement("DELETE FROM AdmLgn WHERE adm_id=?;");
            ps2.setString(1, s);
            ps2.executeUpdate();
            JOptionPane.showMessageDialog(this, "Admin ID : " + s + " has been successfully deleted.");
            refreshTable2();
            clearAll2();
            if (s.equals(admid)) {
                dispose();
                new AdminLogin().setVisible(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel49MouseClicked

    private void jLabel50MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel50MouseClicked
        clearAll2();
    }//GEN-LAST:event_jLabel50MouseClicked

    private void jLabel55MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseClicked
        BookRegister obj = new BookRegister();
        obj.setVisible(true);
        obj.adm = this;
    }//GEN-LAST:event_jLabel55MouseClicked

    private void jLabel56MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel56MouseClicked
        int row = jTable3.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please Select the Row to be Deleted from the Table");
            return;
        }
        int yes = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete?", "Delete Book", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (yes == JOptionPane.NO_OPTION) {
            return;
        }
        String s = jTextField14.getText();
        try {
            ps1 = con.prepareStatement("DELETE FROM Empo WHERE book_id=?;");
            ps1.setString(1, s);
            ps1.executeUpdate();
            ps2 = con.prepareStatement("DELETE FROM Carts WHERE book_id=?;");
            ps2.setString(1, s);
            ps2.executeUpdate();
            JOptionPane.showMessageDialog(this, "Book ID : " + s + " has been successfully deleted.");
            refreshTable3();
            clearAll3();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel56MouseClicked

    private void jLabel57MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel57MouseClicked
        clearAll3();
    }//GEN-LAST:event_jLabel57MouseClicked

    private void jTextField19KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField19KeyReleased
        String s = jTextField19.getText().trim();
        try {
            if (s.isEmpty()) {
                refreshTable3();
                return;
            }
            model = (DefaultTableModel) jTable3.getModel();
            TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
            jTable3.setRowSorter(trs);
            trs.setRowFilter(RowFilter.regexFilter(s));
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextField19KeyReleased

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        int row = jTable3.getSelectedRow();
        model = (DefaultTableModel) jTable3.getModel();
        jTextField14.setText(model.getValueAt(row, 0).toString());
        jTextField15.setText(model.getValueAt(row, 1).toString());
        jTextField16.setText(model.getValueAt(row, 2).toString());
        jTextField17.setText(model.getValueAt(row, 4).toString());
        jTextField18.setText(model.getValueAt(row, 3).toString());
    }//GEN-LAST:event_jTable3MouseClicked

    private void jLabel59MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel59MouseClicked
        int row = jTable3.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please Select the Row to be Updated from the Table");
            return;
        }
        int check;
        CheckBooks chk = new CheckBooks();
        check = chk.checkBookName(jTextField15.getText().trim(), this);
        if (check == 0) {
            jTextField15.grabFocus();
            return;
        }
        check = chk.checkAuthorName(jTextField16.getText().trim(), this);
        if (check == 0) {
            jTextField16.grabFocus();
            return;
        }
        check = chk.checkSubject(jTextField18.getText().trim(), this);
        if (check == 0) {
            jTextField18.grabFocus();
            return;
        }
        check = chk.checkPrice(jTextField17.getText().trim(), this);
        if (check == 0) {
            jTextField17.grabFocus();
            return;
        }
        try {
            ps1 = con.prepareStatement("UPDATE Empo SET bname=?,author=?,subject=?,price=? WHERE book_id=?");
            ps1.setString(1, jTextField15.getText().trim());
            ps1.setString(2, jTextField16.getText().trim());
            ps1.setString(3, jTextField18.getText().trim());
            ps1.setInt(4, Integer.parseInt(jTextField17.getText().trim()));
            ps1.setString(5, jTextField14.getText());
            ps1.executeUpdate();
            JOptionPane.showMessageDialog(this, "Book ID : " + jTextField14.getText() + "\nBook Data Update Successful!");
            refreshTable3();
            clearAll3();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel59MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        try {
            new Profile(this, true, admid).setVisible(true);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        if (menu)
            collapseMenubar();
        else
            expandMenubar();
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        if (!menu) {
            expandMenubar();
            return;
        }
        hideAllPanels();
        jPanel4.setVisible(true);
    }//GEN-LAST:event_jLabel18MouseClicked

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        if (!menu) {
            expandMenubar();
            return;
        }
        hideAllPanels();
        jPanel5.setVisible(true);
        try {
            manageUsersPanel();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        if (!menu) {
            expandMenubar();
            return;
        }
        hideAllPanels();
        jPanel6.setVisible(true);
        try {
            manageAdminsPanel();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jLabel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseClicked
        if (!menu) {
            expandMenubar();
            return;
        }
        hideAllPanels();
        jPanel7.setVisible(true);
        try {
            manageEmporiumPanel();
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel24MouseClicked

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        int yes = JOptionPane.showConfirmDialog(this, "Are you sure you want to Logout?", "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (yes == JOptionPane.YES_OPTION) {
            fadeout();
            AdminLogin admlgn = new AdminLogin();
            admlgn.setVisible(true);
            admlgn.load = load;
            load.hideAllPanels();
            load.jPanel3.setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_jLabel26MouseClicked

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminDashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new AdminDashboard().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
