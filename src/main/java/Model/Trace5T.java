package Model;

public class Trace5T extends Trace {

    public Trace5T() {
        super();
    }

    public Trace5T(String orientation) {
        super(orientation);
    }

    @Override
    public Trace init(String orientation) {
        return new Trace5T(orientation);
    }

    /**
     * Find out if the added point allows you to draw a line in 5T mode
     *
     * @param traceInMaking corresponding to the line
     * @param point         corresponding to the new point
     * @return a boolean if the added point allows to draw a line or not
     */
    
	@Override
	public boolean eligible(Trace traceInMaking, Point point) {
        Trace5T traceCreated = (Trace5T) traceInMaking;
        if (this.getOrientation().equals(traceCreated.getOrientation())) {
            return this.getPoints().get(0).equals(point) || this.getPoints().get(this.getPoints().size() - 1).equals(point);
        }
		return true;
    }
}
