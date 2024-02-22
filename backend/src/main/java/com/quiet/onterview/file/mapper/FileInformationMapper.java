package com.quiet.onterview.file.mapper;


import com.quiet.onterview.file.FileUtils;
import com.quiet.onterview.file.dto.request.FileInformationRequest;
import com.quiet.onterview.file.dto.response.FileInformationResponse;
import com.quiet.onterview.file.entity.FileInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileInformationMapper {

    private final FileUtils fileUtils;

    public FileInformation fileInformationRequestToEntity(
            FileInformationRequest fileInformationRequest) {
        return FileInformation.builder()
                .originFilename(fileInformationRequest.getOriginFilename())
                .saveFilename(fileInformationRequest.getSaveFilename())
                .build();
    }

    public FileInformationResponse fileInformationToResponse(FileInformation fileInformation) {
        return FileInformationResponse.builder()
                .originFilename(fileInformation.getOriginFilename())
                .saveFilename(fileInformation.getSaveFilename())
                .build();
    }

    public FileInformationResponse imageInformationToResponse(FileInformation fileInformation) {
        return FileInformationResponse.builder()
                .originFilename(fileInformation.getOriginFilename())
                .saveFilename(fileUtils.thumbnailUrl(fileInformation.getSaveFilename()))
                .build();
    }
}
