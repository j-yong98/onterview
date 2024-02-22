package com.quiet.onterview.file.dto.response;

import lombok.*;

@Getter
@Builder
@Setter
@NoArgsConstructor
public class FileInformationResponse {

    private String saveFilename;
    private String originFilename;

    public FileInformationResponse(String saveFilename, String originFilename) {
        this.saveFilename = saveFilename;
        this.originFilename = originFilename;
    }
}
