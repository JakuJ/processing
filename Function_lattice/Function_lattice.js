Point = function(x,y) {
  this.x = x;
  this.y = y;
  this.rx = x;
  this.ry = y;
  this.z = (sin(3*this.rx/width*TWO_PI) + sin(3*this.ry/height*TWO_PI));
}
Point.prototype.update = function(vx, vy){
  this.rx += vx;
  this.ry += vy;
  this.z = (sin(3*this.rx/width*TWO_PI) + sin(3*this.ry/height*TWO_PI));
}

var points;

function setup() {
  createCanvas(windowWidth, windowHeight);
  background('black');
  colorMode(HSB);
  noFill();
  strokeWeight(6);
  points = [];
  for(var i = 0; i < width/10; i++) {
    for(var j = 0; j < height/10; j++) {
      points.push(new Point(i*10, j*10));
    }
  }
}

function draw() {
  background('black');
  for(var i = 0; i<points.length; i++){
    strokeWeight(floor(map(points[i].z, -2, 2, 8, 4)));
    stroke(map(points[i].z, -2, 2, 0, 255), 255, 255);
    point(points[i].x, points[i].y);
    points[i].update(5, 0);
  }
}

//support for resizing a window
function windowResized() {
  resizeCanvas(windowWidth, windowHeight);
  redraw();
}
