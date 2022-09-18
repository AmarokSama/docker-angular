package de.htwsaar.pimsar.grp10.web.rest;

import de.htwsaar.pimsar.grp10.service.InventoryService;
import de.htwsaar.pimsar.grp10.service.dto.InventoryDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Controller( "/api" )
@Tag( name = "inventory" )
@Slf4j
public class InventoryResource
{
   /**
    * Name of entity
    */
   private static final String ENTITY_NAME = "inventory";

   private final InventoryService _InventoryService;


   /**
    * constructor
    *
    * @param p_InventoryService inventory service
    */
   public InventoryResource( final InventoryService p_InventoryService )
   {
      _InventoryService = p_InventoryService;
   }

   /**
    * get an inventory item
    *
    * @param p_Id id
    * @return inventory of object
    */
   @Operation( summary = "get an inventory", description = "get an inventory update for a poster" )
   @Parameter( name = "id", description = "id of poster", required = true )
   @ApiResponse( responseCode = "200", content = @Content( mediaType = MediaType.APPLICATION_JSON,
      schema = @Schema( implementation = InventoryDTO.class ) ),
      description = "inventory entry"
   )
   @Get( "/inventory/{id}" )
   public HttpResponse<InventoryDTO> findInventory( @PathVariable( name = "id" ) final Long p_Id )
   {
      log.debug( "REST Request to get an inventory item: {}", p_Id );
      return HttpResponse.ok( _InventoryService.findOne( p_Id ) );
   }

   @Operation( summary = "update an inventory entry",
      description = "update an inventory entry for a poster if it is not existentd it will create one automatically" )
   @Parameter( name = "id", description = "id of poster", required = true )
   @ApiResponse( responseCode = "200", description = "saved inventory obj",
      content = @Content( mediaType = MediaType.APPLICATION_JSON,
         schema = @Schema( implementation = InventoryDTO.class ) )
   )
   @ApiResponse( responseCode = "500", description = "an error occurred" )
   @Put( "/inventory" )
   public HttpResponse<InventoryDTO> updateInventory( @Body final InventoryDTO p_Value )
   {
      log.debug( "REST Request to update an inventory item: {}", p_Value );
      return HttpResponse.ok( _InventoryService.save( p_Value ) );
   }
}
