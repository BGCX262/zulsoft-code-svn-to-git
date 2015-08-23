package my.zulsoft.common.game;

public interface Game {
	void initialize();
	void loadContent();
	void unloadContent();
	void update(GameTime gameTime);
	void draw(GameTime gameTime);
}
