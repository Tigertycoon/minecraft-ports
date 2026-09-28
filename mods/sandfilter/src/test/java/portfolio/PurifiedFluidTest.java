package portfolio;

import net.deechael.sandfilter.utils.SandFilterHelper;
import net.deechael.sandfilter.registry.SandFilterRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.testframework.junit.EphemeralTestServerProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
}
