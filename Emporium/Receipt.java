package Emporium;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Receipt extends javax.swing.JFrame {

    Emporium empo = null;
    Cart crt = null;
    Connection con;
    PreparedStatement ps1 = null, ps2 = null;
    ResultSet rs1 = null, rs2 = null;
    String ordid, usrid, bkid[];
    int total;

    @SuppressWarnings("empty-statement")
    public Receipt() throws ClassNotFoundException, SQLException {
        initComponents();
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Emporium", "root", "avrodeep1411");
        allBlank();
        total = 0;
        bkid = new String[5];
        for (int i = 0; i < 5; bkid[i++] = "");
    }

    private void allBlank() {
        jLabel27.setText("");
        jLabel28.setText("");
        jLabel29.setText("");
        jLabel30.setText("");
        jLabel31.setText("");
        jLabel32.setText("");
        jLabel33.setText("");
        jLabel34.setText("");
        jLabel35.setText("");
        jLabel36.setText("");
        jLabel37.setText("");
        jLabel38.setText("");
        jLabel39.setText("");
        jLabel40.setText("");
        jLabel41.setText("");
        jLabel42.setText("");
    }

    private void fadeout() {
        for (double i = 1.0; i > 0.0; i -= 0.1) {
            this.setOpacity(Float.parseFloat("" + i));
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Receipt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void printPanel() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Print Data");

        job.setPrintable((Graphics pg, PageFormat pf, int pageNum) -> {
            pf.setOrientation(PageFormat.PORTRAIT);
            if (pageNum > 0) {
                return Printable.NO_SUCH_PAGE;
            }

            Graphics2D g2 = (Graphics2D) pg;
            g2.translate(pf.getImageableX(), pf.getImageableY());
            g2.scale(0.47, 0.47);

            jPanel1.print(g2);

            return Printable.PAGE_EXISTS;
        });

        if (job.printDialog()) {
            try {

                job.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void execute(String ordid) throws SQLException {
        this.ordid = ordid;
        displayDetails();
        jLabel43.setText("" + total);
        totalWords();
    }

    private void displayDetails() throws SQLException {
        ps1 = con.prepareStatement("SELECT * FROM AllOrders WHERE ord_id=?");
        ps1.setString(1, ordid);
        rs1 = ps1.executeQuery();
        rs1.next();
        this.usrid = rs1.getString("usr_id");
        details();
        first();
        if (rs1.next()) {
            second();
        } else {
            return;
        }
        if (rs1.next()) {
            third();
        } else {
            return;
        }
        if (rs1.next()) {
            fourth();
        } else {
            return;
        }
        if (rs1.next()) {
            fifth();
        }
    }

    private void details() throws SQLException {
        jLabel10.setText(rs1.getString("ord_id"));
        jLabel12.setText(rs1.getString("usr_id"));
        ps2 = con.prepareStatement("SELECT fname,lname FROM UserProfile WHERE usr_id=?");
        ps2.setString(1, jLabel12.getText());
        rs2 = ps2.executeQuery();
        rs2.next();
        jLabel14.setText(rs2.getString(1) + " " + rs2.getString(2));
        jLabel16.setText(rs1.getString("date"));
    }

    private void first() throws SQLException {
        jLabel23.setText("1");
        jLabel24.setText(rs1.getString("book_id"));
        bkid[0] = jLabel24.getText();
        ps2 = con.prepareStatement("SELECT bname,price FROM Empo WHERE book_id=?");
        ps2.setString(1, jLabel24.getText());
        rs2 = ps2.executeQuery();
        rs2.next();
        jLabel25.setText(rs2.getString(1));
        jLabel26.setText(rs2.getString(2));
        total += rs2.getInt(2);
    }

    private void second() throws SQLException {
        jLabel27.setText("2");
        jLabel28.setText(rs1.getString("book_id"));
        bkid[1] = jLabel28.getText();
        ps2 = con.prepareStatement("SELECT bname,price FROM Empo WHERE book_id=?");
        ps2.setString(1, jLabel28.getText());
        rs2 = ps2.executeQuery();
        rs2.next();
        jLabel29.setText(rs2.getString(1));
        jLabel30.setText(rs2.getString(2));
        total += rs2.getInt(2);
    }

    private void third() throws SQLException {
        jLabel31.setText("3");
        jLabel32.setText(rs1.getString("book_id"));
        bkid[2] = jLabel32.getText();
        ps2 = con.prepareStatement("SELECT bname,price FROM Empo WHERE book_id=?");
        ps2.setString(1, jLabel32.getText());
        rs2 = ps2.executeQuery();
        rs2.next();
        jLabel33.setText(rs2.getString(1));
        jLabel34.setText(rs2.getString(2));
        total += rs2.getInt(2);
    }

    private void fourth() throws SQLException {
        jLabel35.setText("4");
        jLabel36.setText(rs1.getString("book_id"));
        bkid[3] = jLabel36.getText();
        ps2 = con.prepareStatement("SELECT bname,price FROM Empo WHERE book_id=?");
        ps2.setString(1, jLabel36.getText());
        rs2 = ps2.executeQuery();
        rs2.next();
        jLabel37.setText(rs2.getString(1));
        jLabel38.setText(rs2.getString(2));
        total += rs2.getInt(2);
    }

    private void fifth() throws SQLException {
        jLabel39.setText("5");
        jLabel40.setText(rs1.getString("book_id"));
        bkid[4] = jLabel40.getText();
        ps2 = con.prepareStatement("SELECT bname,price FROM Empo WHERE book_id=?");
        ps2.setString(1, jLabel40.getText());
        rs2 = ps2.executeQuery();
        rs2.next();
        jLabel41.setText(rs2.getString(1));
        jLabel42.setText(rs2.getString(2));
        total += rs2.getInt(2);
    }

    private void totalWords() {
        String ones[] = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
        String tens[] = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
        String ten[] = {"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
        String s = Integer.toString(total);
        String str = "";
        if (s.length() == 3) {
            str = ones[s.charAt(0) - '0'] + " Hundred ";
            s = s.substring(1);
        }
        if ((s.length() == 2) && (s.charAt(0) == '1')) {
            str += ten[s.charAt(1) - '0'];
        } else if (s.length() == 2) {
            str += tens[s.charAt(0) - '0'] + " " + ones[s.charAt(1) - '0'];
        } else {
            str += ones[s.charAt(0) - '0'];
        }
        str += " tokens only.";
        jLabel47.setText(str);
    }

    private void updateBalance() throws SQLException {
        ps1 = con.prepareStatement("SELECT balance FROM UserProfile WHERE usr_id=?;");
        ps1.setString(1, usrid);
        rs1 = ps1.executeQuery();
        rs1.next();
        int b = rs1.getInt(1);
        b -= total;
        ps2 = con.prepareStatement("UPDATE UserProfile SET balance=? WHERE usr_id=?;");
        ps2.setInt(1, b);
        ps2.setString(2, usrid);
        ps2.executeUpdate();
    }

    private void clearCart() throws SQLException {
        int i = -1;
        while (!bkid[++i].equals("")) {
            ps1 = con.prepareStatement("DELETE FROM Carts WHERE usr_id=? AND book_id=?;");
            ps1.setString(1, usrid);
            ps1.setString(2, bkid[i]);
            ps1.executeUpdate();
        }
        if (crt != null) {
            crt.execute(usrid);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(0, 255, 204));
        jLabel2.setFont(new java.awt.Font("Segoe UI Emoji", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("<-");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.setOpaque(true);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 110, 60));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI Variable", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Confirm");
        jLabel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 3, true));
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.setOpaque(true);
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
        });
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 120, 80));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(0, 0, 102));
        jPanel1.setPreferredSize(new java.awt.Dimension(1300, 1500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setBackground(new java.awt.Color(0, 0, 102));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/logo.jpg"))); // NOI18N
        jLabel4.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 5, true), new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true)));
        jLabel4.setOpaque(true);
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 200, 200));

        jLabel5.setFont(new java.awt.Font("Segoe Script", 3, 84)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Star Emporium");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, -1, -1));

        jLabel6.setFont(new java.awt.Font("Papyrus", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("The  Ultimate  eBook  Store  for  Learners !!!");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 180, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setText("Official Website : www.staremporium.org");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 260, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("Contact us 24*7 at : XX0000X0XX");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 260, -1, -1));

        jPanel2.setBackground(new java.awt.Color(0, 0, 102));
        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 1400, 10));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setText("Order ID :");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, -1, -1));

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 22)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 102));
        jLabel10.setText("ord00000AP");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 370, -1, -1));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 102));
        jLabel11.setText("User ID :");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 430, -1, -1));

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 22)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 102));
        jLabel12.setText("usr111AP");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 430, -1, -1));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 102));
        jLabel13.setText("Name :");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 490, -1, -1));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 22)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 102));
        jLabel14.setText("Avrodeep Pal");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 490, -1, -1));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 102));
        jLabel15.setText("Date :");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 370, -1, -1));

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 22)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 102));
        jLabel16.setText("01/01/2023");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 370, -1, -1));

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 102));
        jLabel17.setText("GSTIN :");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 430, -1, -1));

        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 22)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 102));
        jLabel18.setText("19XXXXX0000X1ZX");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 430, -1, -1));

        jPanel3.setBackground(new java.awt.Color(0, 0, 102));
        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 1400, 5));

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 102));
        jLabel19.setText("Sl. No.");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 620, -1, -1));

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 102));
        jLabel20.setText("Book ID.");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 620, -1, -1));

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 102));
        jLabel21.setText("Book Name");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 620, -1, -1));

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 102));
        jLabel22.setText("Price (tokens)");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 620, -1, -1));

        jPanel4.setBackground(new java.awt.Color(0, 0, 102));
        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 660, 1400, 2));

        jLabel23.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 102));
        jLabel23.setText("1");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 720, 20, -1));

        jLabel24.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 102));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("bk000XXX");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 720, -1, -1));

        jLabel25.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 102));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("The Merchant of Venice by WIlliam Shakesphere");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 720, 610, -1));

        jLabel26.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 102));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel26.setText("100");
        jLabel26.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 720, 50, -1));

        jLabel27.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 102));
        jLabel27.setText("1");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 780, 20, -1));

        jLabel28.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 102));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("bk000XXX");
        jPanel1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 780, -1, -1));

        jLabel29.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 102));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("The Merchant of Venice by WIlliam Shakesphere");
        jPanel1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 780, 610, -1));

        jLabel30.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 102));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("100");
        jLabel30.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 780, 50, -1));

        jLabel31.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 102));
        jLabel31.setText("1");
        jPanel1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 840, 20, -1));

        jLabel32.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 102));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("bk000XXX");
        jPanel1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 840, -1, -1));

        jLabel33.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 102));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("The Merchant of Venice by WIlliam Shakesphere");
        jPanel1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 840, 610, -1));

        jLabel34.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 102));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel34.setText("100");
        jLabel34.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 840, 50, -1));

        jLabel35.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 102));
        jLabel35.setText("1");
        jPanel1.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 900, 20, -1));

        jLabel36.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 102));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("bk000XXX");
        jPanel1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 900, -1, -1));

        jLabel37.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 102));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("The Merchant of Venice by WIlliam Shakesphere");
        jPanel1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 900, 610, -1));

        jLabel38.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 102));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel38.setText("100");
        jLabel38.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 900, 50, -1));

        jLabel39.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 0, 102));
        jLabel39.setText("1");
        jPanel1.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 960, 20, -1));

        jLabel40.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 0, 102));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("bk000XXX");
        jPanel1.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 960, -1, -1));

        jLabel41.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 102));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("The Merchant of Venice by WIlliam Shakesphere");
        jPanel1.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 960, 610, -1));

        jLabel42.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 0, 102));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel42.setText("100");
        jLabel42.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 960, 50, -1));

        jPanel5.setBackground(new java.awt.Color(0, 0, 102));
        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 1040, 280, 2));

        jLabel43.setFont(new java.awt.Font("Times New Roman", 0, 22)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 102));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel43.setText("10000");
        jLabel43.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel43.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 1070, 60, -1));

        jLabel44.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 0, 102));
        jLabel44.setText("Total :");
        jLabel44.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 1070, -1, -1));

        jLabel45.setFont(new java.awt.Font("Times New Roman", 0, 22)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(0, 0, 102));
        jLabel45.setText("tokens");
        jLabel45.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel1.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 1070, -1, -1));

        jLabel46.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 0, 102));
        jLabel46.setText("Total in words :");
        jPanel1.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 1140, -1, -1));

        jLabel47.setFont(new java.awt.Font("Times New Roman", 0, 22)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 0, 102));
        jLabel47.setText("Ten Thousand tokens only.");
        jPanel1.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 1140, -1, -1));

        jLabel48.setFont(new java.awt.Font("Times New Roman", 1, 22)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 0, 102));
        jLabel48.setText("Remarks :");
        jPanel1.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 1350, -1, -1));

        jLabel49.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(0, 0, 102));
        jLabel49.setText("Thank You for your Purchase.");
        jPanel1.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 1350, -1, -1));

        jLabel50.setFont(new java.awt.Font("Times New Roman", 1, 22)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 0, 102));
        jLabel50.setText("Receiver's Signature");
        jPanel1.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 1350, -1, -1));

        jLabel51.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(0, 0, 102));
        jLabel51.setText("_______________________________________________");
        jPanel1.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 1320, -1, -1));

        jScrollPane1.setViewportView(jPanel1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 1320, 720));

        jLabel100.setFont(new java.awt.Font("Lucida Sans Typewriter", 1, 48)); // NOI18N
        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel100.setText("Receipt");
        getContentPane().add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pics/receipt (1).jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -1100, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        try {
            fadeout();
            ps1 = con.prepareStatement("DELETE FROM AllOrders WHERE ord_id=?");
            ps1.setString(1, ordid);
            ps1.executeUpdate();
            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Receipt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        try {
            int yes = JOptionPane.showConfirmDialog(this, "Confirm Payment?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (yes == JOptionPane.NO_OPTION) {
                return;
            }
            printPanel();
            updateBalance();
            clearCart();
            if (empo != null)
                empo.execute(usrid);
            empo = null;
            crt = null;
            fadeout();
            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(Receipt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        for (double i = 0.1; i <= 1.0; i += 0.1) {
            this.setOpacity(Float.parseFloat("" + i));
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Receipt.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_formWindowOpened

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        jLabel3.setForeground(Color.WHITE);
        jLabel3.setBackground(Color.decode("#000066"));
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        jLabel3.setForeground(Color.decode("#000066"));
        jLabel3.setBackground(Color.WHITE);
    }//GEN-LAST:event_jLabel3MouseExited

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Receipt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Receipt().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Receipt.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Receipt.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
