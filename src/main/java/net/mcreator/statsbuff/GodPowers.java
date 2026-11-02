package net.mcreator.statsbuff;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Poderes activos del modo "dios". Codigo custom (no generado por MCreator).
 */
public class GodPowers {

	/** Distancia maxima del teletransporte divino, en bloques. */
	public static final double TELEPORT_RANGE = 64.0D;

	/**
	 * Teletransporta al jugador hacia donde esta mirando, hasta TELEPORT_RANGE bloques.
	 * Si la mirada choca con un bloque, aparece sobre el. Busca un punto seguro
	 * (dos bloques de aire) subiendo como maximo 8 bloques; nunca bajo el vacio.
	 */
	public static void teleportForward(ServerPlayer player, double maxDist) {
		Level level = player.level();
		Vec3 eye = player.getEyePosition();
		Vec3 end = eye.add(player.getLookAngle().scale(maxDist));

		BlockHitResult hit = level.clip(new ClipContext(eye, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
		Vec3 dest = hit.getType() == HitResult.Type.MISS ? end : Vec3.atBottomCenterOf(hit.getBlockPos().above());

		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos((int) Math.floor(dest.x), (int) Math.floor(dest.y), (int) Math.floor(dest.z));
		// Nunca bajo la altura minima del mundo (anti-vacio).
		if (pos.getY() < level.getMinBuildHeight() + 1) {
			pos.setY(level.getMinBuildHeight() + 1);
		}
		// Subir hasta encontrar dos bloques de aire libres (no aparecer dentro de un bloque).
		for (int i = 0; i < 8; i++) {
			if (level.getBlockState(pos).isAir() && level.getBlockState(pos.above()).isAir()) {
				break;
			}
			pos.move(0, 1, 0);
		}

		Vec3 from = player.position();
		player.teleportTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
		player.fallDistance = 0.0F;
		// Efecto de sonido estilo enderman en origen y destino.
		level.playSound(null, from.x, from.y, from.z, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
		level.playSound(null, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
	}
}
