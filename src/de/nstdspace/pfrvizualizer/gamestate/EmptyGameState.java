package de.nstdspace.pfrvizualizer.gamestate;

import de.nstdspace.pfrvizualizer.GamePosition;
import de.nstdspace.pfrvizualizer.RangeResources;

import java.awt.*;

public class EmptyGameState extends GameState {

    public EmptyGameState(GamePosition heroPosition) {
        super(heroPosition, null);
    }

    @Override
    public Image getRangeResource() {
        return RangeResources.EMPTY_RANGE;
    }
}
