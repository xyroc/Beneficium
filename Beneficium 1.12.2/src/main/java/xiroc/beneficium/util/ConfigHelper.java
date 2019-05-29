package xiroc.beneficium.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import xiroc.beneficium.Beneficium;
import xiroc.beneficium.network.PacketSound;

public class ConfigHelper {

	public static boolean loaded = false;

	public static Configuration config;

	public static HashMap<String, Object> cache = new HashMap();

	public static Object getObject(String key) {
		if (!loaded)
			load();
		return cache.get(key);
	}

	public static int getInt(String key) {
		if (!loaded)
			load();
		return (int) cache.get(key);
	}

	public static boolean getBoolean(String key) {
		if (!loaded)
			load();
		return (boolean) cache.get(key);
	}

	public static float getFloat(String key) {
		if (!loaded)
			load();
		return (float) cache.get(key);
	}

	public static double getDouble(String key) {
		if (!loaded)
			load();
		return (double) cache.get(key);
	}

	public static String getString(String key) {
		if (!loaded)
			load();
		return (String) cache.get(key);
	}

	public static void load() {
		Beneficium.logger.info("Loading the configuration file");
		config = new Configuration(new File(Loader.instance().getConfigDir(), "Beneficium.cfg"));
		config.load();
		cache.put("xp_multiplier", config.getFloat("xp_multiplier_base", "xp_multipliers", 5.0F, 1.0F, 8192F,
				"The xp multiplier for the basic talisman."));
		cache.put("xp_multiplier_divine", config.getFloat("xp_multiplier_divine", "xp_multipliers", 15.0F, 1.0F, 8192F,
				"The xp multiplier for the divine talisman."));
		// WEIGHT_DIVINE_TALISMAN = config.getInt("WEIGHT_DIVINE_TALISMAN", "loot", 5,
		// 1, 50, "The loot weight for the divine talisman");
		cache.put("xp_boost_enable_level_threshold", config.getBoolean("xp_boost_enable_level_threshold",
				"xp_multipliers", false,
				"If set to true, the talismans will be inactive when the players level is below xp_boost_enable_level_threshold_value"));
		cache.put("xp_boost_enable_level_threshold_value", config.getInt("xp_boost_enable_level_threshold_value",
				"xp_multipliers", 10, 1, 128, "The xp threshold value for the talismans"));
		if (config.hasChanged())
			config.save();
		loaded = true;
	}

	//Dump the config cache to the console
	public static void dump() {
		Beneficium.logger.info("### Config Cache Dump ###");
		Iterator<Entry<String, Object>> iterator = cache.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			Beneficium.logger.info(entry.getKey() + " = " + entry.getValue());
		}
		Beneficium.logger.info("### End Config Cache Dump ###");
	}
	
}
