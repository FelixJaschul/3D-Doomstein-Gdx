package com.src.Doom;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game {
    public static final int[][] map = {
        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
        {2,0,0,0,0,0,0,0,2,0,0,0,0,0,0,2},
        {2,0,1,1,1,1,1,0,0,0,0,0,0,0,0,2},
        {2,0,1,0,0,0,1,0,1,0,0,0,0,0,0,2},
        {2,0,1,0,0,0,1,0,1,1,1,0,0,2,2,2},
        {2,0,1,0,0,0,1,0,1,0,0,0,0,0,0,2},
        {2,0,1,0,0,0,1,0,1,0,0,0,0,0,0,2},
        {2,0,0,0,0,0,0,0,1,0,0,0,0,0,0,2},
        {2,2,2,0,0,0,1,1,1,1,1,0,0,2,2,2},
        {2,0,0,0,0,0,1,0,0,0,0,0,0,0,0,2},
        {2,0,0,0,0,0,1,0,0,0,0,0,0,0,0,2},
        {2,0,0,0,0,0,1,0,0,1,1,1,1,0,0,2},
        {2,0,0,0,0,0,1,0,0,1,1,1,1,0,0,2},
        {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
        {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}
    };

    private final Camera camera;
    private final Raycaster raycaster;
    private final TextureManager textures;

    public Game() {
        camera = new Camera(4.5f, 4.5f, 1, 0, 0, -0.66f);
        textures = new TextureManager();
        raycaster = new Raycaster(480, 640, textures);
    }

    public void update(float delta) {
        camera.update(delta, map);
    }

    public void render(SpriteBatch batch) {
        raycaster.render(camera);
        raycaster.draw(batch);
    }

    public void dispose() {
        textures.dispose();
        raycaster.dispose();
    }
}
