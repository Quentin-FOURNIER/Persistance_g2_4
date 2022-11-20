package edu.uga.miage.m1.polygons.gui.persistence;

import edu.uga.miage.m1.polygons.gui.shapes.Triangle;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class XMLVisitorTest {
    @Test
    public void getFileRepresentation() throws IOException {
        XMLVisitor xmlVisitor = new XMLVisitor();
        Triangle triangle = new Triangle(50, 100);
        triangle.accept(xmlVisitor);
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><shapes><shape><type>triangle</type><x>25</x><y>75</y></shape></shapes></root>", xmlVisitor.getFileRepresentation());
    }
}