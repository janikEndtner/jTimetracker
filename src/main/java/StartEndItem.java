import java.time.LocalDateTime;
import java.time.LocalTime;

public class StartEndItem {
	private LocalTime start;
	private LocalTime end;

	public StartEndItem() {}

	public StartEndItem(LocalTime start, LocalTime end) {
		this.start = start;
		this.end = end;
	}

	public LocalTime getStart() {
		return start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}

	public LocalTime getEnd() {
		return end;
	}

	public void setEnd(LocalTime end) {
		this.end = end;
	}
}
