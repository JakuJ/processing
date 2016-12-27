class Bulb
{
  //bulb's variables
  float speed = random (2,10);
  float light = map(speed, 2, 10, 0 , 255);
  PVector position = new PVector(0,0,0);
  PVector velocity = PVector.random3D().mult(speed);
  
  //update bulb's position
  void update()
  {
    position.add(velocity);
    //bouncing off the walls
    if((position.x < -1 * width / 2 && velocity.x < 0) || (position.x > width / 2 && velocity.x > 0)) velocity.x *= -1;
    if((position.y < -1 * height / 2 && velocity.y < 0) || (position.y > height / 2 && velocity.y > 0)) velocity.y *= -1;
    if((position.z < -1 * width / 2 && velocity.z < 0) || (position.z > width / 2 && velocity.z > 0)) velocity.z *= -1;
    //set colour and add to the shape
    stroke(light,255,255);
    vertex(position.x, position.y, position.z);
  }
}

//declare bulbs
Bulb[] lampki = new Bulb[1000];

//initial setup
void setup()
{
  //size of the canvas
  size(1000,1000,P3D);
  //Hue-Saturation-Brightness color mode
  colorMode(HSB);
  //lines only
  noFill();
  //create bulbs
  for(int i = 0; i < lampki.length; i++)
  {
    lampki[i] = new Bulb();
  }
}

int shape_mode = POINTS;

void draw()
{
  //refresh the black background
  background(0);
  //set camera viewing angle
  camera(mouseX - width/2, mouseY - height/2, 1500, 0, 0, 0, 0, 1, 0);
  //draw a box
  stroke(0,0,255);
  strokeWeight(2);
  box(1000);
  //draw all the bulbs
  strokeWeight(4);
  beginShape(shape_mode);
  for(int i = 0; i < lampki.length; i++)
  {
    lampki[i].update();
  }
  endShape();
}

//press any mouse button to change drawing modes
void mousePressed()
{
  if (shape_mode == POINTS) shape_mode = LINES;
  else shape_mode = POINTS;
}

//press any key to reverse bulbs' velocities
void keyPressed()
{
  for(int i = 0; i < lampki.length; i++)
  {
    lampki[i].velocity.mult(-1);
  }
}