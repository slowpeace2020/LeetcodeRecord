package companyOA.mathworks;

import java.util.HashMap;
import java.util.Map;

public class IntegerFlip {

    public static void main(String[] args) {
        getrecurringCycle(1);
//        getrecurringCycle(1000);
    }

    static String recurringCycle(int num){
        if(num==1){
            return "1.0 0";
        }
        int[] reminderArr = new int[1000];
        int index = 1;
        int reminder = 1;
        StringBuilder sb = new StringBuilder("0.");
        reminderArr[reminder%num] = index;
        reminder = (reminder%num)*10;
        index++;
        while (reminderArr[reminder%num]==0){
            if(reminder%num==0){
                sb.append(" 0");
                return sb.toString();
            }

            reminderArr[reminder%num] = index;
            sb.append(reminder/num);
            reminder = (reminder%num)*10;
            index++;
        }
        sb.append(index-reminderArr[reminder%num]);
        return sb.toString();
    }

    static String getrecurringCycle(int num){
        if(1%num==0){
            return 1/num+".0 0";
        }

        Map<Integer,Integer> map  = new HashMap<>();
        StringBuilder sb = new StringBuilder("0.");
        int reminder = 1;
        reminder = (reminder%num)*10;
        int index = 2;
        while (!map.containsKey(reminder)){
            sb.append(reminder/num);
            map.put(reminder,index);
            reminder = (reminder%num)*10;
            index++;
        }
        sb.append(" "+sb.substring(map.get(reminder)));
        System.out.println(sb);
        return sb.toString();
    }
}
