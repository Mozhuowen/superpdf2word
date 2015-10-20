package tools;

public class LogUtil{
	
	public static final int VERBOSE = 1;
	
	public static final int DEBUG = 2;
	
	public static final int INFO = 3;
	
	public static final int WARN = 4;
	
	public static final int ERROR = 5;
	
	public static final int NOTHING = 6;
	
	public static final int LEVEL = 1;
	
	public static void v(String msg) {
		if (LEVEL <= VERBOSE) {
			System.out.println(msg);
		}
	}
	public static void v(Object object,String msg) {
		if (LEVEL <= VERBOSE) {
			String classname = object.getClass().getName();
			System.out.println(classname + " "+msg);
		}		
	}
	public static void e(Object object,String msg) {
		if (LEVEL <= ERROR) {
			String classname = object.getClass().getName();
			System.out.println(classname + " "+msg);
		}	
	}
}