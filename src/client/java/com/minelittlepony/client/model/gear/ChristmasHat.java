package com.minelittlepony.client.model.gear;

import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import org.lwjgl.opengl.GL11;

import com.minelittlepony.client.util.render.Color;
import com.minelittlepony.client.util.render.PonyRenderer;
import com.minelittlepony.model.BodyPart;
import com.minelittlepony.model.IPonyModel;
import com.minelittlepony.pony.meta.Wearable;

import java.util.Calendar;
import java.util.UUID;

public class ChristmasHat extends AbstractGear {

    private static final Identifier TEXTURE = new Identifier("minelittlepony", "textures/models/antlers.png");

    private PonyRenderer left;
    private PonyRenderer right;

    private int tint;

    @Override
    public void init(float yOffset, float stretch) {
        this.cuboidList.clear();

        left = new PonyRenderer(this, 0, 0).size(16, 8)
                .around(-7, 0.5F, 0.5F)
                .offset(-7, 0, 0)
                .at(3, -4, 0)
                .box(0, 0, 0, 7, 1, 1, stretch)
                .tex(0, 2).box(0, -1, 0, 1, 1, 1, stretch)
                .tex(4, 2).box(2, -1, 0, 1, 1, 1, stretch)
                .tex(8, 2).box(4, -1, 0, 1, 1, 1, stretch);

        right = new PonyRenderer(this, 0, 4).size(16, 8)
                .around(7, 0.5F, 0.5F)
                .offset(0, 0, 0)
                .at(-3, -4, 0)
                .box(0, 0, 0, 7, 1, 1, stretch)
                .tex(0, 6).box(6, -1, 0, 1, 1, 1, stretch)
                .tex(4, 6).box(4, -1, 0, 1, 1, 1, stretch)
                .tex(8, 6).box(2, -1, 0, 1, 1, 1, stretch);
    }

    @Override
    public boolean canRender(IPonyModel<?> model, Entity entity) {
        return isChristmasDay() || model.isWearing(Wearable.ANTLERS);
    }

    @Override
    public void setLivingAnimations(IPonyModel<?> model, Entity entity) {
        tint = model.getMetadata().getGlowColor();
    }

    @Override
    public void setRotationAndAngles(boolean rainboom, UUID interpolatorId, float move, float swing, float bodySwing, float ticks) {
        float pi = PI * (float) Math.pow(swing, 16);

        float mve = move * 0.6662f;
        float srt = swing / 10;

        bodySwing = MathHelper.cos(mve + pi) * srt;

        bodySwing += 0.1F;

        left.roll = bodySwing;
        right.roll = -bodySwing;
    }

    private boolean isChristmasDay() {
        Calendar cal = Calendar.getInstance();

        return cal.get(Calendar.MONTH) == Calendar.DECEMBER && cal.get(Calendar.DAY_OF_MONTH) == 25;
    }

    @Override
    public BodyPart getGearLocation() {
        return BodyPart.HEAD;
    }

    @Override
    public Identifier getTexture(Entity entity) {
        return TEXTURE;
    }

    @Override
    public void renderPart(float scale, UUID interpolatorId) {
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);

        if (tint != 0) {
            Color.glColor(tint, 1);
        }

        left.render(scale);
        right.render(scale);

        GL11.glPopAttrib();
    }

}
