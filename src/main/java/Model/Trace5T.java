package Model;

public class Trace5T extends Trace {
    private int nbCommonPart;

    public Trace5T() {
        super();
        this.nbCommonPart = 0;
    }
    public Trace5T(String orientation) {
        super(orientation);
    }


    @Override
    public Trace init(String orientation) {
        return new Trace5T(orientation);
    }

    public boolean eligible(Trace traceInMaking, Point p) {
        Trace5T traceCreated = (Trace5T) traceInMaking;
        if(this.getOrientation().equals(traceCreated.getOrientation())) {
            if(!this.getPoints().get(2).equals(p)) {
                traceCreated.incrementNbCommonPart();
                return (traceCreated.getNbCommonPart() <= 1);
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public Boolean isValid() {
        return this.getPoints().size() == 5 && this.nbCommonPart <= 1;
    }

    public int getNbCommonPart() {
        return nbCommonPart;
    }

    public void incrementNbCommonPart() {
        this.nbCommonPart++;
    }
}
