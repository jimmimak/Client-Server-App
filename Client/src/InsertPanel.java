import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InsertPanel extends JPanel {

    private JPanel panel; //panel pou perilambanei 6 grammes kai 2 sthles apo labels kai textfields
    private JPanel row1;
    private JPanel row2;
    private JPanel row3;
    private JPanel row4;
    private JPanel row5;
    private JPanel row6;
    private JTextField title;
    private JTextField director;
    private JTextField genre;
    private JTextField duration;
    private JTextField description;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JButton insertButton;
    Frame frame;

    public InsertPanel(Frame frame) {
        this.frame = frame;
        this.setLayout(new BorderLayout()); //thetoume sto InsertPanel ton diaxeirhsth diatajhs BorderLayout

        GridLayout layout = new GridLayout(6, 2);
        panel = new JPanel(layout);

        row1 = new JPanel();
        label1 = new JLabel("   Title:          ", JLabel.RIGHT);
        title = new JTextField(20);

        row2 = new JPanel();
        label2 = new JLabel("   Director:   ", JLabel.RIGHT);
        director = new JTextField(20);

        row3 = new JPanel();
        label3 = new JLabel("   Genre:       ", JLabel.RIGHT);
        genre = new JTextField(20);

        row4 = new JPanel();
        label4 = new JLabel("   Duration:  ", JLabel.RIGHT);
        duration = new JTextField(20);

        row5 = new JPanel();
        label5 = new JLabel("   Summary:", JLabel.RIGHT);
        description = new JTextField(20);

        row6 = new JPanel();
        insertButton = new JButton("Insert Movie");

        //Prwth grammh
        row1.setLayout(new FlowLayout());
        row1.add(label1);
        row1.add(title);
        panel.add(row1);

        //Deuterh grammh
        row2.setLayout(new FlowLayout());
        row2.add(label2);
        row2.add(director);
        panel.add(row2);

        //Trith grammh
        row3.setLayout(new FlowLayout());
        row3.add(label3);
        row3.add(genre);
        panel.add(row3);

        //Tetarth grammh
        row4.setLayout(new FlowLayout());
        row4.add(label4);
        row4.add(duration);
        panel.add(row4);

        //Pempth grammh
        row5.setLayout(new FlowLayout());
        row5.add(label5);
        row5.add(description);
        panel.add(row5);

        //Ekth grammh
        row6.setLayout(new FlowLayout());
        row6.add(insertButton);
        panel.add(row6);

        this.add(panel, BorderLayout.NORTH); //prosthetoume to panel sto panw meros tou InsertPanel

        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //an o xrhsths den exei sumplhrwsei toulaxiston ena apo ta pedia tou emfanizetai h antistoixh proeidopoihsh
                if (title.getText().isBlank() || director.getText().isBlank() || genre.getText().isBlank() || duration.getText().isBlank() || description.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "One of the entries is blank. Fill it before you insert the movie.");
                }
                //an o xrhsths sumplhrwse ola ta pedia tote sth metablhth toServer apothikeuetai ena antikeimeno tupou Movie me ta stoixeia pou eishgage o xrhsths
                else{
                    frame.set_toServer(new Movie(title.getText(), director.getText(), genre.getText(), duration.getText(), description.getText()));
                }
                //katharizoume ta textfields
                title.setText("");
                director.setText("");
                genre.setText("");
                duration.setText("");
                description.setText("");
            }
        });
    }
}
