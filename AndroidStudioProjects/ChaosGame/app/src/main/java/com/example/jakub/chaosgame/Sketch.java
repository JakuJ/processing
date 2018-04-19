package com.example.jakub.chaosgame;

// Sketch.java

import processing.core.PApplet;
import java.util.ArrayList;

class Sketch extends PApplet {
    class Point
    {
        //position variables
        private int x, y, radius;
        //object constructor
        Point(int ix, int iy, int ir)
        {
            x = ix;
            y = iy;
            radius = ir;
        }
        //determine closest vertex and place a pixel of color that is higher on HSB scale the closer it is to it
        //used for the jumper
        void update()
        {
            float [] distances = new float [points.size()];
            for(int i = 0; i<points.size(); i++)
            {
                distances[i] = sqrt(pow(x - points.get(i).x,2) + pow(y - points.get(i).y,2));
            }
            float r = min(distances);
            float color_n = 255 - (255 * r / (sqrt(pow(width,2) + pow(height,2))));
            pixels[x + width * y] = color(color_n, color_n, color_n);
        }
        //draw a white circle
        //used for vertices
        void show()
        {
            ellipse(x, y, radius, radius);
        }
    }

    //set up basic colors
    private int white = color(255, 255, 255);
    private int black = color(0, 0, 0);
    //initialize vertices ArrayList and the jumper Point
    private ArrayList<Point> points = new ArrayList<Point>();
    private Point jumper;
    //useful variables
    private boolean working = false;
    private float divisor = 2;

    //initial setup
    public void settings()
    {
        size(displayWidth, displayHeight);
    }

    public void setup() {
        stroke(white);
        fill(white);
        background(black);
        loadPixels();
        colorMode(HSB, 255, 255, 255);
        //place jumper in a random spot on the canvas
        jumper = new Point(round(random(width)), round(random(height)), 2);
        updatePixels();
    }

    //main loop
    public void draw() {
        if (working && points.size() > 0) {
            try{
                //to speed things up, draw 1000 points at once (more gets laggy)
                int i, rzut;
                for (i = 0; i < 1000; i++) {
                    rzut = round(random(points.size() - 1));
                    jumper.x = round((jumper.x + points.get(rzut).x) / divisor);
                    jumper.y = round((jumper.y + points.get(rzut).y) / divisor);
                    jumper.update();
                }
                updatePixels();
                for (i = 0; i < points.size(); i++) {
                    points.get(i).show();
                }
            }
            catch(Exception e)
            {
                background(black);
            }
        }
    }

    //add a vertex
    public void mousePressed() {
        int i;
        points.add(new Point(mouseX, mouseY, 20));
        //pokaż duże kropki dla wierzchołków
        for (i = 0; i < points.size(); i++) {
            points.get(i).show();
        }
    }

    //start or stop drawing
    public void keyPressed() {
        working = !working;
        if(!working)
        {
            points.clear();
            background(black);
            updatePixels();
        }
    }
    //reset everything
    public void keyReleased()
    {
        working = false;
        points.clear();
        background(black);
        loadPixels();
    }
}