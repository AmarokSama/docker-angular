package de.htwsaar.pimsar.grp10.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.LocalDateTime;

/**
 * Entity for object review
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ObjectReview
{

   private String reviewId;
   private Long objectId;
   private Double rating;
   private String comment;
   private LocalDateTime dateAdded;


   /**
    * constructor for mongo db document
    *
    * @param p_ReviewId  review id
    * @param p_ObjectId  object id
    * @param p_Rating    ration
    * @param p_Comment   user comment
    * @param p_DateAdded date of review creation
    */
   @BsonCreator
   @JsonCreator
   public ObjectReview( @BsonProperty( "reviewId" ) @JsonProperty( "reviewId" ) final String p_ReviewId,
                        @BsonProperty( "objectId" ) @JsonProperty( "objectId" ) final Long p_ObjectId,
                        @BsonProperty( "rating" ) @JsonProperty( "rating" ) final Double p_Rating,
                        @BsonProperty( "comment" ) @JsonProperty( "comment" ) final String p_Comment,
                        @BsonProperty( "dateAdded" ) @JsonProperty( "dateAdded" ) final LocalDateTime p_DateAdded )
   {
      reviewId = p_ReviewId;
      objectId = p_ObjectId;
      rating = p_Rating;
      comment = p_Comment;
      dateAdded = p_DateAdded;
   }
}
