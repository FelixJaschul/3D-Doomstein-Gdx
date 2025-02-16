package com.src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class Camera {
    public Vector2 pos = new Vector2();
    public Vector2 dir = new Vector2();
    public Vector2 plane = new Vector2();

    public Camera(float x, float y, float dx, float dy, float px, float py) {
        pos.set(x, y);
        dir.set(dx, dy);
        plane.set(px, py);
    }

    public void update(float delta, int[][] map) {
        handleInput(delta, map);
        handleMouse(delta);
    }

    private void handleInput(float delta, int[][] map) {
        float moveSpeed = 2.8f;
        float speed = moveSpeed * delta;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) move(dir.x, dir.y, speed, map);
        if (Gdx.input.isKeyPressed(Input.Keys.S)) move(-dir.x, -dir.y, speed, map);
        if (Gdx.input.isKeyPressed(Input.Keys.D)) move(dir.y, -dir.x, speed, map);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) move(-dir.y, dir.x, speed, map);
    }

    private void move(float dx, float dy, float speed, int[][] map) {
        float newX = pos.x + dx * speed;
        float newY = pos.y + dy * speed;

        if (map[(int) newX][(int) pos.y] == 0) pos.x = newX;
        if (map[(int) pos.x][(int) newY] == 0) pos.y = newY;
    }

    private void handleMouse(float delta) {
        float mouseDX = -Gdx.input.getDeltaX();
        float rotSpeed = 1.8f;

        float rot = mouseDX * rotSpeed * delta;

        float oldDirX = dir.x;
        dir.x = dir.x * (float) Math.cos(rot) - dir.y * (float) Math.sin(rot);
        dir.y = oldDirX * (float) Math.sin(rot) + dir.y * (float) Math.cos(rot);

        float oldPlaneX = plane.x;
        plane.x = plane.x * (float) Math.cos(rot) - plane.y * (float) Math.sin(rot);
        plane.y = oldPlaneX * (float) Math.sin(rot) + plane.y * (float) Math.cos(rot);
    }
}
