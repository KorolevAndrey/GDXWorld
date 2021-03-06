package com.blastedstudios.gdxworld.world.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class RevoluteJoint extends GDXJoint {
	private static final long serialVersionUID = 1L;
	private boolean enableLimit, enableMotor;
	private float lowerAngle, upperAngle, maxMotorTorque, motorSpeed, referenceAngle;
	private Vector2 anchor = new Vector2();

	@Override public Joint attach(World world) {
		RevoluteJointDef def = new RevoluteJointDef();
		def.enableLimit = enableLimit;
		def.enableMotor = enableMotor;
		def.lowerAngle = lowerAngle;
		def.upperAngle = upperAngle;
		def.maxMotorTorque = maxMotorTorque;
		def.motorSpeed = motorSpeed;
		def.referenceAngle = referenceAngle;
		Body[] bodies = getBodyAB(world);
		def.initialize(bodies[0], bodies[1], anchor);
		return attach(world, def);
	}

	public boolean isEnableLimit() {
		return enableLimit;
	}

	public void setEnableLimit(boolean enableLimit) {
		this.enableLimit = enableLimit;
	}

	public boolean isEnableMotor() {
		return enableMotor;
	}

	public void setEnableMotor(boolean enableMotor) {
		this.enableMotor = enableMotor;
	}

	public float getLowerAngle() {
		return lowerAngle;
	}

	public void setLowerAngle(float lowerAngle) {
		this.lowerAngle = lowerAngle;
	}

	public float getUpperAngle() {
		return upperAngle;
	}

	public void setUpperAngle(float upperAngle) {
		this.upperAngle = upperAngle;
	}

	public float getMaxMotorTorque() {
		return maxMotorTorque;
	}

	public void setMaxMotorTorque(float maxMotorTorque) {
		this.maxMotorTorque = maxMotorTorque;
	}

	public float getMotorSpeed() {
		return motorSpeed;
	}

	public void setMotorSpeed(float motorSpeed) {
		this.motorSpeed = motorSpeed;
	}

	public float getReferenceAngle() {
		return referenceAngle;
	}

	public void setReferenceAngle(float referenceAngle) {
		this.referenceAngle = referenceAngle;
	}

	public Vector2 getAnchor() {
		return anchor;
	}

	public void setAnchor(Vector2 anchor) {
		this.anchor = anchor;
	}

	@Override public Vector2 getCenter() {
		return anchor.cpy();
	}

	@Override public Object clone() {
		RevoluteJoint clone = new RevoluteJoint();
		clone.setAnchor(anchor.cpy());
		clone.setEnableLimit(enableLimit);
		clone.setEnableMotor(enableMotor);
		clone.setLowerAngle(lowerAngle);
		clone.setUpperAngle(upperAngle);
		clone.setMaxMotorTorque(maxMotorTorque);
		clone.setMotorSpeed(motorSpeed);
		clone.setReferenceAngle(referenceAngle);
		return super.clone(clone);
	}

	@Override public void translate(Vector2 center) {
		anchor.add(center);
	}

	@Override public void scl(float scalar) {
		anchor.scl(scalar);
	}
}
