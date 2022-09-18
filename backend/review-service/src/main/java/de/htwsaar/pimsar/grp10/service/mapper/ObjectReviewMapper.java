package de.htwsaar.pimsar.grp10.service.mapper;

import de.htwsaar.pimsar.grp10.domain.ObjectReview;
import de.htwsaar.pimsar.grp10.service.dto.ObjectReviewDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for entity {@link ObjectReview} and its DTO {@link ObjectReviewDTO}
 */
@Mapper( componentModel = "jsr330" )
public interface ObjectReviewMapper extends EntityMapper<ObjectReviewDTO, ObjectReview>
{
}
