package dev.apexstudios.flagexample;

import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod(FlagExampleMod.ID)
public final class FlagExampleModData {
    public static final String DESC_KEY = "pack.%s.description".formatted(FlagExampleMod.ID);

    public FlagExampleModData(IEventBus bus) {
        bus.addListener(GatherDataEvent.class, event -> {
            var generator = event.getGenerator();
            var fileHelper = event.getExistingFileHelper();
            var holders = event.getLookupProvider();
            var includeClient = event.includeClient();
            var includeServer = event.includeServer();
            var pack = generator.getPackOutput();
            var includeCommon = includeServer || includeClient;

            // common
            generator.addProvider(includeCommon, PackMetadataGenerator.forFeaturePack(pack, Component.translatable(DESC_KEY)));

            // server

            // client
            generator.addProvider(includeClient, new EnglishGenerator(pack));
        });
    }
}
