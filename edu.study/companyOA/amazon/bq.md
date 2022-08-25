leetcode  说出答题思路和time complexity
121
295
3
973
*2281  https://leetcode.cn/problems/sum-of-total-strength-of-wizards/solution/li-yong-dan-diao-zhan-de-onjie-fa-by-hao-a9i4/
https://leetcode.cn/problems/sum-of-total-strength-of-wizards/solution/2281-wu-shi-de-zong-li-liang-he-java-by-476ac/

209
387
*2492
56 
*696   https://leetcode.cn/problems/count-binary-substrings/solution/count-binary-substrings-by-ikaruga/
*2104  https://leetcode.com/problems/sum-of-subarray-ranges/discuss/1626628/O(n)-solution-with-monotonous-stack-oror-Full-explaination
*1846
20  
*828  https://leetcode.com/problems/count-unique-characters-of-all-substrings-of-a-given-string/discuss/128952/JavaC%2B%2BPython-One-pass-O(N)
*907  https://leetcode.cn/problems/sum-of-subarray-minimums/solution/xiao-bai-lang-dong-hua-xiang-jie-bao-zhe-489q/
926
994 
1151 
*1335 
*1395 
1492 
*1537 https://leetcode.com/problems/get-the-maximum-score/discuss/767987/JavaC%2B%2BPython-Two-Pointers-O(1)-Space
62
灰度值 question2 https://leetcode.com/discuss/interview-question/1733741/Amazon-OA-or-SDE-Intern/1245814


第二轮find farthest smaller from right  https://www.geeksforgeeks.org/find-the-farthest-smaller-number-in-the-right-side/
第三轮Implement QPS
第四轮LRU

题库
https://coda.io/d/Offer_dm5fzh3MwOp/Amazon_suEfQ
https://www.1point3acres.com/bbs/thread-917579-1-1.html

maxAggregatedTempatureChange 和 movieAward的minimumGroup: https://www.1point3acres.com/bbs/thread-917933-1-1.html
aggregate temperature change：https://www.1point3acres.com/bbs/thread-917024-1-1.html
https://www.1point3acres.com/bbs/thread-917290-1-1.html

determine the minimum number of groups that can be formed such that each movie is in exactly one group

Prime Air：https://www.careercup.com/question?id=5750442676453376

Your team at Amazon is developing a new algorithm for suggesting passwords when a user sets up a new account.
A string of lowercase English characters is said to be redundancy-free if each character occurs at most once in the string. In order to ensure minimum redundancy, the developers suggest a password that has the minimum number of redundancy-free segments it can be divided into.
.--
Given a string, password, find the minimum number of redundancy-free segments we can divide it into.

第一题的意思是divide input string into groups such that each group has no repeating characters。题里把这个叫做redundancy-free segments，之前没见过这道题，可能是新题？
example:
Input: "aabcdea" -> output: 3
Input: "alabama" -> output: 4
Input: "zebra" -> output: 1
.
第二题是常见的log in, register, log out API，直接看着以前做的写完了。

第一题find min health高频题
第二题给出一个unsorted数组arr，要求三元组（x,y,z）满足以下条件：1）x<y<z；2）arr[y]是arr[z]的因数，而arr[x]也是arr[y]的因数；如果arr中存在这样的三元组，返回其中最大的arr[y]，否则return -1
理了一下，大致是index需要递增，而index对应的element成因数关系，从后往前找因数，找‍‌‌‍‌‌‌‌‍‌‌‌‍‌‍‌‌‍‌到符合条件的三元组就取最大值，worst case时间复杂度是O(n^2)，优化的话可以弄一个hashmap记录因数关系

常见题链接
https://www.1point3acres.com/bbs/thread-699232-1-1.html

work assessment
https://coda.io/d/Offer_dm5fzh3MwOp/work-assessment_suyk3#_luYAA
https://coda.io/d/Offer_dm5fzh3MwOp/work-simulation-B-1_suRbS#_lufDs

用了heap, 为什么要用heap, heap是什么,max-heap和min-heap区别 就是根据你OA用到的data structure会问得很细。。挂掉的原因，他们说自己答得还行，可能被坑了，具体我也不清楚。
heap的特点（parent比children大或者小），heap可以怎么represent（array，parent是i，children是2i+1和2i+2），然后heap的insert 和remove
Data Structure： Array 跟 linkedlist的区别，各自的优点跟缺点
heap，dfs bfs是什么 怎么用，还有quicksort的时间复杂度是多少
vector, binary tree, dfs, bfs,linked list问了个遍
问了linear和non-linear datastructure，举例，还有cache的作用，最后还问了我知不知道LRU还是什么的cache
[设计一个‍‌‌‍‌‌‌‌‍‌‌‌‍‌‍‌‌‍‌在Twitch上给聊天室信息过滤的系统，应该说是OOD，最后口述刷题网旗耳和KMP算法]
bq
问了"tell me about a time with tight deadlines"
和"tell me about a ti‍‌‌‍‌‌‌‌‍‌‌‌‍‌‍‌‌‍‌me you took on extra responsibility
BQ1：Significant unexpected obstacle or challenge, how do you overcome it? What was the situation and what was the final outcome?
BQ2:  What was the biggest mistake that you have ever made, how did that happen, what was the outcome?

BQ：out of comfort zone
准备：https://www.1point3acres.com/bbs/thread-839216-1-1.html

tight deadline
BQ： Quick decision under insufficient information & Biggest Challenge
tight ddl, help peers, out of responsibility, conflicts.
Why Amazon
failure，无follow up


BQ:
1. tight ddl
2. new tools experience
3. team conflict
4. 推荐精心准备几个project经历来体现几个lp
quick decision和biggest challenge
Tell me an example of a calculated risk you have taken when speed is critical
Tell me an example when you do something beyond your responsibility
Make a decision‍‌‌‍‌‌‌‌‍‌‌‌‍‌‍‌‌‍‌ with insufficient information
bq第一个是challenge work experience，第二个是help peers，第三个是问如果第二个有办法改进的话会怎么改进
work without clear information,   

反向BQ
1. 你们‍‌‌‍‌‌‌‌‍‌‌‌‍‌‍‌‌‍‌现在是居家办公吗？
2. 我这学期正在学一门aws，aws在你们的工作中是什么样的角色
3. can you tell me a big day when you are working in 热带雨林，他似乎很喜欢这个问题

ood
https://leetcode.com/discuss/interview-question/1759648/amazon-oa-sde-intern-amazon-sign-in-pages-register-login-logout
OOD：实现设计实现一个线段类，线段里有多个2维顶点，实现每个点加上一个数或者减去一个数的操作
OOD2：设计一个Graph类，Graph包含多个点，还有一个cursor，找到距离cursor距离在50以内的最近的点
OOD，restaurant reservation 
1. 设计一个map data structure，要求给key, value, time stamp. 超过一定的times stamp 之后，里面的entry 才是有效的。
例子是：你subscribe了amazone prime membership, 在trial period里，你的记录不会显现。但是过了一个月之后，如果get(userid)，那么就要return你的信息。
这里需要和面试官讨论有哪些method, 有哪些parameter，return type是什么，所有的都需要自己讨论出来

3. 给两组数据，order = {time stamp, customer id, order id}, sale ad event = {time stamp, customer id, event id}, 给每一个order找之前最近的sale ad event or null if not found.
例如 order = {100, 1, 20}, sale event ad = {{90, 1, 33}, {110, 1, 45}}, 那么合适的sale ad event 是{90, 1, 33}
return type需要自己定义，需要同时return order and its matching sale event ad.
disagree ，take risk 还有insufficient info，最后一个是引申出来

设计自动贩卖机
设计一个电动汽车充电网络

BFS BST OOD  Amazon locker  LRU Cache
 ood 设计一个playlist class‍‌‌‍‌‌‌‌‍‌‌‌‍‌‍‌‌‍‌ 需要有add/remove songs 查找playlist长度 查找歌曲分类等功能 中间穿插问了一些time&space

system design

tweet system
设计一个温度播报系统。大概是说城市里有很多sensor，现在需要设计一套系统collect data然后在网页上展示。大概聊了三部分data collection, data storage, presentation。涉及pull/push， sql/nosql, scale, monitoring等等。比如怎么确定一个sensor是坏掉了才一直发送新data，还是因为该地区气‍‌‌‍‌‌‌‌‍‌‌‌‍‌‍‌‌‍‌温确实长时间没有变动。
设计一个amazon.job招聘系统，需要考虑高并发，HR得能发帖子，能转发，用户可以申请。

