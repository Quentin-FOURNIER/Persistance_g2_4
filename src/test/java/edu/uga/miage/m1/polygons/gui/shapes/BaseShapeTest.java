package edu.uga.miage.m1.polygons.gui.shapes;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BaseShapeTest {

    BaseShape baseShape;

    @Before
    public void setUp() {
        baseShape = new BaseShape(100, 50);
        baseShape.setPositionRelativeX(50);
        baseShape.setPositionRelativeY(60);
    }

    @Test
    public void emptyConstructor() {
        BaseShape emptyBaseShape = new BaseShape();
        assertEquals(new ArrayList<>(), emptyBaseShape.getShapes());
    }

    @Test
    public void getPositionRelativeX() {
        assertEquals(50, baseShape.getPositionRelativeX());
    }

    @Test
    public void setPositionRelativeX() {
        baseShape.setPositionRelativeX(500);
        assertEquals(500, baseShape.getPositionRelativeX());
    }

    @Test
    public void getPositionRelativeY() {
        assertEquals(60, baseShape.getPositionRelativeY());
    }

    @Test
    public void setPositionRelativeY() {
        baseShape.setPositionRelativeY(600);
        assertEquals(600, baseShape.getPositionRelativeY());
    }

    @Test
    public void getShapes() throws IOException {
        List<BaseShape> shapes = new ArrayList<>();
        shapes.add(new Triangle(30, 30));
        shapes.add(new Square(40, 40));
        baseShape.setShapes(shapes);
        assertEquals(shapes, baseShape.getShapes());
    }

    @Test(expected = IllegalCallerException.class)
    public void accept() {
        baseShape.accept(null);
    }

    @Test
    public void testToString() throws IOException {
        List<BaseShape> shapes = new ArrayList<>();
        shapes.add(new Triangle(30, 30));
        shapes.add(new Square(40, 40));
        baseShape.setShapes(shapes);
        assertEquals("Triangle [5, 5]Square [15, 15]", baseShape.toString());
    }
}