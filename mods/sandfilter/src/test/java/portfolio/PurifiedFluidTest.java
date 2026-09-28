package portfolio;

import net.deechael.sandfilter.utils.SandFilterHelper;
import net.deechael.sandfilter.registry.SandFilterRegistry;
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.testframework.junit.EphemeralTestServerProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import sfiomn.legendarysurvivaloverhaul.registry.ItemRegistry;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(EphemeralTestServerProvider.class)
class PurifiedFluidTest {
    @Test void purificationSurvivesSaveAndLoad(MinecraftServer server) {
        var fluid = SandFilterHelper.setPurified(new FluidStack(Fluids.WATER, 1000), true);
        var saved = fluid.save(server.registryAccess());
        var loaded = FluidStack.parse(server.registryAccess(), saved).orElseThrow();
        assertEquals(1000, loaded.getAmount());
        assertTrue(SandFilterHelper.isPurified(loaded));
        SandFilterHelper.setPurified(loaded, false);
        assertFalse(SandFilterHelper.isPurified(loaded));
        assertNotNull(SandFilterRegistry.SAND_FILTER_BLOCK.get());
    }

    @Test void lastBottleKeepsPurity() {
        var fluid = SandFilterHelper.setPurified(new FluidStack(Fluids.WATER, 250), true);
        var bottles = new ItemStack(Items.GLASS_BOTTLE);
        // The direct water-bottle path does not access a level.
        var filled = GenericItemFilling.fillItem(null, 250, bottles, fluid);
        assertTrue(fluid.isEmpty());
        assertTrue(bottles.isEmpty());
        assertTrue(filled.is(ItemRegistry.PURIFIED_WATER_BOTTLE.get()));
    }

    @Test void ordinaryWaterStaysOrdinary() {
        var filled = GenericItemFilling.fillItem(null, 250, new ItemStack(Items.GLASS_BOTTLE),
                new FluidStack(Fluids.WATER, 250));
        assertTrue(filled.is(Items.POTION));
    }
}
