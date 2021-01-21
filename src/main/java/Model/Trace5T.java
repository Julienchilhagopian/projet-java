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
        return new Trace5T();
    }



}
