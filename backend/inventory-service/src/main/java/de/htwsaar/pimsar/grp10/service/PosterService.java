package de.htwsaar.pimsar.grp10.service;

import de.htwsaar.pimsar.grp10.service.dto.PosterDTO;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

import java.util.Optional;

/**
 * service interface for managing Posters
 */
public interface PosterService
{
   /**
    * Save a poster object
    *
    * @param p_DTO dto
    * @return save dto
    */
   PosterDTO save( PosterDTO p_DTO );

   /**
    * find all poster by criterias
    *
    * @param p_Pageable search criterias
    * @return filtered poster
    */
   Page<PosterDTO> findAll( Pageable p_Pageable );

   /**
    * find a poster
    *
    * @param p_Id id of poster
    * @return wanted poster or null
    */
   Optional<PosterDTO> findOne( Long p_Id );

   /**
    * delete a poster
    *
    * @param p_Id id of poster
    */
   void delete( Long p_Id );

   /**
    * update average rating of poster
    * @param p_Poster id of poster
    * @param p_Rating new rating ( to add )
    */
   void updateAverageRating( Long p_Poster, Double p_Rating);

   /**
    * update the average rating of poster
    * @param p_Poster id of poster
    * @param p_OldRating old rating ( to subtract )
    * @param p_NewRating new rating ( to add )
    */
   void updateAverageRating( Long p_Poster, Double p_OldRating, Double p_NewRating );
}
