package week3;

public class Challenge {
    /**
     * getDiscount for every month
     *
     * @param month
     * @return
     */
    public static float getDiscount(String month) {
        float discount = 0;
        switch (month){
            case "1":
                System.out.println("January"+" 0.1f.");
                discount = 0.1f;
                break;
            case "2":
                discount = 0.15f;
                System.out.println("January"+" 0.1f.");
                break;
            case "3":
                discount = 0.1f;
                break;
            default:
        }

        return discount;
    }

    public static float getDiscountII(String month) {
        switch (month) {
            case "January":
                return (float) 0.1;
            case "February":
                return (float) 0.15;
            case "March":
                return (float) 0.1;
            case "May":
                return (float) 0.1;
            case "July":
                return (float) 0.1;
            case "August":
                return (float) 0.1;
            case "October":
                return (float) 0.1;
            case "December":
                return (float) 0.1;
            default:
                System.out.println("Invalid Parameter");
        }

        return 0;
    }

    public static float getDiscountI(String month) {
        float discount = 0;
        switch (month) {
            case "January":
                discount = 0.1f;
                System.out.println();
                break;
            case "February":
                discount = 0.15f;
                break;
            case "March":
                discount = 0.1f;
                break;
            case "May":
                discount = 0.1f;
                break;
            case "July":
                discount = 0.1f;
                break;
            case "August":
                discount = 0.1f;
                break;
            case "October":
                discount = 0.1f;
                break;
            case "December":
                discount = 0.1f;
                break;
            default:
        }


        System.out.println(month+" discount is "+discount);
        return discount;
    }

    /**
     * prints the name of show and schedule of that show based on show ID
     * @param showID
     */

    public static void printShowSchedule(int showID) {
        switch (showID) {
            case 1:
                System.out.println("Bird show 12:30 PM");
                break;
            case 2:
                System.out.println("Splash Safari 2:00 PM");
                break;
            case 3:
                System.out.println("Animal Friends Show 12:00 PM, 2:30 PM");
                break;
            case 4:
                System.out.println("Lion Feeding 9:00 AM, 3:00 PM");
                break;
            case 5:
                System.out.println("Zoo Theatre 9:00 AM, 12:30 PM, 3:00 PM");
                break;
        }
    }

    /**
     * decide if a person gets an ice cream or not based on age and the day of the week
     * @param age
     * @param day
     * @return
     */
    public static boolean isFreeIce(int age, String day){
        if(age<=13 && day.equals("Sunday")){
            return true;
        }

        return false;
    }

    /**
     *
     * @param children
     * @param adult
     * @return
     */

    public static float calculatePrice(int children,int adult){
        float amount = children*18+adult*25;
        return amount;
    }

    public static void printReceipt(int children,int adult,String month){
        float adultAmount = calculatePrice(0,adult);
        float childAmount = calculatePrice(children,0);
        float discount = (adultAmount+childAmount)*getDiscount(month);

        System.out.println("|***********************************************|");
        System.out.println("|          Item|            Quantity|      Price|");
        System.out.println("|Adult         |           "+adult+" x 25NZD|"+adultAmount+"    NZD|");
        System.out.println("|Child         |           "+children+" x 18NZD|"+childAmount+"    NZD|");
        System.out.println("|Discount      |           "+String.format("%.2f",discount)+" NZD|-"+String.format("%.2f",discount)+"  NZD|");
        System.out.println("|***********************************************|");
        System.out.println("|                           Total Price|"+(adultAmount+childAmount-discount)+" NZD|");
        System.out.println("|***********************************************|");
        System.out.println("Ticket bought on "+month);
    }

    public static void main(String[] args) {

        float discount = getDiscountI("August");
        System.out.println(discount);



       //Print their receipt for the ticket
//        printReceipt(3,2,"January");
//
//        //Check if their middle child gets a free ice cream
//        boolean medium = isFreeIce(12,"Sunday");
//        if(medium){
//            System.out.println(" the 12 yeas old child could gets a free ice cream");
//        }else{
//            System.out.println(" the 12 yeas old child couldn't gets a free ice cream");
//        }
//
//        //print the schedule for the show.
//        printShowSchedule(2);
    }

}
