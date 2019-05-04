package com.xiaohan.animationtest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileUtils {
    private static String filePath = "/sdcard/xh.txt";
    private static final String TAG = "RandomAccessFileUtils";
    public static boolean addFile(String data) {
        RandomAccessFile rFile = null;
        try {
            File file = new File(filePath);
            rFile = new RandomAccessFile(file, "rw");//读取文件
            long point = rFile.length();
            rFile.seek(point);// 到达文件尾
            rFile.writeBytes(data + "\r\n");
            /*rFile.writeUTF(data + "\r\n");
            rFile.writeInt(2);*/
            rFile.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String readLine() {
        StringBuilder stContent = null;
        RandomAccessFile rFile = null;
        if (new File(filePath).exists()) {
            try {
                stContent = new StringBuilder();
                rFile = new RandomAccessFile(filePath, "r");
                String line = null;
                //rFile.readUTF();
                //rFile.readInt();
                while (null != (line = rFile.readLine())) {//循环遍历
                    stContent.append(line);
                }
                rFile.close();
            } catch (Exception e) {
                //异常处理
            }
        }
        return stContent.toString();
    }

    public static void insert(long pos, String content) {//创建临时空文件
        File tempFile = null;
        try {
            tempFile = File.createTempFile("temp", null);
            //在虚拟机终止时，请求删除此抽象路径名表示的文件或目录
            tempFile.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempFile);
            RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
            raf.seek(pos);
            byte[] buffer = new byte[4];
            int num = 0;
            while (-1 != (num = raf.read(buffer))) {
                fos.write(buffer, 0, num);
            }
            raf.seek(pos);
            raf.writeBytes(content);
            FileInputStream fis = new FileInputStream(tempFile);
            while (-1 != (num = fis.read(buffer))) {
                raf.write(buffer, 0, num);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void change(String oldStr,String newStr){
        try {
            RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
            String line;
            while (null!=(line=raf.readLine())) {
                if(line.contains(oldStr)){
                    String[] split = line.split(oldStr);
                    raf.seek(split[0].length());
                    raf.writeBytes(newStr);
                    raf.writeBytes(split[1]);
                }
            }
           raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
