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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Controller("/api")
@Tag( name = "poster" )
@Slf4j
public class PosterResource
{
   /**
    * Name of entity
    */
   private static final String ENTITY_NAME = "poster";

   @Value( "${micronaut.application.name}" )
   private String _ApplicationName;

   private final PosterService _PosterService;

   public PosterResource( final PosterService p_PosterService )
   {
      _PosterService = p_PosterService;
   }

   /**
    * {@code GET  /posters} : get all the poster.
    *
    * @param p_Request  http rerquest
    * @param p_Pageable pageable
    * @return the {@link HttpResponse} with status {@code 200 (OK)} and the list of posters in body.
    */
   @Operation( summary = "get all posters", description = "get all posters saved in the given db" )
   @ApiResponse( responseCode = "200", content = @Content( mediaType = MediaType.APPLICATION_JSON,
      array = @ArraySchema( schema = @Schema( implementation = PosterDTO.class ) ) ), description = "list of posters" )
   @Get( "/posters" )
   public HttpResponse<List<PosterDTO>> getAllPosters( HttpRequest p_Request, Pageable p_Pageable )
   {
      log.debug( "REST request to get a page of Posters" );
      final Page<PosterDTO> c_Posters = _PosterService.findAll( p_Pageable);
      return HttpResponse.ok( c_Posters.getContent() );
   }

   /**
    * {@code GET  /poster/:id} : get the "id" poster.
    *
    * @param p_PosterId the id of the PosterDTO to retrieve.
    * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the PosterDTO, or with status {@code 404 (Not Found)}.
    */
   @Operation( summary = "get a poster", description = "get a specific poster by its id" )
   @ApiResponse( responseCode = "200", content = @Content( mediaType = MediaType.APPLICATION_JSON,
      schema = @Schema( implementation = PosterDTO.class ) ), description = "poster with given id" )
   @ApiResponse( responseCode = "400", description = "poster not found" )
   @Parameter( name = "posterId", description = "id of the wanted poster", required = true )
   @Get( "/poster/{posterId}" )
   @ExecuteOn( TaskExecutors.IO )
   public Optional<PosterDTO> getPoster( @PathVariable(name = "posterId") Long p_PosterId )
   {
      log.debug( "REST request to get Poster : {}", p_PosterId );
      return _PosterService.findOne( p_PosterId );
   }

   /**
    * Create a new poster
    *
    * @param p_PosterDTO new poster as dto
    * @return saved poster
    * @throws URISyntaxException if the Location URI syntax is incorrect.
    */
   @Operation( summary = "crate a poster", description = "create a specific poster" )
   @ApiResponse( responseCode = "201", description = "poster created",
      content = @Content( mediaType = MediaType.APPLICATION_JSON, schema = @Schema( description = "created poster object", implementation = PosterDTO.class ) ) )
   @ApiResponse( responseCode = "400", description = "poster already exist" )
   @ApiResponse( responseCode = "404", description = "poster cannot be created" )
   @RequestBody( required = true, description = "poster to create",
      content = @Content( schema = @Schema( description = "poster object to created", implementation = PosterDTO.class ) ) )
   @Post( "/poster" )
   @ExecuteOn( TaskExecutors.IO )
   public HttpResponse<PosterDTO> createPoster( @Body PosterDTO p_PosterDTO ) throws URISyntaxException
   {
      log.debug( "REST request to create poster : {}", p_PosterDTO );
      if( p_PosterDTO.getId() != null )
      {
         throw new BadRequestException( "A new Poster cannot already have an ID", ENTITY_NAME, "idexists" );
      }
      final PosterDTO c_Result = _PosterService.save( p_PosterDTO );
      final URI c_URI = new URI( "/api/poster/" + c_Result.getId() );
      return HttpResponse.created( c_Result ).headers( p_Headers ->
                                                       {
                                                          p_Headers.location( c_URI );
                                                       } );
   }

   /**
    * {@code PUT  /poster} : Updates an existing Poster.
    *
    * @param p_PosterDTO the PosterDTO to update.
    * @return the {@link HttpResponse} with status {@code 200 (OK)} and with body the updated PosterDTO,
    * or with status {@code 400 (Bad Request)} if the PosterDTO is not valid,
    * or with status {@code 500 (Internal Server Error)} if the PosterDTO couldn't be updated.
    * @throws URISyntaxException if the Location URI syntax is incorrect.
    */
   @Operation( summary = "update a poster", description = "update a specific poster" )
   @ApiResponse( responseCode = "200", description = "poster updated",
      content = @Content( mediaType = MediaType.APPLICATION_JSON, schema = @Schema( description = "updated poster object", implementation = PosterDTO.class ) ) )
   @ApiResponse( responseCode = "400", description = "poster not found" )
   @ApiResponse( responseCode = "404", description = "poster cannot be update" )
   @RequestBody( required = true, description = "poster to update",
      content = @Content( mediaType = MediaType.APPLICATION_JSON, schema = @Schema( implementation = PosterDTO.class ) ) )
   @Put( "/poster" )
   @ExecuteOn( TaskExecutors.IO )
   public HttpResponse<PosterDTO> updatePoster( @Body PosterDTO p_PosterDTO ) throws URISyntaxException
   {
      log.debug( "REST request to update poster : {}", p_PosterDTO );
      if( p_PosterDTO.getId() == null )
      {
         throw new BadRequestException( "Invalid id", ENTITY_NAME, "idnull" );
      }
      PosterDTO result = _PosterService.save( p_PosterDTO );
      return HttpResponse.ok( result );
   }


   /**
    * {@code DELETE  /vet-posters/:id} : delete the "id" Poster.
    *
    * @param p_PosterId the id of the PosterDTO to delete.
    * @return the {@link HttpResponse} with status {@code 204 (NO_CONTENT)}.
    */
   @Operation( summary = "delete a poster", description = "delete a specific poster by its id" )
   @ApiResponse( responseCode = "204", description = "poster deleted" )
   @ApiResponse( responseCode = "400", description = "poster not found" )
   @ApiResponse( responseCode = "404", description = "poster cannot be deleted" )
   @Parameter( name = "posterId", description = "id of the wanted poster", required = true )
   @Delete( "/poster/{posterId}" )
   @ExecuteOn( TaskExecutors.IO )
   public HttpResponse deletePoster( @PathVariable(name = "posterId") Long p_PosterId )
   {
      log.debug( "REST request to delete poster : {}", p_PosterId );
      _PosterService.delete( p_PosterId );
      return HttpResponse.notFound();
   }


}
