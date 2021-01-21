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

    public boolean NotEligible(String orientation, Point p) {
        if(this.getOrientation().equals(orientation)) {
            return this.getPoints().get(2).equals(p);
        } else {
            return false;
        }
    }


}
