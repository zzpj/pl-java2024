package pl.zzpj.solid.srp.vehicle.solution;

public class FuelPump implements Refualable {

    @Override
    public void reFuel(final Vehicle vehicle){
        final int remainingFuel = vehicle.getRemainingFuel();
        final int additionalFuel = vehicle.getMaxFuel() - remainingFuel;
        vehicle.setRemainingFuel(remainingFuel + additionalFuel);
    }
}
