package xiroc.beneficium.common.advancement.trigger;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import xiroc.beneficium.Beneficium;

public class TriggerBase implements ICriterionTrigger<TriggerBase.Instance> {

	private final ResourceLocation ID;
	private final Map<PlayerAdvancements, TriggerBase.Listeners> listeners = Maps.<PlayerAdvancements, TriggerBase.Listeners>newHashMap();

	public TriggerBase(String name) {
		ID = Beneficium.locate(name);
	}

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	public void addListener(PlayerAdvancements playerAdvancementsIn,
			ICriterionTrigger.Listener<TriggerBase.Instance> listener) {
		TriggerBase.Listeners consumeitemtrigger$listeners = this.listeners.get(playerAdvancementsIn);

		if (consumeitemtrigger$listeners == null) {
			consumeitemtrigger$listeners = new TriggerBase.Listeners(playerAdvancementsIn);
			this.listeners.put(playerAdvancementsIn, consumeitemtrigger$listeners);
		}

		consumeitemtrigger$listeners.add(listener);
	}

	@Override
	public void removeListener(PlayerAdvancements playerAdvancementsIn,
			ICriterionTrigger.Listener<TriggerBase.Instance> listener) {
		TriggerBase.Listeners consumeitemtrigger$listeners = this.listeners.get(playerAdvancementsIn);

		if (consumeitemtrigger$listeners != null) {
			consumeitemtrigger$listeners.remove(listener);

			if (consumeitemtrigger$listeners.isEmpty()) {
				this.listeners.remove(playerAdvancementsIn);
			}
		}
	}

	@Override
	public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
		this.listeners.remove(playerAdvancementsIn);
	}

	public void trigger(EntityPlayerMP parPlayer) {
		TriggerBase.Listeners tameanimaltrigger$listeners = listeners.get(parPlayer.getAdvancements());

		if (tameanimaltrigger$listeners != null) {
			tameanimaltrigger$listeners.trigger(parPlayer);
		}
	}

	public class Instance extends AbstractCriterionInstance {

		public Instance(ResourceLocation criterionIn) {
			super(criterionIn);
		}

	}

	private static class Listeners {
		private final PlayerAdvancements playerAdvancements;
		private final Set<ICriterionTrigger.Listener<TriggerBase.Instance>> listeners = Sets.<ICriterionTrigger.Listener<TriggerBase.Instance>>newHashSet();

		public Listeners(PlayerAdvancements playerAdvancementsIn) {
			this.playerAdvancements = playerAdvancementsIn;
		}

		public boolean isEmpty() {
			return this.listeners.isEmpty();
		}

		public void add(ICriterionTrigger.Listener<TriggerBase.Instance> listener) {
			this.listeners.add(listener);
		}

		public void remove(ICriterionTrigger.Listener<TriggerBase.Instance> listener) {
			this.listeners.remove(listener);
		}

		public void trigger(EntityPlayer player) {
			List<ICriterionTrigger.Listener<TriggerBase.Instance>> list = null;
			for (ICriterionTrigger.Listener listener : listeners) {
				if (list == null) {
					list = Lists.newArrayList();
				}
				list.add(listener);

			}
			if (list != null) {
				for (ICriterionTrigger.Listener listener1 : list) {
					listener1.grantCriterion(playerAdvancements);
				}
			}
		}
	}

	@Override
	public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
		return new TriggerBase.Instance(getId());
	}
}