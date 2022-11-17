package edu.uga.miage.m1.polygons.gui.shapes;


import java.io.IOException;

public class ShapeFactory {


    public BaseShape createShape(Shapes type, int x, int y) throws IOException {
            return switch (type) {
            case TRIANGLE -> new Triangle(x, y);
            case CIRCLE -> new Circle(x, y);
            case SQUARE -> new Square(x, y);
        };
    }

}
