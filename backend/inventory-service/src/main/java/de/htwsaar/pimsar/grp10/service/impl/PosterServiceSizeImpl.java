package de.htwsaar.pimsar.grp10.service.impl;

import de.htwsaar.pimsar.grp10.domain.Poster;
import de.htwsaar.pimsar.grp10.domain.PosterSize;
import de.htwsaar.pimsar.grp10.repository.PosterSizeRepository;
import de.htwsaar.pimsar.grp10.service.PosterSizeService;
import de.htwsaar.pimsar.grp10.service.dto.PosterSizeDTO;
import de.htwsaar.pimsar.grp10.service.mapper.PosterSizeMapper;
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
public class PosterServiceSizeImpl implements PosterSizeService
{

   private final PosterSizeMapper _PosterSizeMapper;
   private final PosterSizeRepository _PosterSizeRepository;

   /**
    * constructor
    *
    * @param p_PosterMapper         poster mapper
    * @param p_PosterSizeRepository poster repo
    */
   public PosterServiceSizeImpl( final PosterSizeMapper p_PosterMapper, final PosterSizeRepository p_PosterSizeRepository )
   {
      _PosterSizeMapper = p_PosterMapper;
      _PosterSizeRepository = p_PosterSizeRepository;
   }

   @Override
   public PosterSizeDTO save( final PosterSizeDTO p_DTO )
   {
      log.debug( "Request to save Poster size: {}", p_DTO );
      PosterSize v_Poster = _PosterSizeMapper.toEntity( p_DTO );
      v_Poster = _PosterSizeRepository.mergeAndSave( v_Poster );
      return _PosterSizeMapper.toDto( v_Poster );
   }

   @Override
   @Transactional
   @ReadOnly
   public Page<PosterSizeDTO> findAll( final Pageable p_Pageable )
   {
      log.debug( "Request to find Poster" );
      return _PosterSizeRepository.findAll( p_Pageable ).map( _PosterSizeMapper::toDto );
   }

   @Override
   @Transactional
   @ReadOnly
   public Optional<PosterSizeDTO> findOne( final Long p_Id )
   {
      log.debug( "Request to find Poster: {}", p_Id );
      return _PosterSizeRepository.findById( p_Id ).map( _PosterSizeMapper::toDto );
   }

   @Override
   public void delete( final Long p_Id )
   {
      log.debug( "Request to delete Poster: {}", p_Id );
      final Optional<PosterSize> c_Optional = _PosterSizeRepository.findById( p_Id );
      c_Optional.ifPresent( p_Size ->
                            {
                               if( p_Size.getPoster().isEmpty() )
                               {
                                  _PosterSizeRepository.deleteById( p_Id );
                               }
                            } );
   }
}
