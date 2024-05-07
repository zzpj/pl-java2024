package pl.zzpj.solid.dip.driver.violation;

public class LimousineCar {

    private final int maxFuel;
    private int remainingFuel;
    private int power;

    public LimousineCar(final int maxFuel) {
        this.maxFuel = maxFuel;
        remainingFuel = maxFuel;
    }

    public void accelerate(){
        power++;
        remainingFuel--;
    }

}