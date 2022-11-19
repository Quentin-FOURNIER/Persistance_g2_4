package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CircleTest {

    Circle circle;
    XMLVisitor xmlVisitor;
    @Before
    public void setUp() throws IOException {
        circle = new Circle(12, 17);
        xmlVisitor = new XMLVisitor();
    }

    @Test
    public void accept() {
        circle.accept(xmlVisitor);
        assertEquals("<shape><type>circle</type><x>-13</x><y>-8</y></shape>", xmlVisitor.getRepresentation());
    }

    @Test
    public void getX() {
        assertEquals(12 - 25, circle.getX());
    }

    @Test
    public void getY() {
        assertEquals(17 - 25, circle.getY());
    }
}