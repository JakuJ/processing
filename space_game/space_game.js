//global variables
var level,
    killCount,
    running,
    pickupType;
//declaring objects
var player,
    enemies,
    projectiles,
    pickups;
//game setup
function setup() {
  //initialize variables
  level = 1;
  killCount = 0;
  running = false;
  pickupType = {
    WEAPON : 0,
    HEALTH : 1,
    NUMBER_OF_PICKUPS : 2
  }
  //initialize canvas
  createCanvas(windowWidth, windowHeight);
  background('black');
  frameRate(120);
  textSize(18);
  noStroke();
  smooth();
  fill('white');
  text("Press any key to start ...", width/2 - 100, height/2);
  //create the player and enemy ships, initialize projectiles array
  player = new Player(width/2, height/2, 100); 
  projectiles = new Array();
  pickups = new Array();
  newLevel();
}

//main game loop
function draw() {
  if (running) {
    background('black');  
    //move and draw you
    player.move(mouseX, mouseY);
    player.show();
    //move and draw enemies
    for (var i = 0; i < enemies.length; i++) {
      enemies[i].move(player.x, player.y);
      enemies[i].show();
    }
    //draw pickups
    for (var i = 0; i < pickups.length; i++) {
      pickups[i].show();
    }
    //pick them up
    for (var i = 0; i < pickups.length; i++) {
      if (abs(pickups[i].x - player.x) < 20 && abs(pickups[i].y - player.y) < 20) {
        switch(pickups[i].type){
          case pickupType.WEAPON:
            player.weaponLvl += 1;
            break;
          case pickupType.HEALTH:
            player.hp += 10; 
            break;
        }
        pickups.splice(i, 1);
      }
    }
    //display stats
    fill('white');
    text(round(frameRate()), width - 20, 18);
    text("Level: " + level, 0, 18);
    text("Kill count: " + killCount, 0, 36);
    //enemies shoot each second
    if (frameCount % 60 == 0) {
      for (var i = 0; i < enemies.length; i++) {
        enemies[i].shoot(1, 20);
      }
    }
    //shooting
    beginShape(POINTS);
    strokeWeight(10);
    for (var i = 0; i < projectiles.length; i++) {
      //propagate and draw each projectile
      projectiles[i].update();
      projectiles[i].show();
      //check if projectile in canvas, delete it if not
      if (projectiles[i].x > width || projectiles[i].x < 0 || 
        projectiles[i].y < 0 || projectiles[i].y > height) {
        projectiles.splice(i, 1);
        break;
      }
      //damage player
      if (projectiles[i].owner != 0 && 
        abs(projectiles[i].x - player.x) < 10 && 
        abs(projectiles[i].y - player.y) < 10) {
        player.hp -= projectiles[i].damage;
        projectiles.splice(i, 1);
        //restart game if player is dead
        if (player.hp < 0) {
          background('black');
          fill('white');
          noStroke();
          text("Level of your demise: " + level + 
          "\nEnemies killed: " + killCount + 
          "\nYou are dead. Press any key to restart ...", 
          width/2 - 150, height/2 - 50);
          level = 1;
          killCount = 0;
          running = false;
          player = new Player(width/2, height/2, 100); 
          newLevel();
        }
        break;
      }
      //damage enemies
      for (var j = 0; j < enemies.length; j++) {
        if (projectiles[i].owner == 0 && 
          abs(projectiles[i].x - enemies[j].x) < 10 && 
          abs(projectiles[i].y - enemies[j].y) < 10) {
          enemies[j].hp -= projectiles[i].damage;
          projectiles.splice(i, 1);
          //kill enemies
          if (enemies[j].hp < 0) {
            enemies.splice(j, 1);
            killCount += 1;
          }
          //ascend a level
          if (enemies.length < 1) {
            level += 1;
            newLevel();
          }
          break;
        }
      }
    }
    endShape();
    noStroke();
  }
}
//new level
function newLevel()
{
  projectiles = [];
  //create new enemies
  enemies = new Array(level);
  var x, y;
  for (var i = 0; i < enemies.length; i++) {
    x = random(width);
    y = random(height);
    if ( abs(player.x - x) > 100 && abs(player.y - y) > 100) {
      enemies[i] = new Enemy(x, y, random(10 * level));
    } else {
      i--;
    }
  }
  //add a random pickup to the canvas
  pickups.push(new Pickup(random(width), random(height), floor(random(pickupType.NUMBER_OF_PICKUPS))));
}
//LMP to shoot
function mouseClicked() {
  player.shoot();
}
//pause game
function keyPressed() {
  if (running == false) {
    running = true;
  }
  else {
    running = false;
  }
}
//support for resizing a window
function windowResized() {
  resizeCanvas(windowWidth, windowHeight);
  redraw();
}