package companyOA.draftking;

import java.util.*;

public class Test {
    class Customer {
        private int customerId;
        private float yearlySpend;

        public Customer(int customerId, float yearlySpend) {
            this.customerId = customerId;
            this.yearlySpend = yearlySpend;
        }

        public int getCustomerId() {
            return customerId;
        }

        public float getYearlySpend() {
            return yearlySpend;
        }
    }

    class Discount {
        private int discountId;
        private int productId;
        private float dollarValue;

        public Discount(int discountId, int productId, float value) {
            this.discountId = discountId;
            this.productId = productId;
            this.dollarValue = value;
        }

        public int getDiscountId() {
            return discountId;
        }

        public int getProductId() {
            return productId;
        }

        public float getDollarValue() {
            return dollarValue;
        }
    }

    class DiscountAssignment {
        private int discountId;
        private int customerId;

        public DiscountAssignment(int discountId,int customerId) {
            this.customerId = customerId;
            this.discountId = discountId;
        }

        public int getDiscountId() {
            return discountId;
        }

        public int getCustomerId() {
            return customerId;
        }
    }

    public static boolean validateDiscounts(List<DiscountAssignment> assignedDiscounts, List<Customer> customers, List<Discount> discounts) {
        //discountId,Discount
        Map<Integer,Discount> discountMap = new HashMap<>();
        //customerId, discountvalue
        Map<Integer,Float> customerDiscount = new HashMap<>();
        Map<Integer,Integer> customerDiscountCount = new HashMap<>();

        Set<Integer> discountIdSet = new HashSet<>();

        for(Discount discount:discounts){
            discountMap.put(discount.discountId,discount);
            discountIdSet.add(discount.discountId);
        }


        for(DiscountAssignment assignment:assignedDiscounts){
            Integer customerId = assignment.customerId;
            Integer discountId = assignment.getDiscountId();
            discountIdSet.remove(discountId);
            customerDiscount.put(customerId,customerDiscount.getOrDefault(customerId, (float) 0)+discountMap.get(discountId).dollarValue);
            customerDiscountCount.put(customerId,customerDiscountCount.getOrDefault(customerId,0)+1);
            if(customerDiscountCount.get(customerId)>3){
                return false;
            }
        }

        if(discountIdSet.size()!=0){
            return false;
        }

        Collections.sort(customers, new Comparator<Customer>() {
            @Override
            public int compare(Customer customer, Customer t1) {
                Float ti_spend = t1.yearlySpend;
                Float customer_spend = customer.yearlySpend;
                return ti_spend.compareTo(customer_spend)==0? customer.customerId-t1.customerId:ti_spend.compareTo(customer_spend);
            }
        });

        PriorityQueue<Map.Entry<Integer,Float>> queue = new PriorityQueue<>((a,b)->(b.getValue()==a.getValue()? a.getKey()-b.getKey():b.getValue().compareTo(a.getValue())));
        queue.addAll(customerDiscount.entrySet());

        int index = 0;
        while (!queue.isEmpty()){

            Map.Entry<Integer,Float> entry = queue.poll();
            Customer customer = customers.get(index);

            if(entry.getKey()!=customer.customerId||customer.yearlySpend<5*entry.getValue()){
                return false;
            }
            index++;
        }

        return true;
    }

    public static void main(String[] args) {

    }
}


