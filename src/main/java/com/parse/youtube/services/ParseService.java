package com.parse.youtube.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parse.youtube.entity.YoutubeResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ParseService {

    public YoutubeResult getEntity(String videoId) {
        YoutubeResult yr = null;
        try {
            Document document = Jsoup
                    .connect(videoId.contains("https://www.youtube.com/watch?v=") ?
                            videoId :
                            "https://www.youtube.com/watch?v=" + videoId)
                    .execute().parse();
            Elements scripts = document.getElementsByTag("script");
            Element videoDetails = scripts.stream().filter(element -> element.html().contains("videoDetails")).findFirst().orElse(null);

            if (videoDetails != null) {
                ObjectMapper mapper = new ObjectMapper();
                String jsonFromString = getJsonFromString(videoDetails.html());
                String content = jsonFromString.substring((jsonFromString.indexOf("\"videoDetails\"") + "\"videoDetails\":".length()),
                        jsonFromString.indexOf("\"annotations\"") - 1);
                yr = mapper.readValue(content, YoutubeResult.class);
            }
        } catch (Exception e) {
            return null;
        }

        return yr;
    }

    public YoutubeResult getHeader(String videoId) {
        YoutubeResult yr;
        try {
            Document document = Jsoup
                    .connect(videoId.contains("https://www.youtube.com/watch?v=") ?
                            videoId :
                            "https://www.youtube.com/watch?v=" + videoId)
                    .execute().parse();
            Element title = document.getElementsByTag("title").first();
            String headerVideo = title.text().split("-")[0];
            yr = new YoutubeResult();
            yr.setTitle(headerVideo.trim());
        } catch (Exception e) {
            return null;
        }

        return yr;
    }

    private String getJsonFromString(String input) {
        int begin = input.indexOf("{", input.indexOf("{") + 1);
        int end = input.lastIndexOf("}", input.lastIndexOf("}") - 1);
        input = input.substring(begin, end);
        return input;
    }
}
