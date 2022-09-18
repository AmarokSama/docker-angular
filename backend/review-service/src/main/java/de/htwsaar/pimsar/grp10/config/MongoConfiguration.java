package de.htwsaar.pimsar.grp10.config;

import io.micronaut.context.annotation.Value;

/**
 * class to configure mongodb
 */
public class MongoConfiguration
{
   @Value( "${mongodb.databaseName}" )
   private String databaseName;

   @Value( "${mongodb.collectionName}" )
   private String collectionName;

   /**
    * get the name of the mongo db database
    *
    * @return mongo db database name
    */
   public String getDatabaseName()
   {
      return databaseName;
   }

   /**
    * get the name of the mongo db database collection
    *
    * @return mongo db database collection name
    */
   public String getCollectionName()
   {
      return collectionName;
   }
}

