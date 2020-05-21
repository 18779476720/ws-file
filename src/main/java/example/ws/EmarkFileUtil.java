package example.ws;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Base64.Encoder;

public class EmarkFileUtil {
    /**
     * @description 计算文件contentMd5值
     *
     * @param filePath {@link String} 文件路径
     * @return
     * @author 宫清
     * @date 2019年7月14日 下午1:35:41
     */
    public static String getContentMD5(String filePath) throws  Exception{
        Encoder encoder = Base64.getEncoder();
        // 获取文件Md5二进制数组（128位）
        byte[] bytes = getFileMd5Bytes128(filePath);
        // 对文件Md5的二进制数组进行base64编码（而不是对32位的16进制字符串进行编码）
        return encoder.encodeToString(bytes);
    }
    /**
     * @description 获取文件Md5二进制数组（128位）
     *
     */
    private static byte[] getFileMd5Bytes128(String filePath)throws Exception{
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(filePath));
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = fis.read(buffer, 0, 1024)) != -1) {
                md5.update(buffer, 0, len);
            }
            return md5.digest();
        } catch (Exception e) {
            throw new Exception("获取文件md5二进制数组失败", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new Exception("关闭读写流失败", e);
                }
            }
        }
    }
    /**
     * @description 获取文件字节流
     * @param filePath {@link String} 文件地址
     * @return
     * @date 2019年7月10日 上午9:17:00
     * @author 宫清
     */
    public static byte[] getBytes(String filePath) throws Exception {
        File file = new File(filePath);
        FileInputStream fis = null;
        byte[] buffer = null;
        try {
            fis = new FileInputStream(file);
            buffer = new byte[(int) file.length()];
            fis.read(buffer);
        } catch (Exception e) {
            throw new Exception("获取文件字节流失败", e.getCause());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new Exception("关闭文件字节流失败", e.getCause());
                }
            }
        }
        return buffer;
    }
}
