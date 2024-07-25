package com.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

public class CodeUtil {
    private static int width = 90;// width
    private static int height = 20;// height
    private static int codeCount = 4;// 定义图片上显示验证码的个数
    private static int xx = 15;
    private static int fontHeight = 18;
    private static  int codeY = 16;
    private static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };


    public static Map<String,Object> generateCodeAndPic() {
        // image buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // Graphics2D gd = buffImg.createGraphics();
        // Graphics2D gd = (Graphics2D) buffImg.getGraphics();
        Graphics gd = buffImg.getGraphics();
        // random
        Random random = new Random();
        // fill img with white
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        // create font, font size will follow img height
        Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
        // set font
        gd.setFont(font);

        // draw rectangle
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);

        // not detected by other program
        gd.setColor(Color.BLACK);
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }

        // save randomcode
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;

        // generate code
        for (int i = 0; i < codeCount; i++) {
            // random number
            String code = String.valueOf(codeSequence[random.nextInt(36)]);
            // random color
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            // draw code
            gd.setColor(new Color(red, green, blue));
            gd.drawString(code, (i + 1) * xx, codeY);

            // add to randomcode
            randomCode.append(code);
        }
        Map<String,Object> map  =new HashMap<String,Object>();
        //store code
        map.put("code", randomCode);
        //store bufferimage
        map.put("codePic", buffImg);
        return map;
    }

    public static void main(String[] args) throws Exception {
        //create file output stream
        OutputStream out = new FileOutputStream("/Users/Desktop/workspace4idea/miaoshaStable/"+System.currentTimeMillis()+".jpg");
        Map<String,Object> map = CodeUtil.generateCodeAndPic();
        ImageIO.write((RenderedImage) map.get("codePic"), "jpeg", out);
        System.out.println("Captcha code is"+map.get("code"));
    }
}