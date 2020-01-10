package de.nstdspace.pfrvizualizer.gamestate;

import de.nstdspace.pfrvizualizer.GamePosition;
import de.nstdspace.pfrvizualizer.RangeResources;

import java.awt.*;

public class RfiGameState extends GameState {

    public RfiGameState(GamePosition heroPosition){
        super(heroPosition);
    }

    @Override
    public Image getRangeResource() {
        String amount = getHeroPosition() == GamePosition.SMALL_BLIND ? "3bb" : "2bb";
        return RangeResources.getImage(getHeroPosition().getShortcut() + "-RFI" + amount);
    }
}
