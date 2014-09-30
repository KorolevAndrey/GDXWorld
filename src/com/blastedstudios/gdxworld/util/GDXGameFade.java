package com.blastedstudios.gdxworld.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.blastedstudios.gdxworld.ui.AbstractScreen;

public abstract class GDXGameFade {
	private static final float FADE_DURATION = Properties.getFloat("screen.fade.duration", 1f);
	
	public static void fadeInPushScreen(GDXGame game, AbstractScreen screen){
		game.pushScreen(screen);
		Table table = buildTable(new Color(0,0,0,1));
		//looks weird, but to make screen fade in, make black table fade out
		table.addAction(Actions.fadeOut(FADE_DURATION));
		screen.getStage().addActor(table);
	}
	
	public static void fadeOutPopScreen(final GDXGame game){
		final AbstractScreen screen = game.peekScreen();
		Table table = buildTable(new Color(0,0,0,0));
		table.addAction(Actions.fadeIn(FADE_DURATION));
		screen.getStage().addActor(table);
		final long timeStartFade = System.currentTimeMillis();
		screen.getRenderListeners().add(new IScreenListener() {
			@Override public void render(float dt) {
				if(System.currentTimeMillis() - timeStartFade > FADE_DURATION*1000f){
					game.popScreen();
					screen.getRenderListeners().remove(this);
				}
			}
		});
	}
	
	/**
	 * @return a table of the given color
	 */
	public static Table buildTable(Color color){
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fill();
		Table table = new Table();
		table.setWidth(Gdx.graphics.getWidth());
		table.setHeight(Gdx.graphics.getHeight());
		Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
		table.setBackground(drawable);
		return table;
	}
	
	public interface IScreenListener{
		void render(float dt);
	}
}
