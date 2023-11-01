package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admin/common")
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        log.info("file{}",file);
        try {
            String originalFilename= file.getOriginalFilename();;
            assert originalFilename != null;
            String  extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName= UUID.randomUUID().toString()+extension;
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        }catch (Exception e){
            log.info("error upload:{}",file);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
