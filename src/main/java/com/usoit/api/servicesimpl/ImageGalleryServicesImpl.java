package com.usoit.api.servicesimpl;

import org.springframework.stereotype.Service;

import com.usoit.api.services.ImageGalleryServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageGalleryServicesImpl implements ImageGalleryServices {

	@Override
	public boolean isKeyExist(String key) {
		

		return false;
	}
}
