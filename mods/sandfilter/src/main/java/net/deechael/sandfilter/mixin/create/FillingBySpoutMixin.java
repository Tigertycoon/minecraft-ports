package net.deechael.sandfilter.mixin.create;

import com.simibubi.create.content.fluids.spout.FillingBySpout;
import net.deechael.sandfilter.utils.SandFilterHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FillingBySpout.class, remap = false)
public class FillingBySpoutMixin {

    @Inject(method = "fillItem", at = @At("RETURN"), cancellable = true, remap = false)
    private static void inject$fillItem$return(Level world, int requiredAmount, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<ItemStack> cir) {
        SandFilterHelper.fill(availableFluid, cir);
    }

}
