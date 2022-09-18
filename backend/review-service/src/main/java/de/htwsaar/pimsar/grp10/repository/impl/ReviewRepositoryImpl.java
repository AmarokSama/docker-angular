package de.htwsaar.pimsar.grp10.repository.impl;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import de.htwsaar.pimsar.grp10.config.MongoConfiguration;
import de.htwsaar.pimsar.grp10.domain.ObjectReview;
import de.htwsaar.pimsar.grp10.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import static com.mongodb.client.model.Filters.eq;

@Singleton
public class ReviewRepositoryImpl implements ReviewRepository
{
   private final MongoClient _MongoClient;

   private final MongoConfiguration _MongoConfig;

   /**
    * constructor
    *
    * @param p_MongoClient {@link MongoClient}
    * @param p_MongoConfig configuration (db name + collection name
    */
   public ReviewRepositoryImpl( final MongoClient p_MongoClient, final MongoConfiguration p_MongoConfig )
   {
      _MongoClient = p_MongoClient;
      _MongoConfig = p_MongoConfig;
   }

   @Override
   public List<ObjectReview> findAll()
   {
      List<ObjectReview> v_Reviews = new ArrayList<>();
      getCollection().find().forEach( v_Reviews::add );
      return v_Reviews;
   }

   @Override
   public ObjectReview findByReviewId( final String p_ReviewId )
   {
      return getCollection().find( eq( "reviewId", p_ReviewId ) ).first();
   }

   @Override
   public ObjectReview save( final ObjectReview p_Review )
   {
      getCollection().insertOne( p_Review ).getInsertedId();
      return findByReviewId( p_Review.getReviewId() );
   }

   @Override
   public void delete( final String p_ReviewId )
   {
      getCollection().deleteOne( eq( "reviewId", p_ReviewId ) );
   }


   /**
    * get the object review collection from the given db and collection
    *
    * @return ObjectReview collection
    */
   private MongoCollection<ObjectReview> getCollection()
   {
      return _MongoClient.getDatabase( _MongoConfig.getDatabaseName() )
                         .getCollection( _MongoConfig.getCollectionName(), ObjectReview.class );
   }
}
