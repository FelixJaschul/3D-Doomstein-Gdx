package com.src;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game {
    public static final int[][] map = {
        // Original map data here
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
