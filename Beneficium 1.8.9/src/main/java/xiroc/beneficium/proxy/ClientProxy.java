package xiroc.beneficium.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import xiroc.beneficium.Beneficium;

public class ClientProxy extends ServerProxy {

	@Override
	public void load() {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Beneficium.talismanOfTheBenefactor, 0,
				new ModelResourceLocation("beneficium:talisman", "inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Beneficium.talismanOfTheBenefactor, 1,
				new ModelResourceLocation("beneficium:divine_talisman", "inventory"));
		ModelBakery.registerItemVariants(Beneficium.talismanOfTheBenefactor,
				new ResourceLocation("beneficium:talisman"), new ResourceLocation("beneficium:divine_talisman"));
	}

}
