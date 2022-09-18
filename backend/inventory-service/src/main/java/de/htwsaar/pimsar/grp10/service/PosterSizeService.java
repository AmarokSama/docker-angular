package de.htwsaar.pimsar.grp10.service;

import de.htwsaar.pimsar.grp10.service.dto.PosterSizeDTO;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

import java.util.Optional;

/**
 * service interface for managing Posters sizes
 */
public interface PosterSizeService
{
   /**
    * Save a poster size object
    *
    * @param p_DTO dto
    * @return save dto
    */
   PosterSizeDTO save( PosterSizeDTO p_DTO );

   /**
    * find all poster size by criterias
    *
    * @param p_Pageable search criterias
    * @return filtered poster size
    */
   Page<PosterSizeDTO> findAll( Pageable p_Pageable );

   /**
    * find a poster size
    *
    * @param p_Id id of poster size
    * @return wanted poster size or null
    */
   Optional<PosterSizeDTO> findOne( Long p_Id );

   /**
    * delete a poster size
    *
    * @param p_Id id of poster size
    */
   void delete( Long p_Id );
}
