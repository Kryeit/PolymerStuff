package com.kryeit.polymerstuff.registry;

import com.kryeit.polymerstuff.PolymerStuff;
import com.kryeit.polymerstuff.content.item.DiscItem;
import com.kryeit.polymerstuff.content.item.util.AutoModeledPolymerItem;
import com.kryeit.polymerstuff.content.item.util.ModeledItem;
import com.kryeit.polymerstuff.music.Song;
import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item KRYEIT_COG = register("kryeit_cog", new ModeledItem(new Item.Settings()));

    // Discs
    public static final Item DISC_MURI_1 = register("disc_muri_1", new ModeledItem(new DiscItem.Settings()));
    public static final Item DISC_MURI_2 = register("disc_muri_2", new ModeledItem(new DiscItem.Settings()));
    public static final Item DISC_MURI_3 = register("disc_muri_3", new ModeledItem(new DiscItem.Settings()));

    public static final Item DISC_RATS_1 = register("disc_rats_1", new ModeledItem(new DiscItem.Settings()));
    public static final Item DISC_RATS_2 = register("disc_rats_2", new ModeledItem(new DiscItem.Settings()));
    public static final Item DISC_RATS_3 = register("disc_rats_3", new ModeledItem(new DiscItem.Settings()));

    public static final Item DISC_RHINO_1 = register("disc_rhino_1", new ModeledItem(new DiscItem.Settings()));
    public static final Item DISC_RHINO_2 = register("disc_rhino_2", new ModeledItem(new DiscItem.Settings()));
    public static final Item DISC_RHINO_3 = register("disc_rhino_3", new ModeledItem(new DiscItem.Settings()));

    public static final Item DISC_TESS_1 = register("disc_tess_1", new ModeledItem(new DiscItem.Settings()));
    public static final Item DISC_TESS_2 = register("disc_tess_2", new ModeledItem(new DiscItem.Settings()));
    public static final Item DISC_TESS_3 = register("disc_tess_3", new ModeledItem(new DiscItem.Settings()));

    public static final Item DISC_MORONIC_1 = register("disc_moronic_1", new ModeledItem(new DiscItem.Settings()));
    public static final Item DISC_MORONIC_2 = register("disc_moronic_2", new ModeledItem(new DiscItem.Settings()));
    public static final Item DISC_MORONIC_3 = register("disc_moronic_3", new ModeledItem(new DiscItem.Settings()));

    public static void register() {
        PolymerItemGroupUtils.registerPolymerItemGroup(new Identifier(PolymerStuff.MODID, "group"), ItemGroup.create(ItemGroup.Row.BOTTOM, -1)
                .icon(KRYEIT_COG::getDefaultStack)
                .displayName(Text.translatable("itemgroup." + PolymerStuff.MODID))
                .entries(((context, entries) -> {
                    entries.add(KRYEIT_COG);
                })).build()
        );
    }

    public static boolean isPolymerDisc(Item item) {
        boolean isMuri = item == DISC_MURI_1 || item == DISC_MURI_2 || item == DISC_MURI_3;
        boolean isRats = item == DISC_RATS_1 || item == DISC_RATS_2 || item == DISC_RATS_3;
        boolean isTess = item == DISC_TESS_1 || item == DISC_TESS_2 || item == DISC_TESS_3;
        boolean isMoronic = item == DISC_MORONIC_1 || item == DISC_MORONIC_2 || item == DISC_MORONIC_3;
        boolean isRhino = item == DISC_RHINO_1 || item == DISC_RHINO_2 || item == DISC_RHINO_3;

        return isMuri || isRats || isTess || isMoronic || isRhino;
    }

    public static Song fromDisc(Item item) {
        if (item == DISC_MURI_1) {
            return Song.MURI_1;
        } else if (item == DISC_MURI_2) {
            return Song.MURI_2;
        } else if (item == DISC_MURI_3) {
            return Song.MURI_3;
        } else if (item == DISC_RATS_1) {
            return Song.RATS_1;
        } else if (item == DISC_RATS_2) {
            return Song.RATS_2;
        } else if (item == DISC_RATS_3) {
            return Song.RATS_3;
        } else if (item == DISC_TESS_1) {
            return Song.TESS_1;
        } else if (item == DISC_TESS_2) {
            return Song.TESS_2;
        } else if (item == DISC_TESS_3) {
            return Song.TESS_3;
        } else if (item == DISC_MORONIC_1) {
            return Song.MORONIC_1;
        } else if (item == DISC_MORONIC_2) {
            return Song.MORONIC_2;
        } else if (item == DISC_MORONIC_3) {
            return Song.MORONIC_3;
        } else if (item == DISC_RHINO_1) {
            return Song.RHINO_1;
        } else if (item == DISC_RHINO_2) {
            return Song.RHINO_2;
        } else if (item == DISC_RHINO_3) {
            return Song.RHINO_3;
        } else {
            return Song.MURI_1;
        }
    }

    public static <T extends Item> T register(String path, T item) {
        Registry.register(Registries.ITEM, new Identifier(PolymerStuff.MODID, path), item);
        if (item instanceof AutoModeledPolymerItem modeledPolymerItem) {
            modeledPolymerItem.defineModels(new Identifier(PolymerStuff.MODID, path));
        }
        return item;
    }
}
