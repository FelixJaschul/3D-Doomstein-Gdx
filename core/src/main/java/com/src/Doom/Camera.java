package com.src.Doom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class Camera {
    public Vector2 position = new Vector2();
    public Vector2 direction = new Vector2();
    public Vector2 plane = new Vector2();

    public Camera(float x, float y, float dx, float dy, float px, float py) {
        position.set(x, y);
        direction.set(dx, dy);
        plane.set(px, py);
    }

    public void update(float delta, int[][] map) {
        handleInput(delta, map);
        handleMouse(delta);
    }

    private void handleInput(float delta, int[][] map) {
        float moveSpeed = 2.8f;
        float speed = moveSpeed * delta;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) move(direction.x, direction.y, speed, map);
        if (Gdx.input.isKeyPressed(Input.Keys.S)) move(-direction.x, -direction.y, speed, map);
        if (Gdx.input.isKeyPressed(Input.Keys.D)) move(direction.y, -direction.x, speed, map);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) move(-direction.y, direction.x, speed, map);
    }

    private void move(float dx, float dy, float speed, int[][] map) {
        float newX = position.x + dx * speed;
        float newY = position.y + dy * speed;

        if (map[(int) newX][(int) position.y] == 0) position.x = newX;
        if (map[(int) position.x][(int) newY] == 0) position.y = newY;
    }

    private void handleMouse(float delta) {
        float mouseDX = -Gdx.input.getDeltaX();
        float rotSpeed = 1.8f;

        float rot = mouseDX * rotSpeed * delta;

        float oldDirX = direction.x;
        direction.x = direction.x * (float) Math.cos(rot) - direction.y * (float) Math.sin(rot);
        direction.y = oldDirX * (float) Math.sin(rot) + direction.y * (float) Math.cos(rot);

        float oldPlaneX = plane.x;
        plane.x = plane.x * (float) Math.cos(rot) - plane.y * (float) Math.sin(rot);
        plane.y = oldPlaneX * (float) Math.sin(rot) + plane.y * (float) Math.cos(rot);
    }
}
