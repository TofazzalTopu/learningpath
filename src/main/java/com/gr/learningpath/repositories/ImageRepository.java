package com.gr.learningpath.repositories;

import com.gr.learningpath.domain.ImageModel;
import com.gr.learningpath.domain.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel, Long> {
    Optional<ImageModel> findByName(String name);

    List<ImageModel> findAllByUser_Username(@Nonnull String username);


    void deleteByImageTypeAndUser_Id(@Nonnull ImageType imageType, @Nonnull Long id);
}
