package pl.zzpj.solid.lsp.shape;

public class Circle implements Shape {
    private double radius;

    @Override
    public double getArea() {
        return this.radius * this.radius * 3.14;
    }

    @Override
    public double getCircumference() {
        return 2 * 3.14 * this.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
