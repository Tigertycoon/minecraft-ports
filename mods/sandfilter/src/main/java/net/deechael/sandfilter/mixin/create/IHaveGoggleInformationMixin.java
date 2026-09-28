package net.deechael.sandfilter.mixin.create;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.utility.CreateLang;
import net.deechael.sandfilter.utils.SandFilterHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(value = IHaveGoggleInformation.class, remap = false)
public interface IHaveGoggleInformationMixin {

    /**
     * @author DeeChael / mlus
     * @reason add purity information to fluid tooltip
     *
     * In Create 6.0.10 (NeoForge 1.21.1) the method signature changed from
     *   containedFluidTooltip(List, boolean, LazyOptional<IFluidHandler>)
     * to
     *   containedFluidTooltip(List, boolean, IFluidHandler)
     */
    @Overwrite
    default boolean containedFluidTooltip(List<Component> tooltip, boolean isPlayerSneaking,
                                          IFluidHandler handler) {
        if (handler == null)
            return false;

        if (handler.getTanks() == 0)
            return false;

        CreateLang.translate("gui.goggles.fluid_container")
                .forGoggles(tooltip);

        boolean isEmpty = true;
        for (int i = 0; i < handler.getTanks(); i++) {
            FluidStack fluidStack = handler.getFluidInTank(i);
            if (fluidStack.isEmpty())
                continue;

            CreateLang.builder()
                    .add(Component.translatable(SandFilterHelper.isPurified(fluidStack) ? "thirst.purity.purified" : "thirst.purity.dirty"))
                    .add(Component.literal(" "))
                    .add(fluidStack.getHoverName().copy())
                    .style(sandfilter$getPurityColor(SandFilterHelper.isPurified(fluidStack)))
                    .forGoggles(tooltip, 1);

            CreateLang.builder()
                    .add(CreateLang.number(fluidStack.getAmount())
                            .add(CreateLang.translate("generic.unit.millibuckets"))
                            .style(ChatFormatting.GOLD))
                    .text(ChatFormatting.GRAY, " / ")
                    .add(CreateLang.number(handler.getTankCapacity(i))
                            .add(CreateLang.translate("generic.unit.millibuckets"))
                            .style(ChatFormatting.DARK_GRAY))
                    .forGoggles(tooltip, 1);

            isEmpty = false;
        }

        if (handler.getTanks() > 1) {
            if (isEmpty)
                tooltip.remove(tooltip.size() - 1);
            return true;
        }

        if (!isEmpty)
            return true;

        CreateLang.translate("gui.goggles.fluid_container.capacity")
                .add(CreateLang.number(handler.getTankCapacity(0))
                        .add(CreateLang.translate("generic.unit.millibuckets"))
                        .style(ChatFormatting.GOLD))
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip, 1);

        return true;
    }

    @Unique
    default ChatFormatting sandfilter$getPurityColor(boolean purified) {
        if (purified) {
            return ChatFormatting.AQUA;
        } else {
            return ChatFormatting.DARK_GRAY;
        }
    }

}
