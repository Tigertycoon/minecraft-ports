package net.deechael.sandfilter.utils;

import net.deechael.sandfilter.registry.SandFilterDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sfiomn.legendarysurvivaloverhaul.registry.ItemRegistry;

public final class SandFilterHelper {

    /**
     * Tags a FluidStack as purified or not.
     * In NeoForge 1.21.1 FluidStack uses DataComponents instead of NBT tags.
     */
    public static FluidStack setPurified(FluidStack fluid, boolean isPurified) {
        if (isPurified) {
            fluid.set(SandFilterDataComponents.PURIFIED, true);
        } else {
            fluid.remove(SandFilterDataComponents.PURIFIED);
        }
        return fluid;
    }

    public static boolean isPurified(FluidStack fluid) {
        return fluid.getOrDefault(SandFilterDataComponents.PURIFIED, false);
    }

    public static void fill(FluidStack availableFluid, CallbackInfoReturnable<ItemStack> cir) {
        if (SandFilterHelper.isPurified(availableFluid)) {
            ItemStack itemStack = cir.getReturnValue();
            if (itemStack.getItem() == Items.AIR)
                return;
            if (!itemStack.is(Items.POTION))
                return;
            // 1.21.1: PotionUtils.getPotion() removed; use DataComponents.POTION_CONTENTS
            PotionContents contents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            if (!contents.is(Potions.WATER))
                return;
            ItemStack newStack = new ItemStack(ItemRegistry.PURIFIED_WATER_BOTTLE.get());
            cir.setReturnValue(newStack);
        }
    }

    private SandFilterHelper() {
    }

}
