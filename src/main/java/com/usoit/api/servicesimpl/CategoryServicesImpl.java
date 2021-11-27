package com.usoit.api.servicesimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoit.api.apicontroller.RestCategoryController;
import com.usoit.api.model.Category;
import com.usoit.api.repository.CategoryRepository;
import com.usoit.api.services.CategoryServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServicesImpl implements CategoryServices {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public boolean isKeyExist(String key) {

		return false;
	}

	@Override
	public boolean save(Category category) {

		if (0 >= category.getId()) {

			categoryRepository.save(category);

			if (category.getId() > 0) {
				return true;
			}

		}

		return false;
	}

	@Override
	public Category getCategoryById(int id) {

		if (id > 0) {

			Optional<Category> optional = categoryRepository.findById(id);

			if (optional != null) {

				return optional.get();
			}
		}

		return null;
	}

	@Override
	public boolean update(Category category) {

		if (category.getId() > 0) {

			categoryRepository.save(category);

			return true;
		}

		return false;
	}

	@Override
	public List<Category> getAllCats() {
		return (List<Category>) categoryRepository.findAll();
	}

	@Override
	public long getCount() {
		return categoryRepository.count();
	}

}
