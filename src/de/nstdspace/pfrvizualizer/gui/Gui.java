package de.nstdspace.pfrvizualizer.gui;

import de.nstdspace.pfrvizualizer.GamePosition;

import javax.swing.*;
import java.awt.*;

import static de.nstdspace.pfrvizualizer.Main.NUMBER_OF_PLAYERS;

public class Gui {

    protected static final Color DEFAULT_FOREGROUND_COLOR = new Color(160, 160, 160);
    protected static final Color BACKGROUND_COLOR = new Color(60, 60, 60);
    protected static final Color PLAYER_PANEL_BACKGROUND = new Color(40, 40, 40);

    protected static final int PANEL_WIDTH = 600;
    protected static final int PANEL_HEIGHT = 400;
    protected static final int PLAYER_PANEL_SIZE = 50;

    protected static final int DEFAULT_PADDING = PANEL_WIDTH / 20;

    private PlayerPanel[] playerPanels;

    public Gui(){
        JFrame frame = new JFrame();

        JPanel tablePanel = new JPanel(null);
        tablePanel.setBackground(BACKGROUND_COLOR);
        tablePanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        frame.add(tablePanel);

        buildPlayerPanels(tablePanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void buildPlayerPanels(JPanel tablePanel) {
        playerPanels = new PlayerPanel[NUMBER_OF_PLAYERS];
        int[] playerPanelPositions = new int[]{
                PANEL_WIDTH / 2 - PLAYER_PANEL_SIZE / 2, PANEL_HEIGHT - PLAYER_PANEL_SIZE - DEFAULT_PADDING,
                DEFAULT_PADDING, 2 * PANEL_HEIGHT / 3 - PLAYER_PANEL_SIZE,
                DEFAULT_PADDING, PANEL_HEIGHT / 3 - PLAYER_PANEL_SIZE,
                PANEL_WIDTH / 2 - PLAYER_PANEL_SIZE / 2, DEFAULT_PADDING,
                PANEL_WIDTH - PLAYER_PANEL_SIZE - DEFAULT_PADDING, 2 * PANEL_HEIGHT / 3 - PLAYER_PANEL_SIZE,
                PANEL_WIDTH - PLAYER_PANEL_SIZE - DEFAULT_PADDING, PANEL_HEIGHT / 3 - PLAYER_PANEL_SIZE
        };
        for(int i = 0; i < NUMBER_OF_PLAYERS; i++){
            PlayerPanel panel = new PlayerPanel();
            panel.setBounds(playerPanelPositions[i * 2], playerPanelPositions[i * 2 + 1], PLAYER_PANEL_SIZE, PLAYER_PANEL_SIZE);
            panel.setGamePosition(GamePosition.values()[i]);
            tablePanel.add(panel);
        }
    }
}
