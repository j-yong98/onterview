package com.quiet.onterviewstorage.file.service;

import com.quiet.onterviewstorage.file.dto.FileDto;
import com.quiet.onterviewstorage.file.dto.ResourceDto;
import com.quiet.onterviewstorage.global.BaseException;
import com.quiet.onterviewstorage.global.ErrorCode;
import com.quiet.onterviewstorage.util.FFmpegManager;
import com.quiet.onterviewstorage.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChunkService {

    private final FFmpegManager fFmpegManager;
    private final FileUtils fileUtils;

    public HttpStatus chunkUpload(MultipartFile file, FileDto.VideoRequest request)
            throws IOException {
        int chunkNumber = request.getChunkNumber();
        int endOfChunk = request.getEndOfChunk();
        String filename = request.getFilename();

        Path videoPath = createFolder(fileUtils.VIDEO_PATH, filename);
        Path imagePath = createFolder(fileUtils.IMAGE_PATH);

        saveTempFile(file, chunkNumber, videoPath);

        // 파일이 전송중인 경우
        if (endOfChunk == 0) {
            log.info(String.format("chunk %s.%d saved ", filename, chunkNumber));
            return HttpStatus.PARTIAL_CONTENT;
        }

        Path mergedVideoPath = mergeTempFile(file, videoPath, filename, chunkNumber);
        log.info(String.format("%s saved", filename));

        String createdThumbnail = fFmpegManager.createThumbnail(mergedVideoPath, imagePath,
                filename);
        log.info(String.format("thumbnail %s saved", createdThumbnail));

        return HttpStatus.OK;
    }

    public Optional<ResourceDto> getStreamResource(
            HttpHeaders headers, String filename
    ) throws IOException {
        Path path = Paths.get(
                String.valueOf(Path.of(fileUtils.VIDEO_PATH, filename.split("\\.")[0])),
                filename);

        log.info("#stream resource path: " + path);
        Resource resource = new FileSystemResource(path);

        long chunkSize = 1024 * 1024;
        long contentLength = resource.contentLength();

        HttpRange httpRange = headers.getRange().stream().findFirst()
                .orElse(HttpRange.createByteRange(0, contentLength - 1));

        long rangeLength = calculateRangeLength(httpRange, contentLength, chunkSize);
        long rangeStart = httpRange.getRangeStart(contentLength);
        if (rangeStart > contentLength) {
            return Optional.empty();
        }

        ResourceRegion region = new ResourceRegion(resource, httpRange.getRangeStart(contentLength),
                rangeLength);

        return Optional.of(new ResourceDto(
                MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM),
                String.valueOf(path),
                region
        ));
    }

    public void delete(String fileName) {
        Path path = Path.of(fileUtils.VIDEO_PATH, fileName);
        File folder = new File(String.valueOf(path));

        if (folder.exists()) {
            Arrays.stream(folder.listFiles())
                    .filter(File::isFile)
                    .forEach(File::delete);

            folder.delete();
        }
    }

    private static Path mergeTempFile(
            MultipartFile file, Path savingPath, String filename, int chunkNumber
    ) throws IOException {
        Path outputFilePath = Path.of(String.valueOf(savingPath), filename + ".mkv");

        if (outputFilePath.toFile().exists()) {
            Files.delete(outputFilePath);
        }

        Files.createFile(outputFilePath);

        for (int number = 1; number <= chunkNumber; number++) {
            Path chunkFile = Paths.get(String.valueOf(savingPath),
                    file.getOriginalFilename() + ".part" + number);

            if (!Files.exists(chunkFile)) {
                log.info(chunkFile + " is not found");
                throw new BaseException(ErrorCode.CHUNK_NOT_FOUND);
            }

            Files.write(outputFilePath, Files.readAllBytes(chunkFile), StandardOpenOption.APPEND);
            Files.delete(chunkFile);
        }

        return outputFilePath;
    }

    private static void saveTempFile(MultipartFile file, int chunkNumber, Path path)
            throws IOException {
        // 임시 저장 파일 이름
        String tempFilename = file.getOriginalFilename() + ".part" + chunkNumber;
        Path tempFilePath = Paths.get(String.valueOf(path), tempFilename);
        // 임시 저장
        Files.write(tempFilePath, file.getBytes());
    }

    private Path createFolder(String subPath, String filename) {
        Path path = Path.of(subPath, filename);
        File dir = new File(String.valueOf(path));
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return path;
    }

    private Path createFolder(String subPath) {
        Path path = Path.of(subPath);
        File dir = new File(String.valueOf(path));
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return path;
    }

    private long calculateRangeLength(HttpRange httpRange, long contentLength, long chunkSize) {
        long start = httpRange.getRangeStart(contentLength);
        long end = httpRange.getRangeEnd(contentLength);
        return Long.min(chunkSize, end - start + 1);
    }
}