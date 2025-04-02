package com.louiskhanh.airbnb_clone_be.user.application.dto;

import java.util.Set;
import java.util.UUID;

public record ReadUserDTO (UUID publicId, 
                        String firstname,
                        String lastName,
                        String email,
                        String imageUrl,
                        Set<String> authorities){
    
}
