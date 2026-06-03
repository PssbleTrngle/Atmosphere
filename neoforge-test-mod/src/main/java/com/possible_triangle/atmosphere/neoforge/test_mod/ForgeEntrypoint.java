package com.possible_triangle.atmosphere.neoforge.test_mod;


import com.possible_triangle.atmosphere.api.v1.AtmosphereConstants;
import com.possible_triangle.atmosphere.neoforge.test_mod.index.AtmosphereBlocks;
import com.tterrag.registrate.AbstractRegistrate;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(AtmosphereConstants.MOD_ID + "_test")
public class ForgeEntrypoint {

    public static final AbstractRegistrate<?> REGISTRATE = new AtmosphereRegistrate(AtmosphereConstants.MOD_ID)
        .defaultCreativeTab(CreativeModeTabs.OP_BLOCKS);

    public ForgeEntrypoint(IEventBus modBus) {
        AtmosphereBlocks.init();
        REGISTRATE.registerEventListeners(modBus);
    }

}
