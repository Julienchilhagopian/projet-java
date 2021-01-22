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
            //traceCreated.incrementNbCommonPart();
            // (traceCreated.getNbCommonPart() <= 2);
            return this.getPoints().get(0).equals(p) || this.getPoints().get(this.getPoints().size() - 1).equals(p);
        } else {
            return true;
        }
    }

    public Boolean isValid() {
        return this.getPoints().size() == 5;
        //&& this.nbCommonPart <= 2;
    }

    public int getNbCommonPart() {
        return nbCommonPart;
    }

    public void incrementNbCommonPart() {
        this.nbCommonPart++;
    }
}
