package de.htwsaar.pimsar.grp10.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PosterSizeDTO implements Serializable
{
   private final Long id;
   private final String name;
}
