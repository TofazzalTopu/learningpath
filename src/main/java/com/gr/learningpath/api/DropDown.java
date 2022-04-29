package com.gr.learningpath.api;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class DropDown {
    private Long value;
    private String label;
}
