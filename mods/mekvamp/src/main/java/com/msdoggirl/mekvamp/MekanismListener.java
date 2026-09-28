package com.msdoggirl.mekvamp;

import mekanism.api.MekanismIMC;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;

public class MekanismListener {

    @SubscribeEvent
    public static void onInterModEnqueue(InterModEnqueueEvent event) {
        String modid = "mekanism";
        InterModComms.sendTo(modid, MekanismIMC.ADD_MEKA_SUIT_HELMET_MODULES, Modules.SUNSCREEN_MODULE);
        InterModComms.sendTo(modid, MekanismIMC.ADD_MEKA_SUIT_HELMET_MODULES, Modules.FEEDING_MODULE);
    }
}
