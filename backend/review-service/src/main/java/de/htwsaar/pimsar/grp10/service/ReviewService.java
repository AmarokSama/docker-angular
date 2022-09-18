package de.htwsaar.pimsar.grp10.service;

import de.htwsaar.pimsar.grp10.service.dto.ObjectReviewDTO;

import java.util.List;

public interface ReviewService
{
   /**
    * find all object reviews
    *
    * @return list of reviews
    */
   List<ObjectReviewDTO> findAll();

   /**
    * find a specific object review for a review id
    *
    * @param p_ReviewId review id
    * @return specific object review
    */
   ObjectReviewDTO findByReviewId( String p_ReviewId );

   /**
    * save a new / update object review
    *
    * @param p_Review review
    * @return specific object review
    */
   ObjectReviewDTO save( ObjectReviewDTO p_Review );

   /**
    * delete a specific object review
    *
    * @param p_ReviewId id of review to delete
    */
   void delete( String p_ReviewId );
}
