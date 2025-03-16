package com.kryeit.polymerstuff.music;

import com.kryeit.polymerstuff.registry.ModItems;
import com.kryeit.polymerstuff.registry.ModSoundEvents;
import net.minecraft.network.packet.s2c.play.PlaySoundFromEntityS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;

public enum Song {

    MURI_1("Hermeto Pascoal - Música da Lagoa", "https://www.youtube.com/watch?v=lZbfNtDCHdM"),
    MURI_2("Melendi - Kisiera yo saber", "https://www.youtube.com/watch?v=zsYGW5Br23Q"),
    MURI_3("Two Door Cinema Club - What You Know", "https://www.youtube.com/watch?v=EzpgItFnNQE"),

    RATS_1("St. Clair, F.V. - The Ship That Will Never Return", "https://www.youtube.com/watch?v=_qqgApAU0XA"),
    RATS_2("Will G. Markwith - Starland Intermezzo", "https://www.youtube.com/watch?v=DUoQtAUcn5c"),
    RATS_3("Seymour Brown - Oh, You Beautiful Doll", "https://www.youtube.com/watch?v=roqgbqtvH_s"),

    TESS_1("The Animals - House Of The Rising Sun", "https://youtu.be/N4bFqW_eu2I"),
    TESS_2("Ray Charles - Hit The Road Jack", "https://youtu.be/uSiHqxgE2d0"),
    TESS_3("Dvořák： Symphony No. 9 ＂From the New World＂ ⧸ Karajan · Berliner Philharmoniker", "https://youtu.be/P_1N6_O254g"),

    MORONIC_1("The Weeknd - Blinding Lights", " https://youtu.be/fHI8X4OXluQ"),
    MORONIC_2("Don't Mine At Night - A Minecraft Parody of Katy Perry's Last Friday Night", "https://youtu.be/X_XGxzMrq04"),
    MORONIC_3("Kyle Dixon & Michael Stein - Teens", "https://youtu.be/Se-3uSaPsaY"),
    ;

    private final String name;
    private final String link;
    protected final Random random;

    Song(String name, String link) {
        this.name = name;
        this.link = link;
        this.random = Random.create();
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public SoundEvent getSoundEvent() {
        return switch (this) {
            case MURI_1 -> ModSoundEvents.MURI_1;
            case MURI_2 -> ModSoundEvents.MURI_2;
            case MURI_3 -> ModSoundEvents.MURI_3;

            case RATS_1 -> ModSoundEvents.RATS_1;
            case RATS_2 -> ModSoundEvents.RATS_2;
            case RATS_3 -> ModSoundEvents.RATS_3;

            case TESS_1 -> ModSoundEvents.TESS_1;
            case TESS_2 -> ModSoundEvents.TESS_2;
            case TESS_3 -> ModSoundEvents.TESS_3;

            case MORONIC_1 -> ModSoundEvents.MORONIC_1;
            case MORONIC_2 -> ModSoundEvents.MORONIC_2;
            case MORONIC_3 -> ModSoundEvents.MORONIC_3;
        };
    }

    public void play(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer();
        if (server != null) {
            var soundEntry = Registries.SOUND_EVENT.getEntry(getSoundEvent());

            for (ServerPlayerEntity serverPlayer : server.getPlayerManager().getPlayerList()) {
                serverPlayer.networkHandler.sendPacket(
                        new PlaySoundFromEntityS2CPacket(
                                soundEntry,
                                SoundCategory.RECORDS,
                                player,
                                4.0f,
                                1.0f,
                                random.nextLong()
                        )
                );
            }
        }
    }
}