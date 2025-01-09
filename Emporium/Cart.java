package Emporium;

import java.awt.Color;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Cart extends javax.swing.JFrame {

    Emporium empo = null;
    Connection con = null;
    PreparedStatement ps1 = null, ps2 = null;
    ResultSet rs1 = null, rs2 = null;
    DefaultTableModel model;
    String usrid;
    int total = 0;

    public Cart() throws ClassNotFoundException, SQLException {
        initComponents();
        refreshFrame();
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Emporium", "root", "avrodeep1411");
        jTextArea1.setHighlighter(null);
        jTextArea2.setHighlighter(null);
        jTextArea3.setHighlighter(null);
    }

    public void execute(String usrid) throws SQLException {
        this.usrid = usrid;
        loginInfo();
        refreshTable();
    }

    private String generateOrdid() {
        String s = "ord";
        int rand = (int) (Math.random() * (99999 - 10000 + 1)) + 10000;
        s += String.valueOf(rand);
        s += String.valueOf(usrid.substring(6));
        return s;
    }

    private void loginInfo() throws SQLException {
        ps1 = con.prepareStatement("SELECT * FROM UserProfile WHERE usr_id=?");
        ps1.setString(1, usrid);
        rs1 = ps1.executeQuery();
        rs1.next();
        jLabel1.setText("Username : " + rs1.getString("fname") + " " + rs1.getString("lname"));
        jLabel7.setText("Avl. Balance : " + rs1.getInt("balance") + " tokens");
    }

    private void fadeout() {
        for (double i = 1.0; i > 0.0; i -= 0.1) {
            this.setOpacity(Float.parseFloat("" + i));
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cart.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void refreshFrame() {
        jTextField1.setText("");
        jTextArea1.setText("");
        jTextArea3.setText("");
        jTextArea2.setText("");
        jTable1.clearSelection();
    }

    public void refreshTable() throws SQLException {
        total = 0;
        ps1 = con.prepareStatement("SELECT * FROM Carts WHERE usr_id=?;");
        ps1.setString(1, usrid);
        rs1 = ps1.executeQuery();
        ResultSetMetaData rsmd = rs1.getMetaData();
        int n = rsmd.getColumnCount();
        model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        while (rs1.next()) {
            ps2 = con.prepareStatement("SELECT * FROM Empo WHERE book_id=?;");
            ps2.setString(1, rs1.getString("book_id"));
            rs2 = ps2.executeQuery();
            rs2.next();
            Vector v = new Vector();
            for (int i = 1; i <= n; i++) { // i=0 is table header
                v.add(rs2.getString(1));
                v.add(rs2.getString(2));
                v.add(rs2.getString(3));
                v.add(rs2.getString(4));
                v.add(rs2.getInt(5));
            }
            model.addRow(v);
            total += rs2.getInt(5);
        }
        model = (DefaultTableModel) jTable1.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        jTable1.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter("bk"));
    }

    private boolean checkBalance() throws SQLException {
        ps1 = con.prepareStatement("SELECT balance FROM UserProfile WHERE usr_id=?");
        ps1.setString(1, usrid);
        rs1 = ps1.executeQuery();
        rs1.next();
        int balance = rs1.getInt(1);
        refreshTable();
        System.out.println(balance + " " + total);
        return balance < total;
    }

    private void makeReceipt(String ordid, String date) throws SQLException, ClassNotFoundException {
        ps1 = con.prepareStatement("SELECT * FROM Carts WHERE usr_id=?;");
        ps1.setString(1, usrid);
        rs1 = ps1.executeQuery();
        while (rs1.next()) {
            ps2 = con.prepareStatement("INSERT INTO AllOrders VALUES(?,?,?,1,?)");
            ps2.setString(1, ordid);
            ps2.setString(2, usrid);
            ps2.setString(3, rs1.getString("book_id"));
            ps2.setString(4, date);
            ps2.executeUpdate();
        }
        Receipt obj = new Receipt();
        obj.setVisible(true);
        obj.empo = empo;
        obj.crt = this;
        obj.execute(ordid);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextArea1 = new javax.swing.JTextArea();
        jTextArea2 = new javax.swing.JTextArea();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 51, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setBackground(new java.awt.Color(0, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("<-");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel3.setOpaque(true);
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 90, 60));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Username");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 40, -1, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Balance");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 90, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe Print", 1, 48)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("My Cart");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 230));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Segoe Print", 0, 20)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(255, 204, 0));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("Title");
        jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextArea1.setOpaque(false);
        getContentPane().add(jTextArea1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 360, 210, 130));

        jTextArea2.setEditable(false);
        jTextArea2.setBackground(new java.awt.Color(255, 204, 0));
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Segoe Print", 0, 18)); // NOI18N
        jTextArea2.setForeground(new java.awt.Color(255, 204, 0));
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setText("Author");
        jTextArea2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextArea2.setOpaque(false);
        getContentPane().add(jTextArea2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 650, 220, 110));

        jTextArea3.setEditable(false);
        jTextArea3.setBackground(new java.awt.Color(255, 204, 0));
        jTextArea3.setColumns(20);
        jTextArea3.setFont(new java.awt.Font("Segoe Print", 0, 18)); // NOI18N
        jTextArea3.setForeground(new java.awt.Color(255, 204, 0));
        jTextArea3.setLineWrap(true);
        jTextArea3.setRows(5);
        jTextArea3.setText("Subject");
        jTextArea3.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextArea3.setOpaque(false);
        getContentPane().add(jTextArea3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 520, 220, 110));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 1, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 51, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Buy All!");
        jLabel10.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), new javax.swing.border.LineBorder(new java.awt.Color(102, 51, 0), 2, true)));
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.setOpaque(true);
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel10MouseExited(evt);
            }
        });
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 780, 140, 60));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 1, 20)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 51, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Remove");
        jLabel11.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), new javax.swing.border.LineBorder(new java.awt.Color(102, 51, 0), 2, true)));
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.setOpaque(true);
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel11MouseExited(evt);
            }
        });
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 780, 140, 60));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 1, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 51, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Refresh");
        jLabel12.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), new javax.swing.border.LineBorder(new java.awt.Color(102, 51, 0), 2, true)));
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel12.setOpaque(true);
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel12MouseExited(evt);
            }
        });
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 780, 140, 60));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/book.jpg"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 290, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book ID.", "Book Name", "Author", "Subject", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 340, 910, 410));

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Search :");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 280, -1, -1));

        jTextField1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 270, 310, 40));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/empobg.jpg"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        for (double i = 0.1; i <= 1.0; i += 0.1) {
            this.setOpacity(Float.parseFloat("" + i));
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Emporium.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_formWindowOpened

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        fadeout();
        dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        String s = jTextField1.getText().trim();
        try {
            if (s.isEmpty()) {
                refreshTable();
                return;
            }
            model = (DefaultTableModel) jTable1.getModel();
            TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
            jTable1.setRowSorter(trs);
            trs.setRowFilter(RowFilter.regexFilter(s));
        } catch (SQLException ex) {
            Logger.getLogger(Emporium.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        try {
            refreshFrame();
            refreshTable();
        } catch (SQLException ex) {
            Logger.getLogger(Emporium.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please Select the Row to be Deleted from the Table");
            return;
        }
        try {
            ps1 = con.prepareStatement("DELETE FROM Carts WHERE usr_id=? AND book_id=?;");
            ps1.setString(1, usrid);
            ps1.setString(2, model.getValueAt(row, 0).toString());
            ps1.executeUpdate();
            refreshTable();
        } catch (SQLException ex) {
            Logger.getLogger(Cart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        String ordid, strDate;
        try {
            if (total == 0) {
                JOptionPane.showMessageDialog(this, "Oops! Cart is Empty!");
                return;
            }
            if (checkBalance()) {
                JOptionPane.showMessageDialog(this, "Insufficient Balance!", "Warning!", JOptionPane.WARNING_MESSAGE);
                return;
            }
            do {
                ordid = generateOrdid();
                ps1 = con.prepareStatement("SELECT * FROM AllOrders WHERE ord_id=?");
                ps1.setString(1, ordid);
                rs1 = ps1.executeQuery();
            } while (rs1.next());
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            strDate = formatter.format(date);
            makeReceipt(ordid, strDate);
        } catch (SQLException ex) {
            Logger.getLogger(Cart.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int row = jTable1.getSelectedRow();
        model = (DefaultTableModel) jTable1.getModel();
        jTextArea1.setText(model.getValueAt(row, 1).toString());
        jTextArea2.setText(model.getValueAt(row, 2).toString());
        jTextArea3.setText(model.getValueAt(row, 3).toString());
    }//GEN-LAST:event_jTable1MouseClicked

    private void jLabel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseEntered
        jLabel10.setForeground(Color.WHITE);
        jLabel10.setBackground(Color.decode("#663300"));
    }//GEN-LAST:event_jLabel10MouseEntered

    private void jLabel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseExited
        jLabel10.setForeground(Color.decode("#663300"));
        jLabel10.setBackground(Color.WHITE);
    }//GEN-LAST:event_jLabel10MouseExited

    private void jLabel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseEntered
        jLabel11.setForeground(Color.WHITE);
        jLabel11.setBackground(Color.decode("#663300"));
    }//GEN-LAST:event_jLabel11MouseEntered

    private void jLabel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseExited
        jLabel11.setForeground(Color.decode("#663300"));
        jLabel11.setBackground(Color.WHITE);
    }//GEN-LAST:event_jLabel11MouseExited

    private void jLabel12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseEntered
        jLabel12.setForeground(Color.WHITE);
        jLabel12.setBackground(Color.decode("#663300"));
    }//GEN-LAST:event_jLabel12MouseEntered

    private void jLabel12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseExited
        jLabel12.setForeground(Color.decode("#663300"));
        jLabel12.setBackground(Color.WHITE);
    }//GEN-LAST:event_jLabel12MouseExited

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Cart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cart.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Cart().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Cart.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Cart.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
