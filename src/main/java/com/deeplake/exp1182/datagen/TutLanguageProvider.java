package com.deeplake.exp1182.datagen;

import com.deeplake.exp1182.Idealland;
import com.deeplake.exp1182.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import static com.deeplake.exp1182.setup.ModSetup.TAB_NAME;

public class TutLanguageProvider extends LanguageProvider {

    public TutLanguageProvider(DataGenerator gen, String locale) {
        super(gen, Idealland.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + TAB_NAME, "Tutorial");
        add(MESSAGE_POWERGEN, "Power generator generating %s per tick!");
        add(MESSAGE_GENERATOR, "Generate ores from ingots!");
        add(SCREEN_TUTORIAL_POWERGEN, "Power generator");

        add(Registration.GENERATOR.get(), "Generator");
        add(Registration.POWERGEN.get(), "Power generator");
        add(Registration.PORTAL_BLOCK.get(), "Mysterious Portal");

        add(Registration.MYSTERIOUS_ORE_OVERWORLD.get(), "Mysterious ore");
        add(Registration.MYSTERIOUS_ORE_NETHER.get(), "Mysterious ore");
        add(Registration.MYSTERIOUS_ORE_END.get(), "Mysterious ore");
        add(Registration.MYSTERIOUS_ORE_DEEPSLATE.get(), "Mysterious ore");
        add(Registration.RAW_MYSTERIOUS_CHUNK.get(), "Mysterious Raw Chunk");
        add(Registration.MYSTERIOUS_INGOT.get(), "Mysterious Ingot");
        add(Registration.THIEF_EGG.get(), "Thief Egg");

        add(Registration.THIEF.get(), "Thief");

        add(KeyBindings.KEY_CATEGORIES_TUTORIAL, "Tutorial Keys");
        add(KeyBindings.KEY_GATHER_MANA, "Gather Mana");
        add(PacketGatherMana.MESSAGE_NO_MANA, "No mana on this location");
    }
}

