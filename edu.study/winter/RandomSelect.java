package winter;

import java.util.Random;

public class RandomSelect {
    int[][] rects;
    public RandomSelect(int[][] rects){
        this.rects = rects;
    }


    public int[] pick(){
        //等效从一堆点中抽一个点，若在某个矩形中包含被抽到的点，则等效抽到这个矩形
        //分别记录抽到的矩形下标、当前点的总数
        int index =-1;
        int n=0;
        Random random =new Random();
        for(int i=0;i<rects.length;i++){
            //当前矩形包含的点数量
            int k = (rects[i][2]-rects[i][0]+1) *(rects[i][3]-rects[i][1]+1);
            n+=k;
            if(random.nextInt(n)<k){//当前矩形有k/n的概率被保留
                index=i;
            }
        }

        int[] rec = rects[index];//抽到矩形后，再从这个矩形中随机抽取x、y的值
        int x1 = rec[0];
        int x2 =rec[2];
        int y1=rec[3];
        int y2=rec[1];
        return new int[]{x1+random.nextInt(x2+1-x1),y1+random.nextInt(y2+1-y1)};
    }
}
