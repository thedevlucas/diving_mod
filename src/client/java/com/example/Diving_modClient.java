package com.example;

import com.example.dialogue.DialogueManager;
import com.example.item.ModItems;
import com.example.sound.ModSounds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

public class Diving_modClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModSounds.register();
		ModItems.registerModItems();
		HudRenderCallback.EVENT.register(DialogueManager::renderAll);

		ServerTickEvents.END_SERVER_TICK.register((MinecraftServer server) -> {
			server.getPlayerManager().getPlayerList().forEach(player -> {
				if (player.isSubmergedInWater()) {
					Utilities.addDialogue();
				}
			});
		});
	}
}