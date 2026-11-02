package net.mcreator.statsbuff;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.KeyMapping;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.mojang.blaze3d.platform.InputConstants;

/**
 * Tecla del teletransporte divino. Solo existe en el cliente:
 * esta clase nunca se carga en un servidor dedicado (Dist.CLIENT).
 */
@Mod.EventBusSubscriber(modid = StatsbuffMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class GodClientKeys {

	public static final KeyMapping TELEPORT_KEY = new KeyMapping("key.statsbuff.teleport", KeyConflictContext.IN_GAME,
			InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, "key.categories.statsbuff");

	@SubscribeEvent
	public static void registerKeys(RegisterKeyMappingsEvent event) {
		event.register(TELEPORT_KEY);
	}

	@Mod.EventBusSubscriber(modid = StatsbuffMod.MODID, value = Dist.CLIENT)
	public static class KeyHandler {
		@SubscribeEvent
		public static void onKeyInput(InputEvent.Key event) {
			if (TELEPORT_KEY.consumeClick()) {
				StatsbuffMod.PACKET_HANDLER.sendToServer(new TeleportGodPacket());
			}
		}
	}
}
