package fr.diginamic.echolink.domain.meteo;

public enum WindDirection {
    NORTH,
    SOUTH,
    EAST,
    WEST,
    NORTH_EAST,
    SOUTH_EAST,
    NORTH_WEST,
    SOUTH_WEST;

    public static WindDirection fromDegrees(int deg) {

        if (deg >= 337 || deg < 23) return WindDirection.NORTH;
        if (deg < 68) return WindDirection.NORTH_EAST;
        if (deg < 113) return WindDirection.EAST;
        if (deg < 158) return WindDirection.SOUTH_EAST;
        if (deg < 203) return WindDirection.SOUTH;
        if (deg < 248) return WindDirection.SOUTH_WEST;
        if (deg < 293) return WindDirection.WEST;
        return WindDirection.NORTH_WEST;
    }
}
