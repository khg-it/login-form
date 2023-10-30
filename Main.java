import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Main extends JFrame {
    private static final String url = "jdbc:mysql://localhost:3307/login"; // Corrected URL
    private static final String username = "root";
    // Consider storing passwords securely (e.g., using environment variables or encryption)
     private static final String password = "khu123_45";

    JLabel l1, l2, l3;
    JPanel p1; // Use JPanel as the primary content holder
    JTextField t1;
    JPasswordField t2;
    JButton b1;

    Main() {p1 = new JPanel();
        p1.setLayout(null); // Set layout to null for absolute positioning
        p1.setBackground(Color.black);
        add(p1);

        l1 = new JLabel("LOGIN");
        l1.setBounds(100, 150, 100, 50);
        l1.setForeground(Color.RED);
        l1.setFont(new Font("ARIAL", Font.BOLD, 30));
        p1.add(l1);

        l2 = new JLabel("USERNAME");
        l2.setBounds(100, 250, 200, 50);
        l2.setForeground(Color.PINK);
        l2.setFont(new Font("ARIAL", Font.BOLD, 30));
        p1.add(l2);
        t1 = new JTextField(" ");
        t1.setBounds(400, 250, 200, 50);
        t1.setBackground(Color.CYAN);
        t1.setColumns(10);
        t1.setFont(new Font("ARIAL", Font.BOLD, 30));
        p1.add(t1);

        l3 = new JLabel("PASSWORD");
        l3.setBounds(100, 350, 200, 50);
        l3.setForeground(Color.PINK);
        l3.setFont(new Font("ARIAL", Font.BOLD, 30));
        p1.add(l3);

        t2 = new JPasswordField(" ");
        t2.setBounds(400, 350, 200, 50);
        t2.setBackground(Color.CYAN);
        t2.setColumns(10);
        t2.setEchoChar('&');
        t2.setFont(new Font("ARIAL", Font.BOLD, 30));
        p1.add(t2);
        b1 = new JButton("login");
        b1.setBounds(200, 450, 100, 50);
        b1.setBackground(Color.magenta);
        p1.add(b1);
        b1.addActionListener(e -> {
            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                String user = t1.getText();
                String pwd = String.valueOf(t2.getPassword());

                PreparedStatement pat = connection.prepareStatement("select * from authenticate where username=? and password=?");
                pat.setString(1, user);
                pat.setString(2, pwd);
                ResultSet res = pat.executeQuery();

                if (res.next()) {
                    JOptionPane.showMessageDialog(p1, "You have successfully logged in", "Message", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(p1, "Wrong details", "Message", JOptionPane.ERROR_MESSAGE);
                }

                // Close resources in a finally block
                res.close();
                pat.close();
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.setBounds(100, 100, 700, 600);
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        m.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        m.setVisible(true);
    }
}
