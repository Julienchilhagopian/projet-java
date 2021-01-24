package Controller.Enum;


public enum Directions {
    VERTICAL ("Vertical"),
    HORIZONTAL ("Horizontal"),
    DIAGONALRIGHT ("DiagonalRight"),
    DIAGONALLEFT ("DiagonalLeft");

    private final String name;

    Directions(String s) {
        name = s;
    }

    @Override
	public String toString() {
        return this.name;
    }
}
