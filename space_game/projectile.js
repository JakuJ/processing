//Projectile object constructor
function Projectile(x, y, velocity, angle, looks, owner, damage) {
  this.x = x;
  this.y = y;
  this.velocity = velocity;
  this.angle = angle;
  this.owner = owner;
  this.damage = damage;
  this.looks = looks;
}
//update projectile's position
Projectile.prototype.update = function() {
  this.x += this.velocity * cos(this.angle);
  this.y += this.velocity * sin(this.angle);
}
//draw the projectile
Projectile.prototype.show = function() {
  stroke(this.looks);
  vertex(this.x, this.y);
}