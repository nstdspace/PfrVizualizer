package de.nstdspace.pfrvizualizer.gamestate;

import de.nstdspace.pfrvizualizer.GamePosition;
import de.nstdspace.pfrvizualizer.RangeResources;

import java.awt.*;

public class BeginGameState extends GameState {

    public BeginGameState(GamePosition heroPosition){
        super(heroPosition);
    }

    @Override
    public Image getRangeResource() {
        if(getHeroPosition() == GamePosition.BIG_BLIND){
            return RangeResources.getImage("empty_range");
        }
        String amount = getHeroPosition() == GamePosition.SMALL_BLIND ? "3bb" : "2bb";
        return RangeResources.getImage(getHeroPosition().getShortcut() + "-RFI" + amount);
    }
}
