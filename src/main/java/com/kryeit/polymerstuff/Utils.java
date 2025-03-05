package com.kryeit.polymerstuff;

import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Utils {

    public static ItemStack getItemStack(String namespace, String path) {
        return Registries.ITEM.getOrEmpty(Identifier.of(namespace, path)).map(ItemStack::new).orElse(ItemStack.EMPTY);
    }
}
