package com.usoit.api.data.converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.usoit.api.data.model.Category;
import com.usoit.api.data.model.Country;
import com.usoit.api.data.model.ImageGallery;
import com.usoit.api.data.model.Itarnary;
import com.usoit.api.data.model.PackageCat;
import com.usoit.api.data.model.Packages;
import com.usoit.api.data.model.Vendor;
import com.usoit.api.data.shared.dto.DtoImageGallery;
import com.usoit.api.data.shared.dto.DtoItarnary;
import com.usoit.api.data.shared.dto.DtoUpdatePackage;
import com.usoit.api.data.vo.ImageGalleryAPI;
import com.usoit.api.data.vo.RestCountry;
import com.usoit.api.data.vo.RestDuration;
import com.usoit.api.data.vo.RestItarnary;
import com.usoit.api.data.vo.RestPackDetails;
import com.usoit.api.data.vo.RestPackage;
import com.usoit.api.data.vo.RestPackageCat;
import com.usoit.api.data.vo.RestViewPackages;
import com.usoit.api.model.request.ReqCountryOnPack;
import com.usoit.api.model.request.ReqImageGallery;
import com.usoit.api.model.request.ReqItarnaryOnPack;
import com.usoit.api.model.request.ReqPackage;
import com.usoit.api.services.CategoryServices;
import com.usoit.api.services.CountryServices;
import com.usoit.api.services.DurationServices;
import com.usoit.api.services.PackageCatServices;
import com.usoit.api.services.VendorServices;

@Component
public class PackageMapper {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private CountryServices countryServices;

	@Autowired
	private DurationServices durationServices;

	@Autowired
	private PackageCatServices packCatServices;

	@Autowired
	private CategoryServices categoryServices;
	
	@Autowired
	private DataConverter dataConverter;

	@Autowired
	private VendorServices vendorServices;

	public RestPackage getRestPackage(Packages packages) {

		System.out.println("Start Rest Pack Mapper!!");
		
		RestPackage restPackage = new RestPackage();

		restPackage.setApprovalStatus(packages.getApprovalStatus());
		restPackage.setPublicId(packages.getPublicId());
		
		System.out.println("Befor Rest User ");
		
		if (packages.getApproveUser() != null) {
			restPackage.setApproveUser(userMapper.getRestUser(packages.getApproveUser()));
		}else {
			restPackage.setApproveUser(null);
		}
		
		
		restPackage.setCode(packages.getCode());
		
		System.out.println("Befor Rest Countrey ");
		
		restPackage.setCountries(DozerMapper.parseObjectList(packages.getCountries(), RestCountry.class));
		restPackage.setDate(packages.getDate());
		restPackage.setDescription(packages.getDescription());
		
		System.out.println("After Load Rest Countrey ");
		
		if (packages.getDuration() != null) {
			restPackage.setDuration(DozerMapper.parseObject(packages.getDuration(), RestDuration.class));
		}
		
		System.out.println("After Pack Mapper Rest Duration ");
		
		if (packages.getImageGalleries() != null) {
			restPackage.setImageGalleries(DozerMapper.parseObjectList(packages.getImageGalleries(), ImageGalleryAPI.class));
		}
		
		System.out.println("After Pack Mapper Image Gallery ");
		
		if (packages.getItarnarys() != null) {
			restPackage.setItarnarys(DozerMapper.parseObjectList(packages.getItarnarys(), RestItarnary.class));
		}
		System.out.println("After Pack Mapper Rest getItarnarys ");
		
		
		restPackage.setName(packages.getName());

		restPackage.setPackageCat(DozerMapper.parseObject(packages.getPackageCat(), RestPackageCat.class));
		
		System.out.println("After Load Rest Pack Cat ");

		restPackage.setPackExcludedText(packages.getPackExcludedText());
		restPackage.setPackHightlightText(packages.getPackIncludedText());
		restPackage.setPackIncludedText(packages.getPackIncludedText());
		restPackage.setPrice(packages.getPrice());
		restPackage.setUpdateApproval(packages.getUpdateApproval());
		
		if (packages.getUpdateUser() != null) {
			restPackage.setUpdateUser(userMapper.getRestUser(packages.getUpdateUser()));
		}else {
			restPackage.setUpdateUser(null);
		}
		System.out.println("Pack Mapper Rest  User Update");
		
		if (packages.getUser() != null) {
			restPackage.setUser(userMapper.getRestUser(packages.getUser()));
		}else {
			restPackage.setUser(null);
		}
		
		System.out.println("Pack Mapper Rest Add User");
		restPackage.setVideoUrl(packages.getVideoUrl());

		System.out.println("End Reat Pack Mapper!!");

		return restPackage;
	}

	public List<RestPackage> getRestPackages(List<Packages> pandinPackages) {

		List<RestPackage> restPack = new ArrayList<>();
		
		if (pandinPackages != null) {
			
			if (pandinPackages.size() > 0) {
				
				for (Packages pack : pandinPackages) {
					
					restPack.add(getRestPackage(pack));
				}
			}
		}
		
		System.out.println("End Set data To GetRestPackages");
		
		return restPack;
		
	}

	public void test(List<Packages> pandinPackages) {

		System.out.println("Run This Fnc Mapper : PP Size" + pandinPackages.size());

	}

	
	public ReqPackage getReqPackage(Packages packages) {
		
		System.out.println("mapper Run !! Pack Req Pack");
		ReqPackage reqPack = new ReqPackage();	
		
		// Build Mapper
		reqPack.setPublicId(packages.getPublicId());
		reqPack.setCode(packages.getCode());
		
		if (packages.getCountries() != null) {
			
			List<ReqCountryOnPack> countryOnPacks = new ArrayList<>();
			
			for (Country count : packages.getCountries()) {
				ReqCountryOnPack countryOnPack = new ReqCountryOnPack();
				countryOnPack.setId(count.getId());
				countryOnPack.setIndex(Math.random());
				countryOnPacks.add(countryOnPack);
			}
			
			
			reqPack.setCountries(countryOnPacks);
		}else {
			reqPack.setCountries(new ArrayList<>());;
		}
		
		System.out.println("After Countries mapper Run !! Pack Req Pack");
		
		reqPack.setDescription(packages.getDescription());
		
		if (packages.getDuration() != null) {
			reqPack.setDuration(packages.getDuration().getId());
		}else {
			reqPack.setDuration(0);
		}
		
		reqPack.setName(packages.getName());
		reqPack.setPackageCat(packages.getPackageCat().getId());
		reqPack.setPackExcludedText(packages.getPackExcludedText());
		reqPack.setPackHightlightText(packages.getPackHightlightText());
		reqPack.setPackIncludedText(packages.getPackIncludedText());
		reqPack.setPrice(Double.toString(packages.getPrice()));
		reqPack.setVideoUrl(packages.getVideoUrl());
		
		System.out.println("mapper Run !! Pack Req Pack Befor Itn");
		if (packages.getItarnarys() != null) {
			
			List<ReqItarnaryOnPack> itarnaryOnPacks = new ArrayList<>();
			
			for (Itarnary item : packages.getItarnarys()) {
				
				ReqItarnaryOnPack itarnaryPack = new ReqItarnaryOnPack();
				
				if (item.getCategory() != null) {
					itarnaryPack.setCategory(item.getCategory().getId());
				}
				
				itarnaryPack.setCity(item.getCity());
				
				if (item.getCountry() != null) {
					itarnaryPack.setCountry(item.getCountry().getId());
					
				}
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				
				if (item.getExpDate() != null) {
					
					itarnaryPack.setExpDate(new Date(formatter.format(item.getExpDate())));
				}
				
				itarnaryPack.setDayOrDurations(item.getDayOrDurations());
				itarnaryPack.setDescription(item.getDescription());
				itarnaryPack.setExcludedText(item.getExcludedText());
				
				itarnaryPack.setHeading(item.getHeading());
				itarnaryPack.setHightLightText(item.getHightLightText());
				itarnaryPack.setHotelText(item.getHotelText());
				itarnaryPack.setIncludedText(item.getIncludedText());
				itarnaryPack.setSourceInfo(item.getSourceInfo());
				itarnaryPack.setSourceUrl(item.getSourceUrl());
				itarnaryPack.setSourceUrl2(item.getSourceUrl2());
				
				
				if (item.getVendor() != null) {
					itarnaryPack.setVendor(item.getVendor().getPublicId());
				}
				
				itarnaryOnPacks.add(itarnaryPack);
				
			}
			
			reqPack.setItarnarys(itarnaryOnPacks);
		}else {
			reqPack.setItarnarys(new ArrayList<>());
		}
		
		List<ReqImageGallery> imageGalleries = new ArrayList<>();
		if(packages.getImageGalleries() != null) {
			
			for (ImageGallery imageInf : packages.getImageGalleries()) {
				
				ReqImageGallery gallery = new ReqImageGallery();
				
				gallery.setIndex(Math.random());
				gallery.setAltTag(imageInf.getAltTag());
				gallery.setName(imageInf.getName());
				gallery.setLocation(imageInf.getLocation());
				gallery.setSrcUrl(imageInf.getSrcUrl());
				
				imageGalleries.add(gallery);
			}
			
			reqPack.setImageGalleries(imageGalleries);
		}else {
			reqPack.setImageGalleries(imageGalleries);
		}
		
		System.out.println("mapper Run !! Pack Req Pack End!!!");
		return reqPack;
	}
	
	
	public DtoUpdatePackage getGetUpdatePack(Packages packages) {
		
		System.out.println("mapper Run !! Pack Req Pack");
		DtoUpdatePackage reqPack = new DtoUpdatePackage();	
		
		// Build Mapper
		reqPack.setPublicId(packages.getPublicId());
		reqPack.setCode(packages.getCode());
		
		if (packages.getCountries() != null) {
			
			List<ReqCountryOnPack> countryOnPacks = new ArrayList<>();
			
			for (Country count : packages.getCountries()) {
				ReqCountryOnPack countryOnPack = new ReqCountryOnPack();
				countryOnPack.setId(count.getId());
				countryOnPack.setIndex(Math.random());
				countryOnPacks.add(countryOnPack);
			}
			
			
			reqPack.setCountries(countryOnPacks);
		}else {
			reqPack.setCountries(new ArrayList<>());;
		}
		
		System.out.println("After Countries mapper Run !! Pack Req Pack");
		
		reqPack.setDescription(packages.getDescription());
		
		if (packages.getDuration() != null) {
			reqPack.setDuration(packages.getDuration().getId());
		}else {
			reqPack.setDuration(0);
		}
		
		reqPack.setName(packages.getName());
		reqPack.setPackageCat(packages.getPackageCat().getId());
		reqPack.setPackExcludedText(packages.getPackExcludedText());
		reqPack.setPackHightlightText(packages.getPackHightlightText());
		reqPack.setPackIncludedText(packages.getPackIncludedText());
		reqPack.setPrice(Double.toString(packages.getPrice()));
		reqPack.setVideoUrl(packages.getVideoUrl());
		
		System.out.println("mapper Run !! Pack Req Pack Befor Itn");
		if (packages.getItarnarys() != null) {
			
			List<DtoItarnary> itarnaryOnPacks = new ArrayList<>();
			
			for (Itarnary item : packages.getItarnarys()) {
				
				DtoItarnary itarnaryPack = new DtoItarnary();
				
				itarnaryPack.setId(item.getId());
				
				if (item.getCategory() != null) {
					itarnaryPack.setCategory(item.getCategory().getId());
				}
				
				itarnaryPack.setCity(item.getCity());
				
				if (item.getCountry() != null) {
					itarnaryPack.setCountry(item.getCountry().getId());
					
				}
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				
				if (item.getExpDate() != null) {
					
					itarnaryPack.setExpDate(new Date(formatter.format(item.getExpDate())));
				}
				
				itarnaryPack.setDayOrDurations(item.getDayOrDurations());
				itarnaryPack.setDescription(item.getDescription());
				itarnaryPack.setExcludedText(item.getExcludedText());
				
				itarnaryPack.setHeading(item.getHeading());
				itarnaryPack.setHightLightText(item.getHightLightText());
				itarnaryPack.setHotelText(item.getHotelText());
				itarnaryPack.setIncludedText(item.getIncludedText());
				itarnaryPack.setSourceInfo(item.getSourceInfo());
				itarnaryPack.setSourceUrl(item.getSourceUrl());
				itarnaryPack.setSourceUrl2(item.getSourceUrl2());
				
				
				if (item.getVendor() != null) {
					itarnaryPack.setVendor(item.getVendor().getPublicId());
				}
				
				itarnaryOnPacks.add(itarnaryPack);
				
			}
			
			reqPack.setItarnarys(itarnaryOnPacks);
		}else {
			reqPack.setItarnarys(new ArrayList<>());
		}
		
		List<DtoImageGallery> imageGalleries = new ArrayList<>();
		if(packages.getImageGalleries() != null) {
			
			for (ImageGallery imageInf : packages.getImageGalleries()) {
				
				DtoImageGallery gallery = new DtoImageGallery();
				
				if (imageInf.getId() > 0) {
					gallery.setId(imageInf.getId());
				}
				gallery.setAltTag(imageInf.getAltTag());
				gallery.setName(imageInf.getName());
				gallery.setLocation(imageInf.getLocation());
				gallery.setSrcUrl(imageInf.getSrcUrl());
				
				
				imageGalleries.add(gallery);
				
			}
			
			reqPack.setImageGalleries(imageGalleries);
		}else {
			reqPack.setImageGalleries(imageGalleries);
		}
		
		System.out.println("mapper Run !! Pack Req Pack End!!!");
		return reqPack;
	}

	public boolean getPackDtoPackToPackage(Packages dbPackages, DtoUpdatePackage dtoPackages) {
		
		if (dbPackages != null && dtoPackages != null) {
			
			dbPackages.setApprovalStatus(0);
			
			dbPackages.setCode(dtoPackages.getCode());
			
			//Set Countries Start
			List<Country> dtoCountriesObj = new ArrayList<>();
			
			if (dtoPackages.getCountries() != null) {
				
				System.out.println("Added Country Done!! ");
				for (ReqCountryOnPack cnty : dtoPackages.getCountries()) {
					
					Country country = countryServices.getCountryById(cnty.getId());
					dtoCountriesObj.add(country);
				}
			}
			
			dbPackages.setCountries(dtoCountriesObj);
			
			//Set Countries End
			
		
			dbPackages.setDescription(dtoPackages.getDescription());
		
			//Set Duration
			if (dtoPackages.getDuration() > 0) {
				
				dbPackages.setDuration(durationServices.getDurationById(dtoPackages.getDuration()));
				
			}else {
				dbPackages.setDuration(null);
			}
			
			//Set Duration End
			System.out.println("Curren DB Iamge Gallery Size: " + dbPackages.getImageGalleries().size());
			
			if (dtoPackages.getImageGalleries() != null) {
				
				List<ImageGallery> dtoImages = new ArrayList<>();
				
				System.out.println("Curren Iamge Gallery Size: " + dtoPackages.getImageGalleries().size());
				
				if (dtoPackages.getImageGalleries().size() > 0) {
					
					for (DtoImageGallery imageGallery : dtoPackages.getImageGalleries()) {
						
						ImageGallery gallery = new ImageGallery();
						
						if (imageGallery.getId() > 0) {
							gallery.setId(imageGallery.getId());
						}
						gallery.setPackages(dbPackages);
						
						gallery.setAltTag(imageGallery.getAltTag());
						gallery.setName(imageGallery.getName());
						gallery.setLocation(imageGallery.getLocation());
						
						gallery.setSrcUrl(imageGallery.getSrcUrl());
						
						dtoImages.add(gallery);
					}
					
					dbPackages.setImageGalleries(dtoImages);
					
				}
			}
			
			System.out.println("After Set DTO DB Iamge Gallery Size: " + dbPackages.getImageGalleries().size());
			
			if (dtoPackages.getItarnarys() != null) {
				
				if (dtoPackages.getItarnarys().size() > 0) {
					
					List<Itarnary> dtoItarnary = new ArrayList<>();
					
					for (DtoItarnary itarnaryDto : dtoPackages.getItarnarys()) {
						
						Itarnary itarnary = new Itarnary();
						
						itarnary = getDtoItnToItarnary(itarnaryDto);
						itarnary.setId(itarnaryDto.getId());
						itarnary.setPackages(dbPackages);
						
						dtoItarnary.add(itarnary);
						
					}
					
					dbPackages.setItarnarys(dtoItarnary);
				}
			}
			
			dbPackages.setName(dtoPackages.getName());
			
			if (dtoPackages.getPackageCat() > 0) {
				
				PackageCat packageCat = packCatServices.getPackCatById(dtoPackages.getPackageCat());
				
				if (packageCat != null) {
					dbPackages.setPackageCat(packageCat);
				}
			}
			
			dbPackages.setPackExcludedText(dtoPackages.getPackExcludedText());
			dbPackages.setPackHightlightText(dtoPackages.getPackHightlightText());
			dbPackages.setPackIncludedText(dtoPackages.getPackIncludedText());
			
			if (dataConverter.getStringToDouble(dtoPackages.getPrice()) > 0) {
				
				dbPackages.setPrice(dataConverter.getStringToDouble(dtoPackages.getPrice()));
			}
			
			dbPackages.setUpdateApproval(0);
			dbPackages.setVideoUrl(dtoPackages.getVideoUrl());
			
			return true;
		}
		
		return false;
		
	}

	private Itarnary getDtoItnToItarnary(DtoItarnary dtoItarnary) {
		
		Itarnary itarnary = new Itarnary();
		
		if (dtoItarnary != null) {
			
			if (dtoItarnary.getCategory() > 0) {
				
				Category category = categoryServices.getCategoryById(dtoItarnary.getCategory());
				
				if (category != null) {
					itarnary.setCategory(category);
				}
			}
			
			itarnary.setCity(dtoItarnary.getCity());
			
			if (dtoItarnary.getCountry() > 0) {
				
				Country country = countryServices.getCountryById(dtoItarnary.getCountry());
				if (country != null) {
					itarnary.setCountry(country);
				};
				
			}
			
			itarnary.setDescription(dtoItarnary.getDescription());
			itarnary.setDayOrDurations(dtoItarnary.getDayOrDurations());
			itarnary.setExcludedText(dtoItarnary.getExcludedText());
			itarnary.setExpDate(dtoItarnary.getExpDate());
			itarnary.setHeading(dtoItarnary.getHeading());
			itarnary.setHightLightText(dtoItarnary.getHightLightText());
			itarnary.setHotelText(dtoItarnary.getHotelText());
			
			itarnary.setIncludedText(dtoItarnary.getIncludedText());
			itarnary.setSourceInfo(dtoItarnary.getSourceInfo());
			itarnary.setSourceUrl(dtoItarnary.getSourceUrl());
			itarnary.setSourceUrl2(dtoItarnary.getSourceUrl2());
			
			if (dtoItarnary.getVendor() != null) {
				System.out.println("Vendor Is Not null Pass !!");
				
				if (dtoItarnary.getVendor().length() >= 30) {
				
					System.out.println("Vendor lenght Pass !!");
					Vendor vendor = vendorServices.getVendorByPublicId(dtoItarnary.getVendor());
					
					if (vendor != null) {
						
						System.out.println("Vendor Found !!");
						itarnary.setVendor(vendor);
						
					}else {
						System.out.println("Vendor Not Found!!");
					}
				}
			}
			
		}
		
		return itarnary;
	}

	public RestPackDetails getRestPackageDetails(Packages packages) {
		

		System.out.println("Start Rest Pack Mapper!!");
		
		RestPackDetails restPackage = new RestPackDetails();

		restPackage.setApprovalStatus(packages.getApprovalStatus());
		restPackage.setPublicId(packages.getPublicId());
		
		System.out.println("Befor Rest User ");
		
		if (packages.getApproveUser() != null) {
			restPackage.setApproveUser(userMapper.getRestUserPack(packages.getApproveUser()));
		}else {
			restPackage.setApproveUser(null);
		}
		
		
		restPackage.setCode(packages.getCode());
		
		System.out.println("Befor Rest Countrey ");
		
		if (packages.getCountries() != null) {
			
			if (packages.getCountries().size() > 0) {
				restPackage.setCountries(DozerMapper.parseObjectList(packages.getCountries(), RestCountry.class));
			}
			System.out.println("Rest Country Size: " + restPackage.getCountries().size());
		}
		
		
		restPackage.setDate(packages.getDate());
		restPackage.setDescription(packages.getDescription());
		
		System.out.println("After Load Rest Countrey ");
		
		if (packages.getDuration() != null) {
			restPackage.setDuration(DozerMapper.parseObject(packages.getDuration(), RestDuration.class));
		}
		
		System.out.println("After Pack Mapper Rest Duration ");
		
		if (packages.getImageGalleries() != null) {
			restPackage.setImageGalleries(DozerMapper.parseObjectList(packages.getImageGalleries(), ImageGalleryAPI.class));
		}
		
		System.out.println("After Pack Mapper Image Gallery ");
		
		if (packages.getItarnarys() != null) {
			restPackage.setItarnarys(DozerMapper.parseObjectList(packages.getItarnarys(), RestItarnary.class));
		}
		System.out.println("After Pack Mapper Rest getItarnarys ");
		
		
		restPackage.setName(packages.getName());

		restPackage.setPackageCat(DozerMapper.parseObject(packages.getPackageCat(), RestPackageCat.class));
		
		System.out.println("After Load Rest Pack Cat ");

		restPackage.setPackExcludedText(packages.getPackExcludedText());
		restPackage.setPackHightlightText(packages.getPackIncludedText());
		restPackage.setPackIncludedText(packages.getPackIncludedText());
		restPackage.setPrice(packages.getPrice());
		restPackage.setUpdateApproval(packages.getUpdateApproval());
		
		if (packages.getUpdateUser() != null) {
			restPackage.setUpdateUser(userMapper.getRestUserPack(packages.getUpdateUser()));
		}else {
			restPackage.setUpdateUser(null);
		}
		System.out.println("Pack Mapper Rest  User Update");
		
		if (packages.getUser() != null) {
			restPackage.setUser(userMapper.getRestUserPack(packages.getUser()));
		}else {
			restPackage.setUser(null);
		}
		
		System.out.println("Pack Mapper Rest Add User");
		restPackage.setVideoUrl(packages.getVideoUrl());

		System.out.println("End Reat Pack Mapper!!");

		return restPackage;
		
	}

	public List<RestViewPackages> getPackageListView(List<Packages> confPackages) {
		
		List<RestViewPackages> list = new ArrayList<>();
		
		if (confPackages != null) {
			
			for (Packages pack : confPackages) {
				
				list.add(getRestViewPackage(pack));
			}
			
			return list;
		}
		
		return null;
	}

	private RestViewPackages getRestViewPackage(Packages pack) {
		
		RestViewPackages viewPackages = new RestViewPackages();
		
		if (pack != null) {
			
			viewPackages.setApprovalStatus(pack.getApprovalStatus());
			viewPackages.setApproveUser(userMapper.getRestUserPack(pack.getApproveUser()));
			viewPackages.setCode(pack.getCode());
			
			if (pack.getCountries() != null) {
				
				viewPackages.setCountries(DozerMapper.parseObjectList(pack.getCountries(), RestCountry.class));
			}else {
				viewPackages.setCountries(null);
			}
			
			viewPackages.setName(pack.getName());
			
			if(pack.getPackageCat() != null) {
				viewPackages.setPackageCat(DozerMapper.parseObject(pack.getPackageCat(), RestPackageCat.class));
			}
			
			if (pack.getDuration() != null) {
				
				viewPackages.setDuration(DozerMapper.parseObject(pack.getDuration(), RestDuration.class));
			}
			
			viewPackages.setPrice(pack.getPrice());
			viewPackages.setDate(pack.getDate());
			viewPackages.setPublicId(pack.getPublicId());
			viewPackages.setUpdateApproval(pack.getUpdateApproval());
			viewPackages.setUpdateDate(pack.getUpdateDate());
			viewPackages.setUpdateUser(userMapper.getRestUserPack(pack.getUpdateUser()));
			viewPackages.setUser(userMapper.getRestUserPack(pack.getUser()));
			viewPackages.setVideoUrl(pack.getVideoUrl());
			viewPackages.setModyfiyStatus(pack.getModydiyStatus());
			
			return viewPackages;
		}
		
		return null;
	}

		

}
