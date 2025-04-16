package com.movie.controller.global;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.VoidResult;
import com.movie.common.resp.RespCode;
import com.movie.common.resp.Result;
import com.movie.exception.BusinessException;
import com.movie.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@PropertySource("classpath:aliyunoss-config.properties")
public class FileController {
    @Value("${oss.bucketName}")
    private String bucketName;
    @Value("${oss.fileUrlPrefix}")
    private String fileUrlPrefix;
    private final OSS oss;
    @PostMapping("/upload")
    public Result upload(@RequestParam("file")MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        String fileName = file.getOriginalFilename();//获取文件原始名字
        assert fileName != null;
        //objectName = 随机不重复的字符串.后缀
        String objectName = UUIDUtils.generateUUID() + fileName.substring(fileName.lastIndexOf("."));
        try (InputStream inputStream = file.getInputStream()) {
            //putObject 桶名称  文件名[重新取]  文件【输入流】
            PutObjectResult result = oss.putObject(bucketName, "images/movie-ticketing-project/" + objectName, inputStream);
            Map<String, Object> data = new HashMap<>();
            //fileUrl文件在服务器上的地址-- 用于保存数据库
            data.put("fileUrl", fileUrlPrefix + "images/movie-ticketing-project/"+objectName); //返回文件在服务器的地址
            data.put("original",fileName); //文件的原始名字mobile_display
            System.out.println("File " + fileName + " was uploaded successfully to OSS.");
            return Result.success(RespCode.UPLOAD_FILE_SUCCESS).setData(data);
        } catch (OSSException oe) {
            System.out.println("Error uploading file " + fileName + " to OSS: " + oe.getMessage());
            oe.printStackTrace();
            throw new BusinessException(RespCode.UPLOAD_FILE_ERROR);
        } catch (IOException e) {
            throw new BusinessException(RespCode.UPLOAD_FILE_ERROR);
        }
    }

    @GetMapping("/delete")
    public Result delete(@RequestParam(value = "fileName") String fileName) {
       try{
           String replace = fileName.replace(
                   "https://" + bucketName + ".oss-cn-chengdu.aliyuncs.com/"
                   , "");
           System.out.println(replace);
           VoidResult voidResult = oss.deleteObject(bucketName, replace);
           System.out.println(voidResult);
       }catch (Exception e){
           e.printStackTrace();
           throw new BusinessException(RespCode.DELETE_FILE_ERROR);
       }
        System.out.println("File " + fileName + " was deleted successfully from OSS.");
        return Result.success(RespCode.DELETE_FILE_SUCCESS);
    }
}
