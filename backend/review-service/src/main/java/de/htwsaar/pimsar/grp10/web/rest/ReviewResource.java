package de.htwsaar.pimsar.grp10.web.rest;

import de.htwsaar.pimsar.grp10.service.ReviewService;
import de.htwsaar.pimsar.grp10.service.dto.ObjectReviewDTO;
import de.htwsaar.pimsar.grp10.web.rest.error.BadRequestException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link de.htwsaar.pimsar.grp10.service.dto.ObjectReviewDTO}
 */
@Controller( value = "/api", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML },
   consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML }
)
@Slf4j
public class ReviewResource
{
   /**
    * ENTITY NAME
    */
   private static final String ENTITY_NAME = "ObjectReview";

   private final ReviewService _PRS;

   /**
    * constructor
    * @param p_PRS service
    */
   public ReviewResource( final ReviewService p_PRS )
   {
      _PRS = p_PRS;
   }

   /**
    * {@code GET  /object-reviews} : get all the object Reviews.
    *
    * @param p_HttpRequest http request
    * @return the {@link HttpResponse} with status {@code 200 (OK)} and the list of object Reviews in body.
    */
   @Operation( summary = "get all object reviews", description = "get all object reviews saved in the given db" )
   @ApiResponse( responseCode = "200", content = @Content( mediaType = MediaType.APPLICATION_JSON,
      array = @ArraySchema( schema = @Schema( implementation = ObjectReviewDTO.class ) ) ), description = "list of object reviews" )
   @Get( "/object-review" )
   public HttpResponse<List<ObjectReviewDTO>> getAllObjectReviews( HttpRequest p_HttpRequest )
   {
      log.debug( "REST request to get a page of ObjectReviews" );
      final List<ObjectReviewDTO> c_Reviews = _PRS.findAll();
      return HttpResponse.ok( c_Reviews );
   }

   /**
    * {@code GET  /object-review/:id} : get the "id" object Review.
    *
    * @param p_ReviewId the id of the ObjectReviewDTO to retrieve.
    * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the ObjectReviewDTO, or with status {@code 404 (Not Found)}.
    */
   @Operation( summary = "get a object review", description = "get a specific object review by its id" )
   @ApiResponse( responseCode = "200", content = @Content( mediaType = MediaType.APPLICATION_JSON,
      schema = @Schema( implementation = ObjectReviewDTO.class ) ), description = "review with given id" )
   @ApiResponse( responseCode = "400", description = "review not found" )
   @Parameter( name = "reviewId", description = "id of the wanted review", required = true )
   @Get( "/object-review/{reviewId}" )
   @ExecuteOn( TaskExecutors.IO )
   public Optional<ObjectReviewDTO> getObjectReview( @PathVariable( name = "reviewId" ) String p_ReviewId )
   {
      log.debug( "REST request to get ObjectReview : {}", p_ReviewId );
      return Optional.ofNullable( _PRS.findByReviewId( p_ReviewId ) );
   }

   /**
    * Create a new object review
    *
    * @param p_ReviewDTO new object review as dto
    * @return saved review
    * @throws URISyntaxException if the Location URI syntax is incorrect.
    */
   @Operation( summary = "crate a object review", description = "create a specific object review" )
   @ApiResponse( responseCode = "201", description = "review created",
      content = @Content( mediaType = MediaType.APPLICATION_JSON, schema = @Schema( description = "created review object", implementation = ObjectReviewDTO.class ) ) )
   @ApiResponse( responseCode = "400", description = "review already exist" )
   @ApiResponse( responseCode = "404", description = "review cannot be created" )
   @RequestBody( required = true, description = "review to create",
      content = @Content( schema = @Schema( description = "review object to created", implementation = ObjectReviewDTO.class ) ) )
   @Post( "/object-review" )
   @ExecuteOn( TaskExecutors.IO )
   public HttpResponse<ObjectReviewDTO> createObjectReview( @Body ObjectReviewDTO p_ReviewDTO ) throws URISyntaxException
   {
      log.debug( "REST request to create Object Review : {}", p_ReviewDTO );
      if( p_ReviewDTO.getReviewId() != null )
      {
         throw new BadRequestException( "A new ObjectReview cannot already have an ID", ENTITY_NAME, "idexists" );
      }
      final ObjectReviewDTO c_Result = _PRS.save( p_ReviewDTO );
      final URI c_URI = new URI( "/api/object-review/" + c_Result.getReviewId() );
      return HttpResponse.created( c_Result ).headers( p_Headers ->
                                                       {
                                                          p_Headers.location( c_URI );
                                                       } );
   }

   /**
    * {@code PUT  /object-review} : Updates an existing objectReview.
    *
    * @param p_ObjectReviewDTO the ObjectReviewDTO to update.
    * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the updated ObjectReviewDTO,
    * or with status {@code 400 (Bad Request)} if the ObjectReviewDTO is not valid,
    * or with status {@code 500 (Internal Server Error)} if the ObjectReviewDTO couldn't be updated.
    * @throws URISyntaxException if the Location URI syntax is incorrect.
    */
   @Operation( summary = "update a object review", description = "update a specific object review" )
   @ApiResponse( responseCode = "200", description = "review updated",
      content = {@Content( mediaType = MediaType.APPLICATION_JSON, schema = @Schema( description = "updated review object", implementation =
         ObjectReviewDTO.class ) ),
                 @Content( mediaType = MediaType.APPLICATION_XML, schema = @Schema( description = "updated review object", implementation =
                    ObjectReviewDTO.class ) )} )
   @ApiResponse( responseCode = "400", description = "review not found" )
   @ApiResponse( responseCode = "404", description = "review cannot be update" )
   @RequestBody( required = true, description = "review to update",
      content = { @Content( mediaType = MediaType.APPLICATION_JSON, schema = @Schema( implementation = ObjectReviewDTO.class ) ),
                  @Content( mediaType = MediaType.APPLICATION_XML, schema = @Schema( implementation = ObjectReviewDTO.class ) )} )
   @Put( "/object-review" )
   @ExecuteOn( TaskExecutors.IO )
   public HttpResponse<ObjectReviewDTO> updateObjectReview( @Body ObjectReviewDTO p_ObjectReviewDTO ) throws URISyntaxException
   {
      log.debug( "REST request to update ObjectReview : {}", p_ObjectReviewDTO );
      if( p_ObjectReviewDTO.getReviewId() == null )
      {
         throw new BadRequestException( "Invalid id", ENTITY_NAME, "idnull" );
      }
      ObjectReviewDTO result = _PRS.save( p_ObjectReviewDTO );
      return HttpResponse.ok( result );
   }


   /**
    * {@code DELETE  /Object-reviews/:id} : delete the "id" objectReview.
    *
    * @param p_ReviewId the id of the ObjectReviewDTO to delete.
    * @return the {@link HttpResponse} with status {@code 204 (NO_CONTENT)}.
    */
   @Operation( summary = "delete a object review", description = "delete a specific object review by its id" )
   @ApiResponse( responseCode = "204", description = "review deleted" )
   @ApiResponse( responseCode = "404", description = "review cannot be deleted" )
   @Parameter( name = "reviewId", description = "id of the wanted review", required = true )
   @Delete( "/object-review/{reviewId}" )
   @ExecuteOn( TaskExecutors.IO )
   public HttpResponse deleteObjectReview( @PathVariable( name = "reviewId" ) String p_ReviewId )
   {
      log.debug( "REST request to delete ObjectReview : {}", p_ReviewId );
      _PRS.delete( p_ReviewId );
      return HttpResponse.noContent();
   }
}
