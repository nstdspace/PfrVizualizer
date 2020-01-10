package de.nstdspace.pfrvizualizer.gamestate;

import de.nstdspace.pfrvizualizer.GamePosition;

public class EmptyGameState extends GameState {

    public EmptyGameState(GamePosition heroPosition) {
        super(heroPosition, null);
    }

    @Override
    public GameStateBuilder.GameStateDescriptor getDescriptor() {
        return GameStateBuilder.GameStateDescriptor.EMPTY;
    }
}
