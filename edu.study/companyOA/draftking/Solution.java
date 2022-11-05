package companyOA.draftking;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static void main(String[] args) {
        ConsoleProcessor processor = new ConsoleProcessor();
        processor.processAllLines();
    }
}

class ConsoleProcessor {

    public OrgChart orgChart = new OrgChart();

    public void processAllLines() {
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();

        Integer numLines = 0;

        try {
            numLines = Integer.valueOf(line.trim());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        for (int i = 0; i < numLines; i++) {
            processLine(in.nextLine());
        }

        in.close();
    }

    protected void processLine(String line) {
        String[] parsedCommand = line.split(",");

        // ignore empty lines
        if (parsedCommand.length == 0) {
            return;
        }

        switch (parsedCommand[0]) {
            case "add":
                orgChart.add(parsedCommand[1], parsedCommand[2], parsedCommand[3]);
                break;
            case "print":
                orgChart.print();
                break;
            case "remove":
                orgChart.remove(parsedCommand[1]);
                break;
            case "move":
                orgChart.move(parsedCommand[1], parsedCommand[2]);
                break;
            case "count":
                System.out.println(orgChart.count(parsedCommand[1]));
                break;
        }
    }
}
class OrgChart {
    int systemId = 0;
    Map<Integer,Employee> map = new    HashMap();
    public void add(String id, String name, String managerId)
    {
        if(map.containsKey(Integer.valueOf(id))){
            return;
        }
        Employee employee = new Employee(id, name, managerId,systemId);
        map.put(Integer.valueOf(id), employee);
        systemId++;
    }

    public void print()
    {
        HashSet<Integer> visited = new    HashSet<>();
        HashMap<Integer,   HashSet<Employee>> relation = new    HashMap<>();
        for(Employee employee:map.values()){
            relation.putIfAbsent(employee.managerId, new    HashSet<>());
            relation.get(employee.managerId).add(employee);
        }
        dfs(relation,visited,-1,"",true);
        return;
    }

    public void remove(String employeeId)
    {
        if(!map.containsKey(Integer.valueOf(employeeId))){
            return;
        }
        map.get(Integer.valueOf(employeeId)).exist = false;
        map.get(Integer.valueOf(employeeId)).sysId = systemId;
        systemId++;
    }

    public void move(String employeeId, String newManagerId)
    {
        if(!map.containsKey(Integer.valueOf(employeeId))){
            return;
        }

        if(!map.containsKey(Integer.valueOf(newManagerId))){
            return;
        }
        map.get(Integer.valueOf(employeeId)).managerId = Integer.valueOf(newManagerId);
        map.get(Integer.valueOf(employeeId)).sysId = systemId;
        systemId++;

    }

    public int count(String employeeId)
    {
        if(!map.containsKey(Integer.valueOf(employeeId))){
            return -1;
        }

        Set<Integer> visited = new    HashSet<>();
        HashMap<Integer,   HashSet<Employee>> relation = new    HashMap<>();
        for(Employee employee:map.values()){
            relation.putIfAbsent(employee.managerId, new    HashSet<>());
            relation.get(employee.managerId).add(employee);
        }

        return dfs(relation, visited, Integer.valueOf(employeeId), "",false);
    }

    private int dfs(   HashMap<Integer,   HashSet<Employee>> relation,Set<Integer> visited,Integer managerId, String space,boolean printOrNot){
        int count = 0;
        List<Employee> list = new ArrayList<>();
        list.addAll(relation.getOrDefault(managerId, new    HashSet<>()));
        Collections.sort(list,(a,b)->(a.sysId.compareTo(b.sysId)));

        for(Employee employee:list){
            if(visited.add(employee.id)){
                String currentSpace =space;
                if(printOrNot){

                    if(!employee.exist&&currentSpace.length()>2){
                        currentSpace = currentSpace.substring(0,currentSpace.length()-2);
                    }
                    System.out.println(currentSpace+employee);

                }
                count+=1+dfs(relation, visited, employee.id, currentSpace+"  ",printOrNot);
            }

        }

        return count;
    }




    class Employee{
        Integer id;
        String name;
        Integer managerId;
        Integer sysId;
        Boolean exist;
        public Employee(String id,String name,String managerId,Integer sysId){
            this.id = Integer.valueOf(id);
            this.name = name;
            this.managerId = Integer.valueOf(managerId);
            this.sysId = sysId;
            this.exist = true;
        }

        @Override
        public String toString(){
            return name+" ["+id+"]";
        }
    }
}