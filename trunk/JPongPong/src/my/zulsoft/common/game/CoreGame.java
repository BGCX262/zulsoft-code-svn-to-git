package my.zulsoft.common.game;

public abstract class CoreGame implements Game {

	private boolean stopGameInd = false;
	protected GraphicsDevice graphicsDevice;
	
	@Override
	public void initialize() {
		
	}

	@Override
	public void loadContent() {
		
	}

	@Override
	public void unloadContent() {
		
	}

	@Override
	public void update(GameTime gameTime) {
		
	}

	@Override
	public void draw(GameTime gameTime) {
		
	}

	protected void stopGame()
	{
		stopGameInd = !stopGameInd;
	}
	
	public boolean exitGame() {
		return stopGameInd;
	}

	public void setGraphicsDevice(GraphicsDevice graphicsDevice) {
		this.graphicsDevice = graphicsDevice;
	}
	
	public GraphicsDevice getGraphicsDevice() {
		return this.graphicsDevice;
	}
}
