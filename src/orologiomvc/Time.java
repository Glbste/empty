package orologiomvc;

import java.util.Observable;

/**
 * Timer che memorizza l'istante corrente e aggiorna l'orologio
 * grazie alla superclasse Observable
 * 
 * @author mauropamiro
 */
public class Time extends Observable{
    // I secondi trascorsi dalle ore 00:00:00
    private int secondi;
    
    public void set(int secondi){
        // Imposto i secondi trascorsi dalle ore 00:00:00 
        this.secondi=secondi%(24*60*60);
        // Notifico le view che mi osservano
        setChanged();
        notifyObservers(secondi);
    }

    public int getSec () {
        return secondi%60;
    }
    
    public int getMin () {
        return (secondi/60)%60;
    }

    public int getHour () {
        return (secondi/60)/60;
    }
}

