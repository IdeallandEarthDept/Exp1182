package com.deeplake.exp1182.datagen;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.setup.ModBlocks;
import com.deeplake.exp1182.setup.ModItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class TutRecipes extends RecipeProvider {

    public TutRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

//        ShapedRecipeBuilder.shaped(ModBlocks.GENERATOR.get())
//                .pattern("mxm")
//                .pattern("x#x")
//                .pattern("#x#")
//                .define('x', Tags.Items.GEMS_DIAMOND)
//                .define('#', Tags.Items.INGOTS_IRON)
//                .define('m', ModItems.MYSTERIOUS_INGOT.get())
//                .group(Main.MOD_ID)
//                .unlockedBy("mysterious", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.MYSTERIOUS_INGOT.get()))
//                .save(consumer);
//        ShapedRecipeBuilder.shaped(ModBlocks.POWERGEN.get())
//                .pattern("mmm")
//                .pattern("x#x")
//                .pattern("#x#")
//                .define('x', Tags.Items.DUSTS_REDSTONE)
//                .define('#', Tags.Items.INGOTS_IRON)
//                .define('m', ModItems.MYSTERIOUS_INGOT.get())
//                .group(Main.MOD_ID)
//                .unlockedBy("mysterious", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.MYSTERIOUS_INGOT.get()))
//                .save(consumer);
//
//        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.MYSTERIOUS_ORE_ITEM),
//                        ModItems.MYSTERIOUS_INGOT.get(), 1.0f, 100)
//                .unlockedBy("has_ore", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.MYSTERIOUS_ORE_ITEM).build()))
//                .save(consumer, "mysterious_ingot1");
//        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_MYSTERIOUS_CHUNK.get()),
//                        ModItems.MYSTERIOUS_INGOT.get(), 0.0f, 100)
//                .unlockedBy("has_chunk", has(ModItems.RAW_MYSTERIOUS_CHUNK.get()))
//                .save(consumer, "mysterious_ingot2");
    }
}
