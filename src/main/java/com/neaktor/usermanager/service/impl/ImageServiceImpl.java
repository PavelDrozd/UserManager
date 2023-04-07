package com.neaktor.usermanager.service.impl;

import com.neaktor.usermanager.data.dto.ImageDto;
import com.neaktor.usermanager.service.ImageService;
import com.neaktor.usermanager.shared.exception.service.ServiceNotFoundException;
import com.neaktor.usermanager.shared.exception.service.ServiceValidationException;
import com.neaktor.usermanager.shared.util.mapper.EntityDtoMapper;
import com.neaktor.usermanager.store.entity.Image;
import com.neaktor.usermanager.store.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private final EntityDtoMapper mapper;


    @Override
    public ImageDto create(ImageDto imageDto) {
        checkImageNull(imageDto);
        Image image = imageRepository.save(mapper.mapToImage(imageDto));
        return mapper.mapToImageDto(image);
    }

    @Override
    public Page<ImageDto> getAll(Pageable pageable) {
        return imageRepository.findAll(pageable).map(mapper::mapToImageDto);
    }

    @Override
    public ImageDto get(Long id) {
        checkIdNull(id);
        return imageRepository.findById(id)
                .map(mapper::mapToImageDto)
                .orElseThrow(() -> new ServiceNotFoundException("Image with id: " + id + " does'nt exist"));
    }

    @Override
    public ImageDto update(ImageDto imageDto) {
        checkImageNull(imageDto);
        Image image = imageRepository.save(mapper.mapToImage(imageDto));
        return mapper.mapToImageDto(image);
    }

    @Override
    public void delete(Long id) {
        checkIdNull(id);
        imageRepository.findById(id).orElseThrow(() -> new ServiceNotFoundException("Image with id: " + id + " doesn't exist"));
        imageRepository.deleteById(id);
    }

    private void checkImageNull(ImageDto imageDto) {
        if(imageDto == null) {
            throw new ServiceValidationException("Image is null");
        }
    }

    private void checkIdNull(Long id) {
        if (id == null) {
            throw new ServiceValidationException("ID is null");
        }
    }
}
