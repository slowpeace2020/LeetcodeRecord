package septemper;
import java.util.ArrayList;

public class Tester {

	public static void main(String[] args) {
		
		ArrayList<Laptop> laptopList = new ArrayList<Laptop>();

		for (int i = 1; i <= 5; i++) {
			Laptop test = new Laptop();
			laptopList.add(test);
			System.out.printf("Laptop %d%n", i);
			test.inputLaptop();
		}
		
		
		
		
		// Finding Touchscreens
		System.out.print("Touchscreen laptops: ");
		for (Laptop aLaptop : laptopList) {
			
			if (aLaptop.isTouchscreen() == true)
				System.out.printf("%d  ", laptopList.indexOf(aLaptop) + 1 );
		}
		System.out.println();
		
		System.out.print("None touchscreen laptops: ");
		for (Laptop aLaptop : laptopList) {
			
			if (aLaptop.isTouchscreen() == false)
				System.out.printf("%d  ", laptopList.indexOf(aLaptop) + 1 );
		}
		System.out.println();
		
		
		// Changing Prices
		for (Laptop aLaptop : laptopList) {
			
			if (aLaptop.isTouchscreen())
				System.out.printf("Laptop %d", laptopList.indexOf(aLaptop));
				aLaptop.changePrice(10);
		}
		
		
		// Displaying
		System.out.println("Model		Price		Touchscreen");
		for (Laptop aLaptop : laptopList) {
			
			aLaptop.outputLaptop();
		}
		System.out.println();
		
		
		// Average Prices
		double touchscreenTotal = 0;
		double numofTouchscreen = 0;
		for (Laptop aLaptop : laptopList) {
			
			if (aLaptop.isTouchscreen() == true) {
				touchscreenTotal += aLaptop.getPrice();
				numofTouchscreen += 1;
			}
		}
		System.out.println("Average price of touchscreen laptop are: " + (touchscreenTotal/numofTouchscreen));
		
		
		double nonetouchscreenTotal = 0;
		double numofNoneTouchscreen = 0;
		for (Laptop aLaptop : laptopList) {
			
			if (aLaptop.isTouchscreen() == false) {
				nonetouchscreenTotal += aLaptop.getPrice();
				numofNoneTouchscreen += 1;
			}
		}
		System.out.println("Average price of non-touchscreen laptop are: " + (nonetouchscreenTotal/numofNoneTouchscreen));
		
		
		// Cheapest
		Laptop cheapestLaptop = laptopList.get(0);
		for (Laptop aLaptop : laptopList) {
			if (aLaptop.getPrice() < cheapestLaptop.getPrice()) {
				cheapestLaptop = aLaptop;
			}
		}
		System.out.println("Cheapest laptop is: " +	cheapestLaptop.getModel() + "	Price: " + cheapestLaptop.getPrice());
		
		
		// Cheapest
		Laptop expensiveLaptop = laptopList.get(0);
		for (Laptop aLaptop : laptopList) {
			if (aLaptop.getPrice() > expensiveLaptop.getPrice()) {
				expensiveLaptop = aLaptop;
			}
		}
		System.out.println("Most expensive laptop is: " + expensiveLaptop.getModel() + "	  Price: "  + expensiveLaptop.getPrice());
	}

}
