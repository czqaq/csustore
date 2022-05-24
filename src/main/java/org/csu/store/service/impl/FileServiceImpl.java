package org.csu.store.service.impl;

import org.csu.store.common.CommonResponse;
import org.csu.store.service.FileService;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileServiceImpl implements FileService {

    public CommonResponse<String> saveFile(InputStream inputStream, String fileName) {

        OutputStream os = null;
        try {
//            String path = "/home/file/img";
            String path = "D:\\Afile\\img\\";
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件

            File tempFile = new File(path);
            if (!tempFile.exists()) {
                boolean res = tempFile.mkdirs();
            }
            System.out.println("执行文件流:"+tempFile.getPath() + File.separator + fileName);
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (IOException e) {

            e.printStackTrace();
            return CommonResponse.createForError("上传失败");
        }  finally {
            // 完毕，关闭所有链接
            try {
                assert os != null;
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return CommonResponse.createForSuccess("上传成功", fileName);
    }
}
