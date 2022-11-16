package edu.uga.miage.m1.polygons.gui.command;

import edu.uga.miage.m1.polygons.gui.shapes.BaseShape;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Move implements Command{

    private JPanel jPanel;
    private List<Object> saves;

    public Move(List<Object> saves, JPanel jPanel) {
        this.jPanel = jPanel;
        this.saves = saves;
    }

    @Override
    public void  execute() {
        saves.add(jPanel);
    }
}
