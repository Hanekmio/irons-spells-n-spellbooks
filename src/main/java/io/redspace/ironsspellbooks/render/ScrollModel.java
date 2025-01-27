package io.redspace.ironsspellbooks.render;

import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class ScrollModel extends NBTOverrideItemModel {
    public ScrollModel(BakedModel original, ModelBakery loader) {
        super(original, loader);
    }

    @Override
    Optional<ResourceLocation> getModelFromStack(ItemStack itemStack) {
        if (ISpellContainer.isSpellContainer(itemStack)) {
            var school = ISpellContainer.get(itemStack).getSpellAtIndex(0).getSpell().getSchoolType();
            return Optional.of(getScrollModelLocation(school));
        }
        return Optional.empty();
    }

    public static ResourceLocation getScrollModelLocation(SchoolType schoolType) {
        return new ResourceLocation(schoolType.getId().getNamespace(), String.format("item/scroll_%s", schoolType.getId().getPath()));
    }
}
