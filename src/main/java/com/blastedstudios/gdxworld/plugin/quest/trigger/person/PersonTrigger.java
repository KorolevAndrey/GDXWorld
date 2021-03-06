package com.blastedstudios.gdxworld.plugin.quest.trigger.person;

import com.blastedstudios.gdxworld.world.quest.trigger.AbstractQuestTrigger;

/**
 * Tracks the distance between two beings. Triggers when distance is below a
 * certain threshold. 
 */
public class PersonTrigger extends AbstractQuestTrigger {
	private static final long serialVersionUID = 1L;
	public static final PersonTrigger DEFAULT = new PersonTrigger("Origin", "Target", 1); 
	private String origin = "", target = "";
	private float distance;
	
	public PersonTrigger(){}
	
	public PersonTrigger(String origin, String target, float distance){
		this.origin = origin;
		this.target = target;
		this.distance = distance;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	@Override public boolean activate(float dt) {
		return getProvider().isNear(origin, target, distance);
	}
	
	@Override public AbstractQuestTrigger clone(){
		return super.clone(new PersonTrigger(origin, target, distance));
	}

	@Override public String toString() {
		return "[PersonTrigger: origin:" + origin + " target:" + target + " distance:" + distance + "]";
	}
}