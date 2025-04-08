//Dimitrios Makris, 3212019119

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Frame extends JFrame {

    private JButton insert, search;
    private Object toServer; //to object pou stelnetai ston server
    private String option; //string pou pairnei thn timh insert, search h exit analoga me to koumpi sto opoio kanei click o xrhsths (Insert, Search h to "x" tou parathurou ths efarmoghs)

    public Frame() {
        super("My App");
        option = "";
        
        //arxikopoihsh twn antikeimenwn SearchPanel kai InsertPanel
        SearchPanel searchpanel = new SearchPanel(this);
        InsertPanel insertpanel = new InsertPanel(this);
        
        //dhmiourgia container pou periexei to panel twn koumpiwn kai katw apo auto
        //dhmiourgountai ta InsertPanel kai SearchPanel analoga me to poio koumpi pathse o xrhsths
        Container pane = getContentPane(); 
        
        JPanel buttons = new JPanel(new FlowLayout()); //dhmiourgia enos jexwristou container pou tha perilambanei ta koumpia

        //arxikopoihsh twn koumpiwn
        insert = new JButton("Insert");
        search = new JButton("Search");
        
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //an to string option den einai keno these sth metablhth toServer thn timh "CANCEL"
                //ki etsi ginetai refresh h timh tou option
                if(!option.equals("")){
                    toServer = "CANCEL";
                }
                option = "search";
                pane.remove(insertpanel); //an sto parathuro ths efarmoghs uparxei to InsertPanel, auto feugei
                revalidate(); //ginetai refresh to parathuro ths efarmoghs me tis allages
                pane.add(searchpanel); //prostithetai sto parathuro ths efarmoghs to SearchPanel
                revalidate(); //ginetai refresh to parathuro ths efarmoghs me tis allages
            }
        });
        
        insert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //an to string option den einai keno these sth metablhth toServer thn timh "CANCEL"
                //ki etsi ginetai refresh h timh tou option
                if(!option.equals("")){
                    toServer = "CANCEL";
                }
                option = "insert";
                pane.remove(searchpanel); //an sto parathuro ths efarmoghs uparxei to SearchPanel, auto feugei
                revalidate(); //ginetai refresh to parathuro ths efarmoghs me tis allages
                pane.add(insertpanel); //prostithetai sto parathuro ths efarmoghs to InsertPanel
                revalidate(); //ginetai refresh to parathuro ths efarmoghs me tis allages
            }
        });
        
        //listener pou ekteleitai otan o xrhsths pathsei "x" sto parathuro ths efarmoghs gia na to kleisei
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we) {
                option = "exit";
                dispose();
            }
        });

        //prosthetoume ta koumpia sto panel buttons
        buttons.add(insert);
        buttons.add(search);
        
        
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(buttons);
        
        setContentPane(pane);
        
        setExtendedState(JFrame.MAXIMIZED_BOTH); //otan trexei h efarmogh jekinaei se fullscreen
        setSize(1001, 300); //orizoume to megethos tou parathurou se 1001 px platos kai 300 px upsos
        setLocationRelativeTo(null); //kentraretai to parathuro sthn othonh
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //kleinei h efarmogh otan kanoume click sto "x"
        setVisible(true); //to plaisio einai orato
    }
    
    void set_toServer(Object toServer){
        this.toServer = toServer;
    }
    
    String getOption(){
        return option;
    }
    
    //h methodos pou xrhsimopoiei o client gia na pairnei mhmumata apo ta grafika
    Object toSend() {
        Object toReturn = toServer;
        toServer = null;
        return toReturn;
    }
}
