package com.src.Doom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;

public class TextureManager {
    private final HashMap<Integer, Texture> textures;

    public TextureManager() {
        textures = new HashMap<>();
        loadTextures();
    }

    private void loadTextures() {
        textures.put(0, new Texture(Gdx.files.internal("D:/Felix/Dokumente/.GdxProjects/Doomstein/core/res/room/wood.jpg")));
        textures.put(1, new Texture(Gdx.files.internal("D:/Felix/Dokumente/.GdxProjects/Doomstein/core/res/room/redbrick.jpg")));
        textures.put(2, new Texture(Gdx.files.internal("D:/Felix/Dokumente/.GdxProjects/Doomstein/core/res/room/bluestone.jpg")));
        // Load other textures
    }

    public Texture getTexture(int textureNumber) {
        return textures.get(textureNumber);
    }

    public void dispose() {
        for (Texture texture : textures.values()) {
            texture.dispose();
        }
    }
}
