package xiroc.beneficium.common.advancement.trigger;

import net.minecraft.advancements.CriteriaTriggers;
import xiroc.beneficium.Beneficium;

public class TriggerRegistry {

	public static final TriggerBase TRIGGER_FIND_TREASURE = new TriggerBase("find_treasure");

	public static final TriggerBase[] TRIGGERS = new TriggerBase[] { TRIGGER_FIND_TREASURE };

	public static void register() {
		Beneficium.logger.info("Registering Advancement Triggers");
		for (TriggerBase trigger : TRIGGERS)
			CriteriaTriggers.register(trigger);
	}

}
