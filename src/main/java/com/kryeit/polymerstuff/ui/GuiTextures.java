/* Code copied from Polyfactory by Patbox
 * https://github.com/Patbox/PolyFactory
 *
 * Learn more about his mods: https://pb4.eu
 */

package com.kryeit.polymerstuff.ui;

import net.minecraft.text.Text;

import java.util.function.Function;

import static com.kryeit.polymerstuff.ui.UiResourceCreator.background;

public class GuiTextures {

    public static final Function<Text, Text> SHOP = background("shop");
    public static final Function<Text, Text> PAGINATED_SHOP = background("paginated_shop");
    public static final Function<Text, Text> PLAYER_HEAD_SHOP = background("player_head_shop");
    public static final Function<Text, Text> DISC_SHOP = background("disc_shop");

    public static void register() {

    }

}
