package unlock;

// you can also use imports, for example:
// import java.util.*;

// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Okta {
  public boolean solution(String[] B) {
    // write your code in Java SE 8
    int m = B.length;
    int n = B[0].length();
    char[][] grid = new char[m][n];
    for(int i=0;i<m;i++){
      grid[i] = B[i].toCharArray();
    }
    sign(grid);
    Queue<int[]> queue = new LinkedList<>();
    boolean[][] visited = new boolean[m][n];
    boolean flag  = false;
    for(int i=0;i<m;i++){
      for(int j=0;j<n;j++){
        if(grid[i][j]=='A'){
          queue.add(new int[]{i,j});
          visited[i][j] = true;
          flag = true;
          break;
        }
      }
      if(flag){
        break;
      }
    }

    int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
    while(!queue.isEmpty()){
      int[] cor = queue.poll();
      if(cor[0]==m-1&&cor[1]==n-1){
        return true;
      }
      for(int[] dir:dirs){
        int x = cor[0]+dir[0];
        int y = cor[1]+dir[1];
        if(x<0||x>=m||y<0||y>=n||grid[x][y]=='X'||grid[x][y]=='Y'||visited[x][y]){
          continue;
        }
        visited[x][y] = true;
        queue.add(new int[]{x,y});
      }


    }


    return false;

  }

  /**
   * mark coordinates which cannot be passed
   * @param grid
   */
  public void sign(char[][] grid){
    int m = grid.length;
    int n = grid[0].length;
    List<Character> stops = new ArrayList<>();
    stops.add('X');
    stops.add('<');
    stops.add('>');
    stops.add('^');
    stops.add('v');

    for(int i=0;i<m;i++){
      for(int j=0;j<n;j++){
        if(grid[i][j]=='>'){
          int start = j;
          grid[i][start++]= 'Y';
          while(start<n&&!stops.contains(grid[i][start])){
            grid[i][start++]= 'Y';
          }
        }else if(grid[i][j]=='<'){
          int start = j;
          grid[i][start--]= 'Y';
          while(start>=0&&!stops.contains(grid[i][start])){
            grid[i][start--]= 'Y';
          }
        }else if(grid[i][j]=='^'){
          int start = i;
          grid[start--][j]= 'Y';
          while(start>=0&&!stops.contains(grid[start][j])){
            grid[start--][j]= 'Y';
          }
        }else if(grid[i][j]=='v'){
          int start = i;
          grid[start++][j]= 'Y';
          while(start<m&&!stops.contains(grid[start][j])){
            grid[start++][j]= 'Y';
          }
        }
      }
    }

  }

  public static void main(String[] args) {
//   String[] B =  {"X.....>", "..v..X.", ".>..X..", "A......"};
   String[] B =  {"...Xv", "AX..^", ".XX.."};
   Okta solution = new Okta();
   solution.solution(B);
  }
}
