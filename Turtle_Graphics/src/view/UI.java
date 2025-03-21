package view;
import controller.Controller;
import model.Turtle;
import model.Point;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;

public class UI {
    private final JFrame frame;
    private final Panel panel;
    private final Turtle turtle;
    private File currFile = null;
    private boolean unsavedChanges = false;

    //add more for fielsd
    public UI() {
        turtle = new Turtle();
        frame = new JFrame("Turtle Graphics");
        panel = new Panel(turtle);
        Controller controller = new Controller(turtle, panel);

        JPanel controlPanel = new JPanel(new GridLayout(4,2,5,5));

        JButton northButton = new JButton("North");
        JButton southButton = new JButton("South");
        JButton westButton = new JButton("West");
        JButton eastButton = new JButton("East");
        JButton clearButton = new JButton("Clear");
        JButton penButton = new JButton("Pen");
        JButton colorButton = new JButton("Color");

        northButton.setActionCommand("North");
        southButton.setActionCommand("South");
        westButton.setActionCommand("West");
        eastButton.setActionCommand("East");
        clearButton.setActionCommand("Clear");
        penButton.setActionCommand("Pen");
        colorButton.setActionCommand("Color");

        northButton.addActionListener(controller);
        southButton.addActionListener(controller);
        westButton.addActionListener(controller);
        eastButton.addActionListener(controller);
        clearButton.addActionListener(controller);
        penButton.addActionListener(controller);
        colorButton.addActionListener(controller);


        controlPanel.add(northButton);
        controlPanel.add(southButton);
        controlPanel.add(westButton);
        controlPanel.add(eastButton);
        controlPanel.add(clearButton);
        controlPanel.add(penButton);
        controlPanel.add(colorButton);



        //make a menu bar with file, edit and about
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem saveAsItem = new JMenuItem("SaveAs");
        JMenuItem quitItem = new JMenuItem("Quit");

        newItem.addActionListener(e-> newFile());
        openItem.addActionListener(e-> openFile());
        saveItem.addActionListener(e-> save(false));
        saveAsItem.addActionListener(e-> save(true));
        quitItem.addActionListener(e->quit());

        fileMenu.add(newItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(openItem);
        fileMenu.add(quitItem);

        //Make an edit menu
        JMenu editMenu = new JMenu("Edit");
        JMenuItem northItem = new JMenuItem("North");
        JMenuItem southItem = new JMenuItem("South");
        JMenuItem westItem = new JMenuItem("West");
        JMenuItem eastItem = new JMenuItem("East");
        JMenuItem clearItem = new JMenuItem("Clear");
        JMenuItem penItem = new JMenuItem("Pen");
        JMenuItem colorItem = new JMenuItem("Color");

        northItem.setActionCommand("North");
        southItem.setActionCommand("South");
        westItem.setActionCommand("West");
        eastItem.setActionCommand("East");
        clearItem.setActionCommand("Clear");
        penItem.setActionCommand("Pen");
        colorItem.setActionCommand("Color");

        northItem.addActionListener(controller);
        southItem.addActionListener(controller);
        westItem.addActionListener(controller);
        eastItem.addActionListener(controller);
        clearItem.addActionListener(controller);
        penItem.addActionListener(controller);
        colorItem.addActionListener(controller);

        editMenu.add(northItem);
        editMenu.add(eastItem);
        editMenu.add(southItem);
        editMenu.add(westItem);
        editMenu.addSeparator();
        editMenu.add(penItem);
        editMenu.add(colorItem);
        editMenu.addSeparator();
        editMenu.add(clearItem);

        //Make a help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        JMenuItem helpItem = new JMenuItem("Help");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Turtle Graphics \nVinzent Schubert 2025 - CS-151 "));
        helpItem.addActionListener(e-> helpMessage());
        helpMenu.add(aboutItem);
        helpMenu.add(helpItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);

        frame.setLayout(new BorderLayout());
        frame.add(controlPanel, BorderLayout.WEST);
        frame.add(panel, BorderLayout.EAST);
        frame.pack(); //fits to size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    private void helpMessage() {
        String message = "north - moves turtle up\neast - moves turtle right\nsouth - moves turtle down\nwest - moves turtle left\npen - puts pen down or up\ncolor - choose pen color\nclear - clear the panel\n";
        JOptionPane.showMessageDialog(frame, message, "Help", JOptionPane.INFORMATION_MESSAGE );
    }

    private void openFile() {
        if(unsavedChanges)return;
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(frame);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            currFile = chooser.getSelectedFile();
            try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(currFile))) {
                List<Point> newPath = (List<Point>) in.readObject();
                turtle.getPath().clear();
                turtle.getPath().addAll(newPath);
                panel.refresh();
                unsavedChanges = false;
            }
            catch(IOException | ClassNotFoundException e){
                JOptionPane.showMessageDialog(frame, "Could not open file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void newFile() {
        if (unsavedChanges) return;
        turtle.clearPath();
        panel.refresh();
        currFile = null;
        unsavedChanges = false;

        }


    private void save(boolean saveAs) {
        if(currFile == null || saveAs){
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showSaveDialog(frame);
            if (returnVal == JFileChooser.APPROVE_OPTION){
                currFile = chooser.getSelectedFile();
            }else{
                return;
            }
        }
        //account for potential file failurs
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(currFile))) {
            out.writeObject(turtle.getPath());
            unsavedChanges = false;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving turtle path to a file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void quit() {

        int doubleCheck = JOptionPane.showConfirmDialog(frame,"Unsaved changes! Quit anyway?", "Close Application", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if(doubleCheck != JOptionPane.YES_OPTION) {
            return;
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.dispose();
        System.exit(0);


    }



}
