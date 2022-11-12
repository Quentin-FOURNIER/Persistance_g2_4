/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import edu.uga.miage.m1.polygons.gui.persistence.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.Visitor;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class implements the square <tt>SimpleShape</tt> extension.
 * It simply provides a <tt>draw()</tt> that paints a square.
 *
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class Square implements SimpleShape, Visitable {

    int x;

    int y;

    private static final String PATH_TO_IMAGE = "src/main/resources/images/";


    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private JComponent createShape(int posX, int posY) throws IOException {
        String path;
        BufferedImage myPicture = ImageIO.read(new File(PATH_TO_IMAGE + "square.png"));
        JLabel component = new JLabel(new ImageIcon(myPicture));
        component.setSize(53, 53);
        component.setLocation(posX - 25, posY - 25);
        component.setVisible(true);
        component.setName("Square");
        return component;
    }

    /**
     * Implements the <tt>SimpleShape.draw()</tt> method for painting
     * the shape.
     * @param g2 The graphics object used for painting.
     */
    public void draw(JPanel jPanel) throws IOException {
        jPanel.add(createShape(x, y));
    }

    @Override
    public void accept(Visitor visitor) {
    	visitor.visit(this);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
