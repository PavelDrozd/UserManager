package com.neaktor.usermanager.shared.util.mapper;

import com.neaktor.usermanager.data.dto.ImageDto;
import com.neaktor.usermanager.data.dto.UserDto;
import com.neaktor.usermanager.store.entity.Image;
import com.neaktor.usermanager.store.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EntityDtoMapper {

    private final ModelMapper mapper;

    public UserDto mapToUserDto(User user) {
        return mapper.map(user, UserDto.class);
    }

    public User mapToUser(UserDto userDto) {
        return mapper.map(userDto, User.class);
    }

    public ImageDto mapToImageDto(Image image) {
        return mapper.map(image, ImageDto.class);
    }

    public Image mapToImage(ImageDto imageDto) {
        return mapper.map(imageDto, Image.class);
    }

}
