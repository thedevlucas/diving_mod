package com.example.dialogue;

import com.example.Diving_mod;
import com.example.Utilities;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class DialogueWindow implements IDialogueWindow {
    private final MinecraftClient client;
    private final int duration;
    private long lastUpdateTime;
    private final Identifier oxigen_frame = new Identifier(Diving_mod.MOD_ID, "textures/gui/oxigen_tank_ui.png");

    private static int currentDamage = 0;
    private static int maxDamage = 400;

    public DialogueWindow(MinecraftClient client, int duration) {
        this.client = client;
        this.duration = duration;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public static void setDamage(int damage, int max) {
        currentDamage = damage;
        maxDamage = max;
    }

    @Override
    public void render(DrawContext context, float tickDelta) {
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        int height = 50;
        int width = 20;
        int x = 10;
        int y = screenHeight - height - 5;

        int fillHeight = (int)((1 - (double)currentDamage / maxDamage) * height);
        fillHeight = Math.min(fillHeight + 11, height);

        int fillY = y + (height - fillHeight);

        context.fill(x + 4, fillY + 9, x + (width - 3), y + (height - 2), 0x80FF0000);
        context.drawTexture(oxigen_frame, x, y, 0, 0, width, height, width, height);
    }

    @Override
    public boolean isDone() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastUpdateTime >= duration * 1000L);
    }
}
