package de.nstdspace.pfrvizualizer.gamestate;

import de.nstdspace.pfrvizualizer.GamePosition;

import java.awt.*;

public class GameStateBuilder {

    private GameState currentGameState;
    private GamePosition lastActedPosition;

    public GameStateBuilder(GamePosition heroPosition){
        currentGameState = new BeginGameState(heroPosition);
        lastActedPosition = GamePosition.BIG_BLIND;
    }

    public void raiseTo(GamePosition position, float raiseAmount) {
        if(currentGameState instanceof BeginGameState){
            currentGameState = new OpenGameState(currentGameState.getHeroPosition(), position, raiseAmount);
        }
        lastActedPosition = position;
    }

    public GamePosition getLastActedPosition() {
        return lastActedPosition;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public Image getRangeResource() {
        return currentGameState.getRangeResource();
    }

    public String toShortString() {
        return "short string " + currentGameState.toString();
    }
}
