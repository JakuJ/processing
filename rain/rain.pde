class Drop
{
  float x = random(width);
  float y = random(-500,-100);
  float z = random (0, 20);
  float fallspeed = map (z, 0, 20, 1, 25);
  float drop_width = map(z, 0, 20, 1, 2);
  float drop_height = map (z, 0, 20, 10, 30);
  
  void fall()
  {
    y += fallspeed;
    fallspeed += 0.1; // gravity
    
    if(y > height || x < 0 || x > width)
    {
      x = random(width);
      y = random(-500,-100);
      fallspeed = map (z, 0, 20, 5, 15);
    }
  }
  void show()
  {
    strokeWeight(drop_width);
    line(x, y, x, y + drop_height);
  }
  
}

Drop[] drops = new Drop[1000];

void setup()
{
  size(1000,600);
  stroke(70,70,230);
  fill(0,0,255);
    for(int i = 0; i < drops.length; i++)
    {
      drops[i] = new Drop();
    }
}
void draw()
{
  background(230,230,250);
  for(int i = 0; i < drops.length; i++)
    {
      drops[i].fall();
      drops[i].show();
    }
}