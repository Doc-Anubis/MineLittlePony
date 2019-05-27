package com.minelittlepony.client.render.layer;

import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.util.AbsoluteHand;

import com.minelittlepony.client.model.entities.ModelIllagerPony;
import com.minelittlepony.client.render.IPonyRender;

public class LayerHeldItemIllager<T extends IllagerEntity> extends LayerHeldPonyItem<T, ModelIllagerPony<T>> {

    public LayerHeldItemIllager(IPonyRender<T, ModelIllagerPony<T>> livingPony) {
        super(livingPony);
    }

    @Override
    public void render(T entity, float move, float swing, float partialTicks, float ticks, float headYaw, float headPitch, float scale) {
        if (shouldRender(entity)) {
            super.render(entity, move, swing, partialTicks, ticks, headYaw, headPitch, scale);
        }
    }

    @Override
    protected void renderArm(AbsoluteHand side) {
        getModel().getArm(side).applyTransform(0.0625F);
    }

    public boolean shouldRender(T entity) {
        return entity.getState() != IllagerEntity.State.CROSSED;
    }
}
