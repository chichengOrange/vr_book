package com.onway.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.onway.common.lang.ArrayUtils;
import com.onway.common.lang.DateUtils;
import com.onway.common.lang.StringUtils;
import com.onway.platform.common.helper.SystemHelper;

/**
 * ͼƬ�ϴ�������
 * 
 * @author wwf
 * @version $Id: ImageUploadUtil.java, v 0.1 2016��4��13�� ����3:58:08 Administrator Exp $
 */
public class ImageUploadUtil {

    private static final Logger   log          = Logger.getLogger(ImageUploadUtil.class);

    private static final String   OS_WINDOWS   = "Windows";

    private static final String   BACK_SYMBOL  = "\\";

    private static final String[] REPLACE_STRS = new String[] { "data:image/jpg;base64,",
            "data:image/jpeg;base64,", "data:image/png;base64,", "data:image/gif;base64,",
            "data:image/bmp;base64,", "data:image/tiff;base64,", "data:image/psd;base64,",
            "data:image/svg;base64,"          };

    private static final String   SPLIT_STRS   = ";base64,";

    private static final String   SYMBOL       = "/";

    /**
     * �ļ��ϴ��Ļ���·��
     */
    private static String         BASE_PATH    = "/usr/local/upload";

    //����һ�����飬���ڱ�����ϴ����ļ����� 
    private static List<String>   fileTypes    = null;
    static {
        /**
         * ���ݲ�ͬϵͳ����ʼ����ͬ��ͼƬ����·��
         */
        String os = System.getProperty("os.name");
        if (os != null && os.startsWith(OS_WINDOWS)) {
            BASE_PATH = "D:/upload/file/";
        }

        //����һ�����飬���ڱ�����ϴ����ļ����� 
        fileTypes = new ArrayList<String>();
        fileTypes.add("jpg");
        fileTypes.add("jpeg");
        fileTypes.add("bmp");
        fileTypes.add("gif");
        fileTypes.add("txt");
    }

    /**
     * �ж����������Ƿ��������
     * 
     * @param request
     * @param keyName
     * @return
     */
    public static boolean exitImage(HttpServletRequest request, String keyName) {
        //ת��ΪMultipartHttpRequest(�ص������)  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //  ��õ�1��ͼƬ������ǰ̨��name���Ƶõ��ϴ����ļ���   
        MultipartFile imgFile1 = multipartRequest.getFile(keyName);//"imgFile"

        //�����һ��ͼƬ  
        if (!(imgFile1.getOriginalFilename() == null || "".equals(imgFile1.getOriginalFilename()))) {
            return true;
        }
        return false;
    }

    /*<p>  
        </p><p>��ʵ����Ĵ��뻹�ǱȽϼ򵥵ģ��ص����ڽ����ǳ�����request����ת��Ϊ<span style="white-space: pre;">MultipartHttpRequest������������������ǾͿ��Եõ��û��ϴ����ļ��ˡ��õ��û��ϴ����ļ�֮��</span></p>  
        <p><span style="white-space: pre;">���ǾͿ�����һЩ���������������ˡ����������ǻ�����һЩ�£��Ǿ����ж��û��ϴ����ļ������Ƿ�����������������Ǹ�</span></p>  
        <p>�����ڵ����ͣ���������ж��Ƿ����������ϴ������ͣ��һ�������ķ������и�������ʵҲ���Խ�����Ĵ���д��һ�������������Ϊ���ã��Ҿͷֿ�д�ˡ�Ҳ���ҵ�����������õġ��൱�ڸ����һ������ɣ�</p>  
        <p> </p>  
        <p>�������������������������������������Ҫ�������¡�һ���ж��û��ϴ����ļ��Ƿ��������Ƕ�������ͷ�Χ֮�ڣ��ڶ������ļ����浽ָ����·�������·���������Լ������ġ�</p>  
        <p>  
        </p>
        <pre class="java" name="code">/** 
         * ͨ������ҳ���ȡ�����ļ��������󱣴浽���ش��̣�������һ���Ѿ������õ�File 
         * @param imgFile ��ҳ���ж�ȡ�����ļ� 
         * @param typeName  ��Ʒ�ķ������� 
         * @param brandName ��Ʒ��Ʒ������ 
         * @param fileTypes �������ļ���չ������ 
         * @return 
         */
    public static File getFile(MultipartFile imgFile, String typeName, String brandName) {
        String fileName = imgFile.getOriginalFilename();
        //��ȡ�ϴ��ļ����͵���չ��,�ȵõ�.��λ�ã��ٽ�ȡ��.����һ��λ�õ��ļ���������õ���չ��  
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        //����չ������Сдת��  
        //        ext = ext.toLowerCase();

        File file = null;
        if (fileTypes.contains(ext.toLowerCase())) { //�����չ�����������ϴ������ͣ��򴴽��ļ�  
            //            file = creatFolder(typeName, brandName, UUID.randomUUID().toString() + "." + ext);
            try {
                file = creatFolder(typeName, UUID.randomUUID().toString() + "." + ext);
                imgFile.transferTo(file); //�����ϴ����ļ�  
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * �ļ��ϴ�
     * @param imgFile
     * @param absolutePath
     * @return
     * @throws IOException
     */
    public static File getFile(MultipartFile imgFile, String absolutePath) throws IOException {

        if (-1 != absolutePath.indexOf(BACK_SYMBOL)) {
            //absolutePath = absolutePath.replace(BACK_SYMBOL, "/"); //ȥ��"/"File.separator
        }

        String fileName = imgFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

        File userAuthImage = new File(absolutePath + File.separator + UUID.randomUUID().toString()
                                      + "." + ext);
        String os = System.getProperty("os.name");
        if (!(os != null && os.startsWith(OS_WINDOWS))) {
            userAuthImage.setExecutable(true);//���ÿ�ִ��Ȩ��  
            userAuthImage.setReadable(true);//���ÿɶ�Ȩ��  
            userAuthImage.setWritable(true);//���ÿ�дȨ��
        }

        if (imgFile == null || ArrayUtils.isEmpty(imgFile.getBytes())) {
            return null;
        }
        FileUtils.writeByteArrayToFile(userAuthImage, imgFile.getBytes());
        return userAuthImage;
    }

    public static File createFolder(String fileUrl, String data) throws IOException {
        File userAuthImage = new File(fileUrl);
        FileUtils.writeByteArrayToFile(userAuthImage, data.getBytes());
        return userAuthImage;
    }

    /** 
     * ����봴��һ���������ļ��С��ļ��� 
                 ������ͨ������������ַ�������һ���ļ��кͶ����ļ������� 
                ͨ�����ְ취���ǿ������������û���ѡ�񱣴浽��Ӧ���ļ����� 
     */
    public static File creatFolder(String typeName, String brandName, String fileName) {
        File file = null;
        typeName = typeName.replaceAll("/", ""); //ȥ��"/"  
        typeName = typeName.replaceAll(" ", ""); //�滻��ǿո�  
        typeName = typeName.replaceAll(" ", ""); //�滻ȫ�ǿո�  

        brandName = brandName.replaceAll("/", ""); //ȥ��"/"  
        brandName = brandName.replaceAll(" ", ""); //�滻��ǿո�  
        brandName = brandName.replaceAll(" ", ""); //�滻ȫ�ǿո�  

        File firstFolder = new File(typeName); //һ���ļ���  
        if (firstFolder.exists()) { //���һ���ļ��д��ڣ���������ļ���  
            File secondFolder = new File(firstFolder, brandName);
            if (secondFolder.exists()) { //��������ļ���Ҳ���ڣ��򴴽��ļ�  
                file = new File(secondFolder, fileName);
            } else { //��������ļ��в����ڣ��򴴽������ļ���  
                secondFolder.mkdir();
                file = new File(secondFolder, fileName); //����������ļ��к��ٺϽ��ļ�  
            }
        } else { //���һ�������ڣ��򴴽�һ���ļ���  
            firstFolder.mkdir();
            File secondFolder = new File(firstFolder, brandName);
            if (secondFolder.exists()) { //��������ļ���Ҳ���ڣ��򴴽��ļ�  
                file = new File(secondFolder, fileName);
            } else { //��������ļ��в����ڣ��򴴽������ļ���  
                secondFolder.mkdir();
                file = new File(secondFolder, fileName);
            }
        }
        return file;
    }

    /**
     * �����ļ�
     * 
     * @param url
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File creatFolder(String url, String fileName) throws IOException {
        try {
            File file = creatFolder(url, "", fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            if (file.exists()) {
                file.delete();
            }

            if (!file.createNewFile()) {
                String os = System.getProperty("os.name");
                if (!(os != null && os.startsWith(OS_WINDOWS))) {
                    file.setExecutable(true);//���ÿ�ִ��Ȩ��  
                    file.setReadable(true);//���ÿɶ�Ȩ��  
                    file.setWritable(true);//���ÿ�дȨ��
                }
                return null;
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * �����ļ�
     * 
     * @param oldPath
     * @param newPath
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //�ļ�����ʱ 
                InputStream inStream = new FileInputStream(oldPath); //����ԭ�ļ� 
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //�ֽ��� �ļ���С 
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("���Ƶ����ļ���������");
            e.printStackTrace();
        }
    }

    /**
     * ��ȡ�ļ����ļ���׺
     * 
     * @param imgFile
     * @return
     */
    public static String getfileExt(MultipartFile imgFile) {
        String fileName = imgFile.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    /**
     * ��ȡ�ļ����ļ���׺
     * 
     * @param imgFile
     * @return
     */
    public static String getStrfileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    /**
     * �ļ��ϴ�
     * @param imgFile
     * @param absolutePath
     * @return
     * @throws IOException
     */
    public static File doUploadFile(MultipartFile imgFile, String absolutePath) throws IOException {

        File userAuthImage = new File(absolutePath);
        String os = System.getProperty("os.name");
        if (!(os != null && os.startsWith(OS_WINDOWS))) {
            userAuthImage.setExecutable(true);//���ÿ�ִ��Ȩ��  
            userAuthImage.setReadable(true);//���ÿɶ�Ȩ��  
            userAuthImage.setWritable(true);//���ÿ�дȨ��
        }

        if (imgFile == null) {//|| ArrayUtils.isEmpty(imgFile.getBytes())
            return null;
        }
        FileUtils.writeByteArrayToFile(userAuthImage, imgFile.getBytes());
        return userAuthImage;
    }

    /**
     * ��֯�ļ���ַ
     * 
     * @param uploadRealpath
     * @return
     */
    public static String filePath(String uploadRealpath) {

        String todayStr = DateUtils.getTodayString();
        String real_path = SystemHelper.getSystemConfig(uploadRealpath);
        real_path = real_path.replace(BACK_SYMBOL, File.separator);

        String os = System.getProperty("os.name");
        if (os != null && os.startsWith(OS_WINDOWS)) {
            real_path = real_path + SYMBOL + todayStr + SYMBOL;
        } else {
            real_path = real_path + File.separator + todayStr + File.separator;
        }
        return real_path;

    }

    // ���UUIDΪ���ֵ��ļ�
    public static String fileUUIDName(MultipartFile imgFile) {

        String fileName = imgFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

        return UUID.randomUUID().toString() + "." + ext;
    }

    /***
     * 
     * 
     * @param url
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File creatFile(String filePath) throws IOException {
        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            if (file.exists()) {
                file.delete();
            }

            if (!file.createNewFile()) {
                String os = System.getProperty("os.name");
                if (!(os != null && os.startsWith(OS_WINDOWS))) {
                    file.setExecutable(true);//���ÿ�ִ��Ȩ��  
                    file.setReadable(true);//���ÿɶ�Ȩ��  
                    file.setWritable(true);//���ÿ�дȨ��
                }
                return null;
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * �ļ��ϴ�
     * @param imgFile
     * @param absolutePath
     * @return
     * @throws IOException
     */
    public static File doUploadBase64File(String imgFile, String absolutePath) throws IOException {

        if (StringUtils.contains(imgFile, SPLIT_STRS)) {
            String imgStr[] = imgFile.split(SPLIT_STRS);
            if (imgStr.length != 2) {
                return null;
            }
            imgFile = imgStr[1];
        }

        File userAuthImage = new File(absolutePath);
        String os = System.getProperty("os.name");
        if (!(os != null && os.startsWith(OS_WINDOWS))) {
            userAuthImage.setExecutable(true);//���ÿ�ִ��Ȩ��  
            userAuthImage.setReadable(true);//���ÿɶ�Ȩ��  
            userAuthImage.setWritable(true);//���ÿ�дȨ��
        }

        if (imgFile == null || ArrayUtils.isEmpty(imgFile.getBytes())) {
            return null;
        }

        BASE64Decoder decoder = new BASE64Decoder();
        FileUtils.writeByteArrayToFile(userAuthImage, decoder.decodeBuffer(imgFile));

        return userAuthImage;

    }

    /**
     * ��Base64λ�����ͼƬ���н��룬�����浽ָ��Ŀ¼
     * 
     * @param base64
     *          base64�����ͼƬ��Ϣ
     * @return
     */
    public static File decodeBase64ToImage(String base64, String absolutePath) {
        File file = new File(absolutePath);
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            FileOutputStream write = new FileOutputStream(file);
            byte[] decoderBytes = decoder.decodeBuffer(base64);
            write.write(decoderBytes);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    public static void main(String[] args) {
        // ���Դ�Base64����ת��ΪͼƬ�ļ�  //data:image/jpg;base64,

        String strImg = "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ceaLj3NsPV9jWhVtflaf3O58xaHr2v8AhwznSZZLfz9vmfuFfdtzj7ynHU1sf8LG8cf9BGT/AMA4v/iK+haK41hJxVlN/wBfM+gqZ/h6kuephotvq7N/+knzbpXhnxD4x1gyvDOfOfdPeTqQq++T1OOgH8q6X4ieGdR0TxBb65p0DvaRJDtkVd3lPGAo3D0wq89K9toqlg48rV9e5nLiKs68aiglFJrl8nbr8ux4YPjN4jA/489LP/bKT/4uuT8TeJr3xVqSX99FbxypEIQIFIXAJPcnn5jX0/RSlhak1aU/w/4JVDPMLQnz0sMk/wDF/wAA8P8AiP8A8if4K/68v/acVeh/DP8A5J5pX0l/9GvXW0VtChy1HO/SxwYnM/bYSOF5bWk3e/e+lreZ82eN/CV34W1ubET/ANnyuWtpwvy4PO3PZh0x7ZrpdH+M+o2VkkGo6dHfyIoUTCYxM3u3BBP5V7XNDFcRNFNGkkbDDI6ggj3BqlBoOjWpzb6TYRH1jtkX+Qr6R5tSrUlDFUuZrrex4nsmneLPH/EPjvV/GGn2kem+HLuGS2u0uo54Waf5lDYGAg9c/hWq/wAZb+xRI9R8MNHPj5sztEG9wrISB+Jr1sAAAAYAqKe3guojFcQxzRnqkihgfwNYfXsK0oSoe6v7zvr5j5Jb3Pnfxz45/wCEz+wf8S77H9k8z/lv5m/ft/2RjG39a6z4O+GbqO7m1+6haOAxGK23jG/JGWHsAMZ75PpXpsfhnQIpBJHoemo453LaRg/nitQAAAAYArXEZrB4b6th4csfW/mKNJ83NJnnnxl/5E22/wCv5P8A0B65Hw38Vv8AhH/D9ppX9i/aPs6keb9q2bssT02HHX1r3GivnJ0ZOfPGVvke9h8yoQwqw1ejzpO/xNfkjyP/AIXf/wBS9/5O/wD2uuL8S+JJPGfiezvI7BoHCJAsKv5hbDE56D+90xX0hRUTw9SatKenob4fNsJhp+0o4a0v8bf5o8i+N/39D+k//tOm+IviTplwt3ol/wCGxeJBI0IL3GMlSV3DC5U8djmvX6KqVCTlKUZWv5GNHNKMaNOlVpc3Jez5mt3foj5wsPDvi6XQ724sLa/h048vArsvmj2T+PHfitfwv480rwpa+UnhgC+A2zXHn/O579VJX6DiveKKiOEcGnGWvpc6qufxrxlCvRvFvpJx++25BZ3H2uxt7nbs86JZNuc4yM4zU9FFdh867N6BRRRQIKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAP/2Q==";
        GenerateImage(strImg, "d:\\test\\1.jpg");// C:\\Users\\Administrator\\Desktop\\test\\1.jpg"

        // ���Դ�ͼƬ�ļ�ת��ΪBase64����  
        System.out.println(getImageToBASE64Str("D:\\test\\yx\\1.jpg"));

    }

    @SuppressWarnings("restriction")
    public static String getBytesToBASE64Str(byte[] data) {// ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��  
        // ���ֽ�����Base64����  
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// ����Base64��������ֽ������ַ���  
    }

    public static String getImageToBASE64Str(String imgFilePath) {// ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��  
        byte[] data = null;

        // ��ȡͼƬ�ֽ�����  
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ���ֽ�����Base64����  
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// ����Base64��������ֽ������ַ���  
    }

    public static boolean GenerateImage(String imgStr, String imgFilePath) {// ���ֽ������ַ�������Base64���벢����ͼƬ  
        if (imgStr == null) // ͼ������Ϊ��  
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64����  
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// �����쳣����  
                    bytes[i] += 256;
                }
            }
            // ����jpegͼƬ  
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}