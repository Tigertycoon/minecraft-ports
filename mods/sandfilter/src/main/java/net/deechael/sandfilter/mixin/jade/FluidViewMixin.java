package net.deechael.sandfilter.mixin.jade;

import net.deechael.sandfilter.utils.SandFilterHelper;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import snownee.jade.api.fluid.JadeFluidObject;
import snownee.jade.api.view.FluidView;
import snownee.jade.util.CommonProxy;

@Mixin(value = FluidView.class, remap = false)
@Pseudo
public class FluidViewMixin {

    @Redirect(method = "readDefault", at = @At(value = "INVOKE", target = "Lsnownee/jade/util/CommonProxy;getFluidName(Lsnownee/jade/api/fluid/JadeFluidObject;)Lnet/minecraft/network/chat/Component;"))
    private static Component redirect$readDefault(JadeFluidObject fluid) {
        FluidStack instance = CommonProxy.toFluidStack(fluid);
        if (instance.isEmpty())
            return CommonProxy.getFluidName(fluid);

        return Component.translatable(SandFilterHelper.isPurified(instance) ? "thirst.purity.purified" : "thirst.purity.dirty")
                .append(" ")
                .append(instance.getHoverName());
    }

}
