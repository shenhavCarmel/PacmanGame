package Logic;

public class Fruit implements TimerListener{
	
	protected int points;
	protected int appearS1;
	protected int appearS2;
	protected int appearS3;
	protected int delay;
	protected GameTimer leadTimer;
	
	// fruits parent class
	public Fruit(int pointsValue, int appearAt1, int appearAt2, int appearAt3, GameTimer leadTimer) {
	
		this.points = pointsValue;
		this.appearS1 = appearAt1;
		this.appearS2 = appearAt2;
		this.appearS3 = appearAt3;
		this.delay =  100;
		this.leadTimer = leadTimer;
	}
	
	// return how many fruits appear at stage 1
	public int getAppearS1() {
		return appearS1;
	}
	
	// return how many fruits appear at stage 2
	public int getAppearS2() {
		return appearS2;
	}

	// return how many fruits appear at stage 3
	public int getAppearS3() {
		return appearS3;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
}
