package com.msdoggirl.mekvamp;

import com.mojang.logging.LogUtils;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.core.ModEffects;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.slf4j.Logger;

@Mod(MekVamp.MODID)
public class MekVamp {

    public static final String MODID = "mekvamp";
    private static final Logger LOGGER = LogUtils.getLogger();
    // Energy cost per tick in joules (replaces FloatingLong.createConst(50))
    private static final long ENERGY_COST = 50L;

    public MekVamp(IEventBus modEventBus) {
        Items.ITEMS.register(modEventBus);
        Modules.MODULES.register(modEventBus);
        modEventBus.addListener(this::addToCreativeTabs);
        NeoForge.EVENT_BUS.register(this);
        modEventBus.register(MekanismListener.class);
    }

    private void addToCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(Items.SUNSCREEN_MODULE.get());
            event.accept(Items.FEEDING_MODULE.get());
        }
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Post event) {
        for (Player player : event.getServer().getPlayerList().getPlayers()) {
            AutofeederLogic.tick(player);

            // Sunscreen module: protect vampires getting sun damage
            if (VampirismAPI.factionRegistry().getFaction(player) == VReference.VAMPIRE_FACTION &&
                VampirismAPI.sundamageRegistry().isGettingSundamage(player, player.level())) {

                ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
                IModule<SunscreenModule> module = IModuleHelper.INSTANCE.getModule(helmet, Modules.SUNSCREEN_MODULE);
                if (module != null && module.isEnabled() && module.canUseEnergy(player, helmet, ENERGY_COST)) {
                    module.useEnergy(player, helmet, ENERGY_COST);
                    player.addEffect(new MobEffectInstance(ModEffects.SUNSCREEN, 60, 5, false, false));
                }
            }
        }
    }
}
