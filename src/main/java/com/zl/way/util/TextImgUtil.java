package com.zl.way.util;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class TextImgUtil {
    /**
     * @throws IOException @throws
     **/
    public static void main(String[] args) throws IOException {
        String name = "鸡蛋";
        generateImg(name, "/Users/xuzhongliang/Downloads/shopLogo", name);
    }

    /**
     * 绘制字体头像 如果是英文名，只显示首字母大写 如果是中文名，只显示最后两个字
     * 
     * @param name
     * @param outputPath
     * @param outputName
     * @throws IOException
     */
    public static void generateImg(String name, String outputPath, String outputName) throws IOException {
        int width = 100;
        int height = 100;
        int nameLen = name.length();
        String nameWritten = name;
        if (nameLen >= 4) {
            nameWritten = name.substring(0, 4);
        }

        String filename = outputPath + File.separator + outputName + ".jpg";
        File file = new File(filename);

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = (Graphics2D)bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.setBackground(getRandomColor());

        g2.clearRect(0, 0, width, height);

        g2.setPaint(Color.WHITE);

        Font font = null;
        font = new Font("微软雅黑", Font.PLAIN, 30);
        g2.setFont(font);

        if (nameLen == 2) {
            g2.drawString(nameWritten, 20, 60);
        } else if (nameLen == 3) {
            g2.setFont(font);
            String firstSecondWritten = nameWritten.substring(0, 2);
            g2.drawString(firstSecondWritten, 20, 50);
            String thirdWritten = nameWritten.substring(2, 3);
            g2.drawString(thirdWritten, 35, 80);
        } else if (nameLen == 4) {
            g2.setFont(font);
            String firstSecondWritten = nameWritten.substring(0, 2);
            g2.drawString(firstSecondWritten, 20, 45);
            String thirdFourWritten = nameWritten.substring(2, 4);
            g2.drawString(thirdFourWritten, 20, 75);
        } else {
            font = new Font("微软雅黑", Font.PLAIN, 50);
            g2.setFont(font);
            g2.drawString(nameWritten, 25, 70);
        }

        // BufferedImage rounded = makeRoundedCorner(bi, 99);
        ImageIO.write(bi, "png", file);
    }

    /**
     * 获得随机颜色
     * 
     * @return
     */
    private static Color getRandomColor() {
        String[] beautifulColors = new String[] {"51,102,204", "204,204,102", "51,51,0"};
        int len = beautifulColors.length;
        Random random = new Random();
        String[] color = beautifulColors[random.nextInt(len)].split(",");
        return new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2]));
    }

    /**
     * 图片做圆角处理
     * 
     * @param image
     * @param cornerRadius
     * @return
     */
    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return output;
    }
}
