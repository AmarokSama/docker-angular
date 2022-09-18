package de.htwsaar.pimsar.grp10.web.rest;

import de.htwsaar.pimsar.grp10.service.OrderService;
import de.htwsaar.pimsar.grp10.service.dto.OrderDTO;
import de.htwsaar.pimsar.grp10.web.rest.error.BadRequestException;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
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

@Tag( name = "order" )
@Slf4j
@Controller( "/api" )
public class OrderResource
{

   private static final String ENTITY_NAME = "order";

   private final OrderService _OrderService;

   public OrderResource( final OrderService p_OrderService )
   {
      _OrderService = p_OrderService;
   }

   /**
    * {@code GET /api/orders}: get all orders
    *
    * @param p_Page paging
    * @return return list of orders
    */
   @Operation( summary = "get a list of order", description = "get a list of order" )
   @ApiResponse( responseCode = "200", description = "order",
      content = @Content( mediaType = MediaType.APPLICATION_JSON,
         array = @ArraySchema( schema = @Schema( description = "order object", implementation = OrderDTO.class ) ) ))
   @Get( "/orders" )
   public HttpResponse<List<OrderDTO>> getAllOrders( Pageable p_Page )
   {
      final Page<OrderDTO> c_Result = _OrderService.findAll( p_Page );
      return HttpResponse.ok( c_Result.getContent() );
   }

   /**
    * {@code GET /api/order/:id}: get one order
    *
    * @param p_Id Id
    * @return return one order
    */
   @Operation( summary = "get an order", description = "get a specific order" )
   @ApiResponse( responseCode = "200", description = "order",
      content = @Content( mediaType = MediaType.APPLICATION_JSON,
         schema = @Schema( description = "order object", implementation = OrderDTO.class ) ) )
   @Parameter( name = "orderId", description = "id of order to delete", required = true )
   @Get( "/order/{orderId}" )
   public Optional<OrderDTO> getOneOrder( @PathVariable( name = "orderId" ) final Long p_Id )
   {
      return _OrderService.findOne( p_Id );
   }

   /**
    * {@code POST /api/order} : create a new order
    *
    * @param p_DTO order to create
    * @return created order
    */
   @Operation( summary = "crate a order", description = "create a specific order" )
   @ApiResponse( responseCode = "201", description = "order created",
      content = @Content( mediaType = MediaType.APPLICATION_JSON,
         schema = @Schema( description = "created order object", implementation = OrderDTO.class ) ) )
   @ApiResponse( responseCode = "400", description = "order already exist" )
   @ApiResponse( responseCode = "404", description = "order cannot be created" )
   @RequestBody( required = true, description = "order to create",
      content = @Content( schema = @Schema( description = "order object to created", implementation = OrderDTO.class ) ) )
   @Post( "/order" )
   public HttpResponse<OrderDTO> createOrder( @Body OrderDTO p_DTO ) throws URISyntaxException
   {
      if( p_DTO.getId() != null )
      {
         throw new BadRequestException( "A new order cannot already have an ID", ENTITY_NAME, "idexists" );
      }
      final OrderDTO c_Result = _OrderService.createOrder( p_DTO );
      final URI c_URI = new URI( "/api/poster/" + c_Result.getId() );
      return HttpResponse.created( c_Result ).headers( p_Headers ->
                                                       {
                                                          p_Headers.location( c_URI );
                                                       } );
   }

   /**
    * {@code GET /api/order/:id}: get all orders
    *
    * @param p_Id id of order
    * @return deleted was successful
    */
   @Operation( summary = "delete a order", description = "create a specific order" )
   @ApiResponse( responseCode = "204", description = "order deleted" )
   @ApiResponse( responseCode = "404", description = "order cannot be deleted" )
   @Parameter(name = "orderId", description = "id of order to delete", required = true)
   @Delete( "/order/{orderId}" )
   public HttpResponse deleteOrder( @PathVariable( name = "orderId" ) final Long p_Id )
   {
      _OrderService.delete( p_Id );
      return HttpResponse.noContent();
   }

}
