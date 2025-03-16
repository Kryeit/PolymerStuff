package com.kryeit.polymerstuff.gui;

import com.kryeit.polymerstuff.MinecraftServerSupplier;
import com.kryeit.polymerstuff.Utils;
import com.kryeit.polymerstuff.registry.ModItems;
import com.kryeit.polymerstuff.ui.GuiTextures;
import eu.pb4.sgui.api.ClickType;
import eu.pb4.sgui.api.elements.GuiElementInterface;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Arrays;
import java.util.Random;

public class ShopGUI extends SimpleGui {

    private static final ItemStack COPYCAT_STEP = Utils.getItemStack("create", "copycat_step");
    private static final ItemStack STAFF_HEAD = Utils.getItemStack("minecraft", "player_head");
    private static final Random RANDOM = new Random();

    private static final Item[] CUSTOM_DISCS = new Item[] {
            ModItems.DISC_MURI_1,
            ModItems.DISC_MURI_2,
            ModItems.DISC_MURI_3,
            ModItems.DISC_RATS_1,
            ModItems.DISC_RATS_2,
            ModItems.DISC_RATS_3,
            ModItems.DISC_RHINO_1,
            ModItems.DISC_RHINO_2,
            ModItems.DISC_RHINO_3,
            ModItems.DISC_TESS_1,
            ModItems.DISC_TESS_2,
            ModItems.DISC_TESS_3
    };

    public ShopGUI(ServerPlayerEntity player) {
        super(ScreenHandlerType.GENERIC_9X6, player, false);
        this.setTitle(GuiTextures.SHOP.apply(Text.literal("")));

        ItemStack copycats = COPYCAT_STEP.copy();
        copycats.setCustomName(Text.literal("Copycat shop").formatted(Formatting.GOLD));

        NbtList loreList = new NbtList();
        loreList.add(NbtString.of(Text.Serializer.toJson(Text.literal("A variety of Copycats to choose from :)").formatted(Formatting.LIGHT_PURPLE))));

        copycats.getOrCreateSubNbt("display").put("Lore", loreList);
        this.setSlot(20, copycats);

        String[] staffNames = {"MuriPlz", "MrRedRhino", "__Tesseract", "RatInATopHat427"};
        String[] onlineStaffNames = MinecraftServerSupplier.getServer().getPlayerManager().getPlayerList().stream()
                .filter(playerEntity -> Arrays.asList(staffNames).contains(playerEntity.getName().getString()))
                .map(playerEntity -> playerEntity.getName().getString())
                .toArray(String[]::new);

        if (onlineStaffNames.length == 0) {
            onlineStaffNames = new String[]{MinecraftServerSupplier.getServer().getPlayerNames()[new Random().nextInt(MinecraftServerSupplier.getServer().getPlayerNames().length)]};
        }

        ItemStack staff = GuiUtils.getPlayerHeadItem(onlineStaffNames[new Random().nextInt(onlineStaffNames.length)],
                Text.literal("Player head shop").formatted(Formatting.GOLD),
                Text.literal("A place to buy the heads of online players!").formatted(Formatting.LIGHT_PURPLE));
        this.setSlot(24, staff);

        Item randomDiscItem = CUSTOM_DISCS[RANDOM.nextInt(CUSTOM_DISCS.length)];
        ItemStack discs = new ItemStack(randomDiscItem);
        discs.setCustomName(Text.literal("Music disc shop").formatted(Formatting.GOLD));

        NbtList discLoreList = new NbtList();
        discLoreList.add(NbtString.of(Text.Serializer.toJson(Text.literal("Get your favorite music discs!").formatted(Formatting.LIGHT_PURPLE))));

        discs.getOrCreateSubNbt("display").put("Lore", discLoreList);
        this.setSlot(22, discs);

        this.open();
    }

    @Override
    public boolean onClick(int index, ClickType type, SlotActionType action, GuiElementInterface element) {
        if (element == null) return false;

        if (element.getItemStack().getItem() == COPYCAT_STEP.getItem()) {
            new CopycatsGUI(player);
        }

        if (element.getItemStack().getItem() == STAFF_HEAD.getItem()) {
            new PlayersGUI(player);
        }

        for (Item discItem : CUSTOM_DISCS) {
            if (element.getItemStack().getItem() == discItem) {
                new DiscsGUI(player);
                return false;
            }
        }

        return false;
    }
}