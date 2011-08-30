package de.d2dev.heroquest.engine.rendering.quads;

import de.d2dev.fourseasons.resource.ResourceLocator;

public abstract class AbstractQuadRenderer implements QuadRenderer, QuadRenderModelListener {

	protected QuadRenderModel model;
	protected ResourceLocator resourceProvider;
	
	protected AbstractQuadRenderer(QuadRenderModel m, ResourceLocator p) {
		this.model = m;
		this.model.addListener( this );
		
		this.resourceProvider = p;
	}

	@Override
	public QuadRenderModel getRenderModel() {
		return this.model;
	}

}
