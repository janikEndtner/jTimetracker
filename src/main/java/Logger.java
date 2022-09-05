import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

	public static void log(String log) {
		String nowStr = getNow();
		System.out.println(nowStr + ' ' + log);
	}

	public static void err(String log) {
		String nowStr = getNow();
		System.err.println(nowStr + ' ' + log);
	}

	private static String getNow() {
		LocalTime now = LocalTime.now();
		return now.format(FORMAT);
	}
}
