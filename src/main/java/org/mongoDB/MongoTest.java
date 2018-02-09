package org.mongoDB;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.mongoDB.org.mongoDB.pojos.Hobby;
import org.mongoDB.org.mongoDB.pojos.Student;

import static com.mongodb.client.model.Filters.*;
import static java.util.Arrays.asList;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;

public class MongoTest
{
    //translation to and from bson for  POJOs
   static CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    static MongoClient mongoClient = new MongoClient("localhost" , MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
    static MongoDatabase database = mongoClient.getDatabase("students");
    static MongoCollection<Document> collection = database.getCollection("students");

    static Document doc = new Document("firstName", "Kate")
            .append("lastName", "Ivanova")
            .append("age", 29)
            .append("hobby", asList("films", "swimming"));

   static MongoCollection<Student> studentsCollection = database.getCollection("students", Student.class);

    public static void main(String[] args)
    {
        ibnsertOne();
        count();
        findFirst();
        findAllDocs();
        filterDocs();
        filterByHobby();
        filterByAge();
        updateOne();
        updateMany();
        deleteOne();
        deleteAll();
        createIndex();

        insertStudent();
        insertManyStudents();
        deleteStudent();
        deleteAllStudentsByFilter();
    }

  static void findFirst()
  {
      System.out.println(collection.find().first().toJson());
  }

   static void count()
   {
       System.out.println("total count of elements = " +collection.count());
   }

    static void ibnsertOne()
    {
        collection.insertOne(doc);
    }

     static void findAllDocs()
    {
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
    }

    static void filterDocs()
    {
        Document myDoc = collection.find(eq("firstName", "Анатолий")).first();
        System.out.println("filtering");
        System.out.println(myDoc.toJson());
    }

    static void filterByHobby()
    {   MongoCursor<Document> cursor = collection.find(eq("hobby", "фильмы")).iterator();
        System.out.println("filtering by hobby");
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
    }

    static void filterByAge()
    {
        System.out.println("filtering by age");
        collection.find(and(gt("age", 30), lte("age", 150))).forEach(printBlock);
    }

    static Block<Document> printBlock = new Block<Document>() {
        public void apply(final Document document) {
            System.out.println(document.toJson());
        }
    };

    static void updateOne()
    {
        collection.updateOne(eq("firstName", "Авраам"), new Document("$set", new Document("firstName", "Новый_Авраам")));
    }

    static void updateMany()
    {
        UpdateResult updateResult = collection.updateMany(lt("age", 23), inc("age", 111)); // 22+111=133
        System.out.println("updated elements = " + updateResult.getModifiedCount());
    }

    static void deleteOne()
    {
        collection.deleteOne(eq("firstName", "Kate"));
    }

    static void deleteAll()
    {
        DeleteResult deleteResult = collection.deleteMany(eq("firstName", "Lika"));
        System.out.println("deleted elements = " + deleteResult.getDeletedCount());
    }

    static void createIndex()
    {
        collection.createIndex(new Document("age", 1));
    }

    static void insertStudent()
    {
        Student ada = new Student("Ada", "Byron", 20, new ArrayList<Hobby>(asList(new Hobby[]{Hobby.SWIMMING, Hobby.FILMS})));

        studentsCollection.insertOne(ada);
    }

    static void insertManyStudents()
    {
        List<Student> students = asList(
                new Student("Vera", "Verkina", 33, new ArrayList<Hobby>(asList(new Hobby[]{Hobby.DIVING, Hobby.DRIVING}))),
                new Student("Inna", "Inkina", 34, new ArrayList<Hobby>(asList(new Hobby[]{Hobby.DIVING, Hobby.READING, Hobby.DRIVING})))
        );
        studentsCollection.insertMany(students);
    }

    static void deleteStudent()
    {
        studentsCollection.deleteOne(eq("firstName", "Ada"));
    }

    static void deleteAllStudentsByFilter()
    {
        DeleteResult deleteResult = studentsCollection.deleteMany(eq("firstName", "Ada"));
        System.out.println("deleted students count = " + deleteResult.getDeletedCount());
    }

}
