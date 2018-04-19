package com.example.jakub.multibodysim

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector

class GameSketch : PApplet()
{
    private var running = false

    private var balls : MutableList<Ball> = mutableListOf()

    private var x0 = 0f
    private var y0 = 0f
    private var t0 : Int = 0
    private var radius : Float = 0f

    override fun settings() {
        size(displayWidth, displayHeight)
    }

    override fun setup() {
        colorMode(HSB, 360f, 255f, 255f)
        strokeWeight(3f)
        background(0)
    }

    override fun draw() {
        background(0)

        if(mousePressed)
        {
            radius = (frameCount - t0).toFloat()
            stroke(255f,0f,255f)
            line(x0, y0, mouseX.toFloat(), mouseY.toFloat())
            noStroke()
            fill(255)
            ellipse(x0, y0, radius * 2, radius * 2)
        }
        if(!balls.isEmpty()) {
            for (b in balls) {
                b.update()
                b.display()
                b.checkBoundaryCollision()
            }
            //balls collision detection
            for (x in 0 .. (balls.size - 1)) for (y in (x + 1) .. (balls.size - 1)) balls[x].checkCollision(balls[y])
        }
    }

    override fun mousePressed()
    {
        t0 = frameCount
        x0 = mouseX.toFloat()
        y0 = mouseY.toFloat()

    }
    override fun mouseReleased()
    {
        //check if overlapping with any other ball
        balls.forEach { b -> if (PVector.sub(b.position, PVector(x0, y0)).mag() < radius + b.radius) return }
        //create new balls
        balls.add(Ball(x0, y0, radius, PVector(x0 - mouseX.toFloat(), y0 - mouseY.toFloat()).mult(0.05f)))
    }


    inner class Ball(x: Float, y: Float, var radius: Float, private var velocity : PVector) {

        var position: PVector = PVector(x, y)
        private var m: Float = radius * radius * 0.01f

        fun update() {
            position.add(velocity)
        }

        fun checkBoundaryCollision() {
            when
            {
                radius > width - position.x -> {
                    position.x = width - radius
                    velocity.x *= -1f
                }
                radius > position.x -> {
                    position.x = radius
                    velocity.x *= -1f
                }
            }
            when
            {
                radius > height - position.y -> {
                    position.y = height - radius
                    velocity.y *= -1f
                }
                radius > position.y -> {
                    position.y = radius
                    velocity.y *= -1f
                }
            }
        }

        fun checkCollision(other: Ball) {

            // Get distances between the balls components
            val distanceVect = PVector.sub(other.position, position)

            // Calculate magnitude of the vector separating the balls
            val distanceVectMag = distanceVect.mag()

            // Minimum distance before they are touching
            val minDistance = radius + other.radius

            if (distanceVectMag < minDistance) {
                val distanceCorrection = ((minDistance - distanceVectMag) / 2.0).toFloat()
                val d = distanceVect.copy()
                val correctionVector = d.normalize().mult(distanceCorrection)
                other.position.add(correctionVector)
                position.sub(correctionVector)

                // get angle of distanceVect
                val theta = distanceVect.heading()
                // precalculate trig values
                val sine = PApplet.sin(theta)
                val cosine = PApplet.cos(theta)

                /* bTemp will hold rotated ball positions. You
       just need to worry about bTemp[1] position*/
                val bTemp = arrayOf(PVector(), PVector())

                /* this ball's position is relative to the other
       so you can use the vector between them (bVect) as the
       reference point in the rotation expressions.
       bTemp[0].position.x and bTemp[0].position.y will initialize
       automatically to 0.0, which is what you want
       since b[1] will rotate around b[0] */
                bTemp[1].x = cosine * distanceVect.x + sine * distanceVect.y
                bTemp[1].y = cosine * distanceVect.y - sine * distanceVect.x

                // rotate Temporary velocities
                val vTemp = arrayOf(PVector(), PVector())

                vTemp[0].x = cosine * velocity.x + sine * velocity.y
                vTemp[0].y = cosine * velocity.y - sine * velocity.x
                vTemp[1].x = cosine * other.velocity.x + sine * other.velocity.y
                vTemp[1].y = cosine * other.velocity.y - sine * other.velocity.x

                /* Now that velocities are rotated, you can use 1D
       conservation of momentum equations to calculate
       the final velocity along the x-axis. */
                val vFinal = arrayOf(PVector(), PVector())

                // final rotated velocity for b[0]
                vFinal[0].x = ((m - other.m) * vTemp[0].x + 2f * other.m * vTemp[1].x) / (m + other.m)
                vFinal[0].y = vTemp[0].y

                // final rotated velocity for b[0]
                vFinal[1].x = ((other.m - m) * vTemp[1].x + 2f * m * vTemp[0].x) / (m + other.m)
                vFinal[1].y = vTemp[1].y

                // hack to avoid clumping
                bTemp[0].x += vFinal[0].x
                bTemp[1].x += vFinal[1].x

                /* Rotate ball positions and velocities back
       Reverse signs in trig expressions to rotate
       in the opposite direction */
                // rotate balls
                val bFinal = arrayOf(PVector(), PVector())

                bFinal[0].x = cosine * bTemp[0].x - sine * bTemp[0].y
                bFinal[0].y = cosine * bTemp[0].y + sine * bTemp[0].x
                bFinal[1].x = cosine * bTemp[1].x - sine * bTemp[1].y
                bFinal[1].y = cosine * bTemp[1].y + sine * bTemp[1].x

                // update balls to screen position
                other.position.x = position.x + bFinal[1].x
                other.position.y = position.y + bFinal[1].y

                position.add(bFinal[0])

                // update velocities
                velocity.x = cosine * vFinal[0].x - sine * vFinal[0].y
                velocity.y = cosine * vFinal[0].y + sine * vFinal[0].x
                other.velocity.x = cosine * vFinal[1].x - sine * vFinal[1].y
                other.velocity.y = cosine * vFinal[1].y + sine * vFinal[1].x
            }
        }

        fun display() {
            noStroke()
            fill(map(velocity.mag(), 0f, 20f, 240f, 0f), 255f, 255f)
            ellipse(position.x, position.y, radius * 2, radius * 2)
        }
    }
}