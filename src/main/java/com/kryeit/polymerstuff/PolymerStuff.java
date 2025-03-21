package com.kryeit.polymerstuff;

import com.kryeit.polymerstuff.command.Shop;
import com.kryeit.polymerstuff.registry.ModItems;
import com.kryeit.polymerstuff.ui.GuiTextures;
import com.kryeit.polymerstuff.ui.UiResourceCreator;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;

public class PolymerStuff implements ModInitializer {

    public static final String MODID = "polymerstuff";
    public static final boolean DEV = FabricLoader.getInstance().isDevelopmentEnvironment();
    @SuppressWarnings("PointlessBooleanExpression")
    public static final boolean DYNAMIC_ASSETS = true && DEV;

    @Override
    public void onInitialize() {

        registerCommands();
        setupPatbox();
    }

    private void setupPatbox() {
        ModItems.register();

        UiResourceCreator.setup();
        GuiTextures.register();

        // Resource pack
        PolymerResourcePackUtils.addModAssets(MODID);
        PolymerResourcePackUtils.markAsRequired();
    }


    public void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicatedServer, commandFunction) -> {
            Shop.register(dispatcher);
        });
    }
}
