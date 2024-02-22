package com.quiet.onterview.file.service;

import com.quiet.onterview.file.dto.request.FileInformationRequest;
import com.quiet.onterview.file.dto.response.FileInformationResponse;
import com.quiet.onterview.file.exception.FileNotExistException;
import com.quiet.onterview.file.mapper.FileInformationMapper;
import com.quiet.onterview.file.repository.FileInformationRepository;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final FileInformationRepository fileInformationRepository;
    private final FileInformationMapper fileInformationMapper;

    @Value("${file.base-url}")
    private String baseUrl;
    @Value("${file.file-key}")
    private String fileKey;
    @Value("${file.file-path-query}")
    private String filePathQuery;
    @Value("${file.file-name-query}")
    private String fileNameQuery;
    @Value("${file.file-path}")
    private String filePath;


    @Override
    public void saveFileInformation(FileInformationRequest... fileInformationRequests) {
        Arrays.stream(fileInformationRequests)
                .map(fileInformationMapper::fileInformationRequestToEntity)
                .forEach(fileInformationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public FileInformationResponse loadFileInformationByFileInformationId(Long fileInformationId) {
        return fileInformationMapper.fileInformationToResponse(
                fileInformationRepository.findById(fileInformationId)
                        .orElseThrow(FileNotExistException::new)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public FileInformationResponse loadFileInformationBySaveFilename(String saveFilename) {
        return fileInformationMapper.fileInformationToResponse(
                fileInformationRepository.findBySaveFilename(saveFilename)
                        .orElseThrow(FileNotExistException::new)
        );
    }

    @Override
    public void deleteFileInformationByFileInformationId(Long fileInformationId) {
        fileInformationRepository.deleteById(fileInformationId);
    }

    @Override
    public void deleteFileInformationBySaveName(String saveFilename) {
        fileInformationRepository.deleteBySaveFilename(saveFilename);
    }

    @Override
    public String transToFileServer(MultipartFile file, String token) {
        MultiValueMap<String, Object> multipartData = new LinkedMultiValueMap<>();
        multipartData.add(fileKey, file.getResource());
        Map result = WebClient.create(baseUrl)
                .post()
                .header(AUTHORIZATION_HEADER, token)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(multipartData))
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        return (String) result.get(fileNameQuery);
    }

    @Override
    public void deleteFilesOnFileServer(String token, Long... fileId) {
        Arrays.stream(fileId)
                .map(fileInformationRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(fileInformation -> deleteFileOnFileServer(
                        fileInformation.getSaveFilename(), token)
                );
    }

    @Override
    public void deleteFileOnFileServer(String filename, String token) {
        WebClient.create(baseUrl)
                .delete()
                .uri(uriBuilder -> uriBuilder
                        .queryParam(filePathQuery, filePath)
                        .queryParam(fileNameQuery, filename)
                        .build())
                .header(AUTHORIZATION_HEADER, token)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
