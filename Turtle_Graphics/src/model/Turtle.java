package model;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Turtle {
    private List<Point> path;
    public static final int WORLD_SIZE = 250;
    public Turtle(){
        this.path = new ArrayList<>();
        path.add(new Point(WORLD_SIZE/2, WORLD_SIZE/2,null, false));
    }
    public void addPoint(Point point){
        this.path.add(point);

    }
    public void clearPath(){
        this.path.clear();
        path.add(new Point(WORLD_SIZE/2, WORLD_SIZE/2, Color.BLACK, false));

    }
    public List<Point> getPath(){
        return this.path;
    }
}
