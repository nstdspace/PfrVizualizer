package de.nstdspace.pfrvizualizer.gui;

import de.nstdspace.pfrvizualizer.GamePosition;
import de.nstdspace.pfrvizualizer.gamestate.EmptyGameState;
import de.nstdspace.pfrvizualizer.gamestate.GameState;
import de.nstdspace.pfrvizualizer.gamestate.BeginGameState;
import de.nstdspace.pfrvizualizer.gamestate.OpenGameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static de.nstdspace.pfrvizualizer.gui.Gui.*;

public class PlayerPanel extends JPanel {

    private static final Color FOLDED_BACKGROUND_COLOR = new Color(150, 150, 150);
    private static final Color ZERO_BET_FOREGROUND_COLOR = new Color(100, 230, 20);
    private static final Color NONZERO_BET_FOREGROUND = new Color(200, 50, 20);
    private static final Color THREE_BET_FOREGROUND = new Color(255, 100, 0);
    private static final Color FOUR_BET_FOREGROUND = new Color(150, 0, 255);

    private GamePosition gamePosition;

    private JPanel defaultPanel;
    private JLabel label;
    private float bet = 0;
    private JLabel betLabel;

    private static final String DEFAULT_CARD = "default_card";
    private static final String RFI_CARD = "rfi_card";
    private static final String THREE_BET_CARD = "three_bet_card";

    private JPanel rfiSelectPanel;
    private JPanel threeBetPanel;

    private Gui gui;

    private boolean isPlayerFolded;

    public PlayerPanel(Gui gui){
        JPanel cardLayoutParent = this;
        setLayout(new CardLayout());

        this.gui = gui;

        defaultPanel = new JPanel();

        defaultPanel.setLayout(new BoxLayout(defaultPanel, BoxLayout.Y_AXIS));
        label = new JLabel("?");
        label.setForeground(DEFAULT_FOREGROUND_COLOR);
        label.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        defaultPanel.add(label);
        betLabel = new JLabel(bet + " BB");
        betLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        betLabel.setForeground(ZERO_BET_FOREGROUND_COLOR);
        defaultPanel.add(betLabel);
        add(defaultPanel, DEFAULT_CARD);

        buildRfiSelectPanel();
        add(rfiSelectPanel, RFI_CARD);

        buildThreeBetPanel();
        add(threeBetPanel, THREE_BET_CARD);

        ((CardLayout) getLayout()).first(this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if(isPlayerFolded){
                    return;
                }
                if(!gui.buttonPlayerSelected){
                    label.setIcon(POKER_BUTTON_IMAGE_ICON);
                    label.setText("");
                }
                if(gui.getMainInstance().isStateBuilding()){
                    if(getGamePosition() == gui.getMainInstance().getCurrentBuilder().getLastActedPosition()){
                        return;
                    }
                    GameState currentGameState = gui.getMainInstance().getCurrentBuilder().getCurrentGameState();
                    if(currentGameState instanceof BeginGameState){
                        ((CardLayout) getLayout()).show(cardLayoutParent, RFI_CARD);
                    } else if(currentGameState instanceof OpenGameState){
                        ((CardLayout) getLayout()).show(cardLayoutParent, THREE_BET_CARD);
                    }
                }
            }
        });

        setPlayerInGame();
    }

    private void buildThreeBetPanel() {
        threeBetPanel = new JPanel();
        threeBetPanel.setBackground(PLAYER_PANEL_BACKGROUND);

    }

    public void simulatedMouseExitCleanup(){
        label.setIcon(null);
        label.setText(gamePosition.getShortcut());
        ((CardLayout) getLayout()).show(this, DEFAULT_CARD);
    }

    private void buildRfiSelectPanel() {
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
                float raiseAmount = raiseAmountString.equals("limp") ? 1 : Float.parseFloat(raiseAmountString);
                gui.getMainInstance().raiseTo(getGamePosition(), raiseAmount);
            }
        });
        return label;
    }

    public void setPlayerFolded(){
        setBackground(FOLDED_BACKGROUND_COLOR);
        defaultPanel.setBackground(FOLDED_BACKGROUND_COLOR);
        isPlayerFolded = true;
        repaint();
    }

    public void setPlayerInGame(){
        setBackground(PLAYER_PANEL_BACKGROUND);
        defaultPanel.setBackground(PLAYER_PANEL_BACKGROUND);
        isPlayerFolded = false;
        repaint();
    }

    public boolean isPlayerFolded(){
        return isPlayerFolded;
    }

    public void setBet(float bet){
        this.bet = bet;
        betLabel.setForeground(NONZERO_BET_FOREGROUND);
        betLabel.setText(bet + " BB");
        if(bet == 0){
            betLabel.setForeground(ZERO_BET_FOREGROUND_COLOR);
        }
    }

    public void setThreeBet(){
        betLabel.setForeground(THREE_BET_FOREGROUND);
        betLabel.setText("3-bet");
    }

    public void setFourBet(){

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
