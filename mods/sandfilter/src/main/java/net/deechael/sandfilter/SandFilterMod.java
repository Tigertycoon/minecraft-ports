package net.deechael.sandfilter;

import net.deechael.sandfilter.compat.create.SandFilterTileEntity;
import net.deechael.sandfilter.compat.create.ponder.SandFilterPonderPlugin;
import net.deechael.sandfilter.registry.SandFilterRegistry;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.core.Direction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(SandFilterMod.ID)
public class SandFilterMod {

    public final static String ID = "sandfilter";

    public SandFilterMod(IEventBus modEventBus) {
        modEventBus.addListener(this::setupClient);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::addCreativeTabContents);

        SandFilterRegistry.init(modEventBus);
    }

    private void setupClient(FMLClientSetupEvent event) {
        event.enqueueWork(() -> PonderIndex.addPlugin(new SandFilterPonderPlugin()));
    }

    private void addCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().location().getNamespace().equals("legendarysurvivaloverhaul")) {
            event.accept(SandFilterRegistry.SAND_FILTER_ITEM.get());
        }
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                SandFilterRegistry.SAND_FILTER_TILE_ENTITY.get(),
                (be, side) -> {
                    if (side != null && side.getAxis() == Direction.Axis.Y) {
                        if (side == Direction.DOWN)
                            return be.purifiedTank.getCapability();
                        else
                            return be.dirtyTank.getCapability();
                    }
                    return null;
                }
        );
    }

}
