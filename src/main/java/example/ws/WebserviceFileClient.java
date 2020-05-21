package example.ws;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;

/**
 * @author xiaosong.xiao
 * @version 1.0
 * @date 创建时间：2020年5月12日 下午1:52:52
 * @parameter
 * @return
 */
public class WebserviceFileClient {

    public static String getFileByteString(File file) throws Exception {
        InputStream in = new FileInputStream(file);
        // 取得文件大小
        long length = file.length();
        System.out.println("文件大小：" + length);
        // 根据大小创建字节数组
        byte[] bytes = new byte[(int) length];
        // 读取文件内容到字节数组
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = in.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        in.close();
        String encodedFileString = Base64.getEncoder().encodeToString(bytes);
        return encodedFileString;
    }
}
