package de.htwsaar.pimsar.grp10.service.impl;

import de.htwsaar.pimsar.grp10.domain.Poster;
import de.htwsaar.pimsar.grp10.repository.PosterRepository;
import de.htwsaar.pimsar.grp10.service.PosterService;
import de.htwsaar.pimsar.grp10.service.dto.PosterDTO;
import de.htwsaar.pimsar.grp10.service.mapper.PosterMapper;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.transaction.annotation.ReadOnly;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import javax.inject.Singleton;
import javax.transaction.Transactional;

/**
 * Service implementation for managing {@link Poster}
 */
@Singleton
@Transactional
@Slf4j
public class PosterServiceImpl implements PosterService
{

   private final PosterMapper _PosterMapper;
   private final PosterRepository _PosterRepository;

   /**
    * constructor
    *
    * @param p_PosterMapper     poster mapper
    * @param p_PosterRepository poster repo
    * @param p_posterMapper
    * @param p_posterRepository
    */
   public PosterServiceImpl( final PosterMapper p_posterMapper, final PosterRepository p_posterRepository )
   {
      _PosterMapper = p_posterMapper;
      _PosterRepository = p_posterRepository;
   }

   @Override
   public PosterDTO save( final PosterDTO p_DTO )
   {
      log.debug( "Request to save Poster: {}", p_DTO );
      Poster v_Poster = _PosterMapper.toEntity( p_DTO );
      v_Poster = _PosterRepository.mergeAndSave( v_Poster );
      return _PosterMapper.toDto( v_Poster );
   }

   @Override
   @Transactional
   @ReadOnly
   public Page<PosterDTO> findAll( final Pageable p_Pageable )
   {
      log.debug( "Request to find Poster" );
      return _PosterRepository.findAll( p_Pageable ).map( _PosterMapper::toDto );
   }

   @Override
   @Transactional
   @ReadOnly
   public Optional<PosterDTO> findOne( final Long p_Id )
   {
      log.debug( "Request to find Poster: {}", p_Id );
      return _PosterRepository.findById( p_Id ).map( _PosterMapper::toDto );
   }

   @Override
   public void delete( final Long p_Id )
   {
      log.debug( "Request to delete Poster: {}", p_Id );
      _PosterRepository.deleteById( p_Id );
   }

   @Override
   public void updateAverageRating( final Long p_Poster, final Double p_Rating )
   {
      log.debug( "Request to update poster rating, id: {}, rating: {}", p_Poster, p_Rating );
      final Optional<PosterDTO> v_Poster = findOne( p_Poster );
      if( v_Poster.isPresent() )
      {
         PosterDTO v_Dto = v_Poster.get();
         double averageRating = v_Dto.getAverageRating() != null ? v_Dto.getAverageRating() : 0D;
         int ratingCount = v_Dto.getRatingCount() != null ? v_Dto.getRatingCount() : 0;
         final Double c_NewAvgRating = ( ( averageRating * ratingCount ) + p_Rating ) / ( ratingCount + 1 );
         final int c_NewRatingCount = ratingCount + 1;
         _PosterRepository.updateAvrAndCount( p_Poster, c_NewAvgRating, c_NewRatingCount );
      }
   }

   @Override
   public void updateAverageRating( final Long p_Poster, final Double p_OldRating, final Double p_NewRating )
   {
      log.debug( "Request to change an value from the poster rating, id: {}, rating: {} => {}", p_Poster, p_OldRating, p_NewRating );
      final Optional<PosterDTO> v_Poster = findOne( p_Poster );
      if( v_Poster.isPresent() )
      {
         PosterDTO v_Dto = v_Poster.get();
         final Double c_NewAvgRating = ( ( v_Dto.getAverageRating() * v_Dto.getRatingCount() ) - p_OldRating + p_NewRating ) / v_Dto.getRatingCount();

         _PosterRepository.updateAvr( p_Poster, c_NewAvgRating );
      }
   }
}
