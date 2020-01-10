package de.nstdspace.pfrvizualizer.gamestate;

import de.nstdspace.pfrvizualizer.GamePosition;

public abstract class GameState {

    private GamePosition involvedPositions[];
    private GamePosition heroPosition;

    public GameState(GamePosition heroPosition, GamePosition ... involvedPositions){
        this.heroPosition = heroPosition;
        this.involvedPositions = involvedPositions;
    }

    public GamePosition getHeroPosition() {
        return heroPosition;
    }

    public GamePosition[] getInvolvedPositions() {
        return involvedPositions;
    }

    public abstract GameStateBuilder.GameStateDescriptor getDescriptor();
}
