package edu.uga.miage.m1.polygons.gui;

import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.BaseShape;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class JDrawingFrameTest {

    @Test
    public void test() throws IOException {
        JDrawingFrame jDrawingFrame = new JDrawingFrame("");
        assertFalse(jDrawingFrame.isMove());
        assertFalse(jDrawingFrame.isGroup());

        assertEquals(new XMLVisitor().getRepresentation(), jDrawingFrame.getXmlVisitor().getRepresentation());
        assertEquals(new JSonVisitor().getRepresentation(), jDrawingFrame.getJsonVisitor().getRepresentation());
        assertEquals(new ArrayList<BaseShape>(), jDrawingFrame.getGroupOfShapesSelected());


    }
}