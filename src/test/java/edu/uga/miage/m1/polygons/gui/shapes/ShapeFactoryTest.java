package edu.uga.miage.m1.polygons.gui.shapes;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ShapeFactoryTest {
    ShapeFactory shapeFactory;

    @Before
    public void setUp() {
        shapeFactory = new ShapeFactory();
    }

    @Test
    public void createShape() throws IOException {
        Triangle triangle = new Triangle(25, 25);
        Square square = new Square(25, 25);
        Circle circle = new Circle(25, 25);
        assertEquals(triangle.getX(), shapeFactory.createShape(Shapes.TRIANGLE, 25, 25).getX());
        assertEquals(square.getX(), shapeFactory.createShape(Shapes.SQUARE, 25, 25).getX());
        assertEquals(circle.getX(), shapeFactory.createShape(Shapes.CIRCLE, 25, 25).getX());


    }

    @Test
    public void stringToEnum() {
        assertEquals(Shapes.TRIANGLE, shapeFactory.stringToEnum("triangle"));
        assertEquals(Shapes.CIRCLE, shapeFactory.stringToEnum("circle"));
        assertEquals(Shapes.SQUARE, shapeFactory.stringToEnum("square"));
        assertNull(shapeFactory.stringToEnum("bonjour"));
    }
}