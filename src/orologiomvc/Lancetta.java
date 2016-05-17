package orologiomvc;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Lancetta dell'orologio analogico.
 * Sottoclasse di Line2D che rappresenta un segmento
 * 
 * @author mauropamiro
 */
public class Lancetta extends Line2D{
    private final Point start,end;
    private final double length;
    private final int width;
    private final int nPosizioni;
    

    public Lancetta(String tipo, int clokHeight, Point clockCenter) {
        start=new Point(clockCenter);
        end=new Point();
        if(tipo.equals("H")){
            this.nPosizioni=12; // 12 posizioni possibili per la lancetta delle ore
            // La lancetta delle ore è più corta e più larga
            this.width=3;
            this.length=(clokHeight/2)*0.5;
            start.translate(1, 1);
        }
        else if(tipo.equals("M")){
            this.nPosizioni=60; // 60 posizioni possibili per la lancetta dei minuti
            // La lancetta dei minuti ha lunghezza e larghezza intermedi
            this.width=2;
            this.length=(clokHeight/2)*0.75;
        }
        else{
            this.nPosizioni=60; // 60 posizioni possibili per la lancetta dei secondi
            // La lancetta dei secondi è più lunga e più sottile
            this.width=1;
            this.length=(clokHeight/2)*0.8;
        }
    }
    
    public void disegna(int valore, Graphics g){
        // Calcolo l'angolo di inclinazione della lancetta rispetto al mezzogiorno
        double angolo=360/nPosizioni*valore;
        // Calcolo le coordinate dell'estremità della lancetta
        end.y=(int)(start.y+Math.sin(Math.toRadians(angolo-90))*length);
        end.x=(int)(start.x+Math.cos(Math.toRadians(angolo-90))*length);
        // Disegno la lancetta
        if(width>1){
            int[] xPoints=new int[]{start.x,end.x,(int)(end.x-Math.cos(Math.toRadians(angolo))*width),(int)(start.x-Math.cos(Math.toRadians(angolo))*width)};
            int[] yPoints=new int[]{start.y,end.y,(int)(end.y-Math.sin(Math.toRadians(angolo))*width),(int)(start.y-Math.sin(Math.toRadians(angolo))*width)};
            g.fillPolygon(xPoints, yPoints, 4);
        }
        else g.drawLine(start.x, start.y,end.x,end.y);
    } 
    
    // Seguono le implementazioni dei metodi astratti ereditati da Line2D
    
    @Override
    public double getX1() {return start.x;}

    @Override
    public double getY1() {return start.y;}

    @Override
    public Point2D getP1() {return start;}

    @Override
    public double getX2() {return end.x;}

    @Override
    public double getY2() {return end.y;}

    @Override
    public Point2D getP2() {return end;}

    @Override
    public void setLine(double x1, double y1, double x2, double y2) {}

    @Override
    public Rectangle2D getBounds2D() {return null;}   
}
