package dev.apexstudios.flagexample;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class EnglishGenerator extends LanguageProvider {
    public EnglishGenerator(PackOutput pack) {
        super(pack, FlagExampleMod.ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(FlagExampleModData.DESC_KEY, "Modded Featured Flag - Example Mod");
    }
}
