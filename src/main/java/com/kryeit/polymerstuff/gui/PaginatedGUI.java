package com.kryeit.polymerstuff.gui;

import com.kryeit.polymerstuff.MinecraftServerSupplier;
import eu.pb4.sgui.api.ClickType;
import eu.pb4.sgui.api.elements.GuiElementInterface;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public abstract class PaginatedGUI extends SimpleGui {

    public int page = 0;

    public PaginatedGUI(ServerPlayerEntity player, Text title) {
        super(ScreenHandlerType.GENERIC_9X6, player, false);
        this.setTitle(title);

        addPreviousPageButton();
        addNextPageButton();

        ItemStack back = Items.BARRIER.getDefaultStack();
        back.setCustomName(Text.literal("Go back").formatted(Formatting.RED));
        this.setSlot(40, back);
    }

    protected abstract void populate();

    @Override
    public boolean onClick(int index, ClickType type, SlotActionType action, GuiElementInterface element) {
        GuiElementInterface slot = this.getSlot(index);
        if (slot == null) return false;
        ItemStack clickedItem = slot.getItemStack();
        if (clickedItem == null) return false;

        if (index == 40) {
            new ShopGUI(player);
            return false;
        }

        if (index == 38) {
            previousPage();
            return false;
        }

        if (index == 42) {
            nextPage();
            return false;
        }

        return false;
    }

    private void addNextPageButton() {
        ItemStack stack = Items.ARROW.getDefaultStack();
        stack.setCustomName(Text.literal("Next page").formatted(Formatting.GREEN));
        NbtCompound displayTag = new NbtCompound();
        NbtList loreList = new NbtList();
        loreList.add(NbtString.of(Text.Serializer.toJson(Text.literal("Go to the next page").formatted(Formatting.GRAY))));
        displayTag.put("Lore", loreList);
        stack.setSubNbt("display", displayTag);
        this.setSlot(42, stack);
    }

    private void addPreviousPageButton() {
        ItemStack stack = Items.ARROW.getDefaultStack();
        stack.setCustomName(Text.literal("Previous page").formatted(Formatting.RED));
        NbtCompound displayTag = new NbtCompound();
        NbtList loreList = new NbtList();
        loreList.add(NbtString.of(Text.Serializer.toJson(Text.literal("Go to the previous page").formatted(Formatting.GRAY))));
        displayTag.put("Lore", loreList);
        stack.setSubNbt("display", displayTag);
        this.setSlot(38, stack);
    }

    public void nextPage() {
        int maxPages = maxPages();
        if (page >= maxPages - 1) return;
        page++;
        this.populate();
    }

    public void previousPage() {
        if (page == 0) return;
        page--;
        this.populate();
    }

    public int maxPages() {
        return (int) Math.ceil((double) MinecraftServerSupplier.getServer().getCurrentPlayerCount() / (3 * 7));
    }

    public void clearItems() {
        for (int i = 0; i < 36; i++) {
            this.setSlot(i, ItemStack.EMPTY);
        }
    }
}