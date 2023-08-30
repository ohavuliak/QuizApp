package com.example.quizapp.dao.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvatarRequest {
    private Long userId;
    private String imageUrl;
}
