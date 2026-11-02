package net.mcreator.statsbuff;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import net.minecraftforge.network.NetworkEvent;

/**
 * Paquete cliente -> servidor: "quiero teletransportarme hacia donde miro".
 * Sin datos: el servidor calcula el destino con la mirada actual del jugador.
 * Se registra con StatsbuffMod.addNetworkMessage (canal ya creado por MCreator).
 */
public class TeleportGodPacket {

	public TeleportGodPacket() {
	}

	public static void encode(TeleportGodPacket msg, FriendlyByteBuf buf) {
		// Sin payload.
	}

	public static TeleportGodPacket decode(FriendlyByteBuf buf) {
		return new TeleportGodPacket();
	}

	public static void handle(TeleportGodPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayer player = ctx.get().getSender();
			if (player != null && player.isAlive()) {
				GodPowers.teleportForward(player, GodPowers.TELEPORT_RANGE);
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
