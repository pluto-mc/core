package com.plutomc.core.client.renderers.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * plutomc_core
 * Copyright (C) 2017  Kevin Boxhoorn
 *
 * plutomc_core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * plutomc_core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with plutomc_core.  If not, see <http://www.gnu.org/licenses/>.
 */
@SideOnly(Side.CLIENT)
public class ModelCrestedCrow extends ModelBase
{
	private ModelRenderer armLeftStart;
	private ModelRenderer armLeftEnd;
	private ModelRenderer wingLeftStart;
	private ModelRenderer wingLeftEnd;
	private ModelRenderer armRightStart;
	private ModelRenderer armRightEnd;
	private ModelRenderer wingRightStart;
	private ModelRenderer wingRightEnd;
	private ModelRenderer tailFront;
	private ModelRenderer tailMid;
	private ModelRenderer tailBack;
	private ModelRenderer legLeft;
	private ModelRenderer legRight;
	private ModelRenderer neck;
	private ModelRenderer head;
	private ModelRenderer beak;
	private ModelRenderer chin;
	private ModelRenderer body;

	public ModelCrestedCrow()
	{
		textureWidth = 77;
		textureHeight = 52;

		armLeftStart = new ModelRenderer(this, 0, 48);
		armLeftStart.addBox(0F, 0F, 0F, 8, 2, 2);
		armLeftStart.setRotationPoint(4F, 4F, 1F);
		armLeftStart.setTextureSize(textureWidth, textureHeight);
		armLeftStart.mirror = true;
		setRotation(armLeftStart, 0F, 0F, 0F);
		armLeftEnd = new ModelRenderer(this, 0, 48);
		armLeftEnd.addBox(0F, 0F, 0F, 8, 2, 2);
		armLeftEnd.setRotationPoint(12F, 4F, 3F);
		armLeftEnd.setTextureSize(textureWidth, textureHeight);
		armLeftEnd.mirror = true;
		setRotation(armLeftEnd, 0F, 0F, 0F);
		wingLeftStart = new ModelRenderer(this, 40, 9);
		wingLeftStart.addBox(0F, 0F, 0F, 8, 1, 10);
		wingLeftStart.setRotationPoint(4F, 4F, 3F);
		wingLeftStart.setTextureSize(textureWidth, textureHeight);
		wingLeftStart.mirror = true;
		setRotation(wingLeftStart, 0F, 0F, 0F);
		wingLeftEnd = new ModelRenderer(this, 40, 0);
		wingLeftEnd.addBox(0F, 0F, 0F, 8, 1, 8);
		wingLeftEnd.setRotationPoint(12F, 4F, 5F);
		wingLeftEnd.setTextureSize(textureWidth, textureHeight);
		wingLeftEnd.mirror = true;
		setRotation(wingLeftEnd, 0F, 0F, 0F);

		armRightStart = new ModelRenderer(this, 0, 48);
		armRightStart.addBox(0F, 0F, 0F, 8, 2, 2);
		armRightStart.setRotationPoint(-12F, 4F, 1F);
		armRightStart.setTextureSize(textureWidth, textureHeight);
		armRightStart.mirror = true;
		setRotation(armRightStart, 0F, 0F, 0F);
		armRightEnd = new ModelRenderer(this, 0, 48);
		armRightEnd.addBox(0F, 0F, 0F, 8, 2, 2);
		armRightEnd.setRotationPoint(-20F, 4F, 3F);
		armRightEnd.setTextureSize(textureWidth, textureHeight);
		armRightEnd.mirror = true;
		setRotation(armRightEnd, 0F, 0F, 0F);
		wingRightStart = new ModelRenderer(this, 40, 9);
		wingRightStart.addBox(0F, 0F, 0F, 8, 1, 10);
		wingRightStart.setRotationPoint(-12F, 4F, 3F);
		wingRightStart.setTextureSize(textureWidth, textureHeight);
		wingRightStart.mirror = true;
		setRotation(wingRightStart, 0F, 0F, 0F);
		wingRightEnd = new ModelRenderer(this, 40, 0);
		wingRightEnd.addBox(0F, 0F, 0F, 8, 1, 8);
		wingRightEnd.setRotationPoint(-20F, 4F, 5F);
		wingRightEnd.setTextureSize(textureWidth, textureHeight);
		wingRightEnd.mirror = true;
		setRotation(wingRightEnd, 0F, 0F, 0F);

		tailFront = new ModelRenderer(this, 0, 37);
		tailFront.addBox(0F, 0F, 0F, 6, 7, 4);
		tailFront.setRotationPoint(-3F, 5F, 14F);
		tailFront.setTextureSize(textureWidth, textureHeight);
		tailFront.mirror = true;
		setRotation(tailFront, 0F, 0F, 0F);
		tailMid = new ModelRenderer(this, 20, 37);
		tailMid.addBox(0F, 0F, 0F, 4, 4, 3);
		tailMid.setRotationPoint(-2F, 6F, 18F);
		tailMid.setTextureSize(textureWidth, textureHeight);
		tailMid.mirror = true;
		setRotation(tailMid, 0F, 0F, 0F);
		tailBack = new ModelRenderer(this, 21, 37);
		tailBack.addBox(0F, 0F, 0F, 2, 1, 13);
		tailBack.setRotationPoint(-1F, 7F, 21F);
		tailBack.setTextureSize(textureWidth, textureHeight);
		tailBack.mirror = true;
		setRotation(tailBack, 0F, 0F, 0F);

		legLeft = new ModelRenderer(this, 40, 0);
		legLeft.addBox(0F, 0F, 0F, 1, 7, 1);
		legLeft.setRotationPoint(1F, 16F, 5F);
		legLeft.setTextureSize(textureWidth, textureHeight);
		legLeft.mirror = true;
		setRotation(legLeft, 0F, 0F, 0F);
		ModelRenderer legLeftFoot = new ModelRenderer(this, 40, 9);
		legLeftFoot.addBox(0F, 0F, 0F, 3, 1, 2);
		legLeftFoot.setRotationPoint(-1F, 7F, -1F);
		legLeftFoot.setTextureSize(textureWidth, textureHeight);
		legLeftFoot.mirror = true;
		setRotation(legLeftFoot, 0F, 0F, 0F);
		ModelRenderer toeLeft1 = new ModelRenderer(this, 40, 12);
		toeLeft1.addBox(0F, 0F, 0F, 1, 1, 2);
		toeLeft1.setRotationPoint(0F, 0F, -2F);
		toeLeft1.setTextureSize(textureWidth, textureHeight);
		toeLeft1.mirror = true;
		setRotation(toeLeft1, 0F, 0F, 0F);
		legLeftFoot.addChild(toeLeft1);
		ModelRenderer toeLeft2 = new ModelRenderer(this, 40, 12);
		toeLeft2.addBox(0F, 0F, 0F, 1, 1, 2);
		toeLeft2.setRotationPoint(2F, 0F, -2F);
		toeLeft2.setTextureSize(textureWidth, textureHeight);
		toeLeft2.mirror = true;
		setRotation(toeLeft2, 0F, 0F, 0F);
		legLeftFoot.addChild(toeLeft2);
		ModelRenderer toeLeft3 = new ModelRenderer(this, 40, 12);
		toeLeft3.addBox(0F, 0F, 0F, 1, 1, 2);
		toeLeft3.setRotationPoint(1F, 0F, 2F);
		toeLeft3.setTextureSize(textureWidth, textureHeight);
		toeLeft3.mirror = true;
		setRotation(toeLeft3, 0F, 0F, 0F);
		legLeftFoot.addChild(toeLeft3);
		legLeft.addChild(legLeftFoot);

		legRight = new ModelRenderer(this, 40, 0);
		legRight.addBox(0F, 0F, 0F, 1, 7, 1);
		legRight.setRotationPoint(-2F, 16F, 5F);
		legRight.setTextureSize(textureWidth, textureHeight);
		legRight.mirror = true;
		setRotation(legRight, 0F, 0F, 0F);
		ModelRenderer legRightFoot = new ModelRenderer(this, 40, 9);
		legRightFoot.addBox(0F, 0F, 0F, 3, 1, 2);
		legRightFoot.setRotationPoint(-1F, 7F, -1F);
		legRightFoot.setTextureSize(textureWidth, textureHeight);
		legRightFoot.mirror = true;
		setRotation(legRightFoot, 0F, 0F, 0F);
		ModelRenderer toeRight1 = new ModelRenderer(this, 40, 12);
		toeRight1.addBox(0F, 0F, 0F, 1, 1, 2);
		toeRight1.setRotationPoint(2F, 0F, -2F);
		toeRight1.setTextureSize(textureWidth, textureHeight);
		toeRight1.mirror = true;
		setRotation(toeRight1, 0F, 0F, 0F);
		legRightFoot.addChild(toeRight1);
		ModelRenderer toeRight2 = new ModelRenderer(this, 40, 12);
		toeRight2.addBox(0F, 0F, 0F, 1, 1, 2);
		toeRight2.setRotationPoint(0F, 0F, -2F);
		toeRight2.setTextureSize(textureWidth, textureHeight);
		toeRight2.mirror = true;
		setRotation(toeRight2, 0F, 0F, 0F);
		legRightFoot.addChild(toeRight2);
		ModelRenderer toeRight3 = new ModelRenderer(this, 40, 12);
		toeRight3.addBox(0F, 0F, 0F, 1, 1, 2);
		toeRight3.setRotationPoint(1F, 0F, 2F);
		toeRight3.setTextureSize(textureWidth, textureHeight);
		toeRight3.mirror = true;
		setRotation(toeRight3, 0F, 0F, 0F);
		legRightFoot.addChild(toeRight3);
		legRight.addChild(legRightFoot);

		neck = new ModelRenderer(this, 0, 17);
		neck.addBox(0F, 0F, 0F, 4, 6, 4);
		neck.setRotationPoint(-2F, -2F, 0F);
		neck.setTextureSize(textureWidth, textureHeight);
		neck.mirror = true;
		setRotation(neck, 0F, 0F, 0F);

		head = new ModelRenderer(this, 0, 5);
		head.addBox(-3F, -3F, -3F, 6, 6, 6);
		head.setRotationPoint(0F, -5F, 2F);
		head.setTextureSize(textureWidth, textureHeight);
		head.mirror = true;
		setRotation(head, 0F, 0F, 0F);
		ModelRenderer crestFront = new ModelRenderer(this, 0, 0);
		crestFront.addBox(-3F, -3F, -3F, 1, 4, 1);
		crestFront.setRotationPoint(2.5F, -4F, 0F);
		crestFront.setTextureSize(textureWidth, textureHeight);
		crestFront.mirror = true;
		setRotation(crestFront, 0F, 0F, 0F);
		head.addChild(crestFront);
		ModelRenderer crestMiddle = new ModelRenderer(this, 4, 0);
		crestMiddle.addBox(-3F, -3F, -3F, 2, 4, 1);
		crestMiddle.setRotationPoint(2F, -4F, 2F);
		crestMiddle.setTextureSize(textureWidth, textureHeight);
		crestMiddle.mirror = true;
		setRotation(crestMiddle, 0F, 0F, 0F);
		head.addChild(crestMiddle);
		ModelRenderer crestBack = new ModelRenderer(this, 10, 0);
		crestBack.addBox(-3F, -3F, -3F, 3, 4, 1);
		crestBack.setRotationPoint(1.5F, -4F, 4F);
		crestBack.setTextureSize(textureWidth, textureHeight);
		crestBack.mirror = true;
		setRotation(crestBack, 0F, 0F, 0F);
		head.addChild(crestBack);
		beak = new ModelRenderer(this, 24, 5);
		beak.addBox(-3F, -3F, -3F, 2, 1, 5);
		beak.setRotationPoint(2F, 3F, -5F);
		beak.setTextureSize(textureWidth, textureHeight);
		beak.mirror = true;
		setRotation(beak, 0F, 0F, 0F);
		head.addChild(beak);
		chin = new ModelRenderer(this, 24, 0);
		chin.addBox(-3F, -3F, -3F, 2, 1, 3);
		chin.setRotationPoint(2F, 4F, -3F);
		chin.setTextureSize(textureWidth, textureHeight);
		chin.mirror = true;
		setRotation(chin, 0F, 0F, 0F);
		head.addChild(chin);

		body = new ModelRenderer(this, 8, 11);
		body.addBox(0F, 0F, 0F, 8, 10, 16);
		body.setRotationPoint(-4F, 4F, -2F);
		body.setTextureSize(textureWidth, textureHeight);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		ModelRenderer legLeftTop = new ModelRenderer(this, 40, 20);
		legLeftTop.addBox(0F, 0F, 0F, 3, 2, 3);
		legLeftTop.setRotationPoint(4F, 10F, 6F);
		legLeftTop.setTextureSize(textureWidth, textureHeight);
		legLeftTop.mirror = true;
		setRotation(legLeftTop, 0F, 0F, 0F);
		body.addChild(legLeftTop);
		ModelRenderer legRightTop = new ModelRenderer(this, 40, 20);
		legRightTop.addBox(0F, 0F, 0F, 3, 2, 3);
		legRightTop.setRotationPoint(1F, 10F, 6F);
		legRightTop.setTextureSize(textureWidth, textureHeight);
		legRightTop.mirror = true;
		setRotation(legRightTop, 0F, 0F, 0F);
		body.addChild(legRightTop);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		head.rotateAngleX = (float) Math.toRadians(headPitch);
		head.rotateAngleY = (float) Math.toRadians(netHeadYaw);
		legRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		legLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		armLeftStart.render(scale);
		armLeftEnd.render(scale);
		wingLeftStart.render(scale);
		wingLeftEnd.render(scale);
		tailFront.render(scale);
		tailMid.render(scale);
		tailBack.render(scale);
		legLeft.render(scale);
		legRight.render(scale);
		armRightStart.render(scale);
		armRightEnd.render(scale);
		wingRightEnd.render(scale);
		wingRightStart.render(scale);
		neck.render(scale);
		head.render(scale);
		body.render(scale);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
