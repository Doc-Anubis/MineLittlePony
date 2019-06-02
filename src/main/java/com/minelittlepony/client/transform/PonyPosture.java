package com.minelittlepony.client.transform;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import com.minelittlepony.model.IModel;

public interface PonyPosture<T extends LivingEntity> {

    PonyPosture<LivingEntity> DEFAULT = new PostureStanding();;
    PonyPosture<LivingEntity> ELYTRA = new PostureElytra();
    PonyPosture<PlayerEntity> FLIGHT = new PostureFlight();
    PonyPosture<PlayerEntity> SWIMMING = new PostureSwimming();
    PonyPosture<LivingEntity> FALLING = new PostureFalling();

    default boolean applies(LivingEntity entity) {
        return true;
    }

    default void apply(T player, IModel model, float yaw, float ticks, int invert) {
        if (applies(player)) {
            double motionX = player.x - player.prevX;
            double motionY = player.onGround ? 0 : player.y - player.prevY;
            double motionZ = player.z - player.prevZ;

            transform(model, player, motionX, invert * motionY, motionZ, yaw, ticks);
        }
    }

    void transform(IModel model, T entity, double motionX, double motionY, double motionZ, float yaw, float ticks);
}