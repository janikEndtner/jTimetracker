import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class KeyListener implements NativeKeyListener {

	private final ActivityChecker checker;

	public KeyListener(ActivityChecker checker) {
		this.checker = checker;
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		this.checker.setActiveSinceLastCheck(true);
	}
}
