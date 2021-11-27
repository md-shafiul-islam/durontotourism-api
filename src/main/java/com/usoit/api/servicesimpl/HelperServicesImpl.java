package com.usoit.api.servicesimpl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.usoit.api.data.converter.DozerMapper;
import com.usoit.api.data.vo.RestAccessUser;
import com.usoit.api.model.Access;
import com.usoit.api.model.Role;
import com.usoit.api.model.User;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.RoleServices;
import com.usoit.api.services.UserServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HelperServicesImpl implements HelperServices {

	private int pageSize = 8;

	private final String STATIC_FILE_DIR = "src/main/resources/static";

	private final String UPLOAD_DIR = "/uimages/";

	@Autowired
	private UserServices userServices;

	private static Access cAccess;

	private boolean useLetters = true;

	private boolean useNumbers = true;

	private Access cByPrincipalUserAccess;

	@Autowired
	private RoleServices roleServices;

	@Override
	public String getRnadoUUID() {
		UUID uuid = UUID.randomUUID();
				
		return uuid.toString();
	}
	
	@Override
	public String getRandomString(int length) {
		return RandomStringUtils.random(length, useLetters, useNumbers);
	}
	
	@Override
	public String getDoubleToString(double doubleValue) {
		
		try {
			return Double.toString(doubleValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "0.0";
	}
	
	@Override
	public double decimelTwoPoint(double value) {

		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.CEILING);

		String decimelValue = df.format(value);

		return Double.parseDouble(decimelValue);
	}
	
	@Override
	public int getStringToInt(String numVal) {
		
		try {
			
			return Integer.parseInt(numVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@Override
	public double stringToDouble(String amount) {
		try {			
			return Double.parseDouble(amount);
		} catch (Exception e) {;
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public User checkUserAccess(HttpSession httpSession, String type, int numType) {
		return checkUserValid(httpSession, type, numType);
	}

	@Override
	public String getUploadDir() {
		return UPLOAD_DIR;
	}

	@Override
	public String getStaticDir() {
		return STATIC_FILE_DIR;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public Access getCurrentAccess() {
		return cAccess;
	}

	@Override
	public boolean isValidAndLenghtCheck(String publicId, int lenght) {

		if (publicId != null) {

			if (publicId.length() >= lenght) {

				return true;
			}
		}

		return false;
	}

	@Override
	public Map<String, RestAccessUser> getRestAccessByUser(User user) {
		return getUserAccessByUser(user);
	}

	@Override
	public User getUserByPrincipal(Principal principal) {

		if (principal == null) {

			return null;
		} else {

			if (principal.getName() != null) {
				return userServices.getUserByUsername(principal.getName());
			} else {
				return null;
			}
		}
	}

	@Override
	public Map<String, RestAccessUser> getAccessMapByPrincipal(Principal principal) {

		User user = getUserByPrincipal(principal);

		if (user != null) {

			return getRestAccessByUser(userServices.getUserRoleAccessByUserPublicID(user.getPublicId()));
		}
		return null;
	}
	
	

	private Map<String, RestAccessUser> getUserAccessByUser(User user) {

		if (user.getRole() != null) {

			if (user.getRole().getAccesses() != null) {

				List<RestAccessUser> accessUsers = DozerMapper.parseObjectList(user.getRole().getAccesses(),
						RestAccessUser.class);

				Map<String, RestAccessUser> accessMap = new HashMap<>();

				for (RestAccessUser access : accessUsers) {

					if (access != null) {

						if (access.getAccessType() != null) {

							accessMap.put(access.getAccessType().getValue(), access);
						}
					}
				}

				return accessMap;

			}
		}

		return null;
	}

	private User checkUserValid(HttpSession httpSession, String type, int numType) {
		User user = (User) httpSession.getAttribute("currentUser");

		log.info("Validation Check Start ...");

		if (user != null) {

			log.info("User Not null ...");
			int id = user.getId();

			user = null;

			user = userServices.getUerById(id);

			if (user != null) {

				if (user.getName() != null) {

					if (user.getName().isEmpty()) {
						return null;
					} else {

						if (user.getRole() != null) {

							log.info("User Role Not Null ...");

							if (user.getRole() != null) {

								log.info("Accesses Not Null ... ");

								Role role = roleServices.getRoleWitAccessById(user.getRole().getId());
								
								cAccess = getAuthUserAccesses(type, numType, role);								
								
							}
						}

						return user;
					}

				} else {
					return null;
				}

			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public Access getAccessByUser(User authUser, String type, int numType) {

		if (authUser != null) {

			if (authUser.getName() != null) {

				if (authUser.getName().isEmpty()) {
					return null;
				} else {

					if (authUser.getRole() != null) {

						log.info("User Role Not Null ...");

						if (authUser.getRole() != null) {

							Role role = roleServices.getRoleWitAccessById(authUser.getRole().getId());

							cByPrincipalUserAccess = getAuthUserAccesses(type, numType, role);
						}
					}

					return cByPrincipalUserAccess;
				}

			} else {
				return null;
			}

		}

		return null;
	}

	private Access getAuthUserAccesses(String type, int numType, Role role) {

		if (role != null) {
			for (Access item : role.getAccesses()) {

				log.info("Principal Access ...");

				if (item.getAccessType() != null) {

					String cType = item.getAccessType().getValue();
					String pStType = type;

					int pNValue = item.getAccessType().getNumValue();

					int paramNValue = numType;

					if (pStType.equals(cType) || pNValue == paramNValue) {

						if (item != null) {
//							log.info("Current Access Principal: Add " + item.getAdd() + " No Access Value: "
//									+ item.getNoAccess() + " All Access!! " + item.getAll());

							return item;
						}

					}
				}

			}
		}

		return null;

	}

	@Override
	public String uploadImageAndGetUrl(MultipartFile file, String path) {
		
		return uploadAndSaveImage(file, path);
	}

	private String uploadAndSaveImage(MultipartFile mFile, String path) {

		if (mFile != null) {
			
			log.info("File not null" + mFile.getName());
			
			if (!mFile.getOriginalFilename().isEmpty()) {

				try {

					String fileExtenstion = FilenameUtils.getExtension(mFile.getOriginalFilename());

					log.info("file Name: " + mFile.getName() + "Fiel Extenstion: " + fileExtenstion);

					String scPath = path;
					path = STATIC_FILE_DIR + UPLOAD_DIR + path;
					File makeFile = new File(path);

					if (!makeFile.exists()) {

						if (makeFile.mkdir()) {
							log.info("Make Directory Done !");
						} else {
							log.info("Make Directory Fail !");
						}

					}

					byte[] bytes = mFile.getBytes();

					long miliSc = new Date(System.currentTimeMillis()).getTime();

					String timeStamp = Long.toString(miliSc);

					log.info("time Stamp: " + timeStamp);

					String fileName = timeStamp + getRandomString(15) + "." + fileExtenstion;
					log.info("File Name: " + fileName);

					String name = path + "/" + fileName;

					log.info("Path: " + path + " File Name: " + fileName);
					log.info("Full Path: " + name);

					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name)));
					stream.write(bytes);
					stream.close();

					return UPLOAD_DIR + scPath + "/" + fileName;

				} catch (IOException e) {
					log.info("GET Helepr Services "+e.getMessage());
					e.printStackTrace();
					
				}
			}
		}else {
			log.info("File is null" + mFile);
		}

		return null;
	}
	
	@Override
	public String makeDirectory(String directoryLocs) {
		
		try {
			
		
			File file = new File(directoryLocs);
			FileUtils.forceMkdir(file);
			if(!file.exists()) {
				
				if(file.mkdir()) {
					log.info("Create Directory success");
				}else {
					log.info("Directory Creation Failed :)");
				}
			}else {
				log.info("Directory Exists");
			}
			log.info("Created File Abs Path "+file.getAbsolutePath());
			log.info("Created File Path "+file.getPath());
			
			return file.getPath();
		} catch (Exception e) {
			log.info("Directory Create Failed. Name "+directoryLocs+" "+e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public String storeFile(String dirPath, MultipartFile mFile) {
		
		try {
			
			String fSpr = File.separator;
			
			log.info("Current Dir Saperetor " + fSpr);
			
			byte[] bytes = mFile.getBytes();
			long miliSc = new Date(System.currentTimeMillis()).getTime();
			String timeStamp = Long.toString(miliSc);
			String fileExtenstion = FilenameUtils.getExtension(mFile.getOriginalFilename());
			String fileName = timeStamp + getRandomString(15) + "." + fileExtenstion;
			
			log.info("file Name: " + mFile.getName() + "Fiel Extenstion: " + fileExtenstion);			
			log.info("File Name: " + fileName);

			String name = dirPath + fSpr + fileName;
			log.info("Create Before File Path "+name);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name)));
			stream.write(bytes);;
			stream.close();
			return fileName;
		} catch (Exception e) {
			log.info("Added file failed "+e.getMessage());
			e.printStackTrace();
			
		}
		
		return null;
	}
}
