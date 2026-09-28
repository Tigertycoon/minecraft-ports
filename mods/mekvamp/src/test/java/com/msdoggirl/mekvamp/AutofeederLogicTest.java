package com.msdoggirl.mekvamp;

import de.teamlapen.vampirism.api.VReference;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AutofeederLogicTest {
    private FluidTank blood(int amount) {
        var tank = new FluidTank(2000);
        tank.setFluid(new FluidStack(VReference.BLOOD.get(), amount));
        return tank;
    }
    @Test void preservesUnusableFraction() {
        var tank = blood(250);
        assertEquals(200, AutofeederLogic.drainBlood(tank, 1000));
        assertEquals(50, tank.getFluidAmount());
    }
    @Test void doesNotConsumeLessThanOneBloodPoint() {
        var tank = blood(99);
        assertEquals(0, AutofeederLogic.drainBlood(tank, 1000));
        assertEquals(99, tank.getFluidAmount());
    }
    @Test void neverDrainsBeyondTheDeficit() {
        var tank = blood(1000);
        assertEquals(300, AutofeederLogic.drainBlood(tank, 300));
        assertEquals(700, tank.getFluidAmount());
    }
    @Test void doesNotConsumeOtherFluids() {
        var tank = new FluidTank(1000);
        tank.setFluid(new FluidStack(Fluids.WATER, 1000));
        assertEquals(0, AutofeederLogic.drainBlood(tank, 1000));
        assertEquals(1000, tank.getFluidAmount());
    }
}
