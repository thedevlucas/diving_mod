package com.example.dialogue;

import com.example.Utilities;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;

public class DialogueWindow implements IDialogueWindow {
    private final MinecraftClient client;
    private final String text;
    private final int duration;
    private long lastUpdateTime;
    private int currentIndex;
    private boolean textFullyDisplayed;

    public DialogueWindow(MinecraftClient client, String text, int duration, int typingSpeed) {
        this.client = client;
        this.text = text;
        this.duration = duration;
        this.lastUpdateTime = System.currentTimeMillis();
        this.currentIndex = 0;
        this.textFullyDisplayed = false;
    }

    @Override
    public void render(DrawContext context, float tickDelta) {
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        PlayerEntity plr = MinecraftClient.getInstance().player;
        TextRenderer textRenderer = client.textRenderer;

        int height = 240;
        int width = 450;
        int x = (screenWidth - width) / 2;
        int y = screenHeight - height - 60;

        context.drawText(textRenderer, "seeee", x + 60, y + height - textRenderer.fontHeight, Utilities.rgba(255, 255, 255, 1f), true);

    }

    @Override
    public boolean isDone() {
        long currentTime = System.currentTimeMillis();
        return textFullyDisplayed && (currentTime - lastUpdateTime >= duration * 1000L);
    }
}
