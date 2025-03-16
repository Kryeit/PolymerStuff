package com.kryeit.polymerstuff.gui;

import com.kryeit.polymerstuff.Utils;
import com.kryeit.polymerstuff.music.Song;
import com.kryeit.polymerstuff.registry.ModItems;
import com.kryeit.polymerstuff.ui.GuiTextures;
import eu.pb4.sgui.api.ClickType;
import eu.pb4.sgui.api.elements.GuiElementInterface;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.LinkedHashMap;

public class DiscsGUI extends PaginatedGUI {
    LinkedHashMap<Item, Integer> items = new LinkedHashMap<>();

    private static final int REQUIRED_COGS = 3;

    public DiscsGUI(ServerPlayerEntity player) {
        super(player, GuiTextures.DISC_SHOP.apply(Text.literal("")));

        // Add Muri discs
        addDisc(ModItems.DISC_MURI_1, Song.MURI_1);
        addDisc(ModItems.DISC_MURI_2, Song.MURI_2);
        addDisc(ModItems.DISC_MURI_3, Song.MURI_3);

        // Add Rats discs
        addDisc(ModItems.DISC_RATS_1, Song.RATS_1);
        addDisc(ModItems.DISC_RATS_2, Song.RATS_2);
        addDisc(ModItems.DISC_RATS_3, Song.RATS_3);

//        // Add Rhino discs
//        addDisc(ModItems.DISC_RHINO_1, "MrRedRhino's Disc 1");
//        addDisc(ModItems.DISC_RHINO_2, "MrRedRhino's Disc 2");
//        addDisc(ModItems.DISC_RHINO_3, "MrRedRhino's Disc 3");
//
        // Add Tess discs
        addDisc(ModItems.DISC_TESS_1, Song.TESS_1);
        addDisc(ModItems.DISC_TESS_2, Song.TESS_2);
        addDisc(ModItems.DISC_TESS_3, Song.TESS_3);

        addDisc(ModItems.DISC_MORONIC_1, Song.MORONIC_1);
        addDisc(ModItems.DISC_MORONIC_2, Song.MORONIC_2);
        addDisc(ModItems.DISC_MORONIC_3, Song.MORONIC_3);

        populate();
        this.open();
    }

    private void addDisc(Item disc, Song song) {
        ItemStack discStack = new ItemStack(disc);
        discStack.setCustomName(Text.literal(song.getName()).formatted(Formatting.GOLD)
                .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, song.getLink()))));
        items.put(disc, 1);
    }

    @Override
    public boolean onClick(int index, ClickType type, SlotActionType action, GuiElementInterface element) {
        super.onClick(index, type, action, element);

        GuiElementInterface slot = this.getSlot(index);
        if (slot == null) return false;
        ItemStack clickedItem = slot.getItemStack();
        if (clickedItem == null) return false;

        if (!items.containsKey(clickedItem.getItem())) {
            return false;
        }

        int itemAmount = items.get(clickedItem.getItem());

        int playerCogs = getPlayerCogCount();
        if (playerCogs >= REQUIRED_COGS) {
            if (hasInventorySpace(clickedItem.getItem(), itemAmount)) {
                removePlayerCogs(REQUIRED_COGS);
                player.giveItemStack(new ItemStack(clickedItem.getItem(), itemAmount));

                player.sendMessage(Text.literal("Successfully purchased " + itemAmount + " " + clickedItem.getName().getString()), false);
            } else {
                player.sendMessage(Text.literal("Your inventory is full. Clear some space before making a purchase."), false);
            }
        } else {
            player.sendMessage(Text.literal("You need " + REQUIRED_COGS + " Kryeit Cogs to purchase this disc."), false);
        }

        return false;
    }

    @Override
    public void populate() {
        this.clearItems();

        int start = page * 3 * 7;
        int end = start + 3 * 7;

        int index = 0;
        for (Item item : items.keySet()) {
            if (index >= start && index < end) {
                int row = (index - start) / 7;
                int col = (index - start) % 7 + 1;
                int slotIndex = row * 9 + col;

                ItemStack itemStack = new ItemStack(item, items.get(item));
                NbtList loreList = new NbtList();
                loreList.add(NbtString.of(Text.Serializer.toJson(Text.literal("Buy 1 for " + REQUIRED_COGS + " Kryeit Cogs").formatted(Formatting.LIGHT_PURPLE))));
                itemStack.getOrCreateSubNbt("display").put("Lore", loreList);
                this.setSlot(slotIndex, itemStack);
            }
            index++;
        }
    }

    private int getPlayerCogCount() {
        int cogCount = 0;
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack != null && stack.getItem() == ModItems.KRYEIT_COG) {
                cogCount += stack.getCount();
            }
        }
        return cogCount;
    }

    private void removePlayerCogs(int amount) {
        int remaining = amount;
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack != null && stack.getItem() == ModItems.KRYEIT_COG) {
                int remove = Math.min(stack.getCount(), remaining);
                stack.decrement(remove);
                remaining -= remove;
                if (remaining <= 0) break;
            }
        }
    }

    private boolean hasInventorySpace(Item item, int amount) {
        int remaining = amount;

        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);

            if (stack.isEmpty()) {
                return true;
            } else if (stack.getItem() == item && stack.getCount() < stack.getMaxCount()) {
                int spaceAvailable = stack.getMaxCount() - stack.getCount();
                remaining -= spaceAvailable;

                if (remaining <= 0) {
                    return true;
                }
            }
        }

        return false;
    }
}