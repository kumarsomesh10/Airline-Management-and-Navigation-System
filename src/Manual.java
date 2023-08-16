import java.awt.Font;

import javax.swing.*;

public class Manual extends JFrame {

    int height = 900;
    int width = 1200;
    public Manual(String errorMessage) {
        // Set JFrame properties
        setTitle("Manual");
        setLocation(0, 0);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        setResizable(false);

        // Create JLabel with error message
        MyLabel messageLabel = new MyLabel(errorMessage);
        messageLabel.setLayout(null);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setFont(new Font("Ariel", 0, 25));
        messageLabel.setBounds(0,0,width,height);
        messageLabel.setVisible(true);

        // Add label to frame
        add(messageLabel);
        setSize(width,height);
        User.message = "";
    }
}

