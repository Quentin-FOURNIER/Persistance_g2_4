package edu.uga.miage.m1.polygons.gui.persistence;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class XMLVisitorTest {

    public Square square;
    public Triangle triangle;
    public Circle circle;
    public XMLVisitor xmlVisitor;

    @Before
    public void setUp() {
        square = new Square(10, 10);
        triangle = new Triangle(10, 10 );
        circle = new Circle(10, 10);
        xmlVisitor = new XMLVisitor();
    }

    @Test
    public void visitSquare() {
        assertEquals("", xmlVisitor.getRepresentation());
        xmlVisitor.visit(square);
        assertEquals("<shape><type>square</type><x>-15</x><y>-15</y></shape>", xmlVisitor.getRepresentation());
    }

    @Test
    public void VisitTriangle() {
        assertEquals("", xmlVisitor.getRepresentation());
        xmlVisitor.visit(triangle);
        assertEquals("<shape><type>triangle</type><x>-15</x><y>-15</y></shape>", xmlVisitor.getRepresentation());
    }

    @Test
    public void VisitCircle() {
        assertEquals("", xmlVisitor.getRepresentation());
        xmlVisitor.visit(circle);
        assertEquals("<shape><type>circle</type><x>-15</x><y>-15</y></shape>", xmlVisitor.getRepresentation());
    }

    @Test
    public void getRepresentation() {
        assertEquals("", xmlVisitor.getRepresentation());
    }
}