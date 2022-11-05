package companyOA.draftking;

import java.util.*;

public class TestII {
    public static void main(String[] args) {
        Customer c1 = new Customer(1, 100);
        Customer c2 = new Customer(2, 200);
        Customer c3 = new Customer(3, 300);
        Customer c4 = new Customer(4, 400);
        Customer c5 = new Customer(5, 500);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(c1);
        customerList.add(c2);
        customerList.add(c3);
        customerList.add(c4);
        customerList.add(c5);
        Discount d1 = new Discount(1, 1,2);
        Discount d2 = new Discount(2, 1,2);
        Discount d3 = new Discount(3, 1,2);
        Discount d4 = new Discount(4, 1,2);
        Discount d5 = new Discount(5, 1,2);
        List<Discount> discountList = new ArrayList<>();
        discountList.add(d1);
        discountList.add(d2);
        discountList.add(d3);
        discountList.add(d4);
        discountList.add(d5);
        System.out.println(d1.discountId + " " +d2.discountId + " "+ d3.discountId);
        DiscountAssignment as1 = new DiscountAssignment(c1.customerId,d1.discountId);
        DiscountAssignment as2 = new DiscountAssignment(c1.customerId,d2.discountId);
        DiscountAssignment as3 = new DiscountAssignment(c1.customerId,d3.discountId);
        DiscountAssignment as4 = new DiscountAssignment(c1.customerId,d4.discountId);
        DiscountAssignment as5 = new DiscountAssignment(c1.customerId,d4.discountId);
        DiscountAssignment as6 = new DiscountAssignment(c1.customerId,d5.discountId);

        List<DiscountAssignment> ass = new ArrayList<>();
        ass.add(as1);
        ass.add(as2);
        ass.add(as3);
        ass.add(as4);
        ass.add(as5);
        ass.add(as6);
        System.out.println(as1.discountId + " " +as2.discountId + " "+ as3.discountId);
        System.out.println(validateDiscount(ass, customerList, discountList));
    }

    public static boolean validateDiscount(List<DiscountAssignment> assignedDiscount, List<Customer> customers,
                                           List<Discount> discounts) {
        HashMap<Integer, List<Integer>> cdMap = new HashMap<>();
        HashSet<Integer> dis = new HashSet<>();
        HashMap<Integer, Discount> dMap = new HashMap<>();
        for (Discount d : discounts) {
            dMap.put(d.discountId, d);
        }
        for (DiscountAssignment d : assignedDiscount) {
            if (!cdMap.containsKey(d.customerId)) {
                cdMap.put(d.customerId, new ArrayList<>());
            }
            cdMap.get(d.customerId).add(d.discountId);
            dis.add(d.discountId);
            System.out.println(d.discountId);
        }
        System.out.println(dis.size());
        if(dis.size() != discounts.size()) {
            return false;
        }
        List<Customer> c = new ArrayList<>();
        for (Customer i : customers) {
            c.add(i);
        }
        Collections.sort(c, (a, b) -> (a.yearlySpend - b.yearlySpend == 0 ? 0 : a.yearlySpend - b.yearlySpend > 0 ? 1 : -1));
        for (Customer cc : c) {
            System.out.println(cc.customerId);
        }
        float last = 0;
        for (int i = 0; i < c.size(); i++) {

            int cId = c.get(i).customerId;
            if (cdMap.get(cId).size() > 3) {
                return false;
            }
            float sum = 0;
            for (Integer dId : cdMap.get(cId)) {
                sum += dMap.get(dId).dollarValue;
            }
            if (sum <= last || sum > c.get(i).yearlySpend * 0.2) {
                return false;
            }
            last = sum;
        }
        return true;
    }



    static class Customer {
        int customerId;
        float yearlySpend;
        public Customer(int customerId, float yearlySpend) {
            this.customerId = customerId;
            this.yearlySpend = yearlySpend;
        }
    }

    static class Discount {
        int discountId;
        int productId;
        float dollarValue;
        public Discount(int discountId, int productId, float value) {
            this.discountId = discountId;
            this.productId = productId;
            this.dollarValue = value;
        }
    }

    static class DiscountAssignment {
        int discountId;
        int customerId;
        public DiscountAssignment(int customerId,int discountId) {
            this.discountId = discountId;
            this.customerId = customerId;
        }
    }
}
