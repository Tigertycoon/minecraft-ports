package net.deechael.sandfilter.mixin.create;

import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import net.createmod.catnip.data.Pair;
import net.deechael.sandfilter.registry.SandFilterDataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sfiomn.legendarysurvivaloverhaul.registry.ItemRegistry;

@Mixin(value = GenericItemEmptying.class, remap = false)
public class GenericItemEmptyingMixin {

    @Inject(method = "emptyItem", at = @At("RETURN"), cancellable = true, remap = false)
    private static void inject$emptyItem$return(Level world, ItemStack stack, boolean simulate, CallbackInfoReturnable<Pair<FluidStack, ItemStack>> cir) {
        Pair<FluidStack, ItemStack> output = cir.getReturnValue();

        if (stack.is(ItemRegistry.PURIFIED_WATER_BOTTLE.get())) {
            FluidStack fluidStack = output.getFirst();
            if (fluidStack.isEmpty())
                return;
            // Mark fluid as purified using DataComponent (no NBT in NeoForge 1.21.1 FluidStack)
            fluidStack.set(SandFilterDataComponents.PURIFIED, true);
            cir.setReturnValue(Pair.of(fluidStack, output.getSecond()));
        }
    }

}
