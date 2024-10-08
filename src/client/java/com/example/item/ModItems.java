package com.example.item;

import com.example.Diving_mod;
import com.example.item.custom.DialogueDebugItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item DIVING_MASK = registerItem("diving_mask", new DialogueDebugItem(new FabricItemSettings()));
    public static final Item DIVING_FINS = registerItem("diving_fins", new DialogueDebugItem(new FabricItemSettings()));
    public static final Item DIVING_TANK = registerItem("diving_tank", new DialogueDebugItem(new FabricItemSettings()));
    public static final Item DIVING_LEGGINS = registerItem("diving_leggins", new DialogueDebugItem(new FabricItemSettings()));


    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(Diving_mod.MOD_ID, name), item);
    }

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries){
        entries.add(DIVING_MASK);
        entries.add(DIVING_FINS);
        entries.add(DIVING_TANK);
        entries.add(DIVING_LEGGINS);
    }

    public static void registerModItems(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}