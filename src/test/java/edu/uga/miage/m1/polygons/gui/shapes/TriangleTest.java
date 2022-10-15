package edu.uga.miage.m1.polygons.gui.shapes;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class TriangleTest {

    public Triangle triangle;

    @Before
    public void setUp() throws Exception {
        triangle = new Triangle(12, 17);
    }

//    @Test
//    public void draw() {
//        //
//    }
//
//    @Test
//    public void accept() {
//        //
//    }

    @Test
    public void getX() {
        assertEquals(12 - 25, triangle.getX());
    }

    @Test
    public void getY() {
        assertEquals(17 - 25, triangle.getY());
    }
}