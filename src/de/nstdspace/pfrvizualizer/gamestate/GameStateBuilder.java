package de.nstdspace.pfrvizualizer.gamestate;

import de.nstdspace.pfrvizualizer.GamePosition;
import de.nstdspace.pfrvizualizer.gamestate.EmptyGameState;
import de.nstdspace.pfrvizualizer.gamestate.GameState;
import de.nstdspace.pfrvizualizer.gamestate.RfiGameState;

import java.awt.*;

import static de.nstdspace.pfrvizualizer.RangeResources.*;

public class GameStateBuilder {

    private GameState currentGameState;

    public GameStateBuilder(GamePosition heroPosition){
        if(heroPosition != GamePosition.BIG_BLIND){
            currentGameState = new RfiGameState(heroPosition);
        } else {
            currentGameState = new EmptyGameState(heroPosition);
        }
    }

    public void raise(GamePosition position, float raiseAmount) {

    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public GameStateDescriptor getCurrentDescriptor(){
        return currentGameState.getDescriptor();
    }

    public enum GameStateDescriptor {
        BU_RFI(BU_RFI_RANGE),
        SB_RFI(SB_RFI_RANGE),
        LJ_RFI(LJ_RFI_RANGE),
        HJ_RFI(HJ_RFI_RANGE),
        CO_RFI(CO_RFI_RANGE),
        EMPTY(EMPTY_RANGE);

        private Image rangeResourceName;

        GameStateDescriptor(Image rangeImageResource){
               this.rangeResourceName = rangeImageResource;
        }

        public Image getRangeResource() {
            return rangeResourceName;
        }
    }
}
