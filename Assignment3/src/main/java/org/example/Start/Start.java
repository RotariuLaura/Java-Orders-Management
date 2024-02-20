package org.example.Start;

import org.example.Presentation.View;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * In clasa Start se face pornirea aplicatiei de gestionare a comenzilor, prin deschiderea ferestrei principale a interfetei grafice.
 */
public class Start {
    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());
    public static void main(String[] args) {
        try {
            View view = new View();
            view.getStartFrame().setVisible(true);
            view.getStartFrame().setResizable(false);
        } catch (Exception e){
            LOGGER.log(Level.INFO, e.getMessage());
        }
    }
}
