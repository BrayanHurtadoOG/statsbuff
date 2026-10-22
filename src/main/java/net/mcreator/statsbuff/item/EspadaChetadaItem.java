package net.mcreator.statsbuff.item;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class EspadaChetadaItem extends SwordItem {
	public EspadaChetadaItem() {
		super(new Tier() {
			public int getUses() {
				return 99999;
			}

			public float getSpeed() {
				return 100f;
			}

			public float getAttackDamageBonus() {
				return 996f;
			}

			public int getLevel() {
				return 0;
			}

			public int getEnchantmentValue() {
				return 100;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of();
			}
		}, 3, -3f, new Item.Properties().rarity(Rarity.EPIC).fireResistant());
	}
}