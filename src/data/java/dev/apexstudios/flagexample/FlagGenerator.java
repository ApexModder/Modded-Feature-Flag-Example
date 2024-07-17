package dev.apexstudios.flagexample;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.flag.FlagProvider;

public final class FlagGenerator extends FlagProvider {
    public FlagGenerator(PackOutput pack, CompletableFuture<HolderLookup.Provider> holderProvider) {
        super(pack, FlagExampleMod.ID, holderProvider);
    }

    @Override
    protected void generate() {
        // add our `experimental` to list of tags to be generated
        flag(FlagExampleMod.FLAG_EXPERIMENTAL);
    }
}
