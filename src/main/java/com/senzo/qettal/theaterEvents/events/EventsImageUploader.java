package com.senzo.qettal.theaterEvents.events;

import java.io.ByteArrayInputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component
public class EventsImageUploader {

	@Autowired
	private AmazonS3 s3;

	@Value("${aws.s3.bucket.events}")
	private String eventsS3Bucket;
	
	public ImageUploadResultDTO upload(MultipartFile file) {
		try {
			String imageName = UUID.randomUUID() +".jpg";
			ObjectMetadata meta = new ObjectMetadata();
			byte[] buffer = file.getBytes();
			meta.setContentLength(buffer.length);
			meta.setContentType("image/jpg");
			ByteArrayInputStream is = new ByteArrayInputStream(buffer);
			PutObjectRequest request = new PutObjectRequest(eventsS3Bucket, imageName, is, meta);
			AccessControlList acl = new AccessControlList();
			acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
			request.setAccessControlList(acl);
			s3.putObject(request);
			return new ImageUploadResultDTO(imageName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
