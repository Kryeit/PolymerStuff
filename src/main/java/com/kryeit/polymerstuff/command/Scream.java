package com.kryeit.polymerstuff.command;

import com.kryeit.polymerstuff.gui.ShopGUI;
import com.kryeit.polymerstuff.music.Song;
import com.kryeit.polymerstuff.registry.ModItems;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class Scream {

    public static int execute(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayer();

        if (player == null) {
            Supplier<Text> message = () -> Text.of("Can't execute from console");
            source.sendFeedback(message, false);
            return 0;
        }

        ItemStack itemStack = player.getStackInHand(player.getActiveHand());

        if (!ModItems.isPolymerDisc(itemStack.getItem())) {
            Supplier<Text> message = () -> Text.of("You need to hold a disc from /shop in your hand to scream");
            source.sendFeedback(message, false);
            return 0;
        }

        Song song = ModItems.fromDisc(itemStack.getItem());

        song.play(player);

        return Command.SINGLE_SUCCESS;
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("scream")
                .executes(Scream::execute)
        );

        dispatcher.register(CommandManager.literal("play")
                .executes(Scream::execute)
        );
    }

}
