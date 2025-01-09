package Main;

import java.awt.Component;
import javax.swing.JOptionPane;

public class CheckEntries {
    
    public int checkUsrName (String s, Component jf) {
        if (s.isEmpty()) {
            JOptionPane.showMessageDialog(jf, "Username cannot be blank.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if (!s.startsWith("usr")) {
            JOptionPane.showMessageDialog(jf, "Username must start with 'usr'.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if ((s.length() < 5) || (s.length() > 15)) {
            JOptionPane.showMessageDialog(jf, "Username must be within 5 to 15 characters.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        return 1;
    }
    
    public int checkAdmName (String s, Component jf) {
        if (s.isEmpty()) {
            JOptionPane.showMessageDialog(jf, "Admin ID cannot be blank", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if (!s.startsWith("adm")) {
            JOptionPane.showMessageDialog(jf, "Admin ID must start with 'adm'", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if ((s.length() < 7) || (s.length() > 15)) {
            JOptionPane.showMessageDialog(jf, "Admin ID must be within 7 to 15 characters.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        return 1;
    }
    
    public int checkPswrd (String s, Component jf) {
        if (s.trim().isEmpty()) {
            JOptionPane.showMessageDialog(jf, "Password cannot be blank.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if ((s.length() < 8) || (s.length() > 20)) {
            JOptionPane.showMessageDialog(jf, "Password must be within 8 to 20 characters.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        int nc=0, uc=0, lc=0, sc=0;
        char ch;
        for (int i=0; i<s.length(); i++) {
            ch = s.charAt(i);
            if (Character.isDigit(ch))
                nc++;
            if (Character.isLetter(ch)) {
                if (Character.isUpperCase(ch))
                    uc++;
                if (Character.isLowerCase(ch))
                    lc++;
            }
            if (!Character.isLetterOrDigit(ch))
                sc++;
        }
        if (nc == 0) {
            JOptionPane.showMessageDialog(jf, "Password must contain atleast one digit from 0-9", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if (uc == 0) {
            JOptionPane.showMessageDialog(jf, "Password must contain atleast one upper case letter from A-Z", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if (lc == 0) {
            JOptionPane.showMessageDialog(jf, "Password must contain atleast one lower case letter from a-z", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if (sc == 0) {
            JOptionPane.showMessageDialog(jf, "Password must contain atleast one special character.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        return 1;
    }
    
    public int checkFname (String s, Component jf) {
        if (s.isEmpty()) {
            JOptionPane.showMessageDialog(jf, "First Name cannot be blank.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if ((s.length() < 2) || (s.length() > 50)) {
            JOptionPane.showMessageDialog(jf, "Name has to be within 2 to 50 letters.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if (Character.isLowerCase(s.charAt(0))) {
            JOptionPane.showMessageDialog(jf, "First Name must start with a capital letter.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        int wsc = 0;
        char ch;
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            if (Character.isDigit(ch)) {
                JOptionPane.showMessageDialog(jf, "Name must not contain any numerics.", "Warning!", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
            if (Character.isWhitespace(ch))
                wsc++;
            else if (!Character.isLetterOrDigit(ch)) {
                JOptionPane.showMessageDialog(jf, "Name must not contain any special characters.", "Warning!", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
            if (wsc == 2) {
                JOptionPane.showMessageDialog(jf, "Only First and Middle name allowed here.", "Warning!", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
        }
        return 1;
    }

    public int checkLname (String s, Component jf) {
        if (s.isEmpty()) {
            JOptionPane.showMessageDialog(jf, "Last Name cannot be blank.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if ((s.length() < 2) || (s.length() > 20)) {
            JOptionPane.showMessageDialog(jf, "Name has to be within 2 to 20 letters.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if (Character.isLowerCase(s.charAt(0))) {
            JOptionPane.showMessageDialog(jf, "Last Name must start with a capital letter.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        char ch;
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            if (Character.isDigit(ch)) {
                JOptionPane.showMessageDialog(jf, "Name must not contain any numerics.", "Warning!", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
            if (!Character.isLetterOrDigit(ch)) {
                JOptionPane.showMessageDialog(jf, "Name must not contain any special characters.", "Warning!", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
            if (Character.isWhitespace(ch)) {
                JOptionPane.showMessageDialog(jf, "Only Last name allowed here.", "Warning!", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
        }
        return 1;
    }

    public int checkEmail (String s, Component jf) {
        if (s.isEmpty()) {
            JOptionPane.showMessageDialog(jf, "Email ID cannot be blank.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if (s.length() > 50) {
            JOptionPane.showMessageDialog(jf, "Email ID has to be within 50 characters.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if (!Character.isLetter(s.charAt(0))) {
            JOptionPane.showMessageDialog(jf, "Email ID must contain start with a letter.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if (s.indexOf('@') == -1) {
            JOptionPane.showMessageDialog(jf, "Email ID must contain a '@' symbol.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if (s.indexOf('@') != s.lastIndexOf('@')) {
            JOptionPane.showMessageDialog(jf, "Email ID must contain only one '@' symbol.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        String s1 = s.substring(0, s.indexOf('@'));
        String s2 = s.substring(s.indexOf('@') + 1);
        if (s1.isEmpty()) {
            JOptionPane.showMessageDialog(jf, "Email ID cannot be blank.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if (s2.isEmpty()) {
            JOptionPane.showMessageDialog(jf, "Invalid Email ID.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        char ch;
        for (int i = 0; i < s1.length(); i++) {
            ch = s1.charAt(i);
            if ((!Character.isLetterOrDigit(ch)) && (ch != '.')) {
                JOptionPane.showMessageDialog(jf, "Email Username can have only a-z, A-Z, 0-9 and '.'\nNo other special characters allowed.", "Warning!", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
        }
        if (s2.charAt(0) == '.') {
            JOptionPane.showMessageDialog(jf, "Email domain cannot start with '.'", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if (s2.charAt(s2.length() - 1) == '.') {
            JOptionPane.showMessageDialog(jf, "Email extension cannot end with '.'", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if (s2.indexOf('.') == -1) {
            JOptionPane.showMessageDialog(jf, "Email Domain and Extension must be separated with a '.' symbol.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if (s2.indexOf('.') != s2.lastIndexOf('.')) {
            JOptionPane.showMessageDialog(jf, "Email ID must contain only one domain and extension.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        for (int i = 0; i < s2.length(); i++) {
            ch = s2.charAt(i);
            if ((!Character.isLetter(ch)) && (ch != '.')) {
                JOptionPane.showMessageDialog(jf, "Email Domain and Extension can only have letters a-z.", "Warning!", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
        }
        return 1;
    }
    
    public int checkBalance (String s, Component jf) {
        if (s.isEmpty()) {
            JOptionPane.showMessageDialog(jf, "Balance cannot be blank.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        char ch;
        for (int i=0; i<s.length(); i++) {
            ch = s.charAt(i);
            if (!Character.isDigit(ch)) {
                JOptionPane.showMessageDialog(jf, "Balance can only be a whole number.", "Warning!", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
        }
        return 1;
    }
    
    public int checkSans (String s, Component jf) {
        if (s.isEmpty()) {
            JOptionPane.showMessageDialog(jf, "Security Answer cannot be blank.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if ((s.length() < 2) || (s.length() > 15)) {
            JOptionPane.showMessageDialog(jf, "Password must be within 2 to 15 characters.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        return 1;
    }
}
