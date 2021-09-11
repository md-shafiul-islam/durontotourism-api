package com.usoit.api.data.converter;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

public class DozerMapper {

	private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
	public static <O, D> D parseObject(O origin, Class<D> destination) {
		
		return mapper.map(origin, destination);
	}
	
	public static <O, D> List<D> parseObjectList(List<O> origins, Class<D> destination) {
		
		List<D> destinationObjectList = new ArrayList<D>();
		
		for (O originItem : origins) {
			
			destinationObjectList.add(mapper.map(originItem, destination));
		}
		
		return destinationObjectList;
	}
	
}
