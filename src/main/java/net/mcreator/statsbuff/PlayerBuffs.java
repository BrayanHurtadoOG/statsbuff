package net.mcreator.statsbuff;

import java.util.UUID;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Buffs permanentes del jugador mientras el mod esta instalado.
 * Codigo custom (no generado por MCreator): se engancha a los eventos de Forge.
 */
@Mod.EventBusSubscriber(modid = StatsbuffMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerBuffs {

	// UUIDs fijos: garantizan que los modificadores nunca se dupliquen.
	private static final UUID MAX_HEALTH_UUID = UUID.fromString("5a7b0001-beef-4000-8000-000000000001");
	private static final UUID SPEED_UUID = UUID.fromString("5a7b0001-beef-4000-8000-000000000002");

	// Vida maxima: 20 base + 80 = 100 HP = 50 corazones.
	private static final AttributeModifier MAX_HEALTH_MODIFIER = new AttributeModifier(MAX_HEALTH_UUID, "StatsBuff max health", 80.0D, AttributeModifier.Operation.ADDITION);
	// Velocidad de movimiento x2.
	private static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(SPEED_UUID, "StatsBuff speed", 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.END || event.side.isClient()) {
			return;
		}
		Player player = event.player;
		ensureModifier(player.getAttribute(Attributes.MAX_HEALTH), MAX_HEALTH_MODIFIER);
		ensureModifier(player.getAttribute(Attributes.MOVEMENT_SPEED), SPEED_MODIFIER);
		// Regeneracion: 1 HP por tick = 20 HP/s = 10 corazones por segundo.
		if (player.isAlive() && player.getHealth() < player.getMaxHealth()) {
			player.heal(1.0F);
		}
	}

	private static void ensureModifier(AttributeInstance instance, AttributeModifier modifier) {
		if (instance != null && !instance.hasModifier(modifier)) {
			instance.addTransientModifier(modifier);
		}
	}

	@SubscribeEvent
	public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
		if (event.getEntity() instanceof Player player) {
			Vec3 motion = player.getDeltaMovement();
			// Impulso vertical fijo: salto de unos 5 bloques de alto.
			player.setDeltaMovement(motion.x, 0.95D, motion.z);
		}
	}

	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event) {
		if (event.getEntity() instanceof Player) {
			// Inmunidad total al dano por caida.
			event.setDamageMultiplier(0.0F);
		}
	}
}
