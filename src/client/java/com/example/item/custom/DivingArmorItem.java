package com.example.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DivingArmorItem extends ArmorItem {
    public DivingArmorItem(ArmorMaterial material, ArmorItem.Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient()) {
            if(entity instanceof PlayerEntity player) {
                if(hasFullSuitOfArmorOn(player)) {
                    StatusEffectInstance water_breathing = new StatusEffectInstance(StatusEffects.WATER_BREATHING,10, 0, false, false);
                    StatusEffectInstance water_speed = new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE,10, 0, false, false);

                    player.addStatusEffect(water_breathing);
                    player.addStatusEffect(water_speed);
                }
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private boolean hasFullSuitOfArmorOn(PlayerEntity player){
        ItemStack boots = player.getInventory().getArmorStack(0);
        ItemStack leggins = player.getInventory().getArmorStack(1);
        ItemStack oxigen = player.getInventory().getArmorStack(2);
        ItemStack mask = player.getInventory().getArmorStack(3);

        return !mask.isEmpty() && !oxigen.isEmpty() && !leggins.isEmpty() && !boots.isEmpty();
    }
}
