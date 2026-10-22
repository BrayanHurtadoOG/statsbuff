/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.statsbuff.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

import net.mcreator.statsbuff.block.BloquePruebaBlock;
import net.mcreator.statsbuff.StatsbuffMod;

public class StatsbuffModBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, StatsbuffMod.MODID);
	public static final RegistryObject<Block> BLOQUE_PRUEBA;
	static {
		BLOQUE_PRUEBA = REGISTRY.register("bloque_prueba", BloquePruebaBlock::new);
	}
	// Start of user code block custom blocks
	// End of user code block custom blocks
}