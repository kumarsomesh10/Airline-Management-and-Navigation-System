import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Textbox extends JFrame implements ActionListener{

    Font font = new Font("Ariel", 0, 25);
    MyLabel label;
    JTextField textField;
    JButton submitButton;
    int prefWIDTH = 1200;
    int prefHEIGHT = 1000;

    public Textbox(String txt)
    {
               
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setPreferredSize(new Dimension(prefWIDTH,prefHEIGHT));
        this.setLayout(null);
        this.setResizable(false);

        label = new MyLabel(txt);
        label.setLayout(null);
        label.setBounds(0,0,prefWIDTH,prefHEIGHT/2);
        label.setFont(font);
        label.setHorizontalAlignment(JLabel.CENTER);
        
        textField = new JTextField(1);
        textField.setLayout(null);
        textField.setBounds(prefWIDTH/2-75,3*prefHEIGHT/5-25,150,50);
        textField.setFont(font);
        textField.setHorizontalAlignment(JLabel.CENTER);


        submitButton = new JButton("Submit");
        submitButton.setFont(font);
        submitButton.setEnabled(true);
        submitButton.setVisible(true);
        submitButton.setBounds(prefWIDTH/2-85,3*prefHEIGHT/4-25,170,50);
        submitButton.setLayout(null);
        

        submitButton.addActionListener(this);

        this.add(textField);
        this.add(label);
        this.add(submitButton);
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User.input = textField.getText();
        User.inputReady = true;
        System.out.println("Text sent = "+textField.getText());
        User.message = "";
    }
}
