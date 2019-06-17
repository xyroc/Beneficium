package xiroc.beneficium.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSound implements IMessage {

	public int id;

	public PacketSound() {}

	public PacketSound(int id) {
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.id);
	}

	public static class PacketHanderSound implements IMessageHandler<PacketSound, IMessage> {

		@Override
		public IMessage onMessage(PacketSound message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new Runnable() {

				@Override
				public void run() {
					switch (message.id) {
					case 0:
						Minecraft.getMinecraft().world.playSound(Minecraft.getMinecraft().player.getPosition(),
								SoundEvents.ENTITY_BLAZE_AMBIENT, SoundCategory.NEUTRAL, 2, 1, false);
					}
				}
			});
			return null;
		}

	}

}
