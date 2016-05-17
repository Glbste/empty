package orologiomvc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;
import java.util.Observer; 
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Quadrante dell'orologio analogico.
 * Si aggiorna quando richiesto dal timer
 * (grazie al metodo update ereditato da Observer)
 * 
 * @author mauropamiro
 */
public class AnalogView extends JPanel implements Observer{    
    private final Lancetta lancette[];
    private BufferedImage image;
    
    public AnalogView (Dimension d) {
        // Imposto la dimensione della view
        this.setSize(d);
        
        // Calcolo le coordinate del centro dell'orologio
        Point centro=new Point(getWidth()/2,getHeight()/2);
        
        // Creo le tre lancette
        lancette=new Lancetta[3];
        lancette[0]=new Lancetta("H",getHeight(),centro);
        lancette[1]=new Lancetta("M",getHeight(),centro);
        lancette[2]=new Lancetta("S",getHeight(),centro);
        
        // Carico l'immagine dell'orologio in memoria  
        String imgPath=getClass().getClassLoader().getResource("resources/orologio.bmp").getPath();
        try {
            File f=new File(imgPath);
            Image img=ImageIO.read(f).getScaledInstance(this.getHeight()-4,this.getHeight()-4,Image.SCALE_SMOOTH);
            image  = new BufferedImage(this.getHeight(),this.getHeight(), BufferedImage.TYPE_INT_RGB);
            image.getGraphics().setColor(Color.WHITE);
            image.getGraphics().fillRect(0, 0, this.getHeight(), this.getHeight());
            image.getGraphics().drawImage(img, 4, 4, this);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Immagine non trovata: "+imgPath);
        }
    } 
   
    /**
     * Implementazione del metodo di Observer
     * 
     * @param o L'oggetto che ha richiesto l'aggiornamento
     * @param arg Eventuali argomenti passati da o
     */
    @Override
    public void update (Observable o, Object arg) {
        // Ottengo il riferimento al model osservato
        Time timer=(Time)o;
        
        // Cancello il contenuto del pannello
        Graphics g=getGraphics();
        g.setColor(this.getBackground());
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        // Disegno l'orologio e le lancette
        if(image!=null) g.drawImage(image, (this.getWidth()-this.getHeight())/2, 0, this.getHeight(),this.getHeight(),this);
        g.setColor(Color.BLACK);

        // Imposto la posizione delle lancette chiedendo ore, minuti e secondi al model
        lancette[0].disegna(timer.getHour(),g);
        lancette[1].disegna(timer.getMin(),g);
        lancette[2].disegna(timer.getSec(),g);
    }

}

