package com.gr.learningpath.api;

import com.gr.learningpath.domain.ImageType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ImageResponse {
    private String name;
    private String type;
    private byte[] imageBinary;
    private ImageType imageType;
}
