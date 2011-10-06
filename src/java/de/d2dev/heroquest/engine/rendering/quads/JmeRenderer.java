/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.d2dev.heroquest.engine.rendering.quads;

import java.util.HashMap;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.*;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

import de.d2dev.fourseasons.resource.JmeAssetLocatorAdapter;
import de.d2dev.fourseasons.resource.ResourceLocator;

/**
 *
 * @author Justus
 */
public class JmeRenderer extends SimpleApplication implements QuadRenderer, QuadRenderModelListener, JmeResizeableApp {
	
	// Variablen die die Cameraeinstellungen bestimmen
	private float cameraSpeed = 2f;
    private float zoomLevel = 5.0f;
    private float zoomSpeed = 3f;

    
	// Spielfeldvariablen
    QuadRenderModel quadRenderModel = null;
	HashMap<RenderQuad, Geometry> geometrics = new HashMap<RenderQuad, Geometry>();
    
    // Listener
    JmeUserInputListener userListener;
    
    // Asset Manager 
    private AssetManager hqAssetManager;
    
 
    
    public JmeRenderer (QuadRenderModel model, AssetManager assetManager) {
        super();
        
        this.quadRenderModel = model;
        this.quadRenderModel.addListener(this);
        hqAssetManager = assetManager;
        userListener = new JmeUserInputListener (this);
    }
    /** 
     * zeichnet den übergebenen Spielfeldstatus(StupidRenderModel) als 3D Szene.
     * 
     */
     
    private void createMap (){
    	
    	this.getRenderer().setBackgroundColor(ColorRGBA.Pink);
    	// Wenn eine neue Map erstellt wird soll sie zentriert werden
    	cam.setLocation(cam.getLocation().add(new Vector3f(this.quadRenderModel.getWidth()/2, this.quadRenderModel.getHeight()/2, 991)));
    	System.out.println("camera: "+cam.getLocation());
    	// Für jedes Quad des QuadRenderModels wird ein Quad erstellt mit der Textur versehen
    	// und zum rootNode hinzugefügt
        for (int i = 0; i < this.quadRenderModel.getQuads().size(); i++){           
        	addQuadToScene(this.quadRenderModel.getQuads().get(i));
        }
    }
    
    
    /**
     * initialisiert die Tastenbelegungen für einfache Navigation in der Szene.
     */
    
    private void initKeys(){
       flyCam.setEnabled(paused);
       this.
       //inputManager.addMapping("right", new KeyTrigger(KeyInput.KEY_RIGHT));
       //inputManager.addMapping("left", new KeyTrigger(KeyInput.KEY_LEFT));
       //inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_UP));
       //inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_DOWN));
       inputManager.addMapping("zoomOut", new KeyTrigger(KeyInput.KEY_O));
       inputManager.addMapping("zoomIn", new KeyTrigger(KeyInput.KEY_I));
       inputManager.addMapping("characterInfo", new KeyTrigger(KeyInput.KEY_C));
       inputManager.addMapping("dragMoveX", new MouseAxisTrigger(MouseInput.AXIS_X, true));
       inputManager.addMapping("dragMoveX", new MouseAxisTrigger(MouseInput.AXIS_X, false));
       inputManager.addMapping("dragMoveY", new MouseAxisTrigger(MouseInput.AXIS_Y, true));
       inputManager.addMapping("dragMoveY", new MouseAxisTrigger(MouseInput.AXIS_Y, false));
       inputManager.addMapping("rightMouseButton", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
       
       inputManager.addListener(userListener, new String[]{"left", "right", "up", "down", "zoomOut", "zoomIn", "dragMoveX", "dragMoveY", "rightMouseButton", "characterInfo"});
    }
    
    
    
    /**
     * Setzt die Kameraprojektionsart auf parallele Projektion und passt die restlichen Werte an.
     */
    private void setCamToParallelProjektion (){
        // Die Kamera muss auf Parallele Projektion eingestellt werden
        cam.setParallelProjection(true);
        // und die Clipping ebenen müssen an das display angepasst werde
        float aspect = (float) cam.getWidth() / cam.getHeight();
        cam.setFrustum( -100, 1100, -zoomLevel * aspect, zoomLevel * aspect, zoomLevel, -zoomLevel );
        cam.update();
    }   
    
    
    /**
     * Muss überschrieben werden, da sie abstract ist.
     */
    @Override
    public void simpleInitApp() {
    	initKeys();
        setCamToParallelProjektion (); 
        createMap();
    }
    
	@Override
	public void setRenderModel(QuadRenderModel m) {
		this.quadRenderModel = m;
		this.quadRenderModel.addListener(this);
		rootNode.detachAllChildren();
		this.createMap();
	}
	@Override
	public QuadRenderModel getRenderModel() {
		return this.quadRenderModel;
	}
	@Override
	public void onAddQuad(RenderQuad quad) {
		System.out.println("hallo add");
		addQuadToScene(quad);
	}
	@Override
	public void onRemoveQuad(RenderQuad quad) {
		System.out.println("hallo");
		rootNode.detachChild(geometrics.get(quad));
		geometrics.remove(quad);
	}
	
	public void removeQuad(RenderQuad quad) {
		System.out.println("hallo");
		rootNode.detachChild(geometrics.get(quad));
		geometrics.remove(quad);
	}
	@Override
	public void onQuadMoved(RenderQuad quad) {
		geometrics.get(quad).move(quad.getX(), transY(quad), quad.getZLayer());
	}
	
	@Override
	public void onQuadTextureChanged(RenderQuad quad) {
		Texture texture = assetManager.loadTexture( quad.getTexture().getName() );
		geometrics.get(quad).getMaterial().setTexture("ColorMap", texture);
	}
	
	public void moveCamera(float x, float y, boolean dontLeaveMap){
		
		// Ein Temporärer Vector zum Bewegen wird erzeugt
		Vector3f tempVec = new Vector3f(x, y, 0);
		
		if (dontLeaveMap){
			
			// rechter Bildrand darf die Map nicht verlassen
			float rechterBildrand = (cam.getLocation().x + (cam.getFrustumRight() - cam.getFrustumLeft())/2);
			if (rechterBildrand + x > quadRenderModel.getWidth())
				tempVec.x = quadRenderModel.getWidth() - rechterBildrand ;
			
			// linker Bildrand darf die Map nicht verlassen
			float linkerBildrand = (cam.getLocation().x - (cam.getFrustumRight() - cam.getFrustumLeft())/2);
			if (linkerBildrand + x < 0)
				tempVec.x = 0 - linkerBildrand;
			
			// oberer Bildrand darf die Map nicht verlassen
			float obererBildrand = (cam.getLocation().y + (cam.getFrustumTop() - cam.getFrustumBottom())/2);
			if (obererBildrand + y > quadRenderModel.getHeight())
				tempVec.y = quadRenderModel.getHeight() - obererBildrand ;
			
			// unterer Bildrand darf die Map nicht verlassen
			float untererBildrand = (cam.getLocation().y - (cam.getFrustumTop() - cam.getFrustumBottom())/2);
			if (untererBildrand + y < 0)
				tempVec.y = 0 - untererBildrand ;
		}
		// Die Eigentliche Bewegung der Kamera um den ev. angepassten Vector
		cam.setLocation(cam.getLocation().add(tempVec));
	}
	
	private Geometry addQuadToScene (RenderQuad quad){
		// Das Quad wird in seiner Größe erstellt
    	Quad quadMesh = new Quad(quad.getWidth(),quad.getHeight());
        Geometry geom = new Geometry("Quad", quadMesh);
        // Das Quad wird im Geometry bewegt
        geom.move(quad.getX(), transY(quad), quad.getZLayer());
        // Rotation des Quads
        switch (quad.getTextureTurn()){
        case TURN_LEFT_180_DEGREE:
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(0, 0, 1f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(0, 1, 1f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(1, 0, 0f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(1, 1, 1f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(2, 0, 0f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(2, 1, 0f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(3, 0, 1f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(3, 1, 0f);
        break;
        case TURN_LEFT_270_DEGREE:
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(0, 0, 1f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(0, 1, 0f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(1, 0, 1f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(1, 1, 1f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(2, 0, 0f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(2, 1, 1f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(3, 0, 0f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(3, 1, 0f);
        break;
        case TURN_LEFT_90_DEGREE:
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(0, 0, 0f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(0, 1, 1f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(1, 0, 0f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(1, 1, 0f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(2, 0, 1f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(2, 1, 0f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(3, 0, 1f);
        	geom.getMesh().getBuffer(VertexBuffer.Type.TexCoord).setElementComponent(3, 1, 1f);       	
        break;
        }
        // Ein Material mit der zum Quad gehörenden Textur wird erzeugt und dem Geometry hinzugefügt
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        System.out.println(quad.getTexture().getName());
        Texture texture = hqAssetManager.loadTexture( quad.getTexture().getName() );
        mat.setTexture("ColorMap", texture);
        mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        geom.setMaterial(mat);
        geom.setQueueBucket(Bucket.Transparent);
        // Das Geometrie Object wird dem rootnode hinzugefügt
        this.rootNode.attachChild(geom);
        // Damit später auf die Quads zugefriffen werden kann werden diese mit dem zugehörigen
        // RenderQuad als Schlüssel gespeichert 
        geometrics.put(quad, geom);
		return geom;
	}
	
	@Override
	public void onResize(int width, int height) {
		
	}	
	
	private float transY (final RenderQuad quad){
		return (this.quadRenderModel.getHeight()-(quad.getY()+quad.getHeight()));
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////// Getter & Setter /////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////
	
	public void setCameraSpeed(float cameraSpeed) {
		this.cameraSpeed = cameraSpeed;
	}
	public float getCameraSpeed() {
		return cameraSpeed;
	}
	public void setZoomSpeed(float zoomSpeed) {
		this.zoomSpeed = zoomSpeed;
	}
	public float getZoomSpeed() {
		return zoomSpeed;
	}
	public float getZoomLevel() {
		return zoomLevel;
	}
	public void setZoomLevel(float zoomLevel) {
		this.zoomLevel = zoomLevel;
	}
	public QuadRenderModel getQuadRenderModel() {
		return quadRenderModel;
	}
	public void setQuadRenderModel(QuadRenderModel quadRenderModel) {
		this.quadRenderModel = quadRenderModel;
	}
	/**
	 * @return the hqAssetManager
	 */
	public AssetManager getHqAssetManager() {
		return hqAssetManager;
	}
	/**
	 * @param hqAssetManager the hqAssetManager to set
	 */
	public void setHqAssetManager(AssetManager hqAssetManager) {
		this.hqAssetManager = hqAssetManager;
	}
}
