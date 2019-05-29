package xiroc.beneficium;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.relauncher.Side;
import xiroc.beneficium.item.ItemArtifactPickaxe;
import xiroc.beneficium.item.ItemTalismanOfTheBenefactor;
import xiroc.beneficium.network.PacketServerConfig;
import xiroc.beneficium.network.PacketSound;
import xiroc.beneficium.util.ConfigHelper;
import xiroc.beneficium.util.CreativeTabBeneficium;
import xiroc.beneficium.util.EventManager;
import xiroc.beneficium.util.Reference;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.MC_VERSIONS, dependencies = Reference.DEPENDENCIES)
public class Beneficium {

	public static final Logger logger = LogManager.getLogger(Reference.NAME);

	public static final SimpleNetworkWrapper NET = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);

	public static final CreativeTabBeneficium tabBeneficium = new CreativeTabBeneficium();

	public static final Item talismanOfTheBenefactor = new ItemTalismanOfTheBenefactor();
	public static final ItemPickaxe artifactPickaxe = new ItemArtifactPickaxe();
	
	public static int packetDiscriminator = 0;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModMetadata data = event.getModMetadata();
		data.autogenerated = false;
		data.name = TextFormatting.GOLD + "Beneficium";
		data.authorList.add("XIROC");
		data.version = Reference.VERSION2;
		data.logoFile = "assets/beneficium/textures/logo/logo.png";
		MinecraftForge.EVENT_BUS.register(new EventManager());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		NET.registerMessage(PacketSound.PacketHanderSound.class, PacketSound.class, packetDiscriminator++, Side.CLIENT);
	}

}
