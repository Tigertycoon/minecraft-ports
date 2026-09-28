package net.deechael.sandfilter.registry;

import com.mojang.serialization.Codec;
import net.deechael.sandfilter.SandFilterMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

import static net.minecraft.core.registries.Registries.DATA_COMPONENT_TYPE;

public class SandFilterDataComponents {

    private static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(DATA_COMPONENT_TYPE, SandFilterMod.ID);

    /**
     * Marks whether the water in a FluidStack has been purified by the Sand Filter.
     * Default is absent (treated as false / not purified).
     */
    public static final DataComponentType<Boolean> PURIFIED = register(
            "purified",
            builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL)
    );

    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        DataComponentType<T> type = builder.apply(DataComponentType.builder()).build();
        DATA_COMPONENTS.register(name, () -> type);
        return type;
    }

    public static void register(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }

}
