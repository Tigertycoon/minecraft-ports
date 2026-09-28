package net.deechael.sandfilter.mixin.create;

import com.simibubi.create.content.fluids.OpenEndedPipe;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.fluid.FluidHelper;
import net.deechael.sandfilter.registry.SandFilterDataComponents;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = OpenEndedPipe.class, remap = false)
public class OpenEndedPipeMixin {

    @Inject(method = "removeFluidFromSpace", at = @At("HEAD"), cancellable = true, remap = false)
    private void inject$removeFluidFromSpace$head(boolean simulate, CallbackInfoReturnable<FluidStack> cir) {
        OpenEndedPipe pipe = ((OpenEndedPipe) (Object) this);

        if (pipe.getWorld() == null)
            return;
        if (!pipe.getWorld().isLoaded(pipe.getOutputPos()))
            return;

        BlockState state = pipe.getWorld().getBlockState(pipe.getOutputPos());
        FluidState fluidState = state.getFluidState();
        boolean waterlog = state.hasProperty(BlockStateProperties.WATERLOGGED);

        if (fluidState.isEmpty() || !fluidState.isSource())
            return;

        if (!waterlog && !state.canBeReplaced())
            return;

        FluidStack stack = new FluidStack(fluidState.getType(), 1000);

        if (!FluidHelper.isWater(stack.getFluid()))
            return;

        // Water drawn from the world is not purified
        // (no explicit set needed — absence of the component = not purified)

        if (simulate) {
            cir.setReturnValue(stack);
        } else {
            AdvancementBehaviour.tryAward(pipe.getWorld(), pipe.getPos(), AllAdvancements.WATER_SUPPLY);

            if (waterlog) {
                pipe.getWorld().setBlock(pipe.getOutputPos(), state.setValue(BlockStateProperties.WATERLOGGED, false), 3);
                pipe.getWorld().scheduleTick(pipe.getOutputPos(), Fluids.WATER, 1);
                cir.setReturnValue(stack);
            } else {
                pipe.getWorld().setBlock(pipe.getOutputPos(), fluidState.createLegacyBlock().setValue(LiquidBlock.LEVEL, 14), 3);
                cir.setReturnValue(stack);
            }
        }
    }

}
