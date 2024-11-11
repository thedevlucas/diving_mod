package com.example;

import com.example.dialogue.DialogueManager;
import com.example.item.ModItems;
import com.example.sound.ModSounds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Diving_modClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModSounds.register();
		ModItems.registerModItems();
		HudRenderCallback.EVENT.register(DialogueManager::renderAll);

		MinecraftClient instance = MinecraftClient.getInstance();
		int DURATION = 5;

		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {

			String playername = instance.getSession().getUsername();
			String UUID	= instance.getSession().getUuid();

			String query = "SELECT COUNT(*) FROM players WHERE playername = ?";
			try (Connection conn = MySQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, playername);
				ResultSet resultSet = stmt.executeQuery();

				if (resultSet.next()) {
					if (resultSet.getInt(1) > 0) {
						//poner funcion para obtener el oxigeno de la db
					} else {
						query = "INSERT INTO players (playername, UUID, 0) VALUES (?, ?, ?)";
						PreparedStatement stmt2 = conn.prepareStatement(query);
						stmt.setString(1, playername);
						stmt.setString(2, UUID);
						stmt.setInt(3, 0);
						stmt2.executeUpdate();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});

		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
			String playername = instance.getSession().getUsername();

			String query = "UPDATE players SET current_oxygen = ? WHERE playername = ?";
			try (Connection conn = MySQLConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setInt(1, 0); //cambiar por el valor actual de oxigeno
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