package dev.apexstudios.flagexample;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(FlagExampleMod.ID)
public final class FlagExampleMod {
    public static final String ID = "flag_example_mod";

    public FlagExampleMod(IEventBus bus) {

    }

    public static ResourceLocation identifier(String identifier) {
        return ResourceLocation.fromNamespaceAndPath(ID, identifier);
    }

    public static String id(String identifier) {
        return "%s:%s".formatted(ID, identifier);
    }
}
