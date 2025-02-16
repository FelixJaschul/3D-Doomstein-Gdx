package com.src;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Raycaster {
    private final int width, height;
    private final Texture texture;
    private final Pixmap pixmap;
    private final TextureManager textures;

    public Raycaster(int width, int height, TextureManager textures) {
        this.width = width;
        this.height = height;
        this.textures = textures;
        pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        texture = new Texture(width, height, Pixmap.Format.RGB888);
    }

    public void render(Camera camera) {
        // Clear screen
        pixmap.setColor(Color.BLACK);
        pixmap.fill();

        // Raycasting loop
        for (int x = 0; x < width; x++) {
            // Calculate ray position and direction
            double cameraX = 2 * x / (double) width - 1; // x-coordinate in camera space
            double raycastDirectionX = camera.dir.x + camera.plane.x * cameraX;
            double raycastDirectionY = camera.dir.y + camera.plane.y * cameraX;

            // Which box of the map we're in
            int mapX = (int) camera.pos.x;
            int mapY = (int) camera.pos.y;

            // Length of ray from one x or y-side to next x or y-side
            double deltaDistanceX = Math.sqrt(1 + (raycastDirectionY * raycastDirectionY) / (raycastDirectionX * raycastDirectionX));
            double deltaDistanceY = Math.sqrt(1 + (raycastDirectionX * raycastDirectionX) / (raycastDirectionY * raycastDirectionY));

            double sideDistanceX, sideDistanceY; // Length of ray from current position to next x or y-side

            // Step direction and initial sideDist
            int stepX, stepY;
            boolean hit = false; // Was there a wall hit?
            int side = 0; // Was a NS or a EW wall hit?

            if (raycastDirectionX < 0) {
                stepX = -1;
                sideDistanceX = (camera.pos.x - mapX) * deltaDistanceX;
            } else {
                stepX = 1;
                sideDistanceX = (mapX + 1.0 - camera.pos.x) * deltaDistanceX;
            }

            if (raycastDirectionY < 0) {
                stepY = -1;
                sideDistanceY = (camera.pos.y - mapY) * deltaDistanceY;
            } else {
                stepY = 1;
                sideDistanceY = (mapY + 1.0 - camera.pos.y) * deltaDistanceY;
            }

            // Perform DDA
            while (!hit) {
                if (sideDistanceX < sideDistanceY) {
                    sideDistanceX += deltaDistanceX;
                    mapX += stepX;
                    side = 0;
                } else {
                    sideDistanceY += deltaDistanceY;
                    mapY += stepY;
                    side = 1;
                }

                // Check if ray has hit a wall
                if (Game.map[mapX][mapY] > 0) hit = true;
            }

            // Calculate distance projected on camera direction
            double perpWallDistance;
            if (side == 0) perpWallDistance = Math.abs((mapX - camera.pos.x + (double) (1 - stepX) / 2) / raycastDirectionX);
            else perpWallDistance = Math.abs((mapY - camera.pos.y + (double) (1 - stepY) / 2) / raycastDirectionY);

            // Calculate height of line to draw on screen
            int lineHeight = (int) (height / perpWallDistance);

            // Calculate lowest and highest pixel to fill in current stripe
            int drawStart = -lineHeight / 2 + height / 2;
            if (drawStart < 0) drawStart = 0;
            int drawEnd = lineHeight / 2 + height / 2;
            if (drawEnd >= height) drawEnd = height - 1;

            // Texturing calculations
            int textureNumber = Game.map[mapX][mapY] - 1; // Subtract 1 from map value to get texture index
            Texture wallTexture = textures.getTexture(String.valueOf(textureNumber));

            // Calculate value of wallX
            double wallX;
            if (side == 0) wallX = camera.pos.y + perpWallDistance * raycastDirectionY;
            else wallX = camera.pos.x + perpWallDistance * raycastDirectionX;

            wallX -= Math.floor(wallX);

            // X coordinate on the texture
            int texX = (int) (wallX * wallTexture.getWidth());
            if (side == 0 && raycastDirectionX > 0) texX = wallTexture.getWidth() - texX - 1;
            if (side == 1 && raycastDirectionY < 0) texX = wallTexture.getWidth() - texX - 1;

            // Draw the vertical stripe
            for (int y = drawStart; y < drawEnd; y++) {
                int texY = (int) (((y - drawStart) * wallTexture.getHeight()) / lineHeight);
                Color color = new Color(wallTexture.getTextureData().consumePixmap().getPixel(texX, texY));
                if (side == 1) color.mul(0.7f); // Make y-sides darker
                pixmap.setColor(color);
                pixmap.drawPixel(x, y);
            }
        }

        // Update texture with new pixmap data
        texture.draw(pixmap, 0, 0);
    }

    public void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(texture, 0, 0);
        batch.end();
    }

    public void dispose() {
        pixmap.dispose();
        texture.dispose();
    }
}
