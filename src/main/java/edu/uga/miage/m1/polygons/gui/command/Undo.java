package edu.uga.miage.m1.polygons.gui.command;

import javax.swing.*;
import java.util.ArrayList;

public class Undo implements Command {

    public Undo(ArrayList<Object> saves, JPanel jPanel) {
        if ( ! saves.isEmpty() ) {
            saves.remove(saves.size() - 1);
        }
    }

    @Override
    public void execute() {

    }
}
