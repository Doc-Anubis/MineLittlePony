package com.minelittlepony.client.model.part;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

import com.minelittlepony.client.model.AbstractPonyModel;
import com.minelittlepony.model.IPart;
import com.minelittlepony.mson.api.ModelContext;
import com.minelittlepony.mson.api.MsonModel;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class PonyTail extends ModelPart implements IPart, MsonModel {

    private final AbstractPonyModel<?> theModel;

    private int tailStop = 0;

    public PonyTail(AbstractPonyModel<?> model) {
        super(model);
        theModel = model;
    }


    @Override
    public void init(ModelContext context) {
        try {
            int segments = context.getLocals().getValue("segments").get().intValue();

            for (int i = 0; i < segments; i++) {
                Segment segment = context.findByName("segment_" + i);
                segment.index = i;
                addChild(segment);
            }

        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setRotationAndAngles(boolean rainboom, UUID interpolatorId, float move, float swing, float bodySwing, float ticks) {
        roll = rainboom ? 0 : MathHelper.cos(move * 0.8F) * 0.2f * swing;
        yaw = bodySwing;

        if (theModel.getAttributes().isCrouching && !rainboom) {
            rotateSneak();
        } else if (theModel.isRiding()) {
            pivotZ = TAIL_RP_Z_RIDING;
            pivotY = TAIL_RP_Y_RIDING;
            pitch = PI / 5;
        } else {
            setPivot(TAIL_RP_X, TAIL_RP_Y, TAIL_RP_Z_NOTSNEAK);
            if (rainboom) {
                pitch = ROTATE_90 + MathHelper.sin(move) / 10;
            } else {
                pitch = swing / 2;

                swingX(ticks);
            }
        }

        if (rainboom) {
            pivotY += 6;
            pivotZ++;
        }

        tailStop = theModel.getMetadata().getTail().ordinal();
    }

    private void swingX(float ticks) {
        float sinTickFactor = MathHelper.sin(ticks * 0.067f) * 0.05f;
        pitch += sinTickFactor;
        yaw += sinTickFactor;
    }

    private void rotateSneak() {
        setPivot(TAIL_RP_X, TAIL_RP_Y, TAIL_RP_Z_SNEAK);
        pitch = -BODY_ROT_X_SNEAK + 0.1F;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void renderPart(MatrixStack stack, VertexConsumer vertices, int overlayUv, int lightUv, float red, float green, float blue, float alpha, UUID interpolatorId) {
        render(stack, vertices, overlayUv, lightUv, red, green, blue, alpha);
    }

    private static class Segment extends ModelPart implements MsonModel {

        public PonyTail tail;

        public int index;

        public Segment(Model model) {
            super(model);
        }

        @Override
        public void init(ModelContext context) {
            tail = context.getContext();
            context.findByName("segment", this);
        }

        @Override
        public void render(MatrixStack stack, VertexConsumer renderContext, int overlayUv, int lightUv, float red, float green, float blue, float alpha) {
            if (index < tail.tailStop) {
                super.render(stack, renderContext, overlayUv, lightUv, red, green, blue, alpha);
            }
        }
    }
}