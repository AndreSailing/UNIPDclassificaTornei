package com.example;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.common.collect.Multimap; import com.google.common.collect.TreeMultimap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;




public class App 
{
    public static void main( String[] args )
    {
        final int PENALITà=0;
        
        List<Giocatore> map = new ArrayList<>();
        TreeMultimap<Integer, Giocatore> classificaFinale= TreeMultimap.create(Integer::compare, (g1, g2) -> g1.getGiocatoreName().compareTo(g2.getGiocatoreName()));

       

        Gson gson = new Gson();
        Type type = new TypeToken<List<Giocatore>>(){}.getType();

       try (FileReader reader = new FileReader("Giocatori.json")) {
            map = gson.fromJson(reader, type);
            if(map==null){map=new ArrayList<>();}
            
        } catch (IOException e) {
            System.err.println("Possibile Errore In Lettura");
            e.printStackTrace();
        }
        Scanner fPath=new Scanner(System.in);
        System.out.println("Inserisci il nome del file TXT con la classifica");
        String filePath=fPath.nextLine();
        System.out.println("numero del torneo");
        int numeroDiTornei=Integer.parseInt(fPath.nextLine());
        fPath.close();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String linea=scanner.nextLine();
                Scanner controlloPrimaParola=new Scanner(linea);
                String primaParola="";
                if(controlloPrimaParola.hasNext()){ primaParola+=controlloPrimaParola.next();}
                if(primaParola.equals("Pos.")){
                    scanner.nextLine();
                    String nextString=scanner.nextLine(),StringaDiControllo="";
                    do {
                        Scanner scRiga=new Scanner(nextString);
                        int posizione=Integer.parseInt(scRiga.next());
                        scRiga.next();
                        String NomeGiocatore="",stringToAdd=scRiga.next();
                        while (!(stringToAdd.equals("f")||stringToAdd.equals("m"))) { 
                            NomeGiocatore+=stringToAdd+" ";
                            System.out.println(NomeGiocatore);
                            stringToAdd=scRiga.next();
                        }
                        boolean b=true;
                        for(Giocatore g:map){
                            if(g.getGiocatoreName().equals(NomeGiocatore)){
                                g.addPunteggio(calcoloPunteggio(posizione));
                                System.err.println("inserito for");
                                b=false;

                            }
                        }
                        if(b){
                            Giocatore giocatore=new Giocatore(NomeGiocatore, calcoloPunteggio(posizione));
                            map.add(giocatore);
                            System.err.println("inserito if");
                        }

                        

                        nextString=scanner.nextLine();
                        scRiga.close();
                    } while (!nextString.equals(StringaDiControllo));
                    
                }
                controlloPrimaParola.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(Giocatore g:map){
            System.err.println("Giocatore");
            
            while(g.torneiGiocati()<numeroDiTornei){
                g.addPunteggio(PENALITà);

            }
            classificaFinale.put(g.punteggioClassifica(numeroDiTornei),g);
            
        }
        int index=classificaFinale.size();
        for (Map.Entry<Integer,Giocatore> elem : classificaFinale.entries()) {
            System.out.println(index+" - " +elem.getValue().getGiocatoreName()+"  - punteggio: "+elem.getKey());
            index--;
            
        }
        try (FileWriter writer = new FileWriter("Giocatori.json")) {
            gson.toJson(map, writer);
            writer.flush();
            System.out.println("JSON scritto con successo!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int calcoloPunteggio(int posizione){
        final Integer[] Punteggi={50,44,41,38,35,32,30,28,26,24,22,20,18,16,14,12,10,9,8,7,6,5,4,3,2 };
        if(posizione-1<Punteggi.length){return Punteggi[posizione-1];}
        return 0;
    }
}
