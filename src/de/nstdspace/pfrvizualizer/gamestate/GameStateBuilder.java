package de.nstdspace.pfrvizualizer.gamestate;

import de.nstdspace.pfrvizualizer.GamePosition;
import de.nstdspace.pfrvizualizer.gamestate.EmptyGameState;
import de.nstdspace.pfrvizualizer.gamestate.GameState;
import de.nstdspace.pfrvizualizer.gamestate.RfiGameState;

import java.awt.*;

import static de.nstdspace.pfrvizualizer.RangeResources.*;

public class GameStateBuilder {

    private GameState currentGameState;
    private GamePosition lastActedPosition;

    public GameStateBuilder(GamePosition heroPosition){
        if(heroPosition != GamePosition.BIG_BLIND){
            currentGameState = new RfiGameState(heroPosition);
        } else {
            currentGameState = new EmptyGameState(heroPosition);
        }
        lastActedPosition = GamePosition.BIG_BLIND;
    }

    public void raise(GamePosition position, float raiseAmount) {
        if(currentGameState instanceof RfiGameState){
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
