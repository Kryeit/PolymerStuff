package com.kryeit.polymerstuff.gui;

import com.kryeit.polymerstuff.MinecraftServerSupplier;
import com.kryeit.polymerstuff.Utils;
import com.kryeit.polymerstuff.ui.GuiTextures;
import eu.pb4.sgui.api.ClickType;
import eu.pb4.sgui.api.elements.GuiElementInterface;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class PlayersGUI extends PaginatedGUI {
    private static final ItemStack COPPER_COIN = Utils.getItemStack("createdeco", "copper_coin");
    public static final Item PLAYER_HEAD_ITEM = Utils.getItemStack("minecraft", "player_head").getItem();

    List<ItemStack> playerHeads;
    int REQUIRED_COINS = 3;

    public PlayersGUI(ServerPlayerEntity player) {
        super(player, GuiTextures.PLAYER_HEAD_SHOP.apply(Text.literal("")));
        playerHeads = getOnlinePlayerHeadItemStacks();
        populate();

        this.open();
    }

    private List<ItemStack> getOnlinePlayerHeadItemStacks() {

        List<ServerPlayerEntity> onlinePlayers = MinecraftServerSupplier.getServer().getPlayerManager().getPlayerList();
        List<ItemStack> playerHeads = new ArrayList<>();

        for (ServerPlayerEntity onlinePlayer : onlinePlayers) {
            ItemStack playerHead = GuiUtils.getPlayerHeadItem(onlinePlayer.getName().getString(),
                    Text.literal(onlinePlayer.getName().getString() + "'s head").formatted(Formatting.GOLD),
                    Text.literal("Buy 1 for " + REQUIRED_COINS + " copper coins").formatted(Formatting.LIGHT_PURPLE));
            playerHeads.add(playerHead);
        }

        return playerHeads;
    }

    @Override
    protected void populate() {
        this.clearItems();

        int page = this.page;

        int start = page * 3 * 7;
        int end = start + 3 * 7;

        for (int i = start; i < end; i++) {
            if (i >= playerHeads.size()) {
                break;
            }

            ItemStack playerHead = playerHeads.get(i);

            int index = i - start;
            int row = index / 7;
            int col = index % 7 + 1;
            int slotIndex = row * 9 + col;

            this.setSlot(slotIndex, playerHead);
        }
    }

    @Override
    public boolean onClick(int index, ClickType type, SlotActionType action, GuiElementInterface element) {
        super.onClick(index, type, action, element);

        GuiElementInterface slot = this.getSlot(index);
        if (slot == null) {
            return false;
        }
        ItemStack clickedItem = slot.getItemStack();
        if (clickedItem == null) {
            return false;
        }

        if (clickedItem.getItem() != PLAYER_HEAD_ITEM) {
            return false;
        }

        int itemAmount = 1;
        int playerCoins = getPlayerCoinCount();

        if (playerCoins >= REQUIRED_COINS) {
            if (hasInventorySpace(clickedItem.getItem(), itemAmount)) {
                removePlayerCoins(REQUIRED_COINS);

                // Get the display name from the clicked item and extract the player name
                String displayName = clickedItem.getName().getString();
                String playerName = displayName.replace("'s head", "");

                // Instead of creating a new head from scratch, clone the clicked item
                // but remove the lore text
                ItemStack itemToGive = clickedItem.copy();

                // Clear the lore from the display tag
                if (itemToGive.hasNbt() && itemToGive.getNbt().contains("display")) {
                    NbtCompound display = itemToGive.getNbt().getCompound("display");
                    if (display.contains("Lore")) {
                        display.remove("Lore");
                    }
                }

                player.giveItemStack(itemToGive);
                player.sendMessage(Text.literal("Successfully purchased " + itemAmount + " " + displayName), false);
            } else {
                player.sendMessage(Text.literal("Your inventory is full. Clear some space before making a purchase."), false);
            }
        } else {
            player.sendMessage(Text.literal("You do not have enough coins to make this purchase."), false);
        }

        return false;
    }

    private int getPlayerCoinCount() {
        int coinCount = 0;
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack != null && stack.getItem() == COPPER_COIN.getItem()) {
                coinCount += stack.getCount();
            }
        }
        return coinCount;
    }

    private void removePlayerCoins(int amount) {
        int remaining = amount;
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack != null && stack.getItem() == COPPER_COIN.getItem()) {
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