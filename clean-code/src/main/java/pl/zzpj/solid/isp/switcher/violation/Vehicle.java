package pl.zzpj.solid.isp.switcher.violation;

public abstract class Vehicle implements Switches {

    private boolean engineRunning;

    public boolean isEngineRunning() {
        return engineRunning;
    }

    @Override
    public void startEngine() {
        engineRunning = true;
    }

    @Override
    public void shutDownEngine() {
        engineRunning = false;
    }

}