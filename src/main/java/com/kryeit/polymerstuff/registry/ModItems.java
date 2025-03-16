package com.kryeit.polymerstuff.registry;

import com.kryeit.polymerstuff.PolymerStuff;
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
    public static final Item DISC_MURI_1 = register("disc_muri_1", new ModeledItem(new Item.Settings()));
    public static final Item DISC_MURI_2 = register("disc_muri_2", new ModeledItem(new Item.Settings()));
    public static final Item DISC_MURI_3 = register("disc_muri_3", new ModeledItem(new Item.Settings()));

//    public static final Item DISC_RATS_1 = register("disc_rats_1", new ModeledItem(new Item.Settings()));
//    public static final Item DISC_RATS_2 = register("disc_rats_2", new ModeledItem(new Item.Settings()));
//    public static final Item DISC_RATS_3 = register("disc_rats_3", new ModeledItem(new Item.Settings()));
//
//    public static final Item DISC_RHINO_1 = register("disc_rhino_1", new ModeledItem(new Item.Settings()));
//    public static final Item DISC_RHINO_2 = register("disc_rhino_2", new ModeledItem(new Item.Settings()));
//    public static final Item DISC_RHINO_3 = register("disc_rhino_3", new ModeledItem(new Item.Settings()));
//
//    public static final Item DISC_TESS_1 = register("disc_tess_1", new ModeledItem(new Item.Settings()));
//    public static final Item DISC_TESS_2 = register("disc_tess_2", new ModeledItem(new Item.Settings()));
//    public static final Item DISC_TESS_3 = register("disc_tess_3", new ModeledItem(new Item.Settings()));

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
        return item == DISC_MURI_1 || item == DISC_MURI_2 || item == DISC_MURI_3;
    }

    public static Song fromDisc(Item item) {
        if (item == DISC_MURI_1) {
            return Song.MURI_1;
        } else if (item == DISC_MURI_2) {
            return Song.MURI_2;
        } else if (item == DISC_MURI_3) {
            return Song.MURI_3;
        } else {
            return null;
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
