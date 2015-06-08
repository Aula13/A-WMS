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
 * @author Stefano Pessina, Daniele Ciriello
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
		
		iconResource.addResource(IconTypeAWMS.HOME.name(), iconPath + "home.png");
		iconResource.addResource(IconTypeAWMS.INPUT_ORDER.name(), iconPath + "input-order.png");
		iconResource.addResource(IconTypeAWMS.OUTPUT_ORDER.name(), iconPath + "output-order.png");
		iconResource.addResource(IconTypeAWMS.PLUS.name(), iconPath + "plus.png");
		iconResource.addResource(IconTypeAWMS.MINUS.name(), iconPath + "minus.png");
		iconResource.addResource(IconTypeAWMS.EDIT.name(), iconPath + "edit.png");
		iconResource.addResource(IconTypeAWMS.CONFIRM.name(), iconPath + "confirm.png");
		iconResource.addResource(IconTypeAWMS.CANCEL.name(), iconPath + "cancel.png");
		iconResource.addResource(IconTypeAWMS.USER.name(), iconPath + "user.png");
		iconResource.addResource(IconTypeAWMS.BATCHES.name(), iconPath + "batches.png");
		iconResource.addResource(IconTypeAWMS.REFRESH.name(), iconPath + "refresh.png");
		iconResource.addResource(IconTypeAWMS.START.name(), iconPath + "play.png");
		iconResource.addResource(IconTypeAWMS.PRINT.name(), iconPath + "print.png");
		iconResource.addResource(IconTypeAWMS.COMPLETED.name(), iconPath + "completed.png");
		
		return iconResource;
	}
	
	/**
	 * init image resource class
	 * 
	 * @return Image resource reference
	 */
	private static ImageResource initImageResource() {
		ImageResource imageResource = new ImageResource(Configuration.SUPERVISOR_LOGGER);
		
		imageResource.addResource(ImageTypeAWMS.VERTICAL_BAR.name(), imagePath + "blue_vertical_bar.png");
		imageResource.addResource(ImageTypeAWMS.HORIZONTAL_BAR.name(), imagePath + "blue_horizontal_bar.png");
		imageResource.addResource(ImageTypeAWMS.WELCOME.name(), imagePath + "welcome.png");
		
		
		return imageResource;
	}
	
}
