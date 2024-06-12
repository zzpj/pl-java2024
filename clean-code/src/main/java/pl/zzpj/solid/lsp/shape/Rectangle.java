package pl.zzpj.solid.lsp.shape;

public class Rectangle implements Shape {
    private double width;
    private double height;

    @Override
    public double getArea() {
        return this.width * this.height;
    }

    @Override
    public double getCircumference() {
        return (2 * this.height) + (2 * this.width);
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
