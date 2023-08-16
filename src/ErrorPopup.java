import java.awt.Font;

import javax.swing.*;

public class ErrorPopup extends JFrame {

    int height = 250;
    int width = 600;
    public ErrorPopup(String errorMessage) {
        // Set JFrame properties
        setTitle("Error");
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
    }

    /*public static void main(String args[])
    {
        new ErrorPopup("hello");
    }*/
}

