package de.htwsaar.pimsar.grp10.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PosterDTO implements Serializable
{
   private final Long id;
   private String title;
   private String category;
   private String description;
   private Double price;
   private Integer ratingCount;
   private Double averageRating;
   private PosterSizeDTO size;
   private String image;
}
