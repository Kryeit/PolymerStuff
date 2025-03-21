package com.kryeit.polymerstuff.registry;

import com.kryeit.polymerstuff.PolymerStuff;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSoundEvents {
    public static final SoundEvent MURI_1 = of("music.disc.muri_1");
    public static final SoundEvent MURI_2 = of("music.disc.muri_2");
    public static final SoundEvent MURI_3 = of("music.disc.muri_3");

    public static final SoundEvent RATS_1 = of("music.disc.rats_1");
    public static final SoundEvent RATS_2 = of("music.disc.rats_2");
    public static final SoundEvent RATS_3 = of("music.disc.rats_3");

    public static final SoundEvent RHINO_1 = of("music.disc.rhino_1");
    public static final SoundEvent RHINO_2 = of("music.disc.rhino_2");
    public static final SoundEvent RHINO_3 = of("music.disc.rhino_3");

    public static final SoundEvent TESS_1 = of("music.disc.tess_1");
    public static final SoundEvent TESS_2 = of("music.disc.tess_2");
    public static final SoundEvent TESS_3 = of("music.disc.tess_3");

    public static final SoundEvent MORONIC_1 = of("music.disc.moronic_1");
    public static final SoundEvent MORONIC_2 = of("music.disc.moronic_2");
    public static final SoundEvent MORONIC_3 = of("music.disc.moronic_3");

    private static SoundEvent of(String path) {
        return SoundEvent.of(Identifier.of(PolymerStuff.MODID, path));
    }
}
