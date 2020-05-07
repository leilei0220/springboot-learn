package com.leilei.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.leilei.config.QrCodeConfig;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;

/**
 * @author leilei
 * @version 1.0
 * @date 2020/3/12 12:28
 * @desc:
 */
public class QrCodeUtil {



    /**
     * 生成二维码图片
     *
     * @param url      二维码要跳转的url地址
     * @param imgPath      中间log地址
     * @param needCompress 是否压缩
     * @return
     * @throws Exception
     */
    private static BufferedImage createQrCodeImage(String url, String imgPath, boolean needCompress) throws Exception {
        Hashtable hints = new Hashtable();

        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, QrCodeConfig.getCharset());
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE,QrCodeConfig.getQrWidth(),QrCodeConfig.getQrHeight(),hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        QrCodeUtil.inserLogotImage(image, imgPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     *
     * @param imgPath      二维码图片
     * @param imgPath      LOGO图片地址
     * @param needCompress 是否压缩
     * @throws Exception
     */
    private static void inserLogotImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println("" + imgPath + "   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        // 压缩logo
        if (needCompress) {
            if (width > QrCodeConfig.getLogoWidth()) {
                width = QrCodeConfig.getLogoWidth();
            }
            if (height > QrCodeConfig.getLogoHeight()) {
                height = QrCodeConfig.getLogoHeight();
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QrCodeConfig.getQrWidth() - width) / 2;
        int y = (QrCodeConfig.getQrHeight() - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 创建文件夹， mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
     *
     * @param destPath
     */
    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 生成带logo二维码
     *
     * @param   url    二维码要跳转的url
     * @param imgPath      中间log地址
     * @param output
     * @param needCompress 是否压缩
     * @throws Exception
     */
    public static void encode(String url, String imgPath, OutputStream output, boolean needCompress)
            throws Exception {

        BufferedImage image = QrCodeUtil.createQrCodeImage(url, imgPath, needCompress);
        ImageIO.write(image, QrCodeConfig.getQrPicType(), output);
    }

    /**
     * 生成不带log 二维码
     *
     * @param url 二维码要跳转的url
     * @param output
     * @throws Exception
     */
    public static void encode(String url, OutputStream output) throws Exception {
        QrCodeUtil.encode(url, null, output, false);
    }

    /**
     * 生成base64编码 图片传null 则二维码不包含图片 否则反之
     * @param url
     * @param imgPath
     * @param stream
     * @param needCompress
     * @return
     * @throws Exception
     */
    public static String encodeBase64(String url, String imgPath, ServletOutputStream stream, boolean needCompress)
        throws Exception {
        String resultImage = null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            BufferedImage image = QrCodeUtil.createQrCodeImage(url, imgPath, needCompress);
            ImageIO.write(image, QrCodeConfig.getQrPicType(), os);
            resultImage = new String("data:image/"+QrCodeConfig.getQrPicType()+";base64," + Base64.encode(os.toByteArray()));
            return resultImage;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
        return null;
    }

}
