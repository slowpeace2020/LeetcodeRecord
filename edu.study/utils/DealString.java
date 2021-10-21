package utils;

public class DealString {
    public static void main(String[] args) {
        String s = "Before solving the 2D problem we first consider a 1D case. The solution is quite simple. Just find the median of all the x coordinates and calculate the distance to the median.\n" +
                "Alternatively, we can also use two pointers to solve the 1D problem. left and right are how many people one left/right side of coordinates i/j. If we have more people on the left we let j decrease otherwise increase i. The time complexity is O(n) and space is O(1).\n" +
                "To be more clear, a better view is that we can think i and j as two meeting points. All the people in [0, i] meet ati and all the people in [j, n - 1] meet at j. We let left = sum(vec[:i+1]), right = sum(vec[j:]), which are the number of people at each meet point, and d is the total distance for the left people meet at i and right people meet at j.\n" +
                "Our job is to let i == j with minimum d.\n" +
                "If we increase i by 1, the distance will increase by left since there are left people at i and they just move 1step. The same applies to j, when decrease j by 1, the distance will increase by right. To make sure the total distance d is minimized we certainly want to move the point with less people. And to make sure we do not skip any possible meet point options we need to move one by one.\n" +
                "For the 2D cases we first need to sum the columns and rows into two vectors and call the 1D algorithm. The answer is the sum of the two. The time is then O(mn) and extra space is O(m+n)";
        s="Since the distance is computed using the Manhattan Distance, we can decompose this 2-d problem into two 1-d problems and combine (add) their solutions. In fact, the best meeting point is just the point that comprised by the two best meeting points in each dimension.";

        s="This problem is an extension of the Two Sum problem. Here, the target is zero. Thus, if a + b + c = 0, then a + b = -c. This essentially reduces this problem to Two Sum.\n" +
                "\n" +
                "Thus, we have to find such triplets whose sum is zero and there should be no duplicates in the answer.\n" +
                "\n" +
                "Approach\n" +
                "The naive approach is to check for each possible triplet. This can be done via three nested loops which will make the time complexity proportional to the third power of the number of elements in the array i.e., O(n3).\n" +
                "\n" +
                "Can we do better \uD83E\uDD14? We can use two pointer trick as follows -\n" +
                "\n" +
                "Sort the array (in time O(n * log(n))).\n" +
                "Now for each element i, do the following steps\n" +
                "Set two pointers left — j = i + 1 and right — k = nums.length - 1.\n" +
                "Check if nums[i] + nums[j] + nums[k] == 0 and if it is zero, add these three numbers to the resultant list.\n" +
                "If the sum nums[i] + nums[j] + nums[k] < 0, this means we can move left pointer forward because since the array is sorted and the sum is less than zero, therefore, it makes sense to check for greater value to make the sum bigger.\n" +
                "If the sum nums[i] + nums[j] + nums[k] > 0, this means we are too right and can move the right pointer backward because since the array is sorted and the sum is greater than zero, therefore, it makes sense to check for smaller value to make the sum lesser.\n" +
                "In between loops, we also need to make sure that we are not checking for duplicate values.\n" +
                "Time Complexity\n" +
                "We are scanning the entire array keeping one element fixed. We are doing this for every element in the array. Thus, we are scanning each element of array n number of times. And we are doing this for n times, hence the worst case time complexity will be O(n2 + n * log n) which comes down to O(n2).\n" +
                "\n" +
                "Space Complexity\n" +
                "We are not using any data structure for the intermediate computations, hence the space complexity is O(1).";

        System.out.println(s.replace("\n","                      "));
    }
}
