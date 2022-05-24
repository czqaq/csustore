package org.csu.store.controller;

import org.csu.store.common.CommonResponse;
import org.csu.store.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/file/")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/upload_img")
    public CommonResponse<String> uploadImg(@RequestParam() MultipartFile file) throws Exception {

        String path = "D:\\Afile\\img\\"; //本地测试
//        String path = "/home/file/img";
        //可规定命名规则，最好是随机形式
        //文件类型
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HHmmss");

        //文件命名为上传时间
        String fileName = df.format(System.currentTimeMillis())+"."+type;

        System.out.println("接收到的文件类型为：" + type);
        System.out.println("拼接文件名为：" + fileName);

        return fileService.saveFile(file.getInputStream(), fileName);
    }

    @GetMapping("/get_img")
    public void get(@RequestParam String imgUrl, HttpServletResponse response) throws FileNotFoundException {
        //System.out.println("被调用");
        String fileName = imgUrl;
        String dir = "D:\\Afile\\img\\";
//        String dir = "/home/file/img/";
        String file = dir + fileName;
        //System.out.println("file: " + file);
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            String diskfilename = fileName;
            response.setContentType("application/msword");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + diskfilename + "\"");
            //System.out.println("data.length " + data.length);
            response.setContentLength(data.length);
            response.setHeader("Content-Range", "" + Integer.valueOf(data.length - 1));
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Etag", "W/\"9767057-1323779115364\"");
            OutputStream os = response.getOutputStream();
            os.write(data);
            //先声明的流后关掉！
            os.flush();
            os.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
