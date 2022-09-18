package de.htwsaar.pimsar.grp10.web.rest;

import de.htwsaar.pimsar.grp10.service.PosterService;
import de.htwsaar.pimsar.grp10.service.dto.PosterDTO;
import de.htwsaar.pimsar.grp10.web.rest.error.BadRequestException;
import io.micronaut.context.annotation.Value;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Controller("/api")
@Tag( name = "poster-size" )
@Slf4j
public class PosterSizeResource
{
   /**
    * Name of entity
    */
   private static final String ENTITY_NAME = "posterSize";

   @Value( "${micronaut.application.name}" )
   private String _ApplicationName;

   private final PosterService _PosterService;

   public PosterSizeResource( final PosterService p_PosterService )
   {
      _PosterService = p_PosterService;
   }

   /**
    * {@code GET  /poster-sizes} : get all the sizes.
    *
    * @param p_Request  http rerquest
    * @param p_Pageable pageable
    * @return the {@link HttpResponse} with status {@code 200 (OK)} and the list of sizes in body.
    */
   @Operation( summary = "get all poster sizes", description = "get all poster sizes saved in the given db" )
   @ApiResponse( responseCode = "200", content = @Content( mediaType = MediaType.APPLICATION_JSON,
      array = @ArraySchema( schema = @Schema( implementation = PosterDTO.class ) ) ), description = "list of poster sizes" )
   @Get( "/poster-sizes" )
   public HttpResponse<List<PosterDTO>> getAllPosterSizes( HttpRequest p_Request, Pageable p_Pageable )
   {
      log.debug( "REST request to get a page of Posters" );
      final Page<PosterDTO> c_Posters = _PosterService.findAll( p_Pageable);
      return HttpResponse.ok( c_Posters.getContent() );
   }

   /**
    * {@code GET  /poster-size/:id} : get the "id" poster.
    *
    * @param p_sizeId the id of the PosterDTO to retrieve.
    * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the PosterDTO, or with status {@code 404 (Not Found)}.
    */
   @Operation( summary = "get a poster size", description = "get a specific poster size by its id" )
   @ApiResponse( responseCode = "200", content = @Content( mediaType = MediaType.APPLICATION_JSON,
      schema = @Schema( implementation = PosterDTO.class ) ), description = "size with given id" )
   @ApiResponse( responseCode = "400", description = "size not found" )
   @Parameter( name = "sizeId", description = "id of the wanted size", required = true )
   @Get( "/poster-size/{sizeId}" )
   @ExecuteOn( TaskExecutors.IO )
   public Optional<PosterDTO> getPoster( @PathVariable(name = "sizeId") Long p_sizeId )
   {
      log.debug( "REST request to get Poster : {}", p_sizeId );
      return _PosterService.findOne( p_sizeId );
   }

   /**
    * Create a new poster
    *
    * @param p_PosterDTO new poster as dto
    * @return saved poster
    * @throws URISyntaxException if the Location URI syntax is incorrect.
    */
   @Operation( summary = "crate a poster size", description = "create a specific poster size" )
   @ApiResponse( responseCode = "201", description = "size created",
      content = @Content( mediaType = MediaType.APPLICATION_JSON, schema = @Schema( description = "created size object", implementation = PosterDTO.class ) ) )
   @ApiResponse( responseCode = "400", description = "size already exist" )
   @ApiResponse( responseCode = "404", description = "size cannot be created" )
   @RequestBody( required = true, description = "size to create",
      content = @Content( schema = @Schema( description = "size object to created", implementation = PosterDTO.class ) ) )
   @Post( "/poster-size" )
   @ExecuteOn( TaskExecutors.IO )
   public HttpResponse<PosterDTO> createPoster( @Body PosterDTO p_PosterDTO ) throws URISyntaxException
   {
      log.debug( "REST request to create size : {}", p_PosterDTO );
      if( p_PosterDTO.getId() != null )
      {
         throw new BadRequestException( "A new size cannot already have an ID", ENTITY_NAME, "idexists" );
      }
      final PosterDTO c_Result = _PosterService.save( p_PosterDTO );
      final URI c_URI = new URI( "/api/poster-size/" + c_Result.getId() );
      return HttpResponse.created( c_Result ).headers( p_Headers ->
                                                       {
                                                          p_Headers.location( c_URI );
                                                       } );
   }



   /**
    * {@code DELETE  /poster-size/:id} : delete the "id" Poster.
    *
    * @param p_sizeId the id of the PosterDTO to delete.
    * @return the {@link HttpResponse} with status {@code 204 (NO_CONTENT)}.
    */
   @Operation( summary = "delete a poster size", description = "delete a specific size by its id" )
   @ApiResponse( responseCode = "204", description = "size deleted" )
   @ApiResponse( responseCode = "400", description = "size not found" )
   @ApiResponse( responseCode = "404", description = "size cannot be deleted" )
   @Parameter( name = "sizeId", description = "id of the wanted size", required = true )
   @Delete( "/poster-size/{sizeId}" )
   @ExecuteOn( TaskExecutors.IO )
   public HttpResponse deletePoster( @PathVariable(name = "sizeId") Long p_sizeId )
   {
      log.debug( "REST request to delete size : {}", p_sizeId );
      _PosterService.delete( p_sizeId );
      return HttpResponse.notFound();
   }


}
