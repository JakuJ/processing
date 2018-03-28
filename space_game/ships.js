//Ship object constructor
function Ship(x, y, hp) {
  this.hp = hp;
  this.x = x;
  this.y = y;
  this.angle = random(TWO_PI);
  this.weaponLvl = 1;
}
//moving a Ship
Ship.prototype.move = function(desiredX, desiredY) {
  var dx = desiredX - this.x;
  var dy = desiredY - this.y;
  this.angle = atan2(dy, dx);
  this.applyForce(dx, dy);
  this.x += this.velocity * cos(this.angle);
  this.y += this.velocity * sin(this.angle);
}
//draw a Ship
Ship.prototype.show = function () {
  var looks = lerpColor(color(255, 0, 0), color(0, 255, 0), this.hp / 100);
  fill(looks);
  push();
  translate(this.x, this.y);
  rotate(this.angle);
  triangle(+20, 0, - 10, -10, -10, 10);
  pop();
}
//Player object constructor
function Player (x, y, hp) {
  Ship.call(this, x, y, hp);
  this.velocity = 1;
}
//Player inherits from Ship
Player.prototype = Object.create(Ship.prototype);
//change Player's velocity 
Player.prototype.applyForce = function(dx, dy) {
  var distance = sqrt(pow(dx, 2) + pow(dy, 2));
  this.velocity = distance / 60;
}
//Player shoots different projectiles than Enemies
Player.prototype.shoot = function () {
  for(var i = 0; i < this.weaponLvl; i++) {
    projectiles.push(new Projectile(this.x, this.y, 2 * this.velocity, this.angle + random(-0.15, 0.15), color(255,50,255), owner = 0, damage = 5 * this.velocity));
  }
}
//Player has a health bar
Player.prototype.show = function () {
  var looks = lerpColor(color(255, 0, 0), color(0, 255, 0), this.hp / 100);
  push();
  translate(this.x, this.y);
  fill(looks);
  rect( -20, 20, (this.hp / 100 * 40), 4);
  rotate(this.angle);
  fill(0,0,255);
  triangle(+20, 0, - 10, -10, -10, 10);
  pop();
}
//Enemy ship constructor
function Enemy (x, y, hp) {
  Ship.call(this, x, y, hp);
  this.velocity = random(0.5, 2);
  this.weaponLvl = ceil(random(level));
}
//Enemy inherits from Ship
Enemy.prototype = Object.create(Ship.prototype);
//Enemies have constant speed
Enemy.prototype.applyForce = function() {
  null;
}
//Enemies can shoot too
Enemy.prototype.shoot = function () {
  for(var i = 0; i < this.weaponLvl; i++) {
    projectiles.push(new Projectile(this.x, this.y, 2 * this.velocity, this.angle + random(-0.3, 0.3), color(255,255,0), owner = 1, damage = 10));
  }
}