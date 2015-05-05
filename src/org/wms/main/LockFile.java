package org.wms.main;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class LockFile {

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
