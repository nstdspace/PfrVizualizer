package de.nstdspace.pfrvizualizer.gui;

import de.nstdspace.pfrvizualizer.GamePosition;
import de.nstdspace.pfrvizualizer.Main;
import de.nstdspace.pfrvizualizer.RangeResources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

import static de.nstdspace.pfrvizualizer.Main.NUMBER_OF_PLAYERS;

public class Gui {

    protected static final Color DEFAULT_FOREGROUND_COLOR = new Color(160, 160, 160);
    protected static final Color BACKGROUND_COLOR = new Color(60, 60, 60);
    protected static final Color LIGHT_BACKGROUND_COLOR = new Color(90, 90, 90);
    protected static final Color DARKER_BACKGROUND_COLOR = new Color(50, 50, 50);
    protected static final Color PLAYER_PANEL_BACKGROUND = new Color(40, 40, 40);

    protected static final int PANEL_WIDTH = 500;
    protected static final int PANEL_HEIGHT = 300;
    protected static final int PLAYER_PANEL_SIZE = PANEL_WIDTH / 8;
    public static final int RANGE_PREVIEW_SIZE = PANEL_WIDTH;

    protected static final int DEFAULT_PADDING = PANEL_WIDTH / 20;

    protected static Image POKER_BUTTON_IMAGE;
    protected static ImageIcon POKER_BUTTON_IMAGE_ICON;

    private JLabel infoLabel;
    private JPanel rangePreviewPanel;
    private JFrame frame;

    private PlayerPanel[] playerPanels;

    private Main mainInstance;

    private Image currentRangePreviewImage;

    private int buttonPlayerIndex = -1;
    private PlayerPanel buttonPlayerPanel = null;
    public boolean buttonPlayerSelected = false;

    public Gui(Main mainInstance){
        this.mainInstance = mainInstance;
        loadResources();

        frame = new JFrame();

        JPanel tablePanel = new JPanel(null);
        tablePanel.setBackground(BACKGROUND_COLOR);
        tablePanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        tablePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                for(PlayerPanel panel : playerPanels){
                    //TODO: not nice. Use glass pane instead
                    panel.simulatedMouseExitCleanup();
                }
            }
        });
        buildPlayerPanels(tablePanel);

        infoLabel = new JLabel("<empty info>");
        infoLabel.setForeground(DEFAULT_FOREGROUND_COLOR);
        infoLabel.setBounds(PANEL_WIDTH / 2 - 50, PANEL_HEIGHT / 2 - 20, 100, 20);
        tablePanel.add(infoLabel);

        JButton resetButton = new JButton("reset");
        resetButton.setBounds(0, 0, (int) resetButton.getPreferredSize().getWidth(), (int) resetButton.getPreferredSize().getHeight());
        resetButton.addActionListener((e) -> {
            mainInstance.resetStateBuilder();
        });
        tablePanel.add(resetButton);

        frame.add(tablePanel, BorderLayout.CENTER);

        buildRangePreviewPanel();
        frame.add(rangePreviewPanel, BorderLayout.SOUTH);

        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setPlayerRaised(GamePosition position, float raiseAmount) {
        PlayerPanel panel = getPlayerPanelAt(position);
        panel.setBet(panel.getBet() + raiseAmount);
    }

    public PlayerPanel getPlayerPanelAt(GamePosition position){
        return playerPanels[(buttonPlayerIndex + position.ordinal()) % NUMBER_OF_PLAYERS];
    }

    public void removeSelectedButtonPlayer(){
        buttonPlayerSelected = false;
        buttonPlayerPanel = null;
        buttonPlayerIndex = -1;
        for(PlayerPanel panel : playerPanels){
            panel.setBet(0);
            panel.setGamePosition(GamePosition.UNKNOWN);
        }
    }

    private void buildRangePreviewPanel() {
        rangePreviewPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if(currentRangePreviewImage != null){
                    g.drawImage(currentRangePreviewImage, 0, 0, PANEL_WIDTH, PANEL_WIDTH, null);
                }
            }
        };
        JPanel rangePreviewLabelPanel = new JPanel();
        JLabel rangePreviewLabel = new JLabel("");
        rangePreviewLabel.setForeground(DEFAULT_FOREGROUND_COLOR);
        rangePreviewLabelPanel.add(rangePreviewLabel);
        rangePreviewLabelPanel.setBackground(LIGHT_BACKGROUND_COLOR);
        rangePreviewLabelPanel.setVisible(false);
        rangePreviewPanel.add(rangePreviewLabelPanel);
        rangePreviewPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                rangePreviewLabel.setText(RangeResources.imageNames.get(currentRangePreviewImage));
                if(rangePreviewLabel.getText() != null){
                    rangePreviewLabelPanel.setVisible(true);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                rangePreviewLabelPanel.setVisible(false);
            }
        });
        rangePreviewPanel.setPreferredSize(new Dimension(RANGE_PREVIEW_SIZE, RANGE_PREVIEW_SIZE));
        rangePreviewPanel.setBackground(DARKER_BACKGROUND_COLOR);
    }

    public void setInfo(String info){
        infoLabel.setText(info);
    }

    public void setCurrentRangePreviewImage(Image currentRangePreviewImage) {
        this.currentRangePreviewImage = currentRangePreviewImage;
        rangePreviewPanel.repaint();
        frame.pack();
    }


    private void loadResources() {
        try {
            POKER_BUTTON_IMAGE = ImageIO.read(Main.class.getResourceAsStream("/dealer-button-poker-stars.png"));
            POKER_BUTTON_IMAGE = POKER_BUTTON_IMAGE.getScaledInstance(3 * PLAYER_PANEL_SIZE / 4, 3 * PLAYER_PANEL_SIZE / 4, Image.SCALE_SMOOTH);
            POKER_BUTTON_IMAGE_ICON = new ImageIcon(POKER_BUTTON_IMAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildPlayerPanels(JPanel tablePanel) {
        playerPanels = new PlayerPanel[NUMBER_OF_PLAYERS];
        int[] playerPanelPositions = new int[]{
                PANEL_WIDTH / 2 - PLAYER_PANEL_SIZE / 2, PANEL_HEIGHT - PLAYER_PANEL_SIZE - DEFAULT_PADDING,
                DEFAULT_PADDING, 2 * PANEL_HEIGHT / 3 - PLAYER_PANEL_SIZE / 2,
                DEFAULT_PADDING, PANEL_HEIGHT / 3 - PLAYER_PANEL_SIZE / 2,
                PANEL_WIDTH / 2 - PLAYER_PANEL_SIZE / 2, DEFAULT_PADDING,
                PANEL_WIDTH - PLAYER_PANEL_SIZE - DEFAULT_PADDING, PANEL_HEIGHT / 3 - PLAYER_PANEL_SIZE / 2,
                PANEL_WIDTH - PLAYER_PANEL_SIZE - DEFAULT_PADDING, 2 * PANEL_HEIGHT / 3 - PLAYER_PANEL_SIZE / 2
        };
        for(int i = 0; i < NUMBER_OF_PLAYERS; i++){
            int index = i;
            PlayerPanel panel = new PlayerPanel(this);
            panel.setBounds(playerPanelPositions[i * 2], playerPanelPositions[i * 2 + 1], PLAYER_PANEL_SIZE, PLAYER_PANEL_SIZE);
            panel.setGamePosition(GamePosition.UNKNOWN);
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if(buttonPlayerPanel == null){
                        buttonPlayerPanel = panel;
                        buttonPlayerIndex = index;
                        for(int j = 0; j < NUMBER_OF_PLAYERS; j++){
                            int currentIndex = (index + j) % NUMBER_OF_PLAYERS;
                            playerPanels[currentIndex].setGamePosition(GamePosition.values()[j]);
                            buttonPlayerSelected = true;
                        }
                        playerPanels[(index + 1) % NUMBER_OF_PLAYERS].setBet(0.5f);
                        playerPanels[(index + 2) % NUMBER_OF_PLAYERS].setBet(1f);
                    }
                    if(!mainInstance.isStateBuilding()){
                        mainInstance.beginGameStateBuilding(getHeroGamePosition());
                    }
                }
            });
            playerPanels[i] = panel;
            tablePanel.add(panel);
        }
    }

    public GamePosition getHeroGamePosition(){
        return playerPanels[0].getGamePosition();
    }

    public Main getMainInstance() {
        return mainInstance;
    }
}
