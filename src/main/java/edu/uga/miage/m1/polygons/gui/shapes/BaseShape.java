package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.persistence.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BaseShape extends JLabel implements SimpleShape, Visitable {

    public static final String PATH_TO_IMAGE = "src/main/resources/images/";
    private int positionRelativeX;
    private int positionRelativeY;
    private List<BaseShape> shapes;

    public BaseShape() {
        this.shapes = new ArrayList<>();
    }

    public BaseShape(int positionX, int positionY) {
        this.shapes = new ArrayList<>();

        this.positionRelativeX = positionX;
        this.positionRelativeY = positionY;

        this.setVisible(true);
        this.setSize(53, 53);
    }

    public int getPositionRelativeX() {
        return positionRelativeX;
    }

    public void setPositionRelativeX(int positionRelativeX) {
        this.positionRelativeX = positionRelativeX;
    }

    public int getPositionRelativeY() {
        return positionRelativeY;
    }

    public void setPositionRelativeY(int positionRelativeY) {
        this.positionRelativeY = positionRelativeY;
    }

    public List<BaseShape> getShapes() {
        return shapes;
    }

    public void setShapes(List<BaseShape> shapes) {
        this.shapes = shapes;
    }

    @Override
    public void accept(Visitor visitor) {
        throw new IllegalCallerException();
    }

    @Override
    public  String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (BaseShape shape: shapes)
            stringBuilder.append(shape);
        return stringBuilder.toString();
    }
}
