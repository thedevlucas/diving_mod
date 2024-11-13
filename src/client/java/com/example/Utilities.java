package com.example;

import com.example.dialogue.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class Utilities {
    public static boolean shouldRender = true;

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

    public static boolean hasFullSuitOfArmorOn(PlayerEntity player) {
        ItemStack boots = player.getInventory().getArmorStack(0);
        ItemStack leggings = player.getInventory().getArmorStack(1);
        ItemStack tank = player.getInventory().getArmorStack(2);
        ItemStack mask = player.getInventory().getArmorStack(3);

        return boots.getItem().getTranslationKey().equals("item.diving_mod.diving_fins") &&
                leggings.getItem().getTranslationKey().equals("item.diving_mod.diving_leggins") &&
                tank.getItem().getTranslationKey().equals("item.diving_mod.diving_tank") &&
                mask.getItem().getTranslationKey().equals("item.diving_mod.diving_mask");
    }
}
