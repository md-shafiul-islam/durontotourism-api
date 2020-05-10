package com.usoit.api.servicesimpl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.usoit.api.data.model.Access;
import com.usoit.api.data.model.User;
import com.usoit.api.services.HelperServices;
import com.usoit.api.services.UserServices;

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
	
	@Override
	public String getRandomString(int length) {
		return RandomStringUtils.random(length, useLetters, useNumbers);
	}

	@Override
	public double decimelTwoPoint(double value) {

		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.CEILING);

		String decimelValue = df.format(value);

		return Double.parseDouble(decimelValue);
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

	private User checkUserValid(HttpSession httpSession, String type, int numType) {
		User user = (User) httpSession.getAttribute("currentUser");

		System.out.println("Validation Check Start ...");

		if (user != null) {

			System.out.println("User Not null ...");
			int id = user.getId();

			user = null;

			user = userServices.getUerById(id);

			if (user != null) {

				if (user.getName() != null) {

					if (user.getName().isEmpty()) {
						return null;
					} else {

						if (user.getRole() != null) {

							System.out.println("User Role Not Null ...");

							if (user.getRole().getAccesses() != null) {

								System.out.println("Accesses Not Null ... ");

								for (Access item : user.getRole().getAccesses()) {

									System.out.println("Access ...");

									if (item.getAccessType() != null) {
										System.out.println("Access Name: " + item.getAccessType().getName() + " Alies: "
												+ item.getAccessType().getValue() + " Num Vale: "
												+ item.getAccessType().getNumValue());

										System.out.println(
												"Type: " + type + " CItem Type: " + item.getAccessType().getName()
														+ " Alies: " + item.getAccessType().getValue());

										String cType = item.getAccessType().getValue();
										String pStType = type;

										int pNValue = item.getAccessType().getNumValue();

										int paramNValue = numType;
										System.out.println("p n Value: " + numType + " DB N type: " + pNValue);

										if (pStType.equals(cType) || pNValue == paramNValue) {

											System.out.println("Selected Access: " + item.getName() + " No Access: "
													+ item.getNoAccess());
											//Set Access To Return
											cAccess = item;

											System.out.println("Current Access: Add " + cAccess.getAdd()
													+ " No Access Value: " + cAccess.getNoAccess());
										}
									}

								}
							}
						}

						return user;
					}

				} else {
					return null;
				}

			}else {
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

							System.out.println("User Role Not Null ...");

							if (authUser.getRole().getAccesses() != null) {

								System.out.println("PrincipalAccesses Not Null ... ");

								for (Access item : authUser.getRole().getAccesses()) {

									System.out.println("Principal Access ...");

									if (item.getAccessType() != null) {
										

										String cType = item.getAccessType().getValue();
										String pStType = type;

										int pNValue = item.getAccessType().getNumValue();

										int paramNValue = numType;
										System.out.println("Principal p n Value: " + numType + " DB N type: " + pNValue);

										if (pStType.equals(cType) || pNValue == paramNValue) {

											System.out.println("Principal Selected Access: " + item.getName() + " No Access: "
													+ item.getNoAccess());
											
											cByPrincipalUserAccess = item;

											if (cByPrincipalUserAccess != null) {
												System.out.println("Current Access Principal: Add " + cByPrincipalUserAccess.getAdd()
												+ " No Access Value: " + cByPrincipalUserAccess.getNoAccess() + " All Access!! " + cByPrincipalUserAccess.getAll());
											}
										}
									}

								}
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

	@Override
	public String uploadImageAndGetUrl(MultipartFile file, String path) {
		return uploadAndSaveImage(file, path);
	}

	private String uploadAndSaveImage(MultipartFile mFile, String path) {

		if (mFile != null) {

			if (!mFile.getOriginalFilename().isEmpty()) {

				try {

					String fileExtenstion = FilenameUtils.getExtension(mFile.getOriginalFilename());

					System.out.println("file Name: " + mFile.getName() + "Fiel Extenstion: " + fileExtenstion);

					String scPath = path;
					path = STATIC_FILE_DIR + UPLOAD_DIR + path;
					File makeFile = new File(path);

					if (!makeFile.exists()) {

						if (makeFile.mkdir()) {
							System.out.println("Make Directory Done !");
						} else {
							System.out.println("Make Directory Fail !");
						}

					}

					byte[] bytes = mFile.getBytes();

					long miliSc = new Date(System.currentTimeMillis()).getTime();

					String timeStamp = Long.toString(miliSc);

					System.out.println("time Stamp: " + timeStamp);

					String fileName = timeStamp + getRandomString(15)+ "." + fileExtenstion;
					System.out.println("File Name: " + fileName);

					String name = path+"//" + fileName;
					
					System.out.println("Path: "+ path + " File Name: " + fileName);
					System.out.println("Full Path: " + name);
					
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name)));
					stream.write(bytes);
					stream.close();

					return UPLOAD_DIR + scPath + "/" + fileName;

				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}

		return null;
	}
}
