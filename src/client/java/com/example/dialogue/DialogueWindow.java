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

    public DialogueWindow(MinecraftClient client, int duration) {
        this.client = client;
        this.duration = duration;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    @Override
    public void render(DrawContext context, float tickDelta) {
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();


        int height = 240;
        int width = 450;
        int x = (screenWidth - width) / 2;
        int y = screenHeight - height - 60;


        context.drawTexture(oxigen_frame, x + 60, 50, 0, 0, 80, 15, 80, 15);

    }

    @Override
    public boolean isDone() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastUpdateTime >= duration * 1000L);
    }
}
