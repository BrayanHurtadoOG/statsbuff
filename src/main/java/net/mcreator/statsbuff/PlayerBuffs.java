package net.mcreator.statsbuff;

import java.util.UUID;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Buffs permanentes del jugador mientras el mod esta instalado.
 * Codigo custom (no generado por MCreator): se engancha a los eventos de Forge.
 *
 * Modo "dios" (v1.1.0): vida y regeneracion al maximo, sin hambre,
 * vision nocturna, respiracion bajo el agua, vuelo en supervivencia,
 * mineria de un golpe y titulo divino en chat / tab / nombre.
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

	/** Nombre divino que se muestra en chat, tab y sobre la cabeza. */
	public static Component godName(String playerName) {
		return Component.literal("Dios " + playerName + ", Entidad Omnipotente").withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD);
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.END || event.side.isClient()) {
			return;
		}
		Player player = event.player;
		ensureModifier(player.getAttribute(Attributes.MAX_HEALTH), MAX_HEALTH_MODIFIER);
		ensureModifier(player.getAttribute(Attributes.MOVEMENT_SPEED), SPEED_MODIFIER);

		if (!player.isAlive()) {
			return;
		}

		// Regeneracion: 1 HP por tick = 20 HP/s = 10 corazones por segundo.
		if (player.getHealth() < player.getMaxHealth()) {
			player.heal(1.0F);
		}

		// Sin hambre: comida y saturacion siempre al maximo, sin agotamiento.
		player.getFoodData().setFoodLevel(20);
		player.getFoodData().setSaturation(20.0F);
		player.getFoodData().setExhaustion(0.0F);

		// Vision nocturna y respiracion bajo el agua permanentes (se refrescan antes de expirar).
		player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 240, 0, false, false, false));
		player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 240, 0, false, false, false));

		// Vuelo en supervivencia: permite el doble-espacio para volar como en creativo.
		if (!player.getAbilities().mayfly) {
			player.getAbilities().mayfly = true;
			player.onUpdateAbilities();
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

	@SubscribeEvent
	public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
		// Mineria divina: cualquier bloque rompible cae de un golpe.
		// (La bedrock y similares siguen siendo irrompibles: el juego no lo permite.)
		event.setNewSpeed(1.0e9F);
	}

	@SubscribeEvent
	public static void onNameFormat(PlayerEvent.NameFormat event) {
		// Nombre sobre la cabeza Y en el chat (el chat decora con getDisplayName()).
		event.setDisplayname(godName(event.getUsername().getString()));
	}

	@SubscribeEvent
	public static void onTabListNameFormat(PlayerEvent.TabListNameFormat event) {
		// Nombre en la lista de jugadores (tab) con el titulo divino.
		event.setDisplayName(godName(event.getPlayer().getName().getString()));
	}
}
