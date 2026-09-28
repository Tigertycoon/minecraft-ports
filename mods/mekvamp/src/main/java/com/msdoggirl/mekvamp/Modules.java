package com.msdoggirl.mekvamp;

import mekanism.common.registration.impl.ModuleDeferredRegister;
import mekanism.common.registration.impl.ModuleRegistryObject;

public class Modules {
    public static final ModuleDeferredRegister MODULES = new ModuleDeferredRegister(MekVamp.MODID);

    public static final ModuleRegistryObject<SunscreenModule> SUNSCREEN_MODULE =
            MODULES.register("sunscreen_unit", SunscreenModule::new, () -> Items.SUNSCREEN_MODULE, m -> m);

    public static final ModuleRegistryObject<AutoFeedingModule> FEEDING_MODULE =
            MODULES.register("auto_feeding_unit", AutoFeedingModule::new, () -> Items.FEEDING_MODULE, m -> m);
}
