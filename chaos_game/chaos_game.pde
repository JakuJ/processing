class Point
{
  //position variables
  int x, y;
  //object constructor
  Point(int ix, int iy)
  {
    x = ix;
    y = iy;
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
    pixels[x+ width * y] = color(color_n, color_n, color_n);
  }
  //draw a white circle (used for vertices)
  void show()
  {
    ellipse(x, y, 10, 10);
  }
}
//set up basic colors
color white = color(255,255,255);
color black = color(0,0,0);
//initialize vertices ArrayList and the jumper Point
ArrayList <Point> points = new ArrayList <Point>();
Point jumper;
//useful variables
boolean working = false;
float divider = 2;
int rzut;

//initial setup
void setup()
{
  size(1200,800);
  stroke(white);
  fill(white);
  background(black);
  loadPixels();
  colorMode(HSB, 255, 255, 255);
  //place jumper in a random spot on the canvas
  jumper = new Point(round(random(width)), round(random(height)));
  updatePixels();
}  

//main loop
void draw()
{
  if(working && points.size() > 0)
  {  
    //to speed things up, draw 100000 points at once
    for (int i = 0; i < 100000; i++)
    {
      rzut = round(random(points.size()-1));
      jumper.x = round((jumper.x + points.get(rzut).x)/divider);
      jumper.y = round((jumper.y + points.get(rzut).y)/divider);
      jumper.update(); 
    }
    updatePixels();
    for (int i = 0; i < points.size(); i++)
    {
      points.get(i).show();
    }
  }  
}

//add a vertex
void mousePressed()
{
  points.add(new Point(mouseX, mouseY));
  //wyczernij planszę
  for (int i = 0; i < width * height; i++)
  {
    pixels[i] = black;
  }
  updatePixels();
  //pokaż duże kropki dla wierzchołków
  for (int i = 0; i < points.size(); i++)
  {
    points.get(i).show();
  }
}

//start or stop drawing
void keyPressed()
{
  if(working == false) working = true;
  else working = false;
}