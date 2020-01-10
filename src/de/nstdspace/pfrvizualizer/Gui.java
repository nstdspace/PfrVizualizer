package de.nstdspace.pfrvizualizer;

import javax.swing.*;
import java.awt.*;

public class Gui {

    private static final Color BACKGROUND_COLOR = new Color(60, 60, 60);

    public Gui(){
        JFrame frame = new JFrame();

        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(BACKGROUND_COLOR);
        tablePanel.setPreferredSize(new Dimension());
        frame.add(tablePanel);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
