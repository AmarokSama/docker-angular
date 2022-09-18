package de.htwsaar.pimsar.grp10.service.mapper;

import de.htwsaar.pimsar.grp10.domain.Poster;
import de.htwsaar.pimsar.grp10.service.dto.PosterDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Poster}
 * and its DTO {@link PosterDTO}.
 *
 * @author ngrus
 */
@Mapper( componentModel = "jsr330", uses = { PosterSizeMapper.class } )
public interface PosterMapper extends EntityMapper<PosterDTO, Poster>
{
   /**
    * default from id
    *
    * @param p_Id poster id
    * @return poster if id not null
    */
   default Poster fromId( Long p_Id )
   {
      if( p_Id == null )
      {
         return null;
      }
      Poster v_Poster = new Poster();
      v_Poster.setId( p_Id );
      return v_Poster;
   }
}
