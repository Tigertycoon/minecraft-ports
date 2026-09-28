package net.deechael.sandfilter.compat.create;

import com.simibubi.create.AllShapes;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.block.IBE;
import net.deechael.sandfilter.registry.SandFilterRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class SandFilterBlock extends Block implements IWrenchable, IBE<SandFilterTileEntity> {

    public SandFilterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull VoxelShape getShape(
            @NotNull BlockState blockState,
            @NotNull BlockGetter blockGetter,
            @NotNull BlockPos blockPos,
            @NotNull CollisionContext collisionContext
    ) {
        return AllShapes.SPOUT;
    }

    @Override
    public void setPlacedBy(
            @NotNull Level pLevel,
            @NotNull BlockPos pPos,
            @NotNull BlockState pState,
            LivingEntity pPlacer,
            @NotNull ItemStack pStack
    ) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        AdvancementBehaviour.setPlacedBy(pLevel, pPos, pPlacer);
    }

    @Override
    public Class<SandFilterTileEntity> getBlockEntityClass() {
        return SandFilterTileEntity.class;
    }

    @Override
    public BlockEntityType<? extends SandFilterTileEntity> getBlockEntityType() {
        return SandFilterRegistry.SAND_FILTER_TILE_ENTITY.get();
    }

}
