package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SquareTest {
    Square square;
    XMLVisitor xmlVisitor;

    @Before
    public void setUp() throws IOException {
        square = new Square(100, 200);
        xmlVisitor = new XMLVisitor();
    }

    @Test
    public void accept() {
        square.accept(xmlVisitor);
        assertEquals("<shape><type>square</type><x>75</x><y>175</y></shape>", xmlVisitor.getRepresentation());
    }

    @Test
    public void testToString() {
        assertEquals("Square [75, 175]", square.toString());
    }
}