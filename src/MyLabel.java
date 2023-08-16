import javax.swing.JLabel;

public class MyLabel extends JLabel{
    public MyLabel(String s)
    {
        super("<html><div style='text-align:center'>" + s.replaceAll("\n", "</div><div style='text-align:center'>") + "</div></html>");
    }
}
