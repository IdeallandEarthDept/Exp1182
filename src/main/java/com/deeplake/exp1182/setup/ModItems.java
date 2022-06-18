package com.deeplake.exp1182.setup;

import com.deeplake.exp1182.Main;
import com.deeplake.exp1182.items.ItemDebug;
import com.deeplake.exp1182.items.ItemTeleport;
import com.deeplake.exp1182.items.ItemTeleportBack;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);
    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(ModSetup.ITEM_GROUP);
    public static final Item.Properties ITEM_PROPERTIES_HIDDEN = new Item.Properties();
    public static final RegistryObject<Item> BOSS_EGG = ITEMS.register("spawn_worldboss", () -> new ForgeSpawnEggItem(ModEntities.WORLD_BOSS, 0xff0000, 0x00ff00, ITEM_PROPERTIES));
    public static final RegistryObject<Item> TP_GO = ITEMS.register("tp_go", () -> new ItemTeleport(ITEM_PROPERTIES));
    public static final RegistryObject<Item> TP_BACK = ITEMS.register("tp_back", () -> new ItemTeleportBack(ITEM_PROPERTIES));
    public static final RegistryObject<Item> BULLET1 = ITEMS.register("bullet1", () -> new Item(ITEM_PROPERTIES));
    public static final RegistryObject<Item> BULLET2 = ITEMS.register("bullet2", () -> new Item(ITEM_PROPERTIES));

    public static final RegistryObject<Item> ITEM_DEBUG = ITEMS.register("item_debug", () -> new ItemDebug(ITEM_PROPERTIES));
//    public static final RegistryObject<Item> MYSTERIOUS_INGOT = ITEMS.register("mysterious_ingot", () -> new Item(ITEM_PROPERTIES));
//    public static final RegistryObject<Item> RAW_MYSTERIOUS_CHUNK = ITEMS.register("raw_mysterious_chunk", () -> new Item(ITEM_PROPERTIES));
}
