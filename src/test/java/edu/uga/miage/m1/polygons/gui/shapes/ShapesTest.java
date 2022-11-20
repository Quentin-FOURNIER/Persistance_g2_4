package edu.uga.miage.m1.polygons.gui.shapes;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShapesTest {

    @Test
    public void values() {
        assertEquals(Shapes.CIRCLE, Shapes.valueOf("CIRCLE"));
    }

}