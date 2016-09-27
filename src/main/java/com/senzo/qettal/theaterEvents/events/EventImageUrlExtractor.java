package com.senzo.qettal.theaterEvents.events;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventImageUrlExtractor {

	@Value("${aws.s3.url}")
	private String amazonS3Url;
	@Value("${aws.s3.bucket.events}")
	private String amazonS3EventsBucket;
	
	public String extractFrom(Event event) {
		return String.format("http://%s.%s/%s", amazonS3EventsBucket, amazonS3Url, event.getImage());
	}

}
