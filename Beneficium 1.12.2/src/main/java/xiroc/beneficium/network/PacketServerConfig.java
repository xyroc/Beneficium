package xiroc.beneficium.network;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import xiroc.beneficium.Beneficium;
import xiroc.beneficium.util.ConfigHelper;

public class PacketServerConfig implements Packet<INetHandler> {

	HashMap<String, Object> cache;

	public PacketServerConfig() {}

	public PacketServerConfig(HashMap<String, Object> cache) {
		this.cache = cache;
	}

	@Override
	public void readPacketData(PacketBuffer buf) throws IOException {
		Gson gson = new GsonBuilder().create();
		cache = new HashMap();
		for (int i = 0; i < buf.readInt(); i++) {
			String[] arg = buf.readCharSequence((int) buf.readShort(), Charset.forName("UTF-8")).toString().split("%%");
			try {
				cache.put(arg[0], gson.fromJson(arg[2], Class.forName(arg[1])));
			} catch (Exception e) {
				Beneficium.logger.error("Failed to load the server config.");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void writePacketData(PacketBuffer buf) throws IOException {
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

	@Override
	public void processPacket(INetHandler handler) {
		ConfigHelper.loaded = true;
		ConfigHelper.cache = this.cache;
		ConfigHelper.dump();
	}

}
