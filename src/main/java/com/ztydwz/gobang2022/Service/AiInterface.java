package main.java.com.ztydwz.gobang2022.Service;

public interface AiInterface {
    public boolean ifChange();  //是否三手交换

    public int[] getLocation(); //获取AI下的位置

    public int[] fiveDa(int[][] map, int N); //我方进行五手N打

    public int[] drFiveDa(int[] arr); //我方进行五手N打

    public boolean isForbiddenHand(int i, int j);
}
