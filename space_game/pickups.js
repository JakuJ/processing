function Pickup (x, y, type){
  this.x = x;
  this.y = y;
  this.type = type;
}

Pickup.prototype.show = function () {
  push();
  translate(this.x, this.y);
  switch (this.type) {
    case pickupType.WEAPON:
      fill(255,0,0);
      rect(0, 0, 20, 20);
      break;
    case pickupType.HEALTH:
      fill(0,255,0);
      rect(0, 0, 20, 20);
      break;
  }
  pop();
}