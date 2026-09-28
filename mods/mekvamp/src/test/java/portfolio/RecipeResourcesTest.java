package portfolio;

import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.item.crafting.Recipe;
import org.junit.jupiter.api.Test;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;

class RecipeResourcesTest {
    private void check(String name) throws Exception {
        var access = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        var ops = RegistryOps.create(JsonOps.INSTANCE, access);
        try (var stream = getClass().getResourceAsStream("/data/mekvamp/recipe/" + name + ".json")) {
            assertNotNull(stream);
            var json = JsonParser.parseReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            assertTrue(Recipe.CODEC.parse(ops, json).isSuccess(), () -> Recipe.CODEC.parse(ops, json).toString());
        }
    }
    @Test void sunscreenRecipeResolvesAgainstPinnedMods() throws Exception { check("module_sunscreen_unit"); }
    @Test void autofeederRecipeResolvesAgainstPinnedMods() throws Exception { check("module_auto_feeding_unit"); }
}
