package com.usoit.api.services;

import java.util.List;

import com.usoit.api.data.model.Category;

public interface CategoryServices {

	public List<Category> getAllCats();

	public long getCount();

	public boolean save(Category category);

	public Category getCategoryById(int id);

	public boolean update(Category category);

}
