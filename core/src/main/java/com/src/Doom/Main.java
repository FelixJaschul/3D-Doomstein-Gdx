package com.src.Doom;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Game game;

    @Override
    public void create() {
        batch = new SpriteBatch();
        game = new Game();
        Gdx.input.setCursorCatched(true);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.update(Gdx.graphics.getDeltaTime());
        game.render(batch);
    }

    @Override
    public void dispose() {
        batch.dispose();
        game.dispose();
    }
}
