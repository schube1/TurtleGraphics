package view;
import model.Turtle;
import model.Point;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Panel extends JPanel {
    private final Turtle turtle;

    public Panel(Turtle turtle) {
        this.turtle = turtle;
        setPreferredSize(new Dimension(Turtle.WORLD_SIZE, Turtle.WORLD_SIZE));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        List<Point> path = turtle.getPath();

        for (int i =1; i < path.size(); i++){
            Point past = path.get(i-1);
            Point pres = path.get(i);

            if(pres.isPenDown()){
                g.setColor(pres.getColor());
                g.drawLine(past.getX(), past.getY(), pres.getX(), pres.getY());
            }

        }
        Point turtleLoc = path.get(path.size()-1); //***** get path size -1
        g.setColor(Color.GREEN);
        if(turtleLoc.isPenDown()){
            g.fillOval(turtleLoc.getX()-5,turtleLoc.getY()-5, 10, 10 );
        }else{
            g.drawOval(turtleLoc.getX()-5,turtleLoc.getY()-5, 10, 10 );
        }
    }
    public void refresh(){
        repaint();
    }
}
