package xiroc.beneficium.common.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import xiroc.beneficium.Beneficium;

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
		cache.put("xp_multiplier", config.getFloat("xp_multiplier_base", "xp_multipliers", 5.0F, 1.0F, 8192F, "The xp multiplier for the basic talisman."));
		cache.put("xp_multiplier_divine", config.getFloat("xp_multiplier_divine", "xp_multipliers", 15.0F, 1.0F, 8192F, "The xp multiplier for the divine talisman."));
		cache.put("xp_boost_enable_level_threshold", config.getBoolean("xp_boost_enable_level_threshold", "xp_multipliers", false, "If set to true, the talismans will be inactive when the players level is below xp_boost_enable_level_threshold_value"));
		cache.put("xp_boost_enable_level_threshold_value", config.getInt("xp_boost_enable_level_threshold_value", "xp_multipliers", 10, 1, 256, "The xp threshold value for the talismans"));
		cache.put("artifact_pickaxe_material_name", config.getString("artifact_pickaxe_material_name", "tools", "artifact", "The name of the artifact pickaxe's tool material"));
		cache.put("artifact_pickaxe_harvest_level", config.getInt("artifact_pickaxe_harvest_level", "tools", 10, 0, 100000, "The harvest level of the artifact pickaxe"));
		cache.put("artifact_pickaxe_durability", config.getInt("artifact_pickaxe_durability", "tools", 7500, 0, 100000, "The durability of the artifact pickaxe"));
		cache.put("artifact_pickaxe_enchantability", config.getInt("artifact_pickaxe_enchantability", "tools", 10, 0, 100000, "The enchantability of the artifact pickaxe"));
		cache.put("artifact_pickaxe_efficiency", config.getFloat("artifact_pickaxe_efficiency", "tools", 15.0F, 0F, 100000.0F, "The efficiency of the artifact pickaxe"));
		cache.put("artifact_pickaxe_attack_damage", config.getFloat("artifact_pickaxe_attack_damage", "tools", 3.5F, 0F, 100000.0F, "The attack damage of the artifact pickaxe (the final damage value will be 2 + artifact_pickaxe_attack_damage)"));
		cache.put("artifact_pickaxe_active_duration", config.getInt("artifact_pickaxe_active_duration", "tools", 1200, 0, 72000, "The duration of the artifact pickaxe active in ticks (1 second = 20 ticks)"));
		cache.put("artifact_pickaxe_active_cooldown", config.getInt("artifact_pickaxe_cooldown", "tools", 6000, 0, 72000, "The cooldown for the artifact pickaxe active in ticks (1 second = 20 ticks)"));
		cache.put("artifact_pickaxe_loot_chance", config.getFloat("artifact_pickaxe_loot_chance", "loot", 0.1F, 0.0F, 1.0F, "The chance of the artifact pickaxe being generated in a jungle temple chest. (0.01 = 1%"));
		cache.put("divine_talisman_loot_chance", config.getFloat("divine_talisman_loot_chance", "loot", 0.05F, 0.0F, 1.0F, "The chance of the divine talisman being generated in a chest. (0.01 = 1%"));
		cache.put("generate_treasure", config.getBoolean("generate_treasure", "worldgen", true, "Determines if treasure chests should be generated in the overworld."));
		if (config.hasChanged())
			config.save();
		loaded = true;
	}

	/**
	 * Dumps the config cache into to console
	 */
	public static void dump() {
		Beneficium.logger.info("### Config Cache Dump ###");
		Iterator<Entry<String, Object>> iterator = cache.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			Beneficium.logger.info(entry.getKey() + " = " + entry.getValue());
		}
		Beneficium.logger.info("### End of Config Cache Dump ###");
	}

	public static void merge(HashMap<String, Object> cache) {
		Iterator<Entry<String, Object>> iterator = cache.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			ConfigHelper.cache.put(entry.getKey(), entry.getValue());
		}
	}

}
