import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class TimeWriter {
	private final String absPath;
	private final DateTimeFormatter formatter;
	private final long maxAllowedBreakMs;

	private File file;
	private ArrayList<StartEndItem> items;

	public TimeWriter(String absPath, String format, long maxAllowedBreakMs) {
		this.absPath = absPath;
		this.formatter = DateTimeFormatter.ofPattern(format);
		this.maxAllowedBreakMs = maxAllowedBreakMs;
	}

	public void writeLogs() {
		this.file = new File(createFilePath());
		try {
			boolean fileCreated = file.createNewFile();
			if (fileCreated) {
				Logger.log("File created");
			}
		} catch (IOException e) {
			throw new RuntimeException("file creation failed", e);
		}
		this.readCurrentFile();
		this.createNewItems();
		this.writeFile();
	}

	private String createFilePath() {
		var dayFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return absPath + LocalDateTime.now().format(dayFormat) + ".txt";
	}

	private void readCurrentFile() {
		Scanner reader;
		try {
			reader = new Scanner(file, StandardCharsets.UTF_8);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found: " + file.getAbsolutePath(), e);
		} catch (IOException e) {
			throw new RuntimeException("IOException while reading file: " + file.getAbsolutePath(), e);
		}
		this.items = new ArrayList<>();
		while (reader.hasNextLine()) {
			this.items.add(this.parseLine(reader.nextLine()));
		}
		reader.close();
	}

	private void writeFile() {
		try {
			FileWriter myWriter = new FileWriter(file, StandardCharsets.UTF_8);
			for (var item : items) {
				myWriter.write(itemToString(item));
			}
			myWriter.close();
		} catch (IOException e) {
			throw new RuntimeException("Problem while wrinting file", e);
		}
	}

	private String itemToString(StartEndItem item) {
		return item.getStart().format(formatter) + " - " + item.getEnd().format(formatter) + "\n";
	}

	private StartEndItem parseLine(String line) {
		String[] parts = line.split(" - ");
		if (parts.length != 2) {
			throw new RuntimeException("wrong size of parts");
		}
		var item = new StartEndItem();
		item.setStart(LocalTime.parse(parts[0], formatter));
		item.setEnd(LocalTime.parse(parts[1], formatter));
		return item;
	}

	private void createNewItems() {
		if (this.items.isEmpty()) {
			this.addNewStartEndItem();
		}
		// replace old end, if younger than MAX_AGE
		if (lastEndItemYoungerThanMaxAllowedBreak()) {
			this.addNewStartEndItem();
		} else {
			// if older than MAX_AGE, create new item
			replaceLastEndItem();
		}
	}

	private boolean lastEndItemYoungerThanMaxAllowedBreak() {
		var lastItem = getLastItem();
		var from = lastItem.getEnd();
		var to = LocalDateTime.now();
		Duration duration = Duration.between(from, to);
		return duration.toMillis() > maxAllowedBreakMs;
	}

	private void replaceLastEndItem() {
		var lastItem = getLastItem();
		lastItem.setEnd(LocalTime.now());
	}

	private StartEndItem getLastItem() {
		return this.items.get(this.items.size()-1);
	}

	private void addNewStartEndItem() {
		var item = new StartEndItem(LocalTime.now(), LocalTime.now());
		this.items.add(item);
	}
}
