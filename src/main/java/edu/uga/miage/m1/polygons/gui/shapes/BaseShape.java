package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.persistence.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class BaseShape extends JLabel implements SimpleShape, Visitable {

    private int positionX;
    private int positionY;
    private int positionRelativeX;
    private int positionRelativeY;
    private ArrayList<BaseShape> shapes;

    public BaseShape() {
        this.shapes = new ArrayList<>();
    }

    public BaseShape(int positionX, int positionY) {
        this.shapes = new ArrayList<>();

        //this.positionX = positionX;
        //this.positionY = positionY;

        this.positionRelativeX = positionX;
        this.positionRelativeY = positionY;

        this.setVisible(true);
        this.setSize(53, 53);
    }

//    public int getPositionX() {
//        return this.positionX;
//    }
//
//    public void setPositionX(int positionX) {
//        this.positionX = positionX;
//    }
//
//    public int getPositionY() {
//        return this.positionY;
//    }
//
//    public void setPositionY(int positionY) {
//        this.positionY = positionY;
//    }

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

    public ArrayList<BaseShape> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<BaseShape> shapes) {
        this.shapes = shapes;
    }

    @Override
    public void accept(Visitor visitor) {
        throw new IllegalCallerException();
    }

    public  String toString() {
        String s = "";
        for (BaseShape shape: shapes)
            s += shape;
        return "∞" + s + "∞";
    }
}
