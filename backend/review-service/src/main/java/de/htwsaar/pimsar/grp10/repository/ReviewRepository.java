package de.htwsaar.pimsar.grp10.repository;

import de.htwsaar.pimsar.grp10.domain.ObjectReview;

import java.util.List;

/**
 * repository for DB CRUD of a object review
 */
public interface ReviewRepository
{
   /**
    * find all post reviews
    *
    * @return list of reviews
    */
   List<ObjectReview> findAll();

   /**
    * find a specific object review for a review id
    *
    * @param p_ReviewId review id
    * @return specific object review
    */
   ObjectReview findByReviewId( String p_ReviewId );

   /**
    * save a new / update object review
    *
    * @param p_Review review
    * @return specific object review
    */
   ObjectReview save( ObjectReview p_Review );

   /**
    * delete a specific object review
    *
    * @param p_ReviewId id of review to delete
    */
   void delete( String p_ReviewId );
}
