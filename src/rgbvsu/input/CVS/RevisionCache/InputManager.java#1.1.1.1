package rgbvsu.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {
        
  private static final int KEY_COUNT = 256;
        
  private enum KeyState {
    RELEASED, // Not down
    PRESSED,  // Down, but not the first time
    PRESSED_ONCE,      // Down for the first time
	RELEASED_ONCE	// Up for the first time
  }
        
  // Current state of the keyboard
  private static boolean[] currentKeys = null;
        
  // Polled keyboard state
  private static KeyState[] keys = null;
        
  public InputManager() {
    currentKeys = new boolean[ KEY_COUNT ];
    keys = new KeyState[ KEY_COUNT ];
    for( int i = 0; i < KEY_COUNT; ++i ) {
      keys[ i ] = KeyState.RELEASED;
    }
  }
        
  public static synchronized void poll() {
    for( int i = 0; i < KEY_COUNT; ++i ) {
      // Set the key state 
      if( currentKeys[ i ] ) {
        // If the key is down now, but was not
        // down last frame, set it to ONCE,
        // otherwise, set it to PRESSED
        if( keys[ i ] == KeyState.RELEASED )
          keys[ i ] = KeyState.PRESSED_ONCE;
        else
          keys[ i ] = KeyState.PRESSED;
      } else {
		if( keys[ i ] == KeyState.PRESSED )
			keys[ i ] = KeyState.RELEASED_ONCE;
		else
			keys[ i ] = KeyState.RELEASED;
      }
    }
  }
        
  public static boolean keyDown( int keyCode ) {
    return keys[ keyCode ] == KeyState.PRESSED_ONCE ||
           keys[ keyCode ] == KeyState.PRESSED;
  }
        
  public static boolean keyDownOnce( int keyCode ) {
    return keys[ keyCode ] == KeyState.PRESSED_ONCE;
  }
  
  public static boolean keyUpOnce(int keyCode) {
	return keys[ keyCode ] == KeyState.RELEASED_ONCE;
  }
  
  public synchronized void keyPressed( KeyEvent e ) {
    int keyCode = e.getKeyCode();
    if( keyCode >= 0 && keyCode < KEY_COUNT ) {
      currentKeys[ keyCode ] = true;
    }
  }

  public synchronized void keyReleased( KeyEvent e ) {
    int keyCode = e.getKeyCode();
    if( keyCode >= 0 && keyCode < KEY_COUNT ) {
      currentKeys[ keyCode ] = false;
    }
  }

  public void keyTyped( KeyEvent e ) {
    // Not needed
  }
}