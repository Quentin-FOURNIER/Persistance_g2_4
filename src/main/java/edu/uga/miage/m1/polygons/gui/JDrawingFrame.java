package edu.uga.miage.m1.polygons.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.uga.miage.m1.polygons.gui.persistence.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Shapes;
import gui.shapes.ShapeComposite;
import gui.shapes.SimpleShape;
import lombok.extern.java.Log;
import shapeLibrary.com.BaseShape;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.uga.miage.m1.polygons.gui.shapes.ShapeFactory;
import edu.uga.miage.m1.polygons.gui.shapes.ShapeFactoryAdpter;

//import shapeLibrary.com.*;


/**
 * This class represents the main application class, which is a JFrame subclass
 * that manages a toolbar of shapes and a drawing canvas.
 *
 * @author <a href=
 * "mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
@Log
public class JDrawingFrame extends JFrame implements MouseListener, MouseMotionListener {


    @Serial
    private static final long serialVersionUID = 1L;

    private final JToolBar mainToolbar;

    private Shapes mainSelected;

    private final JPanel mainPanel;

    private final JLabel mLabel;

    private final transient ActionListener mainReusableActionListener = new ShapeActionListener();

    private final transient XMLVisitor xmlVisitor = new XMLVisitor();

    public XMLVisitor getXmlVisitor() {
        return xmlVisitor;
    }

    public JSonVisitor getJsonVisitor() {
        return jsonVisitor;
    }

    public List<BaseShape> getGroupOfShapesSelected() {
        return groupOfShapesSelected;
    }

    private final transient JSonVisitor jsonVisitor = new JSonVisitor();

    private List<BaseShape> groupOfShapesSelected = new ArrayList<>();

    /**
     * Tracks buttons to manage the background.
     */
    private final EnumMap<Shapes, JButton> mainButtons = new EnumMap<>(Shapes.class);


    public boolean isMove() {
        return move;
    }

    public boolean isGroup() {
        return group;
    }

    private boolean move = false;

    private boolean group = false;

    private ArrayList<BaseShape> groupOfShapes = new ArrayList<>();

    private BaseShape constructGroup = new BaseShape();

    private final transient SavesPanels savesPanels = new SavesPanels();
    
    
    /**
     * Début Ajout pour librairie 
     */
    private ArrayList<SimpleShape> lesShapesLib = new ArrayList<>();
    private List<SimpleShape> groupOfShapesSelectedLib = new ArrayList<>(); // ici on pourra mettre les formes composite 
    private List<SimpleShape> CreationGroupLib = new ArrayList<>(); 
    private int nbGroup = 10;
    public void redrawLib() {

    	for (SimpleShape sh : lesShapesLib) { 
    		System.out.println(sh);
            Graphics2D g2 = (Graphics2D) mainPanel.getGraphics();
            sh.draw(g2);	

		}
		mainPanel.repaint();

    }
    
    public SimpleShape getShapeInCoord(int x , int y){
    	for (SimpleShape sh : lesShapesLib) {
    		if(sh.isSelected(x, y))
    			return sh;
		}
    	return null;
    }
    
    
    /**
     * Fin Ajout pour librairie 
     */



    /**
     * Default constructor that populates the main window.
     */
    public JDrawingFrame(String frameName) throws IOException {
        super(frameName);

        BufferedImage undoPicture = ImageIO.read(new File("src/main/resources/images/undo.png"));
        BufferedImage redoPicture = ImageIO.read(new File("src/main/resources/images/redo.png"));
        BufferedImage importPicture = ImageIO.read(new File("src/main/resources/images/import.png"));
        BufferedImage exportPicture = ImageIO.read(new File("src/main/resources/images/export.png"));
        BufferedImage groupPicture = ImageIO.read(new File("src/main/resources/images/group.png"));
        BufferedImage binPicture = ImageIO.read(new File("src/main/resources/images/bin.png"));

        // TOOLBAR
        mainToolbar = new JToolBar("Toolbar");
        JButton xmlButton = new JButton("XML", new ImageIcon(exportPicture));
        JButton jsonButton = new JButton("JSON", new ImageIcon(exportPicture));
        JButton importation = new JButton(new ImageIcon(importPicture));
        JButton whiteBoard = new JButton(new ImageIcon(binPicture));
        JToggleButton groupShapes = new JToggleButton(new ImageIcon(groupPicture));
        JButton undoButton = new JButton(new ImageIcon(undoPicture));
        JButton redoButton = new JButton(new ImageIcon(redoPicture));

        xmlButton.addActionListener(new ExportXMLActionListener());
        jsonButton.addActionListener(new ExportJSONActionListener());
        importation.addActionListener(new ImportActionListener());
        whiteBoard.addActionListener(new WhiteBoardActionListener());
        groupShapes.addActionListener(new GroupShapesActionListener());
        undoButton.addActionListener(new UndoActionListener());
        redoButton.addActionListener(new RedoActionListener());

        mainToolbar.add(undoButton);
        mainToolbar.add(redoButton);
        mainToolbar.addSeparator();
        mainToolbar.add(xmlButton);
        mainToolbar.add(jsonButton);
        mainToolbar.addSeparator();
        mainToolbar.add(importation);
        mainToolbar.addSeparator();
        mainToolbar.add(whiteBoard);
        mainToolbar.addSeparator();
        mainToolbar.add(groupShapes);
        mainToolbar.addSeparator();

        // PANEL
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(null);
        mainPanel.setMinimumSize(new Dimension(1000, 600));
        mainPanel.addMouseListener(this);
        mainPanel.addMouseMotionListener(this);

        mLabel = new JLabel(" ", SwingConstants.LEFT);
        // Fills the panel
        setLayout(new BorderLayout());
        add(mainToolbar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(mLabel, BorderLayout.SOUTH);
        // Add shapes in the menu
        String userDir = System.getProperty("user.dir");
        addShape(Shapes.SQUARE, new ImageIcon(userDir + "/src/main/resources/images/square.png"));
        addShape(Shapes.TRIANGLE, new ImageIcon(userDir + "/src/main/resources/images/triangle.png"));
        addShape(Shapes.CIRCLE, new ImageIcon(userDir + "/src/main/resources/images/circle.png"));
        addShape(Shapes.MINOU, new ImageIcon(userDir + "/src/main/resources/gui/images/binome_1_4.jpg"));
        //edu/uga/miage/m1/polygons/gui/
        setPreferredSize(new Dimension(1000, 600));
    }

    /**
     * Injects an available <tt>SimpleShape</tt> into the drawing frame.
     *
     * @param shape The name of the injected <tt>SimpleShape</tt>.
     * @param icon  The icon associated with the injected <tt>SimpleShape</tt>.
     */
    private void addShape(Shapes shape, ImageIcon icon) {
        JButton button = new JButton(icon);
        button.setBorderPainted(false);
        mainButtons.put(shape, button);
        button.setActionCommand(shape.toString());
        button.addActionListener(mainReusableActionListener);
        if (mainSelected == null) {
            button.doClick();
        }
        mainToolbar.add(button);
        mainToolbar.validate();
//        repaint();
    }


    private JLabel getComponent(int x, int y) {
        // on recherche le premier composant qui correspond aux coordonnées de la souris
        for (Component component : mainPanel.getComponents()) {
            if (component instanceof JComponent && component.getBounds().contains(x, y)) {
                return (JLabel) component;
            }
        }
        return null;
    }

    public void mouseClicked(MouseEvent evt) {
    	SimpleShape shape = getShapeInCoord(evt.getX(),evt.getY());
//        if (mainPanel.contains(evt.getX(), evt.getY()) && getComponent(evt.getX(), evt.getY()) == null && !group) {
        if (shape == null && !group && !move) {
        	System.out.println("here");
            try {
//                BaseShape newGroup = new BaseShape();
//                groupOfShapes.add(newGroup);
                SimpleShape s = new ShapeFactoryAdpter().createShape(mainSelected, evt.getX(), evt.getY());
//                newGroup.getShapes().add(s);
//                mainPanel.add(s);
                Graphics2D g2 = (Graphics2D) mainPanel.getGraphics();
                s.draw(g2);
                lesShapesLib.add(s);
                ArrayList<BaseShape> aSupr = new ArrayList<>();
                for (BaseShape bs : groupOfShapes) {
                    if (bs.getShapes().isEmpty()) {
                        aSupr.add(bs);
                    }
                }
                for (BaseShape bs : aSupr) {
                    groupOfShapes.remove(bs);
                }
                savesPanels.addPanel(groupOfShapes);

                s.accept(jsonVisitor);
                s.accept(xmlVisitor);
//                mainPanel.repaint();
                
//                redrawLib();
            } catch (IOException e) {
                throw new IllegalCallerException();
            }
        }
    }

    public void mouseEntered(MouseEvent evt) { /* Pas d'action */ }

    public void mouseExited(MouseEvent evt) { /* Pas d'action */ }

    private void completeGroupOfShapes(BaseShape shape) {
        for (BaseShape shapes : groupOfShapes) {
            if (shapes.getShapes().contains(shape)) {
                groupOfShapesSelected = shapes.getShapes();
            }
        }
    }

    public void mousePressed(MouseEvent evt) {

//        BaseShape shape = (BaseShape) getComponent(evt.getX(), evt.getY());
    	SimpleShape shape = getShapeInCoord(evt.getX(),evt.getY());
//		System.out.println("click : " + evt.getX() + " - " + evt.getY());
    	
		System.out.println(shape);

        if (move) {
//            for (BaseShape s : groupOfShapesSelected) {
//                s.setBorder(null);
//            }
            move = false;
            groupOfShapesSelectedLib = new ArrayList<>();
//            redrawLib();
//            try {
//                savesPanels.addPanel(groupOfShapes);
//            } catch (IOException e) {
//                throw new IllegalCallerException();
//            }

        } else if (!group && shape != null) {

//            completeGroupOfShapes(shape);
        	System.out.println("- deplace groupe");
        	groupOfShapesSelectedLib.add(shape);
        	move = true;
//        	shape.moveTo(evt.getX(), evt.getY());

//            for (BaseShape s : groupOfShapesSelected) {
//                s.setPositionRelativeX(evt.getX() - s.getX());
//                s.setPositionRelativeY(evt.getY() - s.getY());
//                move = true;
//                s.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//            }

        } else if (group && shape != null) {
        	shape.setIdGroupe(nbGroup);
        	CreationGroupLib.add(shape);
        	System.out.println(CreationGroupLib.toString());

//            shape.setPositionRelativeX(evt.getX() - shape.getX());
//            shape.setPositionRelativeY(evt.getY() - shape.getY());
//            constructGroup.getShapes().add(shape);
//            shape.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        }


    }

    public void mouseReleased(MouseEvent evt) { /* Pas d'action */ }

    public void mouseDragged(MouseEvent evt) { /* Pas d'action */ }

    public void mouseMoved(MouseEvent evt) {
        modifyLabel(evt);
        if (move) {
            // si on déplace
            for (SimpleShape s : groupOfShapesSelectedLib) {
//                s.setLocation(evt.getX() - s.getPositionRelativeX(), evt.getY() - s.getPositionRelativeY());
            	s.moveTo(evt.getX(), evt.getY());
            }
            redrawLib();
        	

        }
    }

    private void modifyLabel(MouseEvent evt) {
        mLabel.setText("(" + evt.getX() + "," + evt.getY() + ")");
    }

    /**
     * Simple action listener for shape tool bar buttons that sets the drawing
     * frame's currently selected shape when receiving an action event.
     */
    private class ShapeActionListener implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            for (Map.Entry<Shapes, JButton> shape : mainButtons.entrySet()) {
                JButton btn = shape.getValue();
                if (evt.getActionCommand().equals(shape.getKey().toString())) {
                    btn.setBorderPainted(true);
                    mainSelected = shape.getKey();
                } else {
                    btn.setBorderPainted(false);
                }
//                btn.repaint();
            }
        }
    }

    private enum FileType {
        DOSSIER, FICHIER, BOTH
    }

    private String chooserPath(FileType type) {
        JFileChooser chooser;
        String path = "";
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        switch (type) {
            case DOSSIER -> chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            case FICHIER -> chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            case BOTH -> chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        }
        int res = chooser.showOpenDialog(null);

        if (res == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            path = file.getAbsolutePath();
        }
        return path;
    }

    //
    private void writeFile(String path, String data) {
        try (FileWriter file = new FileWriter(path)) {
            file.write(data);
        } catch (IOException ex) {
            Logger.getLogger(String.valueOf(Level.WARNING), ex.toString());
        }
    }

    //
    public String readFile(String path) {
        String res = "";
        try {
            res = Files.readString(Path.of(path));
        } catch (IOException ex) {
            Logger.getLogger(String.valueOf(Level.WARNING), "Erreur read File");
        }
        return res;
    }


    public void loadFromJSON(String data) {
        try {
            ObjectNode node = new ObjectMapper().readValue(data, ObjectNode.class);
            String type;
            BaseShape groupJson;
            BaseShape creationShape;
            ShapeFactory shapeFactory = new ShapeFactory();
            int x;
            int y;
            for (JsonNode j : node.get("shapes")) {
                groupJson = new BaseShape();
                for (JsonNode shapeInGroupe : j.get("groupe")) {
                    type = shapeInGroupe.get("type").asText();
                    x = shapeInGroupe.get("x").asInt();
                    y = shapeInGroupe.get("y").asInt();
                    creationShape = shapeFactory.createShape(shapeFactory.stringToEnum(type), x, y);
                    groupJson.getShapes().add(creationShape);
                    mainPanel.add(creationShape);

                }
                groupOfShapes.add(groupJson);
            }
        } catch (IOException e) {
            Logger.getLogger(String.valueOf(Level.WARNING), "Erreur load JSON");

        }
    }

    public ShapeComposite findComPositeWithId(List<ShapeComposite> lesCP, int id) {
    	for (ShapeComposite shapeComposite : lesCP) {
			if(shapeComposite.getIdGroupe() == id)
				return shapeComposite;
		}
    	return null;
    }
    //
    public void loadFromXML(String path) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setExpandEntityReferences(false);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(path));
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("shape");
            NodeList elementsGroupe;
            String type;
            BaseShape groupXml;
//            BaseShape creationShape;
//            ShapeFactory shapeFactory = new ShapeFactory();
            
            /**
             * Lib code
             */
            lesShapesLib = new ArrayList<>();
            List<ShapeComposite> lesShCompos = new ArrayList<>();
            SimpleShape creationShape;
            ShapeFactoryAdpter shapeFactoryLib = new ShapeFactoryAdpter();
            ShapeComposite leComposite;
            int x;
            int y;
            int ngroup;
            
            for (int i = 0; i < nList.getLength(); i++) {
            	Element shape = (Element) nList.item(i);
                type = shape.getElementsByTagName("type").item(0).getTextContent();
            	y = Integer.parseInt(shape.getElementsByTagName("y").item(0).getTextContent());
            	x = Integer.parseInt(shape.getElementsByTagName("x").item(0).getTextContent());
            	ngroup = Integer.parseInt(shape.getElementsByTagName("idGroup").item(0).getTextContent());
            	creationShape = shapeFactoryLib.createShape(shapeFactoryLib.stringToEnum(type), x, y, ngroup);
            	if(ngroup != 0) {
            		leComposite = findComPositeWithId(lesShCompos, ngroup);
            		if(leComposite != null)
            			leComposite.getShapes().add(creationShape);
            		else {
            			List<SimpleShape> temp = new ArrayList<>();
            			temp.add(creationShape);
            			leComposite = new ShapeComposite(temp, ngroup);
            			lesShCompos.add(leComposite); 
            			lesShapesLib.add(leComposite);
            		}
            	}else {
        			lesShapesLib.add(creationShape);
            	}
            	

            }
            redrawLib();
            
//            for (int i = 0; i < nList.getLength(); i++) {
////                groupXml = new BaseShape();
//                Element xmlGroupe = (Element) nList.item(i);
//                elementsGroupe = xmlGroupe.getElementsByTagName("shape");
//                for (int index = 0; index < elementsGroupe.getLength(); index++) {
//                    Element shape = (Element) elementsGroupe.item(index);
//                    type = shape.getElementsByTagName("type").item(0).getTextContent();
//                    y = Integer.parseInt(shape.getElementsByTagName("y").item(0).getTextContent());
//                    x = Integer.parseInt(shape.getElementsByTagName("x").item(0).getTextContent());
//                    creationShape = shapeFactory.createShape(shapeFactory.stringToEnum(type), x, y);
//                    groupXml.getShapes().add(creationShape);
//                    mainPanel.add(creationShape);
//                }
//                groupOfShapes.add(groupXml);
//            }

        } catch (ParserConfigurationException e) {
            Logger.getLogger(String.valueOf(Level.WARNING), "Erreur load XML : Parser");
        } catch (IOException e) {
            Logger.getLogger(String.valueOf(Level.WARNING), "Erreur load XML ");
        } catch (SAXException e) {
            Logger.getLogger(String.valueOf(Level.WARNING), "Erreur load XML : Sax");
        }

    }

    private class GroupShapesActionListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            group = !group;
            // xxxxx
            if (!group) {
//                for (BaseShape cg : constructGroup.getShapes()) {
//                    cg.setBorder(null);
//                    for (BaseShape gos : groupOfShapes) {
//                        gos.getShapes().remove(cg);
//                    }
//                }
//
//                groupOfShapes.add(constructGroup);
//                constructGroup = new BaseShape();
            	System.out.println(CreationGroupLib);
            	ShapeComposite shCompo = new ShapeComposite(CreationGroupLib, nbGroup);
            	for (SimpleShape simpleShape : CreationGroupLib) {
            		lesShapesLib.remove(simpleShape);
				}
            	lesShapesLib.add(shCompo);
        		System.out.println("----------");

        		System.out.println(lesShapesLib);
        		
        		for(SimpleShape s : lesShapesLib){
        			System.out.println("id groupe " + s.getIdGroupe());
        			if(s instanceof ShapeComposite) {
        				System.out.println(((ShapeComposite) s).getShapes());
        			}
        		}
        		
        		nbGroup++;
            	CreationGroupLib= new ArrayList<>();
            }
        }
    }

    private class WhiteBoardActionListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
        	lesShapesLib = new ArrayList<>();
            repaint();
            groupOfShapes = new ArrayList<>();
        }
    }

    private class UndoActionListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            savesPanels.undo(mainPanel);

        }
    }

    private class RedoActionListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            savesPanels.redo(mainPanel);

        }
    }

    private class ExportXMLActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            StringBuilder xmlString = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><shapes>");
//            ShapeFactory xmlFactory = new ShapeFactory();
//            BaseShape shapeCreator;
        	xmlVisitor.cleanRepresentation();

            for (SimpleShape sh : lesShapesLib) {
            	sh.accept(xmlVisitor);
            	xmlString.append(xmlVisitor.getRepresentation());
            	xmlVisitor.cleanRepresentation();
			}
            
//            for (BaseShape s : groupOfShapes) {
//                if (!s.getShapes().isEmpty()) {
//                    xmlString.append("<groupe>");
//                    for (Component sp : s.getShapes()) {
//                        try {
//                            shapeCreator = xmlFactory.createShape(xmlFactory.stringToEnum(sp.getName()), sp.getX() + 25, sp.getY()+ 25);
//                            shapeCreator.accept(xmlVisitor);
//                            xmlString.append(xmlVisitor.getRepresentation());
//                        } catch (IOException ex) {
//                            Logger.getLogger(String.valueOf(Level.WARNING), "Erreur export XML");
//                        }
//                    }
//                    xmlString.append("</groupe>");
//                }
//            }

            xmlString.append("</shapes></root>");
            String path = chooserPath(FileType.DOSSIER);
            writeFile(path + "/Export.xml", xmlString.toString());
        }
    }


    private class ExportJSONActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String path = chooserPath(FileType.DOSSIER);
            StringBuilder json = new StringBuilder();
            json.append("{\n \"shapes\": [\n");
            ShapeFactory jsonFactory = new ShapeFactory();
            BaseShape shapeCreator;
            for (BaseShape s : groupOfShapes) {
                if (!s.getShapes().isEmpty()) {
                    json.append("{\"groupe\":[");
                    for (Component sp : s.getShapes()) {
                        try {
                            shapeCreator = jsonFactory.createShape(jsonFactory.stringToEnum(sp.getName()), sp.getX() + 25, sp.getY()+ 25);
                           // shapeCreator.accept(jsonVisitor);
                            json.append(jsonVisitor.getRepresentation());
                        } catch (IOException ex) {
                            Logger.getLogger(String.valueOf(Level.WARNING), "Erreur export JSON");
                        }
                    }
                    json.append("]\n}");
                }
            }

            json.append("] \n}");
            writeFile(path + "/Export.json", json.toString().replace("}{", "},\n{"));

        }
    }

    //
    private class ImportActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String path;
            path = chooserPath(FileType.FICHIER);

            List<String> type = Arrays.stream(path.split("\\.")).filter(file -> "xml".equals(file) || "json".equals(file)).toList();
            if (type.isEmpty())
                Logger.getLogger(String.valueOf(Level.WARNING), "Le fichier doit être de type json ou xml");
            else {
                mainPanel.removeAll();
                repaint();
                switch (type.get(0)) {
                    case "xml" -> loadFromXML(path);
                    case "json" -> loadFromJSON(readFile(path));
                    default -> Logger.getLogger(String.valueOf(Level.WARNING), "Problème import");
                }
            }


        }
    }

}
