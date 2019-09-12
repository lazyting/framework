package utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import exception.ToolException;
import security.Coder;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class FileUtil {

    /**
     * 下载图片
     *
     * @param imageUrl
     * @param response
     */
    public static void downloadPic(String imageUrl, HttpServletResponse response) {
        URL url = null;
        OutputStream os = null;
        InputStream is = null;
        String fileName = "fileName.png";
        try {
            try {
                url = new URL(imageUrl);
                // 打开连接
                URLConnection con = url.openConnection();
                // 设置请求超时为5s
                // con.setConnectTimeout(5*1000);
                // 输入流
                is = con.getInputStream();
                // 1K的数据缓冲
                byte[] bs = new byte[1024];
                // 读取到的数据长度
                int len;
                response.setHeader("Content-Type", "multipart/form-data");
                // response.setHeader("Last-Modified",fileModel.getModifydate().toString());
                if (EmptyUtil.isNotEmpty(fileName)) {
                    response.setHeader("FileName", URLEncoder.encode(fileName, "UTF-8"));
                }
                os = response.getOutputStream();
                // 开始读取
                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
            } finally {
                // 完毕，关闭所有链接
                if (EmptyUtil.isNotEmpty(os)) {
                    os.close();
                }
                if (EmptyUtil.isNotEmpty(is)) {
                    is.close();
                }
            }
        } catch (IOException e) {
//			logger.error(e.toString());
        }
    }

    /**
     * 使用地址生成base64字符串
     *
     * @param imgUrl
     * @return
     */
    public static String image2Base64(String imgUrl) {
        URL url = null;
        InputStream is = null;
        ByteArrayOutputStream outStream = null;
        HttpURLConnection httpUrl = null;
        try {
            url = new URL(imgUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            httpUrl.getInputStream();
            is = httpUrl.getInputStream();
            outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = is.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            // 对字节数组Base64编码
            return Coder.encodeBase64(outStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpUrl != null) {
                httpUrl.disconnect();
            }
        }
        return imgUrl;
    }

    /**
     * 判断父文件夹是否存在
     *
     * @param file
     * @return
     */
    public static boolean createFileByFilePath(File file) {
        boolean result = false;
        if (!file.getParentFile().exists()) {
            result = file.getParentFile().mkdir();
        } else {
            result = true;
        }
        return result;
    }

    /**
     * 生成图片文件
     *
     * @param bufferedImage 图片 buffered
     * @param imgPath       图片路径
     */
    public static void writeImage(BufferedImage bufferedImage, String imgPath) {
        // 生成二维码的图片
        File file = new File(imgPath);
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeImageByStream(BufferedImage bufferedImage, String filePath) throws IOException {
        OutputStream os = new FileOutputStream(new File(filePath));
        JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
        en.encode(bufferedImage);
    }

    //base64字符串转化成图片
    public static boolean GenerateImage(String imgStr, String newFilePath) {
        //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(newFilePath);//新生成的图片
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        System.out.println(sPath);
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (!file.exists()) {
            file = null;
            throw new ToolException("[" + sPath + "] not exist");
        }

        if (file.isDirectory()) {
            throw new ToolException("[" + sPath + "] is directory, can out delete");
        }
        return file.delete();
    }
}
