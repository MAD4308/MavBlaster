package com.example.insy4308.mavblaster.objects;

import java.nio.FloatBuffer;

public class Square {
    private int program;
    private FloatBuffer squareVertices = null;
    private float centerX = 0.0f;
    private float centerY = 0.0f;
    private float width = 0.0f;
    private float height = 0.0f;
    private float rotation = 0.0f;

    public Square() {
        program = -1;
        centerX = 0.0f;
        centerY = 0.0f;
        width = 0.0f;
        height = 0.0f;
        rotation = 0.0f;
    }

    public Square(int program, FloatBuffer squareVertices, float centerX, float centerY, float width, float height, float rotation) {
        this.program = program;
        this.squareVertices = squareVertices;
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }

    public FloatBuffer getSquareVertices() {
        return squareVertices;
    }

    public void setSquareVertices(FloatBuffer squareVertices) {
        this.squareVertices = squareVertices;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

}
