//Dimitrios Makris, 3212019119

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    //HashMap sto opoio apothikeuontai oi tainies, exontas san kleidi ton titlo tous
    //kai san timh oles tis plhrofories pou tis aforoun
    private static HashMap<String, Movie> movies = new HashMap<>();

    public static void main(String[] args) {
        try {
            initMovies();

            ServerSocket server = new ServerSocket(5555);

            while (true) {
                Socket sock = server.accept(); //anamonh gia sundesh apo ton pelath
                try (
                        ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream()); //stream gia apostolh mhnumatwn ston client
                        ObjectInputStream instream = new ObjectInputStream(sock.getInputStream()); //stream gia anagnwsh mhnumatwn apo ton server
                        ) {

                    Object strin = instream.readObject(); //anagnwsh mhnumatos apo ton client
                    
                    //an o client eipe ston server "BEGIN"
                    if (strin.equals("BEGIN")) {
                        outstream.writeObject("LISTENING"); //tote o server apantaei me "LISTENING" kai jekinaei h epikoinwnia metaju tous
                        outstream.flush();
                        do {
                            strin = instream.readObject(); ////anagnwsh mhnumatos apo ton client
                            
                            //an o client esteile ston server "RQ_SEARCH" tote jekinaei h diadikasia ths anazhthshs
                            if (strin.equals("RQ_SEARCH")) {
                                strin = instream.readObject(); // anagnwsh mhnumatos apo ton client
                                
                                //an to mhnuma einai "CANCEL" tote o server stelnei ston client "CANCEL" kai skiparei to trexwn loop
                                if (strin.equals("CANCEL")) {
                                    outstream.writeObject("CANCEL");
                                    continue;
                                }

                                String search = (String) strin; //metatroph autou pou egrapse o xrhsths se string kai to apothikeuoume se mia metavlhth "search"
                                
                                //exw thesei sthn klash SearchPanel analoga me to an o xrhsths pragmatopoiei anazhthsh me bash
                                //ton titlo ths tainias h ton skhnotheth na mpainei mprosta apo ton titlo h ton skhnotheth
                                //ena "T: " an o xrhsths anazhta me bash ton titlo h ena "D: " an o xrhsths anazhta me bash ton
                                //skhnotheth. H parakatw if ekteleitai loipon gia thn periptwsh pou o xrhsths pragmatopoiei anazhthsh me bash ton titlo
                                if (search.charAt(0) == 'T') {
                                    String title = search.subSequence(3, search.length()).toString(); //apokoptoume apo to string "search" to "T: " kai etsi menei mono o titlos me ton opoio o xrhsths ekane anazhthsh
                                    //an autos o titlos uparxei ws kleidi sto HashMap movies
                                    if (movies.containsKey(title)) {
                                        outstream.writeObject(movies.get(title)); //o server stelnei ston client ta stoixeia ths tainias pou antistoixoun ston titlo
                                        outstream.flush();
                                    } else {
                                        outstream.writeObject("NO RECORD"); //alliws o server stelnei ston client to mhnuma "NO RECORD"
                                        outstream.flush();
                                    }
                                } else {
                                    ArrayList<Movie> movlist = new ArrayList<>(); //lista sthn opoia apothikeuontai oi tainies pou exoun skhnotheththei apo enan sugkekrimeno skhnotheth
                                    String director = search.subSequence(3, search.length()).toString(); //apokoptoume apo to string "search" to "D: " kai etsi menei mono o skhnotheths me ton opoio o xrhsths ekane anazhthsh
                                    //diasxizoume oles tis tainies pou uparxoun san times sto HashMap movies
                                    for (Movie mov : movies.values()) {
                                        //an brethei mia tainia pou na exei skhnotheththei apo ton skhnotheth me ton opoio ekane anazhthsh o xrhsths prostithetai sto ArrayList movlist
                                        if (mov.directorEq(director)) {
                                            movlist.add(mov);
                                        }
                                    }
                                    //an to ArrayList movlist einai keno shmainei oti den uparxei kamia tainia tou
                                    //skhnotheth me bash ton opoio pragmatopoihse anazhthsh o xrhsths ki etsi o server
                                    //stelnei to mhnuma "NO RECORD" ston client
                                    if (movlist.isEmpty()) {
                                        outstream.writeObject("NO RECORD");
                                    } 
                                    //alliws o server stelnei ston client oles tis tainies tou skhnotheth pou epsaje o xrhsths
                                    else {
                                        outstream.writeObject(movlist);
                                    }
                                    outstream.flush();
                                }
                            } 
                            //an o client esteile ston server "RQ_INSERT" tote jekinaei h diadikasia ths eisagwghs
                            else if (strin.equals("RQ_INSERT")) {
                                strin = instream.readObject(); // anagnwsh mhnumatos apo ton client
                                
                                //an to mhnuma einai "CANCEL" tote o server stelnei ston client "CANCEL" kai skiparei to trexwn loop
                                if (strin.equals("CANCEL")) {
                                    outstream.writeObject("CANCEL");
                                    continue;
                                }
                                Movie toinsert = (Movie) strin; //metatroph autou pou egrapse o xrhsths se tupo antikeimenou "Movie" apothikeush tou se mia metavlhth "toinsert"
                                //an o titlos ths tainias pou phge na eisagei o xrhsths uparxei hdh
                                //o server stelnei to mhnuma "ERR_TITLE" ston client
                                if (movies.containsKey(toinsert.getTitle())) {
                                    outstream.writeObject("ERR_TITLE");
                                    outstream.flush();
                                } 
                                //alliws h tainia eisagetai sto HashMap kai apo ekei grafetai sto arxeio antikeimenwn pou perilambanei oles tis tainies
                                else {
                                    movies.put(toinsert.getTitle(), toinsert);
                                    saveMovies(); //sunarthsh pou grafei se ena arxeio tis tainies pou uparxoun sto HashMap movies
                                    outstream.writeObject("OK"); //afou h diadikasia ths eisagwghs oloklhrwthike epituxws stelnetai ston client to mhnuma "OK"
                                    outstream.flush();
                                }
                            } 
                            //an o client steilei "END" ston server tote h sundesh metaju tous diakoptetai
                            //kai o server perimenei gia sundesh me kainourio client
                            else if (strin.equals("END")) {
                                break;
                            }
                        } while (true);
                    } else {
                        outstream.writeObject("Not welcomed...");
                        outstream.flush();
                    }
                }catch(IOException ex){ 
                    System.out.println("Connection Closing...");
                }catch (ClassNotFoundException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void initMovies() {
        try {
            File moviefile = new File("movies.ser"); //arxikopoihsh enos arxeiou antikeimenwn movies
            //an to arxeio movies.ser uparxei hdh tote to diabazei kai pernaei tis tainies pou auto periexei sto HashMap movies
            if (!moviefile.createNewFile()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(moviefile))) {
                    Movie temp;
                    while (true) {
                        temp = (Movie) ois.readObject();
                        movies.put(temp.getTitle(), temp);
                    }
                } catch (EOFException ex) {
                } catch (IOException ex) {
                    System.out.println("Problem reading file");
                } catch (ClassNotFoundException ex) {
                    System.out.println("Class \"Movie\" not found");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //sunarthsh pou grafei se ena arxeio tis tainies pou uparxoun sto HashMap movies
    private static void saveMovies() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("movies.ser"))) {
            for (Movie mov : movies.values()) {
                oos.writeObject(mov);
            }
        } catch (IOException ex) {
            System.out.println("Problem writing to file");
        }
    }
}
