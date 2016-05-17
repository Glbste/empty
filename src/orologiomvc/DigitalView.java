package orologiomvc;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observer; 
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * Quadrante dell'orologio digitale.
 * Si aggiorna quando richiesto dal timer
 * (grazie al metodo update ereditato da Observer)
 * 
 * @author mauropamiro
 */
public class DigitalView extends JPanel implements Observer {
    private final JLabel labels[];

    public DigitalView (Dimension d) {
        // Imposto la dimensione e lo sfondo del pannello
        setSize(d);
        setBackground(Color.WHITE);
        
        // Creo le tre etichette, ne imposto le proprietà e le aggiungo alla DigitalView
        labels=new JLabel[3];
        for(int i=0;i<3;i++) labels[i]=new JLabel("00");
        for(int i=0;i<labels.length;i++){
            labels[i].setSize(this.getWidth()/labels.length,this.getHeight());
            labels[i].setBorder(new BevelBorder(BevelBorder.LOWERED));
            labels[i].setFont(labels[i].getFont().deriveFont((float)35));
            add(labels[i]);
        }
    }   
    
    /**
     * Implementazione del metodo di Observer
     * 
     * @param o L'oggetto che ha richiesto l'aggiornamento
     * @param arg Eventuali argomenti passati da o
     */    
    @Override
    public void update (java.util.Observable o, Object arg) {
        // Recupero le informazioni sull'orario corrente
        Time timer=(Time)o;
        // Scrivo nelle etichette ore, minuti e secondi
        labels[0].setText(String.format("%02d", timer.getHour()));
        labels[1].setText(String.format("%02d", timer.getMin()));
        labels[2].setText(String.format("%02d", timer.getSec()));
        // Ridisegno la DigitalView
        revalidate();
        repaint();
    }             
}

