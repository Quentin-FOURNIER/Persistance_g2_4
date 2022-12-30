package edu.uga.miage.m1.polygons.gui;

import edu.uga.miage.m1.polygons.gui.shapes.*;
import shapeLibrary.com.BaseShape;
import shapeLibrary.com.Circle;
import shapeLibrary.com.Minou;
import shapeLibrary.com.Square;
import shapeLibrary.com.Triangle;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SavesPanels {

    private final List<List<BaseShape>> saves;

    private int index;

    public SavesPanels() {
        saves = new ArrayList<>();
        saves.add(new ArrayList<>());
        index = 0;
    }

    public void addPanel(List<BaseShape> panel) throws IOException {

        List<BaseShape> listBaseShape = new ArrayList<>();

        BaseShape tmpBaseShape;
        BaseShape tmpShape = null;


        while (saves.size() > index + 1) {
            saves.remove(index+1);
        }

        for (BaseShape baseShape: panel) {
            tmpBaseShape = new BaseShape();

            for (BaseShape shape: baseShape.getShapes()) {

                tmpShape = switch (shape.getName()) {
                    case "triangle" -> new Triangle(shape.getX() + 25, shape.getY() + 25);
                    case "circle" -> new Circle(shape.getX() + 25, shape.getY() + 25);
                    case "square" -> new Square(shape.getX() + 25, shape.getY() + 25);
                    case "minou" -> new Minou(shape.getX() + 25, shape.getY() + 25);
                    default -> throw new IllegalArgumentException();
                };

            }
            listBaseShape.add(tmpShape);
            tmpBaseShape.getShapes().add(tmpShape);
            if (tmpShape != null)
                tmpBaseShape.add(tmpShape);
        }

        saves.add(listBaseShape);

        index++;
    }

    public void undo(JPanel panel) {
        if (index >= 1) {
            index--;
            panel.removeAll();
            for (BaseShape bs : saves.get(index)) {
                panel.add(bs);
            }
            panel.repaint();
        }
    }

    public void redo(JPanel panel) {
        if (index < saves.size() -1) {
            index++;
            panel.removeAll();
            for (BaseShape bs : saves.get(index)) {
                panel.add(bs);
            }
            panel.repaint();
        }
    }

    public int getIndex() {
        return index;
    }

    public List<List<BaseShape>> getSaves() {
        return saves;
    }
}
