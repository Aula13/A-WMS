package org.wms.main;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * The file being locked in order to check if another application instance is running
 * 
 * @author Stefano Pessina, Daniele Ciriello
 *
 */
public class LockFile {

	/**
	 * the lock file's name
	 */
	public final static String lockFileName = ".app.lock";

	/**
	 * Check if another instance of the application is running
	 * 
	 * @return true if another instance of the application is running
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static boolean checkLockFile() throws IOException {

		RandomAccessFile raf = null;
		FileChannel channel = null;
		FileLock lock = null;

		File file = new File(lockFileName);
		file.deleteOnExit();
		file.createNewFile();

		raf = new RandomAccessFile(file, "rw");
		channel = raf.getChannel();
		lock = channel.tryLock();
		
		if (lock == null) 
			return false;
		else return true;
	}
}
