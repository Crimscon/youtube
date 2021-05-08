package com.parse.youtube.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class YoutubeResult {
    private String videoId;
    private String title;
    private String lengthSeconds;
    private List<String> keywords;
    private String channelId;
    private Boolean isOwnerViewing;
    private String shortDescription;
    private Boolean isCrawlable;
    private Double averageRating;
    private Boolean allowRatings;
    private String viewCount;
    private String author;
    private Boolean isPrivate;
    private Boolean isUnpluggedCorpus;
    private Boolean isLiveContent;
}
