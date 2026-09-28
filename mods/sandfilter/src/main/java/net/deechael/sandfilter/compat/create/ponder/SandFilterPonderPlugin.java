package net.deechael.sandfilter.compat.create.ponder;

import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.deechael.sandfilter.SandFilterMod;
import net.deechael.sandfilter.compat.create.ponder.scene.SandFilterScene;
import net.minecraft.resources.ResourceLocation;

public class SandFilterPonderPlugin implements PonderPlugin {

    private static final ResourceLocation SAND_FILTER_BLOCK_ID =
            ResourceLocation.fromNamespaceAndPath(SandFilterMod.ID, "sand_filter");

    @Override
    public String getModId() {
        return SandFilterMod.ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        // registerScenes receives a helper keyed by ResourceLocation directly
        helper.forComponents(SAND_FILTER_BLOCK_ID)
                .addStoryBoard("sand_filter", SandFilterScene::filtering, AllCreatePonderTags.FLUIDS);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        // No custom ponder tags needed; we reuse Create's built-in FLUIDS tag
    }

}
