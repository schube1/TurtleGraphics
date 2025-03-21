package controller;

import model.Turtle;
import model.Point;
import view.Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private final Turtle turtle;
    private final Panel panel;
    private boolean penIsDown = false;
    private Color penColor = Color.BLACK;

    public Controller(Turtle turtle, Panel panel) {
        this.turtle = turtle;
        this.panel = panel;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        String action = e.getActionCommand();
        Point latestPoint = turtle.getPath().get(turtle.getPath().size()-1);
        int newX = latestPoint.getX();
        int newY = latestPoint.getY();
        int steps = 0;
        if(action.equals("North") || action.equals("South") || action.equals("East") ||action.equals("West")) {
            String inputValue = JOptionPane.showInputDialog(null, "How many steps?", "Distance", JOptionPane.QUESTION_MESSAGE);
            if (inputValue != null && !inputValue.isEmpty()) {
                try {
                    steps = Integer.parseInt(inputValue);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "not a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                return; //incase the user cancels or doesnt enter
            }
        }
        switch(action){
            case "North":
                newY -= steps;
                if(newY<0)newY=0;
                break;
            case "South":
                newY += steps;
                if(newY>Turtle.WORLD_SIZE)newY = Turtle.WORLD_SIZE;
                break;
            case "East":
                newX += steps;
                if(newX>Turtle.WORLD_SIZE)newX=Turtle.WORLD_SIZE;
                break;
            case "West":
                newX -= steps;
                if(newX<0)newX=0;
                break;
            case "Pen":
                penIsDown = !penIsDown;
                if(!turtle.getPath().isEmpty()){
                    Point endPoint = turtle.getPath().get(turtle.getPath().size()-1);
                    turtle.addPoint(new Point (endPoint.getX(), endPoint.getY(), endPoint.getColor(), penIsDown));
                }
                panel.refresh();
                return;
            case "Color":
                Color newColor = JColorChooser.showDialog(null, "Pick a color", penColor);
                if(newColor!=null){
                    penColor = newColor;
                }

                return;

            case "Clear":
                turtle.clearPath();
                penIsDown = false;
                panel.refresh();
                return;

        }

    turtle.addPoint(new Point(newX, newY, penColor, penIsDown));
    panel.refresh();

    }
}
