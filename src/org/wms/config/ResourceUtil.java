package org.wms.config;

import it.rmautomazioni.view.graphicsresource.IconResource;
import it.rmautomazioni.view.graphicsresource.IconType;
import it.rmautomazioni.view.graphicsresource.ImageResource;
import it.rmautomazioni.view.graphicsresource.ImageType;

/**
 * Load graphics for the GUI
 * 
 * Keep reference to object for retrive icon/image
 * 
 * 
 * @author stefano
 *
 */
public class ResourceUtil {
	
	/**
	 * reference to image resource class
	 * use it for define new image or get existent image from cache
	 */
	public static final ImageResource imageResource = initImageResource();
	
	/**
	 * reference to icon resource class
	 * use it for define new icon or get existent icon cache
	 */
	public static final IconResource iconResource = initIconResource();
	
	/**
	 * image path prefix = 'resource/image/'
	 */
	private static final String imagePath = "image/";
	
	/**
	 * icon path prefix = 'resource/icon/'
	 */
	private static final String iconPath = "icon/";
		
	/**
	 * init the icon resource class
	 * 
	 * @return Icon resource reference
	 */
	private static IconResource initIconResource() {
		IconResource iconResource = new IconResource(Configuration.SUPERVISOR_LOGGER);
		
		iconResource.addResource(IconType.HOME.name(), iconPath + "home.png");
		iconResource.addResource(IconType.SETTING.name(), iconPath + "home.png");
		
		return iconResource;
	}
	
	/**
	 * init image resource class
	 * 
	 * @return Image resource reference
	 */
	private static ImageResource initImageResource() {
		ImageResource imageResource = new ImageResource(Configuration.SUPERVISOR_LOGGER);
		
		imageResource.addResource(ImageType.VERTICAL_BAR.name(), imagePath + "blue_vertical_bar.png");
		imageResource.addResource(ImageType.HORIZONTAL_BAR.name(), imagePath + "blue_horizontal_bar.png");
		
		imageResource.getResource(ImageType.VERTICAL_BAR.name());
		
		return imageResource;
	}
	
}
