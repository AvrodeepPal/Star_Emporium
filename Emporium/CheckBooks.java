package Emporium;

import java.awt.Component;
import javax.swing.JOptionPane;

public class CheckBooks {
    
    public int checkBookName (String s, Component jf) {
        if (s.isEmpty()) {
            JOptionPane.showMessageDialog(jf, "Book Name cannot be blank.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if ((s.length() < 2) || (s.length() > 100)) {
            JOptionPane.showMessageDialog(jf, "Book Name must be within 2 to 100 characters.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if (Character.isLowerCase(s.charAt(0))) {
            JOptionPane.showMessageDialog(jf, "Book Name must start with a capital letter.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        return 1;
    }
    
    public int checkAuthorName (String s, Component jf) {
        if (s.isEmpty()) {
            JOptionPane.showMessageDialog(jf, "Author Name cannot be blank.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if ((s.length() < 2) || (s.length() > 100)) {
            JOptionPane.showMessageDialog(jf, "Author Name must be within 2 to 100 characters.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if (Character.isLowerCase(s.charAt(0))) {
            JOptionPane.showMessageDialog(jf, "Author Name must start with a capital letter.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        char ch;
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            if (Character.isDigit(ch)) {
                JOptionPane.showMessageDialog(jf, "Author Name must not contain any numerics.", "Warning!", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
            else if (!Character.isLetterOrDigit(ch) && (ch != ' ')) {
                JOptionPane.showMessageDialog(jf, "Author Name must not contain any special characters.", "Warning!", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
        }
        return 1;
    }
    
    public int checkSubject (String s, Component jf) {
        if (s.isEmpty()) {
            JOptionPane.showMessageDialog(jf, "Author Name cannot be blank.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if ((s.length() < 2) || (s.length() > 100)) {
            JOptionPane.showMessageDialog(jf, "Author Name must be within 2 to 100 characters.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        if (Character.isLowerCase(s.charAt(0))) {
            JOptionPane.showMessageDialog(jf, "Subject must start with a capital letter.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        int wsc=0;
        char ch;
        for (int i = 0; i < s.length(); i++) {
            ch = s.charAt(i);
            if (Character.isDigit(ch)) {
                JOptionPane.showMessageDialog(jf, "Author Name must not contain any numerics.", "Warning!", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
            if (Character.isWhitespace(ch))
                wsc++;
            else if (!Character.isLetterOrDigit(ch)) {
                JOptionPane.showMessageDialog(jf, "Author Name must not contain any special characters.", "Warning!", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
            if (wsc == 3) {
                JOptionPane.showMessageDialog(jf, "Only 3 words allowed here.", "Warning!", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
        }
        return 1;
    }
    
    public int checkPrice (String s, Component jf) {
        if (s.isEmpty()) {
            JOptionPane.showMessageDialog(jf, "Price cannot be blank.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        char ch;
        for (int i=0; i<s.length(); i++) {
            ch = s.charAt(i);
            if (!Character.isDigit(ch)) {
                JOptionPane.showMessageDialog(jf, "Price can only be a whole number.", "Warning!", JOptionPane.WARNING_MESSAGE);
                return 0;
            }
        }
        int p = Integer.parseInt(s);
        if ((p < 5) || (p > 100)) {
            JOptionPane.showMessageDialog(jf, "Price must be between 5 and 100 tokens (both inclusive).", "Warning!", JOptionPane.WARNING_MESSAGE);
            return 0;
        }
        return 1;
    }
}
