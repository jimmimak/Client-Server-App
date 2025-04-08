//Dimitrios Makris, 3212019119

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Client {

    public static void main(String[] args) {
        try (Socket sock = new Socket("localhost", 5555)) {
            
            ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream()); //stream gia apostolh mhnumatwn ston server
            ObjectInputStream instream = new ObjectInputStream(sock.getInputStream()); //stream gia anagnwsh mhnumatwn apo ton server
            
            Object serverin; //metablhth sthn opoia apothhkeuontai ta mhnumata tou server
            Object clientmsg; //mhnuma tou xrhsth pros ton server
            
            outstream.writeObject("BEGIN"); //o client stelnei begin gia na jekinhsei thn epikoinwnia me ton server
            
            //an o server apanthsei me "LISTENING"
            if (instream.readObject().equals("LISTENING")) {
                Frame a = new Frame(); //arxikopoihsh tou parathurou ths efarmoghs
                
                //h efarmogh perimenei mexri o xrhsths na pathsei to koumpi "Insert" h "Search"
                while (a.getOption().equals("")) {
                    Thread.sleep(1);
                }
                
                while (true) {
                    //an o xrhsths pathse panw sto koumpi "Search" mpainoume sto parakatw loop
                    while (a.getOption().equals("search")) {
                        outstream.writeObject("RQ_SEARCH"); //o client stelnei ston server to mhnuma "RQ_SEARCH" gia na tou pei oti jekinaei h prajh ths anazhthshs
                        
                        //epanalhpsh oso to mhnuma tou xrhsth einai keno
                        do {
                            System.out.print(""); //xwris auth th grammh kwdika den leitourgouse h prajh ths anazhthshs
                            clientmsg = a.toSend(); //apothikeuetai sth metablhth clientmsg auto pou egrapse o xrhsths sthn mpara "Search by title" h "Search by director"
                        } while (clientmsg == null);
                        outstream.writeObject(clientmsg); //to mhnuma tou xrhsth stelnetai ston server
                        serverin = instream.readObject(); //o client diabazei to mhnuma pou tou esteile o server
                        
                        //an o server steilei "NO RECORD" shmainei oti den brethike h tainia
                        if (serverin.equals("NO RECORD")) {
                            JOptionPane.showMessageDialog(null, "No such movie found");
                        }
                        //an o server steilei "CANCEL" tote bgainoume apo to loop
                        else if (serverin.equals("CANCEL")) {
                            break;
                        } 
                        //alliws emfanizetai ena parathuro me tis plhrofories ths tainias pou epsaxne o xrhsths
                        else {
                            JOptionPane.showMessageDialog(null, serverin);
                        }
                    }
                    //an o xrhsths pathse panw sto koumpi "Insert" mpainoume sto parakatw loop
                    while (a.getOption().equals("insert")) {
                        outstream.writeObject("RQ_INSERT"); //o client stelnei ston server to mhnuma "RQ_INSERT" gia na tou pei oti jekinaei h prajh ths eisagwghs
                        //epanalhpsh oso to mhnuma tou xrhsth einai keno
                        do {
                            System.out.print("");
                            clientmsg = a.toSend();
                        } while (clientmsg == null);
                        outstream.writeObject(clientmsg); //to mhnuma tou xrhsth stelnetai ston server
                        serverin = instream.readObject(); //o client diabazei to mhnuma pou tou esteile o server
                        
                        //an o server pei ston client "OK" shmainei oti h eisagwgh ektelesthke epituxws ki emfanizetai to katallhlo mhnuma ston xrhsth
                        if (serverin.equals("OK")) {
                            JOptionPane.showMessageDialog(a, "Movie inserted successfully");
                        } 
                        //an o server pei ston client "ERR_TITLE" shmainei oti uparxei hdh mia tainia me auton ton titlo kataxwrhmenh
                        else if (serverin.equals("ERR_TITLE")) {
                            JOptionPane.showMessageDialog(a, "There is already a movie with that title");
                        }
                    }
                    //an o xrhsths pathsei panw sto "x" tou parathurou ths efarmoghs, 
                    //o client stelnei to mhnuma "END" ston server kai h sundesh metaju tous diakoptetai
                    if (a.getOption().equals("exit")) {
                        outstream.writeObject("END");
                        break;
                    }
                }
            }
            //kleinoume tis roes pou xrhsimopoihsame gia thn epikoinwnia
            //metaju tou server kai tou client
            instream.close();
            outstream.close();
            System.out.println("Connection Closing...");

        } catch (IOException ex) {
            System.out.println("Connection Failed!");
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found!");
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
