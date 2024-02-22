package com.quiet.onterviewstorage.file.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.MediaType;

@RequiredArgsConstructor
@Getter
public class ResourceDto {

    private final MediaType mediaType;
    private final String path;
    private final ResourceRegion region;
}