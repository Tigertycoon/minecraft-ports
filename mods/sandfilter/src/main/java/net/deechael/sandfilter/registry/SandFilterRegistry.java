package net.deechael.sandfilter.registry;

import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.data.SharedProperties;
import net.deechael.sandfilter.SandFilterMod;
import net.deechael.sandfilter.compat.create.SandFilterBlock;
import net.deechael.sandfilter.compat.create.SandFilterTileEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SandFilterRegistry {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(SandFilterMod.ID);

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(SandFilterMod.ID);

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, SandFilterMod.ID);

    /**
     * DeferredRegister.Blocks.registerBlock() calls BlockBehaviour.Properties.setId()
     * before constructing the Block, fixing the "Missing intrusive holder" crash in NeoForge 1.21.1.
     */
    public static final DeferredBlock<SandFilterBlock> SAND_FILTER_BLOCK =
            BLOCKS.registerBlock(
                    "sand_filter",
                    SandFilterBlock::new,
                    BlockBehaviour.Properties.ofFullCopy(SharedProperties.copperMetal())
            );

    public static final DeferredItem<AssemblyOperatorBlockItem> SAND_FILTER_ITEM =
            ITEMS.register(
                    "sand_filter",
                    () -> new AssemblyOperatorBlockItem(SAND_FILTER_BLOCK.get(), new Item.Properties())
            );

    /**
     * SmartBlockEntity requires BlockEntityType as the first constructor argument, but
     * BlockEntityType.Builder.of() only provides (BlockPos, BlockState).  The array trick
     * captures the type reference so it is available when block entities are instantiated.
     */
    @SuppressWarnings("unchecked")
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SandFilterTileEntity>>
            SAND_FILTER_TILE_ENTITY = BLOCK_ENTITIES.register("sand_filter", () -> {
                BlockEntityType<SandFilterTileEntity>[] ref = new BlockEntityType[1];
                ref[0] = BlockEntityType.Builder
                        .of((pos, state) -> new SandFilterTileEntity(ref[0], pos, state),
                                SAND_FILTER_BLOCK.get())
                        .build(null);
                return ref[0];
            });

    public static void init(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        SandFilterDataComponents.register(modEventBus);
    }

}
