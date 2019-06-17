package xiroc.beneficium.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import net.minecraftforge.fml.common.Loader;
import xiroc.beneficium.Beneficium;

public class NetHelper {

	public static final String resourcePackURL = "http://xiroc.ovh/download/AlternativeTalismanTextures";

	private static void downloadToFile(URL url, String path) {
		try {
			URLConnection connection = url.openConnection();
			ReadableByteChannel channel = Channels.newChannel(connection.getInputStream());
			FileOutputStream output = new FileOutputStream(new File(path));
			output.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void downloadResourcePack() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Beneficium.logger.info("Downloading the alternative talisman texture pack");
				try {
					downloadToFile(new URL(resourcePackURL),
							Loader.instance().getConfigDir() + "/../resourcepacks/AlternativeTalismanTextures.zip");
				} catch (Exception e) {
					Beneficium.logger.warn("Failed to download the alternative talisman texture pack: " + e.getMessage());
				}
			}
		}, "Benficium Downloader").start();
	}

}
