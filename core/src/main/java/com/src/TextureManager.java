package com.src;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;

public class TextureManager {
    private final HashMap<String, Texture> textures;

    public TextureManager() {
        textures = new HashMap<>();
        loadTextures();
    }

    private void loadTextures() {
        textures.put("wood", new Texture(Gdx.files.internal("D:/Felix/Dokumente/.GdxProjects/Doomstein/core/res/room/wood.jpg")));
        textures.put("bluestone", new Texture(Gdx.files.internal("D:/Felix/Dokumente/.GdxProjects/Doomstein/core/res/room/bluestone.jpg")));
        // Load other textures
    }

    public Texture getTexture(String name) {
        return textures.get(name);
    }

    public void dispose() {
        for (Texture texture : textures.values()) {
            texture.dispose();
        }
    }
}
