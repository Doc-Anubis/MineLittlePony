package com.minelittlepony.client.model.races;

import net.minecraft.entity.LivingEntity;

import com.minelittlepony.client.model.armour.ModelPonyArmor;
import com.minelittlepony.client.model.armour.PonyArmor;
import com.minelittlepony.client.util.render.PonyRenderer;
import com.minelittlepony.model.BodyPart;
import com.minelittlepony.model.armour.IEquestrianArmour;
import com.mojang.blaze3d.platform.GlStateManager;

public class ModelZebra<T extends LivingEntity> extends ModelEarthPony<T> {

    public PonyRenderer bristles;

    public ModelZebra(boolean useSmallArms) {
        super(useSmallArms);
    }

    @Override
    public IEquestrianArmour<?> createArmour() {
        return new PonyArmor<>(new Armour(), new Armour());
    }

    @Override
    public void transform(BodyPart part) {
        if (part == BodyPart.HEAD || part == BodyPart.NECK) {
            GlStateManager.translatef(0, -0.1F, 0);
        }
        if (part == BodyPart.NECK) {
             GlStateManager.scalef(1, 1.3F, 1);
        }
        super.transform(part);
    }

    @Override
    protected void initHead(float yOffset, float stretch) {
        super.initHead(yOffset, stretch);

        bristles = new PonyRenderer(this, 56, 32);
        head.addChild(bristles);

        bristles.offset(-1, -1, -3)
                .box(0, -10, 2, 2, 6, 2, stretch)
                .box(0, -10, 4, 2, 8, 2, stretch)
                .box(0, -8, 6, 2, 6, 2, stretch)
                .pitch = 0.3F;
        bristles.child(0).offset(-1.01F, 2, -7) //0.01 to prevent z-fighting
                .box(0, -10, 4, 2, 8, 2, stretch)
                .box(0, -8, 6, 2, 6, 2, stretch)
                .pitch = -1F;
    }

    class Armour extends ModelPonyArmor<T> {

        @Override
        public void transform(BodyPart part) {
            if (part == BodyPart.HEAD || part == BodyPart.NECK) {
                GlStateManager.translatef(0, -0.1F, 0);
            }
            if (part == BodyPart.NECK) {
                 GlStateManager.scalef(1, 1.3F, 1);
            }
            super.transform(part);
        }
    }
}
