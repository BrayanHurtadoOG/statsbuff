/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.statsbuff.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;

import net.mcreator.statsbuff.item.EspadaChetadaItem;
import net.mcreator.statsbuff.StatsbuffMod;

public class StatsbuffModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, StatsbuffMod.MODID);
	public static final RegistryObject<Item> BLOQUE_PRUEBA;
	public static final RegistryObject<Item> ESPADA_CHETADA;
	static {
		BLOQUE_PRUEBA = block(StatsbuffModBlocks.BLOQUE_PRUEBA);
		ESPADA_CHETADA = REGISTRY.register("espada_chetada", EspadaChetadaItem::new);
	}

	// Start of user code block custom items
	// End of user code block custom items
	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return block(block, new Item.Properties());
	}

	private static RegistryObject<Item> block(RegistryObject<Block> block, Item.Properties properties) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), properties));
	}
}