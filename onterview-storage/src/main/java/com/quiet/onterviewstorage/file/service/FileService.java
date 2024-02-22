package com.quiet.onterviewstorage.file.service;

import com.quiet.onterviewstorage.file.dto.FileDto.FileResponse;
import com.quiet.onterviewstorage.global.BaseException;
import com.quiet.onterviewstorage.global.ErrorCode;
import com.quiet.onterviewstorage.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final FileUtils fileUtils;

    public FileResponse imageUpload(MultipartFile image) throws IOException {
        log.info(image.getOriginalFilename());
        String uuidFileName = fileUtils.createUUIDFileName(
                image.getOriginalFilename().split("\\.")[1]);
        image.transferTo(Path.of(fileUtils.IMAGE_PATH, uuidFileName));
        return new FileResponse(fileUtils.IMAGE_PATH, uuidFileName);
    }

    public Resource getImage(String filePath, String fileName) {
        Path path = Path.of(filePath, fileName);
        File file = new File(String.valueOf(path));
        if (!file.exists()) {
            log.info(filePath + "/" + fileName + " not found");
            throw new BaseException(ErrorCode.FILE_NOT_FOUND);
        }

        return new FileSystemResource(file);
    }

    public void deleteImage(String filePath, String fileName) {
        log.info(filePath);
        Path path = Path.of(filePath, fileName);
        File file = new File(String.valueOf(path));

        if (!file.exists()) {
            throw new BaseException(ErrorCode.FILE_NOT_FOUND);
        }

        file.delete();
    }
}
