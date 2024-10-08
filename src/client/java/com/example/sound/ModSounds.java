package com.example.sound;

import com.example.Diving_mod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds  {
    //public static final SoundEvent TELEPORT_SOUND_EFFECT = registerSoundEvent("teleport_sound_effect");

    private static SoundEvent registerSoundEvent(String location) {
        SoundEvent soundEvent = SoundEvent.of(new Identifier(Diving_mod.MOD_ID, location));
        return Registry.register(Registries.SOUND_EVENT, soundEvent.getId(), soundEvent);
    }

    public static void register() {}
}