package com.msdoggirl.mekvamp;

import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.player.vampire.IBloodStats;
import de.teamlapen.vampirism.api.entity.player.vampire.IVampirePlayer;
import de.teamlapen.vampirism.entity.vampire.DrinkBloodContext;
import mekanism.api.gear.IModule;
import mekanism.api.gear.IModuleHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class AutofeederLogic {
    private static final int MB_PER_BLOOD_POINT = 100;

    public static void tick(Player player) {
        // Server-side only
        if (player.level().isClientSide) return;

        // Only act on vampires
        if (VampirismAPI.factionRegistry().getFaction(player) != VReference.VAMPIRE_FACTION) return;

        CompoundTag data = player.getPersistentData();
        long currentTime = player.level().getGameTime();
        long lastFeed = data.getLong("mekvamp_last_feed");

        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        IModule<AutoFeedingModule> module = IModuleHelper.INSTANCE.getModule(helmet, Modules.FEEDING_MODULE);
        if (module == null || !module.isEnabled()) return;

        IVampirePlayer vampire = VampirismAPI.vampirePlayer(player);
        if (vampire == null) return;

        IBloodStats stats = vampire.getBloodStats();
        if (stats == null) return;

        if (currentTime - lastFeed < 200) return;

        int current = stats.getBloodLevel();
        int max = stats.getMaxBlood();
        int missing = max - current;
        if (missing <= 0) return;

        int request = missing * MB_PER_BLOOD_POINT;

        for (int slot = 0; slot < player.getInventory().items.size(); slot++) {
            ItemStack stack = player.getInventory().items.get(slot);
            if (stack.isEmpty()) continue;
            var handler = stack.getCapability(Capabilities.FluidHandler.ITEM);
            if (handler == null) continue;
            int gained = drainBlood(handler, request);
            if (gained > 0) {
                ItemStack source = stack.copy();
                player.getInventory().setItem(slot, handler.getContainer());
                vampire.drinkBlood(gained / MB_PER_BLOOD_POINT, 0, new DrinkBloodContext(source));
                data.putLong("mekvamp_last_feed", currentTime);
                return;
            }
        }
    }

    static int drainBlood(IFluidHandler handler, int maxAmount) {
        FluidStack wanted = new FluidStack(VReference.BLOOD.get(), maxAmount);
        FluidStack sim = handler.drain(wanted, IFluidHandler.FluidAction.SIMULATE);
        if (sim.isEmpty() || !sim.getFluid().isSame(VReference.BLOOD.get())) return 0;

        int usable = Math.min(sim.getAmount(), maxAmount) / MB_PER_BLOOD_POINT * MB_PER_BLOOD_POINT;
        if (usable == 0) return 0;
        FluidStack exec = handler.drain(wanted.copyWithAmount(usable), IFluidHandler.FluidAction.EXECUTE);
        return exec.getAmount();
    }
}
