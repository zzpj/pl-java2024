package pl.zzpj.solid.lsp.vehiclemovement.violation;

public class Car extends Vehicle {

    @Override
    public void changeGear(Gear gear) {
        if(Gear.R.equals(gear) && getGear().equals(Gear.D)){
            throw new RuntimeException("Can't change to REVERSE gear when " + getGear().toString() + " gear is engaged!");
        }
        super.changeGear(gear);
    }
}
