package unlock;

public class WordDictionary {
    class TrieNode{//构造前缀树节点
        public char val;  //节点字符值
        public boolean isWord; //是否为一个单词的结尾
        public TrieNode[] children = new TrieNode[26]; //下一个字符的值列表
        public TrieNode(){

        }

        public TrieNode(char val){
            this.val = val;
        }
    }

    TrieNode root;
    public WordDictionary() {
        root = new TrieNode(' ');//前缀树公共节点，这样可以存入任意字母开头的字符串
    }

    public void addWord(String word) {
        TrieNode cur = root;//添加新节点，构造单词
        for(int i=0;i<word.length();i++){
            char c=word.charAt(i);
            if(cur.children[c-'a']==null){
                cur.children[c-'a'] = new TrieNode(c);
            }
            cur = cur.children[c-'a'];
        }

        cur.isWord = true;//单词结尾的地方标记
    }

    public boolean search(String word) {

        return search(word,root);//从公共根节点开始搜索
    }

    public boolean search(String word, TrieNode node) {
        TrieNode cur = node;
        for(int i=0;i<word.length();i++){
            char c=word.charAt(i);
            if(c=='.'){//字符为通配符
                TrieNode[] children = cur.children;//任意child节点都有可能
                for(TrieNode next : children){
                    if(next==null){//过滤空节点
                        continue;
                    }
                    String str = word.substring(i+1);//通配符匹配子节点字符，之后的节点需要继续检查
                    if(str.equals("")&&next.isWord){//通配符是最后一个字符，则检查子节点是否为一个单词的结尾
                        return true;
                    }else if(search(str,next)){//否则，递归检查，从子节点开始检查之后的字符串
                        return true;
                    }
                }

                return false;
            }else {
                if(cur.children[c-'a']==null){//当前节点不为通配符，直接找到对应的child节点检查
                    return false;
                }
                cur = cur.children[c-'a'];
            }
        }

        return cur.isWord;
    }

    public static void main(String[] args) {
        WordDictionary test = new WordDictionary();
        test.addWord("bad");
        test.addWord("dad");
        test.addWord("mad");
//        test.search("pad");
//        test.search("bad");
//        test.search(".ad");
        test.search("b..");

    }
}
