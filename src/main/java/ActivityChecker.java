import java.time.LocalDateTime;

public class ActivityChecker {

	private final TimeWriter writer;

	private boolean activeSinceLastCheck = true;
	private LocalDateTime now;

	public ActivityChecker(TimeWriter writer) {
		this.writer = writer;
		this.now = LocalDateTime.now();
	}

	public void checkActivity() {
		if (activeSinceLastCheck) {
			now = LocalDateTime.now();
			Logger.log("User was active.");
			writer.writeLogs();
		} else {
			Logger.log("Not active");
		}
		activeSinceLastCheck = false;
	}

	public void setActiveSinceLastCheck(boolean active) {
		this.activeSinceLastCheck = active;
	}
}
