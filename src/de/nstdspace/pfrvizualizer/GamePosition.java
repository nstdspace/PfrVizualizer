package de.nstdspace.pfrvizualizer;

public enum GamePosition {
    BUTTON("BU"),
    SMALL_BLAND("SB"),
    BIG_BLIND("BB"),
    LOWJACK("LJ"),
    HIGHJACK("HJ"),
    CUTOFF("CO");

    private String shortcut;

    GamePosition(String shortcut){
        this.shortcut = shortcut;
    }

    public String getShortcut() {
        return shortcut;
    }
}
