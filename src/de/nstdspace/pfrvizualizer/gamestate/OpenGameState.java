package de.nstdspace.pfrvizualizer.gamestate;

import de.nstdspace.pfrvizualizer.GamePosition;
import de.nstdspace.pfrvizualizer.RangeResources;

import java.awt.*;

public class OpenGameState extends GameState {

    private GamePosition rfiPosition;
    private float openAmount;

    public OpenGameState(GamePosition heroPosition, GamePosition rfiPosition, float openAmount){
        super(heroPosition, rfiPosition);
        this.openAmount = openAmount;
        this.rfiPosition = rfiPosition;
    }

    public GamePosition getRfiPosition() {
        return rfiPosition;
    }

    @Override
    public Image getRangeResource() {
        String normalizedOpenAmount = openAmount == 2.5f ? String.valueOf(openAmount) : String.valueOf((int) openAmount);
        String ending = normalizedOpenAmount.equals("1") ? "OL" : normalizedOpenAmount + "BB-RFI";
        String fileName = getHeroPosition().getShortcut() + "-vs-" + getRfiPosition().getShortcut() + "-" + ending;
        System.out.println(fileName);
        return RangeResources.namesToImages.get(fileName);
    }
}
