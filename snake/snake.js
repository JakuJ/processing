var apple, 
    speed;

function Snake(x, y) {
  this.x = x;
  this.y = y;
  this.vx = speed;
  this.vy = 0;
  this.body = [[this.x, this.y], [this.x - speed, this.y], [this.x - 2 * speed, this.y]];
}

Snake.prototype.move = function(ate) {
  this.x = mod((this.x + this.vx), width);
  this.y = mod((this.y + this.vy), height);
  this.body.unshift([this.x, this.y]);
  if (!ate) {
    this.body.pop();
  }
  for (var i = 1; i < this.body.length; i++) {
    if (this.x == this.body[i][0] && this.y == this.body[i][1]) {      
      for (var j = i; j < this.body.length; ) {
        this.body.pop();
      }
      break;
    }
  }
}

Snake.prototype.show = function () {
  fill(0, 255, 0);
  for (var i = 0; i < this.body.length; i++) {
    rect(this.body[i][0], this.body[i][1], 0.85 * speed, 0.85 * speed);
  }
}

function mod(n, m) {
  return ((n % m) + m) % m;
}

function new_apple() {
  var x, y, good;
  while (true) {
    x = floor(random() * width / speed) * speed;
    y = floor(random() * height / speed) * speed;
    good = true;
    for (var i = 0; i < player.body.length; i++) {
      if (player.body[i][0] == x && player.body[i][1] == y) {
        good = false;
      }
    }
    if (good) {
      apple = [x, y];
      return;
    }
  }
}

function setup() {
  createCanvas(640, 640);
  background('black');
  frameRate(20);
  textSize(16);
  noStroke();
  speed = 20;
  score = 0;
  player = new Snake(height/2, width/2);
  new_apple();
}

function draw() {
  bugfix = false;
  background('black');
  text("Score: " + (player.body.length * 10 - 30), 30, 30);
  if (player.x == apple[0] && player.y == apple[1]) {
    new_apple();
    player.move(true);
  } else {
    player.move(false);
  }
  fill(255, 0, 0);
  rect(apple[0], apple[1], 0.85 * speed, 0.85 * speed);
  player.show();
}

function keyPressed() {
  switch(keyCode) {
  case 37:
    if (player.body[1][0] < player.x || player.body[1][0] - player.x == width - speed) {
      break;
    }
    player.vx = -1 * speed;
    player.vy = 0;
    break;
  case 38:
    if (player.body[1][1] < player.y || player.body[1][1] - player.y == height - speed) {
      break;
    }
    player.vx = 0;
    player.vy = -1 * speed;
    break;
  case 39:
    if (player.body[1][0] > player.x || player.x - player.body[1][0] == width - speed) {
      break;
    }
    player.vx = speed;
    player.vy = 0;
    break;
  case 40:
    if (player.body[1][1] > player.y || player.y - player.body[1][1] == height - speed) {
      break;
    }
    player.vx = 0;
    player.vy = speed;
    break;
  default:
    break;
  }
}