package com.blastedstudios.gdxworld.plugin.mode.npc;

import java.util.Arrays;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.blastedstudios.gdxworld.physics.PhysicsHelper;
import com.blastedstudios.gdxworld.ui.leveleditor.LevelEditorScreen;
import com.blastedstudios.gdxworld.ui.leveleditor.mode.AbstractMode;
import com.blastedstudios.gdxworld.world.GDXLevel;
import com.blastedstudios.gdxworld.world.GDXNPC;

@PluginImplementation
public class NPCMode extends AbstractMode {
	private NPCWindow npcWindow;
	
	@Override public boolean touchDown(int x, int y, int x1, int y1) {
		super.touchDown(x,y,x1,y1);
		Gdx.app.debug("NPCMouseMode.touchDown", "x="+x+ " y="+y);
		GDXNPC npc = screen.getLevel().getClosestNPC(coordinates.x, coordinates.y);
		if(npc == null || npc.getCoordinates().dst(coordinates.x, coordinates.y) > LevelEditorScreen.NODE_RADIUS)
			npc = new GDXNPC();
		if(npcWindow == null)
			screen.getStage().addActor(npcWindow = new NPCWindow(screen.getSkin(), this, npc));
		npcWindow.setCoordinates(new Vector2(coordinates.x, coordinates.y));
		return false;
	}

	public void addNPC(GDXNPC npc){
		Gdx.app.log("WorldEditorScreen.addNPC", npc.toString());
		if(screen.getBodies().containsKey(npc))
			for(Body body : screen.getBodies().remove(npc))
				screen.getWorld().destroyBody(body);
		if(!screen.getLevel().getNpcs().contains(npc))
			screen.getLevel().getNpcs().add(npc);
		screen.getBodies().put(npc, Arrays.asList(PhysicsHelper.createCircle(screen.getWorld(), 
				LevelEditorScreen.NODE_RADIUS, npc.getCoordinates(), BodyType.StaticBody)));
	}

	public void removeNPC(GDXNPC npc) {
		Gdx.app.log("WorldEditorScreen.removeNPC", npc.toString());
		for(Body body : screen.getBodies().remove(npc))
			screen.getWorld().destroyBody(body);
		screen.getLevel().getNpcs().remove(npc);
	}

	@Override public boolean contains(float x, float y) {
		return npcWindow != null && npcWindow.contains(x, y);
	}

	@Override public void clean() {
		if(npcWindow != null)
			npcWindow.remove();
		npcWindow = null;
	}

	@Override public void loadLevel(GDXLevel level) {
		super.loadLevel(level);
		for(GDXNPC npc : level.getNpcs())
			addNPC(npc);
	}
}