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
        circle = new Circle(100, 200);
        xmlVisitor = new XMLVisitor();
    }

    @Test
    public void accept() {
        circle.accept(xmlVisitor);
        assertEquals("<shape><type>circle</type><x>75</x><y>175</y></shape>", xmlVisitor.getRepresentation());
    }

    @Test
    public void testToString() {
        assertEquals("Circle [75, 175]", circle.toString());
    }
}