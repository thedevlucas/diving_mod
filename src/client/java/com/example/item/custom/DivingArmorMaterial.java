package com.example.item.custom;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

import static net.minecraft.entity.EquipmentSlot.*;
import static net.minecraft.item.Items.CHEST;

public class DivingArmorMaterial implements ArmorMaterial {

    @Override
    public int getDurability(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> 300;
            case CHESTPLATE -> 400;
            case LEGGINGS -> 350;
            case BOOTS -> 250;
        };
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> 2; // Protección del casco
            case CHESTPLATE -> 5; // Protección de la pechera
            case LEGGINGS -> 4; // Protección de los pantalones
            case BOOTS -> 2; // Protección de las botas
        };
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public SoundEvent getEquipSound() {
        return null;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }

    // Otros métodos...
}
