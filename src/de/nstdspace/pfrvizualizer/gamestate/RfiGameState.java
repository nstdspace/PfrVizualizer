package de.nstdspace.pfrvizualizer.gamestate;

import de.nstdspace.pfrvizualizer.GamePosition;

public class RfiGameState extends GameState {

    public RfiGameState(GamePosition heroPosition){
        super(heroPosition);
    }


    @Override
    public GameStateBuilder.GameStateDescriptor getDescriptor() {
        return GameStateBuilder.GameStateDescriptor.valueOf(getHeroPosition().getShortcut() + "_RFI");
    }
}
