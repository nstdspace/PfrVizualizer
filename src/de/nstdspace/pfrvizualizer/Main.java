package de.nstdspace.pfrvizualizer;

import de.nstdspace.pfrvizualizer.gamestate.GameStateBuilder;
import de.nstdspace.pfrvizualizer.gui.Gui;

import javax.swing.*;

public class Main {

    public static final int NUMBER_OF_PLAYERS = 6;

    private GameStateBuilder currentBuilder;
    private Gui gui;
    private boolean isStateBuilding = false;

    public static boolean ondemandMode = false;

    public Main(){
        setNativeLookAndFeel();
        RangeResources.load();
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
            gui.setCurrentRangePreviewImage(currentBuilder.getRangeResource());
            gui.setInfo(currentBuilder.toShortString());
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

    public void raiseTo(GamePosition position, float raiseAmount) {
        GamePosition nextPosition = currentBuilder.getLastActedPosition().next();
        while(nextPosition != position){
            gui.setPlayerFolded(nextPosition);
            nextPosition = nextPosition.next();
        }
        currentBuilder.raiseTo(position, raiseAmount);
        gui.setPlayerRaisedTo(position, raiseAmount);
        gameStateChanged();
    }

    public static void main(String... args){
        for(String arg : args){
            if(arg.equals("odm")){
                ondemandMode = true;
            } else {
                throw new IllegalArgumentException("Argument \"" + arg + "\" is not supported. Currently supported arguments: \n - odm: load resources on demand rather than all on startup");
            }
        }
        new Main();
    }
}
