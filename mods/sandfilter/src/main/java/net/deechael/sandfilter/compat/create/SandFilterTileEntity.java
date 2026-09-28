package net.deechael.sandfilter.compat.create;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import net.deechael.sandfilter.utils.SandFilterHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import java.util.List;

public class SandFilterTileEntity extends SmartBlockEntity implements IHaveGoggleInformation {

    public static final int TANK_SIZE = 1000;
    // Package-accessible so SandFilterMod.registerCapabilities() can return the right tank
    public SmartFluidTankBehaviour dirtyTank;
    public SmartFluidTankBehaviour purifiedTank;

    public SandFilterTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        dirtyTank = SmartFluidTankBehaviour.single(this, TANK_SIZE);
        behaviours.add(dirtyTank);
        purifiedTank = SmartFluidTankBehaviour.single(this, TANK_SIZE);
        behaviours.add(purifiedTank);
    }

    @Override
    protected AABB createRenderBoundingBox() {
        return super.createRenderBoundingBox().expandTowards(0, -2, 0);
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide)
            return;

        if (dirtyTank.getPrimaryHandler().getFluidAmount() >= 10 && purifiedTank.getPrimaryHandler().getFluidAmount() < TANK_SIZE) {
            FluidStack water = dirtyTank.getPrimaryHandler().drain(10, IFluidHandler.FluidAction.EXECUTE);
            SandFilterHelper.setPurified(water, true);
            purifiedTank.getPrimaryHandler().fill(water, IFluidHandler.FluidAction.EXECUTE);
        }
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        CreateLang.translate("gui.goggles.fluid_container").forGoggles(tooltip);

        int dirtyWaterAmount = dirtyTank.getPrimaryHandler().getFluidAmount();
        int purifiedWaterAmount = purifiedTank.getPrimaryHandler().getFluidAmount();

        buildTooltip(tooltip, dirtyWaterAmount, dirtyTank);
        buildTooltip(tooltip, purifiedWaterAmount, purifiedTank);

        if (dirtyTank.isEmpty() && purifiedTank.isEmpty()) {
            CreateLang.translate("gui.goggles.fluid_container.capacity")
                    .add(
                            CreateLang.number(dirtyTank.getPrimaryHandler().getTankCapacity(0))
                                    .add(CreateLang.translate("generic.unit.millibuckets"))
                                    .style(ChatFormatting.GOLD)
                    )
                    .style(ChatFormatting.GRAY)
                    .forGoggles(tooltip, 1);
        }

        return !dirtyTank.isEmpty() || !purifiedTank.isEmpty();
    }

    private void buildTooltip(List<Component> tooltip, int amount, SmartFluidTankBehaviour tank) {
        if (!tank.isEmpty()) {
            CreateLang.builder()
                    .add(Component.translatable(SandFilterHelper.isPurified(tank.getPrimaryHandler().getFluid()) ? "thirst.purity.purified" : "thirst.purity.dirty"))
                    .add(Component.literal(" "))
                    .add(CreateLang.fluidName(tank.getPrimaryHandler().getFluid()))
                    .style(ChatFormatting.GRAY)
                    .forGoggles(tooltip);

            CreateLang.builder()
                    .add(CreateLang.number(amount)
                            .add(CreateLang.translate("generic.unit.millibuckets"))
                            .style(ChatFormatting.GOLD))
                    .text(ChatFormatting.GRAY, " / ")
                    .add(CreateLang.number(tank.getPrimaryHandler().getCapacity())
                            .add(CreateLang.translate("generic.unit.millibuckets"))
                            .style(ChatFormatting.DARK_GRAY))
                    .forGoggles(tooltip, 1);
        }
    }

}
