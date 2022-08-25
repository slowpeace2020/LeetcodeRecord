package august;

public class TextEditor {
    int cursor=0;
    StringBuilder sb;
    public TextEditor() {
        sb = new StringBuilder();
    }

    public void addText(String text) {
        sb.insert(cursor,text);
        cursor+=text.length();
    }

    public int deleteText(int k) {
       int tmp = cursor;
       cursor-=k;
       if(cursor<0)cursor = 0;
       sb.delete(cursor,tmp);
       return tmp-cursor;
    }

    public String cursorLeft(int k) {
        cursor -=k;
        if(cursor<0)cursor=0;
        if(cursor<10){
            return sb.substring(0,cursor);
        }
        return sb.substring(cursor-10,cursor);
    }

    public String cursorRight(int k) {
        cursor+=k;
        if(cursor>sb.length()){
            cursor = sb.length();
        }
        if(cursor<10){
            return sb.substring(0,cursor);
        }

        return sb.substring(cursor-10,cursor);
    }

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        editor.addText("leetcode");
        editor.deleteText(4);
        editor.addText("practice");
        editor.cursorRight(3);
        editor.cursorLeft(8);
        editor.deleteText(10);
        editor.cursorLeft(2);
        editor.cursorRight(6);
    }
}
