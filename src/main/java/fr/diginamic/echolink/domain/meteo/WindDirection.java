package fr.diginamic.echolink.domain.meteo;

public enum WindDirection {
    North,
    South,
    East,
    West,
    NorthEast,
    SouthEast,
    NorthWest,
    SouthWest;

    public static WindDirection fromDegrees(int deg) {

        if (deg >= 337 || deg < 23) return WindDirection.North;
        if (deg < 68) return WindDirection.NorthEast;
        if (deg < 113) return WindDirection.East;
        if (deg < 158) return WindDirection.SouthEast;
        if (deg < 203) return WindDirection.South;
        if (deg < 248) return WindDirection.SouthWest;
        if (deg < 293) return WindDirection.West;
        return WindDirection.NorthWest;
    }
}
