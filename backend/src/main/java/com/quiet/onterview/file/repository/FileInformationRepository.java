package com.quiet.onterview.file.repository;

import com.quiet.onterview.file.entity.FileInformation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInformationRepository extends JpaRepository<FileInformation, Long> {

    Optional<FileInformation> findBySaveFilename(String saveFilename);
    void deleteBySaveFilename(String saveFilename);
}
