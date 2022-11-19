package edu.uga.miage.m1.polygons.gui.command;

import javax.swing.*;

public class Move implements Command{

    private JPanel jPanel;

    public Move(JPanel jPanel) {
        this.jPanel = jPanel;
    }

    @Override
    public void  execute() {
        // not implement
    }
}
