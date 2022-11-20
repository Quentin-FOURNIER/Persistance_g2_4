package edu.uga.miage.m1.polygons.gui.persistence;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class JSonVisitorTest {

    public Square square;
    public Triangle triangle;
    public Circle circle;
    public JSonVisitor jsonVisitor;

    @Before
    public void setUp() throws IOException {
        square = new Square(10, 10);
        triangle = new Triangle(10, 10);
        circle = new Circle(10, 10);
        jsonVisitor = new JSonVisitor();
    }

    @Test
    public void complete() {
        square.accept(jsonVisitor);
        assertEquals("{\"type\": \"square\",\"x\": -15,\"y\": -15}", jsonVisitor.getRepresentation());
        triangle.accept(jsonVisitor);
        assertEquals("{\"type\": \"triangle\",\"x\": -15,\"y\": -15}", jsonVisitor.getRepresentation());
        circle.accept(jsonVisitor);
        assertEquals("{\"type\": \"circle\",\"x\": -15,\"y\": -15}", jsonVisitor.getRepresentation());
    }
}