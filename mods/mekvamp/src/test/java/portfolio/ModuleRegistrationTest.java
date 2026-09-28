package portfolio;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.neoforged.testframework.junit.EphemeralTestServerProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

class ModuleRegistrationTest {
    @Test void integrationItemsExistInRuntime() {
        long count = BuiltInRegistries.ITEM.keySet().stream().filter(id -> id.getNamespace().equals("mekvamp")).count();
        assertEquals(2, count);
    }
}
