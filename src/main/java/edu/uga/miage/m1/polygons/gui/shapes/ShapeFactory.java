package edu.uga.miage.m1.polygons.gui.shapes;


import java.io.IOException;

public class ShapeFactory {


    public BaseShape createShape(Shapes type, int x, int y) throws IOException {
            return switch (type) {
            case TRIANGLE -> new Triangle(x, y);
            case CIRCLE -> new Circle(x, y);
            case SQUARE -> new Square(x, y);
            case MINOU -> new Minou(x, y);
        };
    }

    public Shapes stringToEnum(String type) {
        return switch (type) {
            case "circle" -> Shapes.CIRCLE;
            case "triangle" -> Shapes.TRIANGLE;
            case "square" -> Shapes.SQUARE;
            case "minou" -> Shapes.MINOU;
            default -> null;
        };
    }

}
