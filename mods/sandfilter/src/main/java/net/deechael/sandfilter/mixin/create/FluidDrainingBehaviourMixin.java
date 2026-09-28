package net.deechael.sandfilter.mixin.create;

import com.simibubi.create.content.fluids.transfer.FluidDrainingBehaviour;
import com.simibubi.create.foundation.fluid.FluidHelper;
import net.deechael.sandfilter.registry.SandFilterDataComponents;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FluidDrainingBehaviour.class, remap = false)
public abstract class FluidDrainingBehaviourMixin {

    @Inject(method = "getDrainableFluid", at = @At("RETURN"), remap = false, cancellable = true)
    public void inject$getDrainableFluid$return(BlockPos rootPos, CallbackInfoReturnable<FluidStack> cir) {
        FluidStack output = cir.getReturnValue();
        if (FluidHelper.isWater(output.getFluid())) {
            // Mark as NOT purified (dirty water from a source)
            output.remove(SandFilterDataComponents.PURIFIED);
            cir.setReturnValue(output);
        }
    }

}
