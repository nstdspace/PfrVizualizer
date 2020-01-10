package de.nstdspace.pfrvizualizer.gui;

import de.nstdspace.pfrvizualizer.GamePosition;

import javax.swing.*;

import static de.nstdspace.pfrvizualizer.gui.Gui.DEFAULT_FOREGROUND_COLOR;
import static de.nstdspace.pfrvizualizer.gui.Gui.PLAYER_PANEL_BACKGROUND;

public class PlayerPanel extends JPanel {

    private GamePosition gamePosition;
    private JLabel label;

    public PlayerPanel(){
        setBackground(PLAYER_PANEL_BACKGROUND);
        label = new JLabel("?");
        label.setForeground(DEFAULT_FOREGROUND_COLOR);
        add(label);
    }

    public void setGamePosition(GamePosition gamePosition) {
        this.gamePosition = gamePosition;
        this.label.setText(gamePosition.getShortcut());
    }

    public GamePosition getGamePosition() {
        return gamePosition;
    }
}
