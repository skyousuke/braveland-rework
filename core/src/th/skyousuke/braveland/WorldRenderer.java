package th.skyousuke.braveland;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class WorldRenderer implements Disposable {

    private WorldController worldController;

    private OrthographicCamera camera = new OrthographicCamera();
    private OrthographicCamera uiCamera = new OrthographicCamera();

    private Viewport viewport = new FitViewport(960, 540, camera);
    private Viewport uiViewport = new FitViewport(960, 540, uiCamera);

    private SpriteBatch batch = new SpriteBatch();
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        shapeRenderer.setColor(Color.RED);
    }

    public void render() {
        Gdx.gl20.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        uiCamera.update();
        worldController.getCameraHelper().applyTo(camera);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        worldController.getLevel().draw(batch);
        batch.end();

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        worldController.getLevel().debug(shapeRenderer);
        shapeRenderer.end();

        batch.setProjectionMatrix(uiCamera.combined);
        shapeRenderer.setProjectionMatrix(uiCamera.combined);
        worldController.getLevel().drawUi(batch, shapeRenderer);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        uiViewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
