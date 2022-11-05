package septemper;
import java.util.Scanner;

public class Laptop {
	
	private String model;
	private double price;
	private boolean touchscreen;
	
	public Laptop() {
		this.model = "";
		this.price = 0.00;
		this.touchscreen = false;
	}
	
	public void inputLaptop() {
		Scanner input = new Scanner(System.in);
		System.out.print("Model: ");
		String newModel = input.nextLine();
		
		if (newModel.length() < 3)
			System.out.println("!!!ERROR Input cannot be less than 3 characters!!!");
		else
			this.setModel(newModel);
		
		
		System.out.print("Price: ");
		double newPrice = input.nextDouble();
		
		if (newPrice < 100 || newPrice > 8000)
			System.out.println("!!!ERROR Input cannot be either less than 100 or greater than 8000");
		else
			this.setPrice(newPrice);
			
		
		System.out.print("Touchscreen: ");
		boolean newTouchscreen = input.nextBoolean();
		System.out.println();
		
		this.setTouchscreen(newTouchscreen);
		
		
	}
	
	public void outputLaptop() {
		
		System.out.println(this.model + "  	" + this.price + "  	" + this.touchscreen);
	}
	
	public void changePrice(double percent) {
		
		double oldPrice = this.getPrice();
		double percentage = percent / 100;
		
		this.setPrice(oldPrice + (oldPrice * percentage));
		double difference = this.getPrice() - oldPrice;
		
		
		System.out.println("Percent change   :" + percent + "% \n"
						+  "Price difference :" + difference + "\n"
						+  "Old Price  :" + oldPrice + "\n"
						+  "New Price  :" + this.getPrice() + "\n");
						
	}
	
	
	// Getters & Setters
	// ===================================

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isTouchscreen() {
		return touchscreen;
	}

	public void setTouchscreen(boolean touchscreen) {
		this.touchscreen = touchscreen;
	}
	
	// ===================================


	
	
}
