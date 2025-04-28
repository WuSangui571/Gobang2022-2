package main.java.com.ztydwz.gobang2022.Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class ImageValue {
    public static BufferedImage whiteChess;
    public static BufferedImage blackChess;
    public static BufferedImage pointerImage;

    //初始化图片
    public static void init() {
        try {
            whiteChess = ImageIO.read(ImageValue.class.getResource("../image/WhiteChess.png"));
            blackChess = ImageIO.read(ImageValue.class.getResource("../image/BlackChess.png"));
            pointerImage = ImageIO.read(ImageValue.class.getResource("../image/pointer.gif"));
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

}


