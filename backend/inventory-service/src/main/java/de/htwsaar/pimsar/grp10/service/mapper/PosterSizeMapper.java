package de.htwsaar.pimsar.grp10.service.mapper;

import de.htwsaar.pimsar.grp10.domain.PosterSize;
import de.htwsaar.pimsar.grp10.service.dto.PosterSizeDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link PosterSize}
 * and its DTO {@link PosterSizeDTO}.
 */
@Mapper( componentModel = "jsr330")
public interface PosterSizeMapper extends EntityMapper<PosterSizeDTO, PosterSize>
{
   /**
    * default from id
    *
    * @param p_Id poster id
    * @return poster if id not null
    */
   default PosterSize fromId( Long id )
   {
      if( id == null )
      {
         return null;
      }
      PosterSize petType = new PosterSize();
      petType.setId( id );
      return petType;
   }
}
