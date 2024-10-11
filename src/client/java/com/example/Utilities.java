package com.example;

import com.example.dialogue.*;
import net.minecraft.client.MinecraftClient;

public class Utilities {
    public static boolean shouldRender = true;

    public static int rgba(int red, int green, int blue, float alpha) {
        red = Math.max(0, Math.min(255, red));
        green = Math.max(0, Math.min(255, green));
        blue = Math.max(0, Math.min(255, blue));

        int alphaInt = Math.round(Math.max(0, Math.min(1, alpha)) * 255);
        return (alphaInt << 24) | (red << 16) | (green << 8) | blue;
    }


    public static void addDialogue() {
        synchronized (DialogueManager.windows) {
            for (IDialogueWindow window : DialogueManager.windows) {
                if (window instanceof DialogueWindow && !window.isDone()) {
                    DialogueManager.windows.remove(window);
                    break;
                }
            }
        }

        DialogueManager.addDialogueWindow(new DialogueWindow(MinecraftClient.getInstance(), 5));
        

    }
}
