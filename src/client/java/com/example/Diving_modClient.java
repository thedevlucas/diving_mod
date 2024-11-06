package com.example;

import com.example.dialogue.DialogueManager;
import com.example.item.ModItems;
import com.example.sound.ModSounds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class Diving_modClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModSounds.register();
		ModItems.registerModItems();
		HudRenderCallback.EVENT.register(DialogueManager::renderAll);

		MinecraftClient instance = MinecraftClient.getInstance();

		int DURATION = 240; //Seconds

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (instance.world == null) return;

			ClientPlayerEntity player = instance.player;
			if (player == null) return;

			if (player.isSubmergedInWater() && Utilities.hasFullSuitOfArmorOn(player)) {
				ItemStack oxygenHelmet = player.getInventory().getArmorStack(2);
				int currentDamage = oxygenHelmet.getDamage();
				int maxDamage = oxygenHelmet.getMaxDamage();

				int setDamage = currentDamage + (maxDamage/DURATION)/20;
				Diving_mod.LOGGER.info("anshe");
				if (oxygenHelmet.getDamage() >= maxDamage) {
					player.getInventory().armor.set(2, ItemStack.EMPTY);

				}
				else {
					Utilities.addDialogue();
					oxygenHelmet.setDamage(setDamage);
				}
			}
		});
	}
}