package pl.zzpj.solid.ocp.vehicle.solution;

public class EventHandler {

    private Vehicle vehicle;

    public EventHandler(final Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void changeDrivingMode(final DrivingMode drivingMode){
        vehicle.setPower(drivingMode.getPower());
        vehicle.setSuspensionHeight(drivingMode.getSuspensionHeight());
        // now, when we need to add another mode (e.g. ECONOMY) just create a new class: Economy.
    }
}
