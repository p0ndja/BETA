package me.palapon2545.music.NoteblockAPI;

import org.bukkit.Sound;

public class Instrument {

    public static Sound getInstrument(byte instrument) {
        switch (instrument) {
            case 0:
                return Sound.BLOCK_NOTE_HARP;
            case 1:
                return Sound.BLOCK_NOTE_BASS;
            case 2:
                return Sound.BLOCK_NOTE_BASEDRUM;
            case 3:
                return Sound.BLOCK_NOTE_SNARE;
            case 4:
                return Sound.BLOCK_NOTE_HAT;
            case 5:
                return Sound.BLOCK_NOTE_PLING;
            case 6:
                return Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
            case 7:
            	return Sound.ENTITY_ITEM_PICKUP;
            case 8:
            	return Sound.ENTITY_CHICKEN_EGG;
            default:
                return Sound.BLOCK_NOTE_PLING;
        }
    }

    public static org.bukkit.Instrument getBukkitInstrument(byte instrument) {
        switch (instrument) {
            case 0:
                return org.bukkit.Instrument.PIANO;
            case 1:
                return org.bukkit.Instrument.BASS_GUITAR;
            case 2:
                return org.bukkit.Instrument.BASS_DRUM;
            case 3:
                return org.bukkit.Instrument.SNARE_DRUM;
            case 4:
                return org.bukkit.Instrument.STICKS;
            default:
                return org.bukkit.Instrument.PIANO;
        }
    }
}
