package ld27.util;

import java.io.IOException;
import java.io.InputStream;

public class FileIOHelper {
	public static InputStream loadResource(String path){
		return FileIOHelper.class.getResourceAsStream(path);
	}
}