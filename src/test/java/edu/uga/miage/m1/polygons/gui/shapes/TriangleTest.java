package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import org.junit.Test;
import org.junit.Before;

import java.io.IOException;

import static org.junit.Assert.*;

public class TriangleTest {
    Triangle triangle;
    XMLVisitor xmlVisitor;

    @Before
    public void setUp() throws IOException {
        triangle = new Triangle(100, 200);
        xmlVisitor = new XMLVisitor();
    }

    @Test
    public void accept() {
        triangle.accept(xmlVisitor);
        assertEquals("<shape><type>triangle</type><x>75</x><y>175</y></shape>", xmlVisitor.getRepresentation());
    }

    @Test
    public void testToString() {
        assertEquals("Triangle [75, 175]", triangle.toString());
    }
}