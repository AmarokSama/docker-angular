package de.htwsaar.pimsar.grp10.service.impl;

import de.htwsaar.pimsar.grp10.domain.ObjectReview;
import de.htwsaar.pimsar.grp10.integration.dto.ChangedRatingDTO;
import de.htwsaar.pimsar.grp10.integration.event.ObjectReviewEvent;
import de.htwsaar.pimsar.grp10.repository.ReviewRepository;
import de.htwsaar.pimsar.grp10.service.ReviewService;
import de.htwsaar.pimsar.grp10.service.dto.ObjectReviewDTO;
import de.htwsaar.pimsar.grp10.service.mapper.ObjectReviewMapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.inject.Singleton;

/**
 * Implementation for {@link ReviewService}
 */
@Singleton
public class ReviewServiceImpl implements ReviewService
{
   private final ReviewRepository _RR;
   private final ObjectReviewMapper _ORM;
   private final ObjectReviewEvent _ORE;


   /**
    * constructor
    *
    * @param p_RR {@link ReviewRepository}
    * @param p_ORM {@link ObjectReviewMapper}
    * @param p_ORE {@link ObjectReviewEvent}
    */
   public ReviewServiceImpl( final ReviewRepository p_RR, final ObjectReviewMapper p_ORM, final ObjectReviewEvent p_ORE )
   {
      _RR = p_RR;
      _ORM = p_ORM;
      _ORE = p_ORE;
   }

   @Override
   public List<ObjectReviewDTO> findAll()
   {
      return _RR.findAll().stream().map( _ORM::toDto ).collect( Collectors.toList() );
   }

   @Override
   public ObjectReviewDTO findByReviewId( final String p_ReviewId )
   {
      return _ORM.toDto( _RR.findByReviewId( p_ReviewId ) );
   }

   @Override
   public ObjectReviewDTO save( final ObjectReviewDTO p_Review )
   {
      if( p_Review.getReviewId() == null )
      {
         return createReview( p_Review );
      }
      else
      {
         return updateReview( p_Review );
      }
   }


   private ObjectReviewDTO createReview( final ObjectReviewDTO p_Review )
   {
      p_Review.setReviewId( UUID.randomUUID().toString() );
      _ORE.sendCreate( p_Review );
      ObjectReview v_ReviewEntity = _ORM.toEntity( p_Review );
      return _ORM.toDto( _RR.save( v_ReviewEntity ) );
   }

   private ObjectReviewDTO updateReview( final ObjectReviewDTO p_Review )
   {

      ObjectReviewDTO v_Review = findByReviewId( p_Review.getReviewId() );
      final ChangedRatingDTO c_RatingChange = new ChangedRatingDTO( v_Review.getObjectId(), v_Review.getRating(), p_Review.getRating() );
      v_Review.setComment( p_Review.getComment() );
      v_Review.setDateAdded( p_Review.getDateAdded() );
      v_Review.setRating( p_Review.getRating() );
      _ORE.sendUpdate( c_RatingChange );
      ObjectReview v_ReviewEntity = _ORM.toEntity( p_Review );
      return _ORM.toDto( _RR.save( v_ReviewEntity ) );
   }


   @Override
   public void delete( final String p_ReviewId )
   {
      _RR.delete( p_ReviewId );
   }
}
