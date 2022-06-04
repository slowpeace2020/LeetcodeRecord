package march;

import java.util.Arrays;

public class Trie {
    class TrieNode{
        boolean isWord;
        TrieNode[] child;
        public TrieNode(){
            child = new TrieNode[26];
        }
    }

    TrieNode root;
    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode cur = root;
        for(char c:word.toCharArray()){
            if(cur.child[c-'a']==null){
                cur.child[c-'a'] = new TrieNode();
            }
            cur = cur.child[c-'a'];
        }
        cur.isWord = true;
    }

    public boolean search(String word) {
        TrieNode cur = root;
        for(char c:word.toCharArray()){
            TrieNode next = cur.child[c-'a'];
            if(next==null){
                return false;
            }
            cur = next;
        }
        return cur.isWord;
    }

    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        for(char c:prefix.toCharArray()){
            TrieNode next = cur.child[c-'a'];
            if(next==null){
                return false;
            }
            cur = next;
        }
        return true;
    }

    int n=-1;
    public void wiggleSort(int[] nums) {
        //找到中位数索引
        int midIndex = this.quickSelect(nums,0,nums.length-1);
        //找到中位数
        int mid = nums[midIndex];
        n=nums.length;
        //三分法
        for(int i=0,j=0,k=nums.length-1;j<=k;){
            if(nums[V(j)]>mid){
                swap(nums,V(j++),V(i++));
            }else if(nums[V(j)]<mid){
                swap(nums,V(j),V(k--));
            }else{
                j++;
            }
        }
    }

    public int V(int i){
        return (1+2*(i)) % (n|1);
    }

    public void swap(int[] nums,int i,int j){
        int t = nums[i];
        nums[i]=nums[j];
        nums[j]=t;
    }

    public int quickSelect(int[] nums,int left,int right){
        int pivot = nums[left];
        int l = left;
        int r = right;
        while(l<r){
            while(l<r&&nums[r]>=pivot){
                r--;
            }
            if(l<r){
                nums[l++]=nums[r];
            }
            while(l<r&&nums[l]<=pivot){
                l++;
            }
            if(l<r){
                nums[r--]=nums[l];
            }
        }
        nums[l]=pivot;
        if(l==nums.length/2){
            return l;
        }else if(l>nums.length/2){
            return this.quickSelect(nums,left,l-1);
        }else{
            return this.quickSelect(nums,l+1,right);
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        trie.search("apple");
        trie.search("app");
        trie.startsWith("app");
        trie.insert("app");
        trie.startsWith("app");
    }
}
