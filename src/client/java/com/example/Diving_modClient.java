package com.example;

import com.example.dialogue.DialogueManager;
import com.example.dialogue.DialogueWindow;
import com.example.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Diving_modClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		AtomicInteger globalOxigen = new AtomicInteger();
		AtomicBoolean first = new AtomicBoolean(true);

		ModItems.registerModItems();
		HudRenderCallback.EVENT.register(DialogueManager::renderAll);

		MinecraftClient instance = MinecraftClient.getInstance();
		int DURATION = 240;

		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
			String playername = instance.getSession().getUsername();
			String UUID	= instance.getSession().getUuid();

			String query = "SELECT COUNT(*), current_oxygen FROM players WHERE playername = ?";
			try (Connection conn = MySQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, playername);
				ResultSet resultSet = stmt.executeQuery();

				if (resultSet.next()) {
					if (resultSet.getInt(1) > 0) {
						globalOxigen.set(resultSet.getInt("current_oxygen"));
					} else {
						query = "INSERT INTO players (playername, UUID, current_oxygen) VALUES (?, ?, ?)";
						PreparedStatement stmt2 = conn.prepareStatement(query);
						stmt2.setString(1, playername);
						stmt2.setString(2, UUID);
						stmt2.setInt(3, 0);
						stmt2.executeUpdate();
					}
				}
			} catch (SQLException e) {
				Diving_mod.LOGGER.info("ERROR: "+e.getMessage());
				e.printStackTrace();
			}
		});

		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
			String playername = instance.getSession().getUsername();
			first.set(true);

			String query = "UPDATE players SET current_oxygen = ? WHERE playername = ?";
			try (Connection conn = MySQLConnection.getConnection();
				 PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setInt(1, globalOxigen.get());
				stmt.setString(2, playername);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (instance.world == null) return;

			ClientPlayerEntity player = instance.player;
			if (player == null) return;

			if (player.isSubmergedInWater() && Utilities.hasFullSuitOfArmorOn(player)) {
				ItemStack oxygenHelmet = player.getInventory().getArmorStack(2);

				if (first.get())
				{
					oxygenHelmet.setDamage(globalOxigen.get());
					first.set(false);
				}

				int currentDamage = oxygenHelmet.getDamage();
				int maxDamage = oxygenHelmet.getMaxDamage();

				int setDamage = currentDamage + (maxDamage/DURATION)/20;

				if (oxygenHelmet.getDamage() >= maxDamage) {
					player.getInventory().armor.set(2, ItemStack.EMPTY);
				}
				else {
					DialogueWindow.setDamage(currentDamage, maxDamage);
					Utilities.addDialogue();
					oxygenHelmet.setDamage(setDamage);
					globalOxigen.set(setDamage);
				}
			}
		});
	}
}
