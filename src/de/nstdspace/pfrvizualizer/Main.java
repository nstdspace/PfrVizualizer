package de.nstdspace.pfrvizualizer;

import de.nstdspace.pfrvizualizer.gamestate.GameStateBuilder;
import de.nstdspace.pfrvizualizer.gui.Gui;
import sun.swing.SwingUtilities2;

import javax.swing.*;

public class Main {

    public static final int NUMBER_OF_PLAYERS = 6;

    private GameStateBuilder currentBuilder;
    private Gui gui;
    private boolean isStateBuilding = false;

    public Main(){
        setNativeLookAndFeel();
        gui = new Gui(this);
    }

    private void setNativeLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args){
        new Main();
    }

    public void beginGameStateBuilding(GamePosition heroPosition) {
        currentBuilder = new GameStateBuilder(heroPosition);
        isStateBuilding = true;
        gameStateChanged();
    }

    public boolean isStateBuilding(){
        return isStateBuilding;
    }

    public GameStateBuilder getCurrentBuilder(){
        return currentBuilder;
    }

    public void gameStateChanged(){
        if(currentBuilder != null){
            gui.setCurrentRangePreviewImage(currentBuilder.getCurrentDescriptor().getRangeResource());
            gui.setInfo(currentBuilder.getCurrentDescriptor().toString());
        } else {
            gui.setCurrentRangePreviewImage(null);
            gui.setInfo("<empty info>");
            gui.buttonPlayerSelected = false;
        }
    }

    public void resetStateBuilder() {
        isStateBuilding = false;
        currentBuilder = null;
        gui.removeSelectedButtonPlayer();
        gameStateChanged();
    }

    public void raiseFirstIn(GamePosition position, float raiseAmount) {
        currentBuilder.raise(position, raiseAmount);
        gui.setPlayerRaised(position, raiseAmount);
    }
}
