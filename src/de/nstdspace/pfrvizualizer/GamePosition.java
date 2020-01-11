package de.nstdspace.pfrvizualizer;

public enum GamePosition {
    BUTTON("BU"),
    SMALL_BLIND("SB"),
    BIG_BLIND("BB"),
    LOWJACK("LJ"),
    HIGHJACK("HJ"),
    CUTOFF("CO"),
    UNKNOWN("?");

    private String shortcut;

    GamePosition(String shortcut){
        this.shortcut = shortcut;
    }

    public String getShortcut() {
        return shortcut;
    }

    public GamePosition next() {
        switch(this){
            case BUTTON: return SMALL_BLIND;
            case SMALL_BLIND: return BIG_BLIND;
            case BIG_BLIND: return LOWJACK;
            case LOWJACK: return HIGHJACK;
            case HIGHJACK: return CUTOFF;
            case CUTOFF: return BUTTON;
            default: return UNKNOWN;
        }
    }
}
