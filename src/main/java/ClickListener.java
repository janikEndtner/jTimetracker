import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

public class ClickListener implements NativeMouseListener {

	private final ActivityChecker checker;

	public ClickListener(ActivityChecker checker) {
		this.checker = checker;
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		this.checker.setActiveSinceLastCheck(true);
	}
}
