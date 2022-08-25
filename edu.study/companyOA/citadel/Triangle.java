package companyOA.citadel;

public class Triangle {
    public boolean isTrangle(int a,int b,int c){
        if(Math.abs(a-b)<c&&c<a+b){
            return true;
        }

        return false;
    }

    //判断一个点是不是在三角形内部
    //https://leetcode.cn/circle/discuss/7OldE4/
    private double product(int[] p1,int[] p2,int[] p3){
        //首先根据坐标计算p1p2和p1p3的向量，然后再计算叉乘
        //p1p2向量(p2.x-p1.x,p2.y-p1.y)
        //p1p3向量(p3.x-p1.x,p3.y-p1.y)
        return (p2[0]-p1[0])*(p3[1]-p1[1])-(p2[1]-p1[1])*(p3[0]-p1[0]);
    }

    public boolean isInTrangle(int[] p1,int[] p2,int[] p3, int[] p){
        //保证p1，p2，p3是逆时针顺序
        if(product(p1,p2,p3)<0){
            return isInTrangle(p1,p3,p2,p);
        }

        if(product(p1,p2,p)>0&&product(p2,p3,p)>0&&product(p1,p3,p)>0){
            return true;
        }

        return false;
    }

}
