package com.example.dialogue;

import com.example.Utilities;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;

public class DialogueWindow implements IDialogueWindow {
    private final MinecraftClient client;
    private final int duration;
    private long lastUpdateTime;

    public DialogueWindow(MinecraftClient client, int duration) {
        this.client = client;
        this.duration = duration;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    @Override
    public void render(DrawContext context, float tickDelta) {
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        TextRenderer textRenderer = client.textRenderer;

        int height = 240;
        int width = 450;
        int x = (screenWidth - width) / 2;
        int y = screenHeight - height - 60;

        context.drawText(textRenderer, "O₂", x + 65, y + height - textRenderer.fontHeight * 2, Utilities.rgba(255, 255, 255, 1f), true);
        context.drawText(textRenderer, "100%", x + 60, y + height - textRenderer.fontHeight, Utilities.rgba(255, 255, 255, 1f), true);

    }

    @Override
    public boolean isDone() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastUpdateTime >= duration * 1000L);
    }
}
