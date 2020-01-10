package de.nstdspace.pfrvizualizer.gui;

import de.nstdspace.pfrvizualizer.GamePosition;
import de.nstdspace.pfrvizualizer.gamestate.RfiGameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static de.nstdspace.pfrvizualizer.gui.Gui.*;

public class PlayerPanel extends JPanel {

    private static final Color FOLDED_BACKGROUND_COLOR = new Color(150, 150, 150);
    private static final Color ZERO_BET_FOREGROUND_COLOR = new Color(100, 230, 20);
    private static final Color NONZERO_BET_FOREGROUND = new Color(200, 50, 20);

    private GamePosition gamePosition;

    private JPanel defaultPanel;
    private JLabel label;
    private float bet = 0;
    private JLabel betLabel;

    private JPanel rfiSelectPanel;

    private Gui gui;

    public PlayerPanel(Gui gui){
        JPanel cardLayoutParent = this;
        setLayout(new CardLayout());

        this.gui = gui;
        setBackground(PLAYER_PANEL_BACKGROUND);

        defaultPanel = new JPanel();
        defaultPanel.setBackground(PLAYER_PANEL_BACKGROUND);
        defaultPanel.setLayout(new BoxLayout(defaultPanel, BoxLayout.Y_AXIS));
        label = new JLabel("?");
        label.setForeground(DEFAULT_FOREGROUND_COLOR);
        label.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        defaultPanel.add(label);
        betLabel = new JLabel(bet + " BB");
        betLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        betLabel.setForeground(ZERO_BET_FOREGROUND_COLOR);
        defaultPanel.add(betLabel);
        add(defaultPanel);

        buildRfiSelectPanel();
        add(rfiSelectPanel);

        ((CardLayout) getLayout()).first(this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if(!gui.buttonPlayerSelected){
                    label.setIcon(POKER_BUTTON_IMAGE_ICON);
                    label.setText("");
                }
                if(gui.getMainInstance().isStateBuilding()){
                    if(gui.getMainInstance().getCurrentBuilder().getCurrentGameState() instanceof RfiGameState){
                        ((CardLayout) getLayout()).last(cardLayoutParent);
                    }
                }
            }
        });
    }

    public void simulatedMouseExitCleanup(){
        label.setIcon(null);
        label.setText(gamePosition.getShortcut());
        ((CardLayout) getLayout()).first(this);
    }

    private void buildRfiSelectPanel() {
        Insets ZERO_INSETS = new Insets(0, 0, 0, 0);
        rfiSelectPanel = new JPanel(new BorderLayout());
        JLabel limp = createRfiSelectLabel("limp");
        JLabel open2 = createRfiSelectLabel("2");
        JLabel open2p5 = createRfiSelectLabel("2.5");
        JLabel open3 = createRfiSelectLabel("3");
        JLabel open4 = createRfiSelectLabel("4");
        rfiSelectPanel.add(limp, BorderLayout.NORTH);
        JPanel quadSelect = new JPanel(new GridLayout(2, 2, 0, 0));
        quadSelect.add(open2);
        quadSelect.add(open2p5);
        quadSelect.add(open3);
        quadSelect.add(open4);
        rfiSelectPanel.add(quadSelect, BorderLayout.CENTER);
    }

    private JLabel createRfiSelectLabel(String text){
        JLabel label = new JLabel(text);
        label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(DARKER_BACKGROUND_COLOR));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                String raiseAmountString = ((JLabel) e.getSource()).getText();
                float raiseAmount = raiseAmountString.equals("limp") ? 1 : Float.valueOf(raiseAmountString);
                gui.getMainInstance().raise(getGamePosition(), raiseAmount);
            }
        });
        return label;
    }

    public void playerFolded(){
        setBackground(FOLDED_BACKGROUND_COLOR);
    }

    public void setBet(float bet){
        this.bet = bet;
        betLabel.setForeground(NONZERO_BET_FOREGROUND);
        betLabel.setText(bet + " BB");
        if(bet == 0){
            betLabel.setForeground(ZERO_BET_FOREGROUND_COLOR);
        }
    }

    public float getBet() {
        return bet;
    }

    public void setGamePosition(GamePosition gamePosition) {
        this.gamePosition = gamePosition;
        label.setIcon(null);
        this.label.setText(gamePosition.getShortcut());
    }

    public GamePosition getGamePosition() {
        return gamePosition;
    }
}
