package org.wms.config;

import it.rmautomazioni.view.graphicsresource.IconResource;
import it.rmautomazioni.view.graphicsresource.IconType;
import it.rmautomazioni.view.graphicsresource.ImageResource;
import it.rmautomazioni.view.graphicsresource.ImageType;

public class ResourceUtil {
	
	public static final ImageResource imageResource = initImageResource();
	
	public static final IconResource iconResource = initIconResource();
	
	private static final String imagePath = "image/";
	
	private static final String iconPath = "icon/";
		
	private static IconResource initIconResource() {
		IconResource iconResource = new IconResource(Configuration.SUPERVISOR_LOGGER);
		
		iconResource.addResource(IconType.HOME.name(), iconPath + "home.png");
		iconResource.addResource(IconType.SETTING.name(), iconPath + "home.png");
		
		return iconResource;
	}
	
	private static ImageResource initImageResource() {
		ImageResource imageResource = new ImageResource(Configuration.SUPERVISOR_LOGGER);
		
		imageResource.addResource(ImageType.VERTICAL_BAR.name(), imagePath + "blue_vertical_bar.png");
		imageResource.addResource(ImageType.HORIZONTAL_BAR.name(), imagePath + "blue_horizontal_bar.png");
		
		imageResource.getResource(ImageType.VERTICAL_BAR.name());
		
		return imageResource;
	}
	
}
