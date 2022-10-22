package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class SquareTest {
    Square square;
    XMLVisitor xmlVisitor;
    @Before
    public void setUp() {
        square = new Square(12, 17);
        xmlVisitor = new XMLVisitor();
    }

    @Test
    public void Square() {
        assertEquals(12 - 25, square.x);
        assertEquals(17 - 25, square.y);
    }

    @Test
    public void accept() {
        square.accept(xmlVisitor);
        assertEquals("<shape><type>square</type><x>-13</x><y>-8</y></shape>", xmlVisitor.getRepresentation());
    }

    @Test
    public void getX() {
        assertEquals(12 - 25, square.getX());
    }

    @Test
    public void getY() {
        assertEquals(17 - 25, square.getY());
    }
}