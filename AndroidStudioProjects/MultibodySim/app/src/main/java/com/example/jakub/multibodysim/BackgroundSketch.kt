package com.example.jakub.multibodysim

import processing.core.PApplet

class BackgroundSketch : PApplet()
{
    inner class Point (var x : Float, var y : Float)
    {
        var rx : Float ; var ry : Float ; var z : Float
        init {
            rx = x
            ry = y
            z = (sin(3*rx/width*TWO_PI) + sin(3*ry/height*TWO_PI))
        }
        fun update (vx : Float, vy : Float)
        {
            rx += vx
            ry += vy
            z = (sin(3*rx/width*TWO_PI) + sin(3*ry/height*TWO_PI))
        }
    }

    private var points = mutableListOf<Point>()

    override fun settings() {
        size(displayWidth, displayHeight)
    }

    override fun setup() {
        background(0)
        colorMode(HSB)
        noFill()
        strokeWeight(6f)
        val numX = 9
        val numY = 12
        val xs : List<Float> = (0 .. numX).map { x -> x * width/numX.toFloat()}
        val ys : List<Float> = (0 .. numY).map { x -> x * height/numY.toFloat()}
        for (x in xs)
        {
            for (y in ys)
            {
                points.add(Point(x, y))
            }
        }
    }

    override fun draw()
    {
        background(0)
        for( p in points)
        {
            strokeWeight(floor(map(p.z, -2f, 2f, 8f, 4f)).toFloat())
            stroke(map(p.z, -2f, 2f, 0f, 255f), 255f, 255f)
            ellipse(p.x, p.y, 10f, 10f)
            p.update(5f, 0f)
        }
    }
}