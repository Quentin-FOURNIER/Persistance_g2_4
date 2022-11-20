package edu.uga.miage.m1.polygons.gui;

import edu.uga.miage.m1.polygons.gui.shapes.BaseShape;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SavesPanelsTest {

    SavesPanels savesPanels;

    @Before
    public void setUp() {
        savesPanels = new SavesPanels();
    }

    @Test
    public void addPanel() throws IOException {
        JPanel panel = new JPanel();

        ArrayList<BaseShape> baseShapes = new ArrayList<>();
        BaseShape baseShape = new BaseShape();
        baseShape.getShapes().add(new Circle(0, 0));
        baseShapes.add(baseShape);
        savesPanels.undo(panel);
        savesPanels.redo(panel);
        savesPanels.addPanel(baseShapes);
        assertEquals(1, savesPanels.getIndex());
        assertEquals(baseShapes.get(0).getX(), savesPanels.getSaves().get(1).get(0).getX() + 25);
        savesPanels.undo(panel);
        assertEquals(0, savesPanels.getIndex());
        savesPanels.redo(panel);
        assertEquals(1, savesPanels.getIndex());

    }

    @Test
    public void addPanel2() throws IOException {
        JPanel panel = new JPanel();

        ArrayList<BaseShape> baseShapes = new ArrayList<>();
        BaseShape baseShape = new BaseShape();
        baseShape.getShapes().add(new Triangle(0, 0));
        baseShapes.add(baseShape);
        savesPanels.undo(panel);
        savesPanels.redo(panel);
        savesPanels.addPanel(baseShapes);
        assertEquals(1, savesPanels.getIndex());
        assertEquals(baseShapes.get(0).getX(), savesPanels.getSaves().get(1).get(0).getX() + 25);
        savesPanels.undo(panel);
        assertEquals(0, savesPanels.getIndex());
        savesPanels.redo(panel);
        assertEquals(1, savesPanels.getIndex());

    }

    @Test
    public void addPanel3() throws IOException {
        JPanel panel = new JPanel();

        ArrayList<BaseShape> baseShapes = new ArrayList<>();
        BaseShape baseShape = new BaseShape();
        baseShape.getShapes().add(new Square(0, 0));
        baseShapes.add(baseShape);
        savesPanels.undo(panel);
        savesPanels.redo(panel);
        savesPanels.addPanel(baseShapes);
        assertEquals(1, savesPanels.getIndex());
        assertEquals(baseShapes.get(0).getX(), savesPanels.getSaves().get(1).get(0).getX() + 25);
        savesPanels.undo(panel);
        assertEquals(0, savesPanels.getIndex());
        savesPanels.redo(panel);
        assertEquals(1, savesPanels.getIndex());

    }

    @Test
    public void addPanel4() throws IOException {
        JPanel panel = new JPanel();

        ArrayList<BaseShape> baseShapes = new ArrayList<>();
        BaseShape baseShape = new BaseShape();
        baseShape.getShapes().add(new Square(0, 0));
        baseShapes.add(baseShape);
        savesPanels.addPanel(baseShapes);
        savesPanels.undo(panel);
        baseShape.getShapes().add(new Triangle(0, 0));
        savesPanels.addPanel(baseShapes);
        assertEquals(1, savesPanels.getIndex());


    }

    @Test
    public void addPanel5() throws IOException {
        JPanel panel = new JPanel();

        ArrayList<BaseShape> baseShapes = new ArrayList<>();
        BaseShape baseShape = new BaseShape();
        baseShape.getShapes().add(new Square(0, 0));
        baseShape.getShapes().add(new Triangle(0, 0));
        baseShape.getShapes().add(new Circle(0, 0));
        baseShapes.add(baseShape);
        savesPanels.addPanel(baseShapes);
        baseShape = new BaseShape();
        baseShapes = new ArrayList<>();
        baseShapes.add(baseShape);
        savesPanels.addPanel(baseShapes);
        savesPanels.undo(panel);

        assertEquals(1, savesPanels.getIndex());


    }

    @Test(expected = IllegalArgumentException.class)
    public void addPanel56() throws IOException {

        ArrayList<BaseShape> baseShapes = new ArrayList<>();
        BaseShape baseShape = new BaseShape();
        Square square = new Square(0, 0);
        square.setName("Hello world !");
        baseShape.getShapes().add(square);
        baseShapes.add(baseShape);
        savesPanels.addPanel(baseShapes);
    }
}