import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import static java.lang.Thread.sleep;

public final class Timetracker {

	private static final long CHECK_EVERY_MS = 1000*5*1;
	private static final long  MAX_ALLOWED_BREAK_MS = 1000*60*5;
	private static final String FORMAT = "HH:mm:ss";
	private static final String ABS_PATH = "C:/Users/enja/IdeaProjects/timetracker/logs/";

	public static void main(String[] args) throws InterruptedException {
		var writer = new TimeWriter(ABS_PATH, FORMAT, MAX_ALLOWED_BREAK_MS);
		var checker = new ActivityChecker(writer);
		var keyListener = new KeyListener(checker);
		var clickListener = new ClickListener(checker);

		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}
		GlobalScreen.addNativeKeyListener(keyListener);
		GlobalScreen.addNativeMouseListener(clickListener);

		while (true) {
			checker.checkActivity();
			sleep(CHECK_EVERY_MS);
		}
	}
}
