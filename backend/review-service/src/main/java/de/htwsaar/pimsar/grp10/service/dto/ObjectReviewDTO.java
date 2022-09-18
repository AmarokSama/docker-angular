package de.htwsaar.pimsar.grp10.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO class for Object Review
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Schema( name = "Poster review", description = "a object review object" )
@Introspected
public class ObjectReviewDTO implements Serializable
{
   @Schema(description = "id of review")
   private String reviewId;
   @Schema( description = "id of object" )
   private Long objectId;
   @Schema( description = "rating of poster" )
   private Double rating;
   @Schema( description = "review comment" )
   private String comment;
   @Schema( description = "date when review was added" )
   @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
   private LocalDateTime dateAdded;

}
