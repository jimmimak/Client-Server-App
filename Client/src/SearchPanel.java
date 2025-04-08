import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchPanel extends JPanel{
    
    private JPanel panel;
    private final JTextField title;
    private final JTextField director;
    private JLabel label1;
    private JLabel label2;
    private JButton checkButton1;
    private JButton checkButton2;
    Frame frame;
    
    public SearchPanel(Frame frame){
        this.frame = frame;
        this.setLayout(new BorderLayout()); //thetoume sto SearchPanel ton diaxeirhsth diatajhs BorderLayout
        panel = new JPanel(new FlowLayout()); //dhmiourgoume ena kainourio panel pou tha exei ton diaxeiristh diatajhs FlowLayout
        
        //arxikopoihsh twn components
        label1 = new JLabel("Search by title: ");
        label2 = new JLabel("Search by director: ");
        title = new JTextField(20);
        director = new JTextField(20);
        checkButton1 = new JButton("✔");
        checkButton2 = new JButton("✔");
        
        //prosthikh twn components sto panel
        panel.add(label1);
        panel.add(title);
        panel.add(checkButton1);
        panel.add(label2);
        panel.add(director);
        panel.add(checkButton2);
        
        this.add(panel, BorderLayout.NORTH); //prosthikh tou panel sto panw meros tou SearchPanel
        
        checkButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.set_toServer("T: " + title.getText()); //thetoume sth metablhth toServer to string "T: (titlos pou eishgage o xrhsths sto textfield)"
                //katharizoume ta textfields
                title.setText("");
                director.setText("");
            }
        });
        
        checkButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.set_toServer("D: " + director.getText()); //thetoume sth metablhth toServer to string "D: (skhnotheths pou eishgage o xrhsths sto textfield)"
                //katharizoume ta textfields
                title.setText("");
                director.setText("");
            }
        });
    }
}
