package xiroc.beneficium.network;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xiroc.beneficium.Beneficium;
import xiroc.beneficium.util.ConfigHelper;

public class PacketServerConfig implements IMessage {

	HashMap<String, Object> cache;

	public PacketServerConfig() {}

	public PacketServerConfig(HashMap<String, Object> cache) {
		this.cache = cache;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		Gson gson = new GsonBuilder().create();
		cache = new HashMap();
		int count = buf.readInt();
		for (int i = 0; i < count; i++) {
			int s = (int) buf.readShort();
			String[] arg = buf.readCharSequence(s, Charset.forName("UTF-8")).toString().split("%%");
			try {
				cache.put(arg[0], gson.fromJson(arg[2], Class.forName(arg[1])));
			} catch (Exception e) {
				Beneficium.logger.error("Failed to load the server config.");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		Gson gson = new GsonBuilder().create();
		Iterator<Entry<String, Object>> iterator = this.cache.entrySet().iterator();
		Iterator<Entry<String, Object>> iterator2 = this.cache.entrySet().iterator();
		int count = 0;
		while (iterator2.hasNext()) {
			iterator2.next();
			count++;
		}
		buf.writeInt(count);
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			String data = entry.getKey() + "%%" + entry.getValue().getClass().getTypeName() + "%%"
					+ gson.toJson(entry.getValue());
			buf.writeShort((short) data.length());
			buf.writeCharSequence(data, Charset.forName("UTF-8"));
		}
	}

	public static class PacketHandlerServerConfig implements IMessageHandler<PacketServerConfig, IMessage> {

		@Override
		public IMessage onMessage(PacketServerConfig message, MessageContext ctx) {
			Beneficium.logger.info("Loading Server Config");
			ConfigHelper.loaded = true;
			ConfigHelper.merge(message.cache);
			return null;
		}

	}

}
