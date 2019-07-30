package QRCode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtil {
    private static final String CHARSET = "utf-8";
    private static final int WIDTH = 400; // 二维码宽
    private static final int HEIGHT = 400; // 二维码高
    private static final int QRCOLOR = 0xFF000000; // 默认是黑色
    private static final int BGWHITE = 0xFFFFFFFF; // 背景颜色
    private static final String FORMAT = "png"; // 背景颜色
    private static Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>() {
        {
            put(EncodeHintType.CHARACTER_SET, CHARSET);// 设置编码方式
            //如果不加以下两行，若生成带logo的二维码，会无法识别
            put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
            put(EncodeHintType.MARGIN, 0);
        }
    };

    public static File encodeQRCode(String content, File qrFile) {
        return encodeQRCode(content, null, null, "", qrFile);
    }


    /**
     * 根据内容生成二维码
     *
     * @param content
     * @param width
     * @param height
     * @param format
     * @param qrFile
     * @return
     */
    public static File encodeQRCode(String content, Integer width, Integer height, String format, File qrFile) {

        if (width == null) {
            width = WIDTH;
        }
        if (height == null) {
            height = HEIGHT;
        }
        if ("".equals(format)) {
            format = FORMAT;
        }
        // 生成二维码矩阵
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            MatrixToImageWriter.writeToFile(bitMatrix, format, qrFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qrFile;
    }

    /**
     * 二维码图片组合logo图片
     *
     * @param qrFile    二维码图片文件
     * @param logoFile  logo图片
     * @param newQrFile 组合后带logo的二维码图片
     * @return
     */
    public static File encodeWithLogo(File qrFile, File logoFile, File newQrFile) {
        try (OutputStream os = new FileOutputStream(newQrFile)) {
            Image image2 = ImageIO.read(qrFile);
            int width = image2.getWidth(null);
            int height = image2.getHeight(null);
            BufferedImage bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //BufferedImage bufferImage =ImageIO.read(image) ;
            Graphics2D g2 = bufferImage.createGraphics();
            g2.drawImage(image2, 0, 0, width, height, null);
            int matrixWidth = bufferImage.getWidth();
            int matrixHeigh = bufferImage.getHeight();

            int logoWidth_i = matrixWidth / 4;
            int logoHeigh_i = matrixHeigh / 4;
            int x_i = (matrixWidth - logoWidth_i) / 2;
            int y_i = (matrixHeigh - logoHeigh_i) / 2;
            //读取Logo图片
            BufferedImage logo = ImageIO.read(logoFile);
            //开始绘制图片
            g2.drawImage(logo, x_i, y_i, logoWidth_i, logoHeigh_i, null);//绘制
            //设置图片外白框，width参数设置边框大小
            BasicStroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            g2.setStroke(stroke);
            //指定弧度的圆角矩形
            RoundRectangle2D.Float round = new RoundRectangle2D.Float(x_i, y_i, logoWidth_i, logoHeigh_i, 20, 20);
            g2.setColor(Color.white);
            g2.draw(round);// 绘制圆弧矩形
            g2.setColor(new Color(128, 128, 128));

            // 绘制圆弧矩形 头像框外增加一个灰色圆角矩形框
//            RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(x_i + 2, y_i + 2, logoWidth_i - 4, logoHeigh_i - 4, 20, 20);
//            g2.draw(round2);
            g2.dispose();
            bufferImage.flush();
            JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
            en.encode(bufferImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newQrFile;
    }

    // 生成带logo的二维码图片
    public static void drawLogoQRCode(File logoFile, File codeFile, String qrUrl) {
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
            BitMatrix bm = multiFormatWriter.encode(qrUrl, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
            BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    image.setRGB(x, y, bm.get(x, y) ? QRCOLOR : BGWHITE);
                }
            }
            int width = image.getWidth();
            int height = image.getHeight();
            if (logoFile != null && logoFile.exists()) {
                // 构建绘图对象
                Graphics2D g = image.createGraphics();
                // 读取Logo图片
                BufferedImage logo = ImageIO.read(logoFile);
                // 开始绘制logo图片
                g.drawImage(logo, width * 2 / 5, height * 2 / 5, width * 2 / 10, height * 2 / 10, null);
                g.dispose();
                logo.flush();
            }
            image.flush();
            ImageIO.write(image, "png", codeFile); // TODO
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 二维码嵌套背景图的方法
     *@param bigPath 背景图 - 可传网络地址
     *@param smallPath 二维码地址 - 可传网络地址
     *@param newFilePath 生成新图片的地址
     *@param  x 二维码x坐标
     *@param  y 二维码y坐标
     * */
    public static void mergeImage(String bigPath, String smallPath, String newFilePath, String x, String y) throws IOException {

        try {
            BufferedImage small;
            BufferedImage big;
            if (bigPath.contains("http://") || bigPath.contains("https://")) {
                URL url = new URL(bigPath);
                big = ImageIO.read(url);
            } else {
                big = ImageIO.read(new File(bigPath));
            }

            if (smallPath.contains("http://") || smallPath.contains("https://")) {
                URL url = new URL(smallPath);
                small = ImageIO.read(url);
            } else {
                small = ImageIO.read(new File(smallPath));
            }
            Graphics2D g = big.createGraphics();
            float fx = Float.parseFloat(x);
            float fy = Float.parseFloat(y);
            int x_i = (int) fx;
            int y_i = (int) fy;
            g.drawImage(small, x_i, y_i, small.getWidth(), small.getHeight(), null);
            g.dispose();
            ImageIO.write(big, "png", new File(newFilePath));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 解析二维码
     *
     * @param path
     * @return
     */
    public static String decodeQRCode(String path) {
        try {

            // 读取图片
            BufferedImage image = ImageIO.read(new File(path));

            // 多步解析
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

            // 一步到位
            // BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)))

            // 设置字符集编码
            Map<DecodeHintType, String> hints = new HashMap<>();
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

            // 对图像进行解码
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);
            // 解码内容

            return result.getText();
        } catch (Exception e) {
        }

        return null;
    }
}
