package com.kryeit.polymerstuff.content.item;

import com.kryeit.polymerstuff.MinecraftServerSupplier;
import com.kryeit.polymerstuff.music.Song;
import com.kryeit.polymerstuff.registry.ModItems;
import eu.pb4.polymer.core.api.item.PolymerItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.entity.mob.HoglinBrain.getSoundEvent;

public class DiscItem extends Item implements PolymerItem {

    private Song song;
    private boolean playing = false;

    public DiscItem(Settings settings, Song song) {
        super(
                settings
                        .maxCount(1)
                        .rarity(Rarity.EPIC)
        );
        this.song = song;
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        var type = ModItems.fromDisc(itemStack.getItem());
        if (type != null) {
            return itemStack.getItem();
        }

        return Items.MUSIC_DISC_MALL;
    }

    public Song getSong() {
        return song;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        super.use(world, user, hand);

        if (world.isClient) {
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        Song song = this.getSong();

        if (playing) {
            var soundEntry = Registries.SOUND_EVENT.getEntry(song.getSoundEvent());

            for (ServerPlayerEntity serverPlayer : MinecraftServerSupplier.getServer().getPlayerManager().getPlayerList()) {
                serverPlayer.networkHandler.sendPacket(
                        new StopSoundS2CPacket(
                                soundEntry.getKey().orElse(null).getRegistry(),
                                SoundCategory.RECORDS
                        )
                );
            }
            playing = false;
            return TypedActionResult.success(user.getStackInHand(hand));
        }

        song.play((ServerPlayerEntity) user);
        playing = true;
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
