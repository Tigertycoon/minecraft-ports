package net.muluk.legendaryicefire.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.muluk.legendaryicefire.LegendaryIcefireIntegrationMod;
import net.muluk.legendaryicefire.item.drink.FireDragonCanteenItem;
import net.muluk.legendaryicefire.item.drink.IceDragonCanteenItem;
import net.muluk.legendaryicefire.item.drink.LightningDragonCanteenItem;
import net.muluk.legendaryicefire.item.heal.VitalDragonStewItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, LegendaryIcefireIntegrationMod.MOD_ID);

    public static final DeferredHolder<Item, FireDragonCanteenItem> FIRE_CANTEEN = ITEMS.register("fire_dragon_canteen",
            () -> new FireDragonCanteenItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final DeferredHolder<Item, IceDragonCanteenItem> ICE_CANTEEN = ITEMS.register("ice_dragon_canteen",
            () -> new IceDragonCanteenItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final DeferredHolder<Item, LightningDragonCanteenItem> LIGHTNING_CANTEEN = ITEMS.register("lightning_dragon_canteen",
            () -> new LightningDragonCanteenItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final DeferredHolder<Item, VitalDragonStewItem> VITAL_DRAGON_STEW = ITEMS.register("vital_dragon_stew",
            () -> new VitalDragonStewItem(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
