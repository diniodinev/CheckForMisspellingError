package com.example.batch.core;

import com.example.batch.entities.Manufacturer;
import com.example.batch.entities.Product;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Cannibal on 1.2.2015 г..
 */
public class TestBatch {
    private static final Logger logger = LoggerFactory.getLogger(TestBatch.class);

    public static void main(String[] args) throws SQLException, IOException {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("thePersistenceUnit");
        EntityManager entityManager = factory.createEntityManager();
        populateWithProducts(entityManager);
        populateWithManufactures(entityManager);
        entityManager.getTransaction().commit();

        System.out.println("------------------------->");

        List<Manufacturer> manu = entityManager.createNativeQuery("select * from MANUFACTURER", Manufacturer.class).getResultList();
        for (Manufacturer manufact : manu) {

            List<Product> prod = manufact.getProducts();

            for (Iterator iter = prod.iterator(); iter.hasNext(); ) {
                Product sdr = (Product) iter.next();
                System.out.println(sdr.getName());
            }
        }

        startServer();

    }

    public static void startServer() {
        //TODO For testing purposes only, to be deleted
        Server server = null;
        try {
            server = Server.createTcpServer().start();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        logger.info("Server started and connection is open.");
        logger.info("URL: jdbc:h2:" + server.getURL() + "/mem:test");
    }

    public static void populateWithProducts(EntityManager entityManager) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            for (int i = 0; i < 100; i++) {
                entityManager.persist(new Product(i, "Product number:" + i));
            }
        } catch (RuntimeException e) {
            try {
                tx.rollback();
            } catch (RuntimeException rbe) {
                logger.error("Could not roll back transaction", rbe);
            }
            throw e;
        }
    }

    public static void populateWithManufactures(EntityManager entityManager) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            for (int i = 0; i < 10; i++) {
                Manufacturer manufacturer = new Manufacturer("Manufacturer:" + i);
                manufacturer.setProducts(getProducts(entityManager, i));
                entityManager.persist(manufacturer);
            }

        } catch (RuntimeException e) {
            try {
                tx.rollback();
            } catch (RuntimeException rbe) {
                logger.error("Could not roll back transaction", rbe);
            }
            throw e;
        }
    }

    public static List<Product> getProducts(EntityManager entityManager, int number) {
        List<Product> products = new ArrayList<>();
        EntityTransaction tx = entityManager.getTransaction();
        for (int i = 0; i < 10; i++) {
            products.add(entityManager.find(Product.class, (long) (i * number)));
        }
        return products;
    }
}
