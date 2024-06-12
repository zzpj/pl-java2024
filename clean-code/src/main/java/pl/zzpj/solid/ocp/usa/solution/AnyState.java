package pl.zzpj.solid.ocp.usa.solution;

public class AnyState implements SpeedLimitFine {
    private static final int STATE_MAX_SPEED = 0;

    @Override
    public double calculateFine(int velocity) {
        double fine = 0.0;
        if (velocity > STATE_MAX_SPEED) {
            //calculate...
            return fine;
        }
        return 0.0;
    }
}
