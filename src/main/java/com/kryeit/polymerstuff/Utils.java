package com.kryeit.polymerstuff;

import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class Utils {

    public static ItemStack getItemStack(String namespace, String path) {
        return Registries.ITEM.getOrEmpty(Identifier.of(namespace, path)).map(ItemStack::new).orElse(ItemStack.EMPTY);
    }
}
