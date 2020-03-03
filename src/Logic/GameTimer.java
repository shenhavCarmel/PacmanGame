package Logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

import GUI_Windows.Board;
import GUI_Windows.Game;


public class GameTimer implements ActionListener {

	private static GameTimer instance;
	private Timer t;
	private ArrayList<TimerListener> timerListeners;

	
	public static GameTimer getInstace() {

		if (instance == null) {
			instance = new GameTimer();
		}
		return instance;
	}

	private GameTimer() {
		// create a timer who ticks at 1/10 seconds
		t = new Timer(100,this);
		timerListeners = new ArrayList<TimerListener>();
	}
	
	
	public void stopTimer() {
		t.stop();
	}

	public void startTimer() {
		t.start();
	}

	public void doubleSpeed() {
		t.setDelay(50);	
	}

	public void regularSpeed() {
		t.setDelay(100);
	}
	
	public void addListener(TimerListener l) {
		timerListeners.add(l);
	}

	public void clearListeners() {
		timerListeners.removeAll(timerListeners);
	}

	public void RemoveListener(Object o) {
		timerListeners.remove(o);
	}

	// at a timer tick - initiate action at every listener
	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i=0; i<timerListeners.size(); i++) {
			timerListeners.get(i).action();
		}
	}


}
