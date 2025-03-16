package com.kryeit.polymerstuff.music;

import com.kryeit.polymerstuff.registry.ModItems;
import com.kryeit.polymerstuff.registry.ModSoundEvents;
import net.minecraft.item.Item;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public enum Song {

    MURI_1("Hermeto Pascoal - Música da Lagoa", "https://www.youtube.com/watch?v=lZbfNtDCHdM"),
    MURI_2("Melendi - Kisiera yo saber", "https://www.youtube.com/watch?v=zsYGW5Br23Q"),
    MURI_3("Two Door Cinema Club - What You Know", "https://www.youtube.com/watch?v=EzpgItFnNQE"),

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
        };
    }

    public void play(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer();
        if (server != null) {
            player.networkHandler.sendPacket(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(getSoundEvent()), SoundCategory.MUSIC, player.getPos().x, player.getPos().y, player.getPos().z, 1.0f, 1.0f, this.random.nextLong()));

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

            scheduler.scheduleAtFixedRate(() -> {
                for (ServerPlayerEntity otherPlayer : server.getPlayerManager().getPlayerList()) {
                    otherPlayer.networkHandler.sendPacket(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(getSoundEvent()), SoundCategory.MUSIC, player.getPos().x, player.getPos().y, player.getPos().z, 1.0f, 1.0f, this.random.nextLong()));
                }
            }, 0, 1, TimeUnit.SECONDS);
        }
    }

}
