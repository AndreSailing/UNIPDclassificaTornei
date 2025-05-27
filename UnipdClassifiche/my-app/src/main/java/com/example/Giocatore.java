package com.example;

import java.util.ArrayList;
import java.util.List;

public class Giocatore {
    private final int TORNEICONSIDERATI=6;
    public List<Integer> punteggi;
    String giocatore;

    public Giocatore() {
        this.giocatore="";
        this.punteggi=new ArrayList<>();
    }
    
    public Giocatore(String Nome) {
        this.giocatore=Nome;
        this.punteggi=new ArrayList<>();
    }
    
    public Giocatore(String Nome,int punteggio) {
        this.giocatore=Nome;
        this.punteggi=new ArrayList<>();
        this.punteggi.add(punteggio);
    }
    
    //setter e getter
    void setGiocatoreName(String NuovoNome){this.giocatore=NuovoNome;}
    String getGiocatoreName(){return this.giocatore;}

    void addPunteggio(int punteggio){punteggi.add(punteggio);}
    List<Integer> getPunteggi(){return punteggi;}
    
    //Metodi per La lista
    Integer popLower(){
        if(punteggi.size()!=0){
            int lower=punteggi.get(0),index=0;
            for (int i=0; i<punteggi.size();i++) {
                if(punteggi.get(i)<lower){
                    lower=punteggi.get(i);
                    index=i;
                }
                
            }
            punteggi.remove(index);
            return lower;
            
        }
        return -1;
    }
    boolean hasPunteggio(){return (punteggi.size()>0);}
    int torneiGiocati(){return punteggi.size();}
    Integer punteggioClassifica(int numeroDiTornei){
        Integer rPunti=0;
        if(numeroDiTornei<TORNEICONSIDERATI){
            
            for(Integer i:punteggi){
                rPunti+=i;
            }
        }else{
            List<Integer> list=punteggi;
            while (list.size()>TORNEICONSIDERATI) { 
                list=removeMin(list);   
            }
            for(Integer i:list){
                rPunti+=i;
            }
        }
        return rPunti;
    }
    private List<Integer> removeMin(List<Integer> list){
    // Trova l'indice dell'elemento massimo
    Integer max=list.get(0);
    int index=0;
    for (int i=0; i<list.size(); i++) {
        if (list.get(i) < max) {
            max = list.get(i);
            index = i;
        }
    }

    // Rimuove correttamente il massimo dalla lista originale
    list.remove(index);
    System.err.println(index);
    return list;
}
}
