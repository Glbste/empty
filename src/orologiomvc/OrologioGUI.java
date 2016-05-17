package orologiomvc;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.GregorianCalendar;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;

/**
 * Finestra principale dell'applicazione:
 * - lancia un thread che aggiorna il timer ad ogni secondo 
 *   (grazie al metodo run ereditato da Runnable);
 * - fa comparire un menu contestuale se l'utente clicca con il tasto destro sul quadrante
 *   (grazie al metodo mousePressed ereditato da MouseListener);
 * - passa dalla vista analogica a quella digitale e viceversa selezionando la voce corrispondente del menu contestuale
 *   (grazie al metodo ActionPerformed ereditato da ActionListener);
 * 
 * @author mauropamiro
 */
public class OrologioGUI extends JFrame implements Runnable, ActionListener, MouseListener {
    
    // Il model: rappresenta un orario, memorizzato come il numero di secondi dalle ore 00:00:00
    private final Time timer;
    
    // Le voci del menu a comparsa popupMenu per la scelta della View
    JRadioButtonMenuItem mnAnalogView=new JRadioButtonMenuItem("Analog view");
    JRadioButtonMenuItem mnDigitalView=new JRadioButtonMenuItem("Digital view");

    /**
     * Creates new form OrologioGUI
     */
    public OrologioGUI() {
        initComponents();
        // Creo il menu a comparsa per la scelta della view
        // (farò in modo che venga visualizzato cliccando sulla view con il tasto destro del mouse)
        creaPopupMenu();  
        // Creo model (Time) e view (DigitalView)
        timer=new Time();        
        createDigitalView();   
        // Faccio partire il conteggio del tempo trascorso 
        startClock();
    }
    
    private void createDigitalView() {
        // Creo la view e la aggiungo alla finestra
        DigitalView view=new DigitalView(timerPanel.getSize()); 
        timerPanel.add(view,BorderLayout.CENTER);
        // La finestra gestisce gli eventi generati dalla view
        view.addMouseListener(this);
        // La view si aggiorna automaticamente ad ogni modifica del model (timer)
        timer.addObserver(view);   
        // Ridisegno la finestra
        revalidate();
        repaint();
    }

    private void createAnalogView() {
        // Creo la view e la aggiungo alla finestra
        AnalogView view=new AnalogView(timerPanel.getSize());       
        timerPanel.add(view,BorderLayout.CENTER);
        // La finestra gestisce gli eventi generati dalla view
        view.addMouseListener(this);
        // La view si aggiorna automaticamente ad ogni modifica del model (timer)
        timer.addObserver(view);  
        // Ridisegno la finestra
        revalidate();
        repaint();     
    }
    
    private void startClock(){
        // Faccio partire un thread per il conteggio dei secondi trascorsi
        new Thread(this).start();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenu = new javax.swing.JPopupMenu();
        timerPanel = new javax.swing.JPanel();

        popupMenu.setName(""); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        timerPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        timerPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void creaPopupMenu() {        
        mnAnalogView.addActionListener(this);
        mnDigitalView.addActionListener(this);
        
        ButtonGroup mnGroup=new ButtonGroup();
        mnGroup.add(mnAnalogView);
        mnGroup.add(mnDigitalView);
        
        popupMenu.add(mnAnalogView);
        popupMenu.add(mnDigitalView);
        
        mnAnalogView.setSelected(false);
        mnDigitalView.setSelected(true);  
        
        mnDigitalView.setEnabled(false);
        mnAnalogView.setEnabled(true);
    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JPanel timerPanel;
    // End of variables declaration//GEN-END:variables

    /**
     * Implementazione del metodo di Runnable
     */
    @Override
    public void run() {
        // Aggiorna il model (timer) ad ogni secondo
        while(true){
            try {
                // Ottengo l'ora attuale
                GregorianCalendar now=new GregorianCalendar();
                int secondsOfDay=now.get(GregorianCalendar.SECOND)+now.get(GregorianCalendar.MINUTE)*60+now.get(GregorianCalendar.HOUR_OF_DAY)*60*60; 
                // Imposto l'ora attuale nel timer
                timer.set(secondsOfDay);
                // Mi sospendo per un secondo
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                return;
            }
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
     * Implementazione del metodo di MouseListener
     * @param e Evento generato dal mouse 
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Se è stato premuto il tasto destro sulla view, mostro il menu a comparsa per la scelta dell view
        if(SwingUtilities.isRightMouseButton(e)) popupMenu.show(this, e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * Implementazione del metodo di ActionListener
     * @param e Evento generato dal mouse
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // A seconda della voce di menu selezionata, mostro la view corrispondente
        if(e.getActionCommand().equals("Analog view")){
            // Elimino la view attualmente selezionata
            timer.deleteObservers();
            timerPanel.removeAll();
            // Creo e aggiungo alla finestra una AnalogView
            createAnalogView();
            // Aggiorno le voci del menu contestuale per la scelta della view
            mnAnalogView.setEnabled(false);
            mnDigitalView.setEnabled(true);
        }
        else if(e.getActionCommand().equals("Digital view")){
            // Elimino la view attualmente selezionata
            timer.deleteObservers();
            timerPanel.removeAll();
            // Creo e aggiungo alla finestra una DigitalView
            createDigitalView();
            // Aggiorno le voci del menu contestuale per la scelta della view
            mnDigitalView.setEnabled(false);
            mnAnalogView.setEnabled(true);
        }
    }
}
