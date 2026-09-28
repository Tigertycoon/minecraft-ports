package net.deechael.sandfilter.mixin.create;

import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import net.deechael.sandfilter.utils.SandFilterHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GenericItemFilling.class, remap = false)
public class GenericItemFillingMixin {

    // remap = false added to match FillingBySpout — GenericItemFilling is a modded class
    @Inject(method = "fillItem", at = @At("RETURN"), cancellable = true, remap = false)
    private static void inject$fillItem$return(Level world, int requiredAmount, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<ItemStack> cir) {
        SandFilterHelper.fill(availableFluid, cir);
    }

}
