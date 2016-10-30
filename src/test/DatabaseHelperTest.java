package test;

import dbhelper.DatabaseHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Andrey on 30.10.2016.
 */
public class DatabaseHelperTest {
    DatabaseHelper databaseHelper;
    @Before
    public void setUp() throws Exception {
        databaseHelper = new DatabaseHelper();
    }

    @Test
    public void establishConnection() throws Exception {
        Assert.assertTrue("Connection is not created" , databaseHelper.establishConnection("org.postgresql.Driver",
                "jdbc:postgresql://localhost:5432/test", "postgres", "dacota20") != null);
    }

    @Test
    public void getConnectionDB() throws Exception {
        Assert.assertTrue("Connection is empty", databaseHelper.getConnectionDB() != null);

    }

    @Test
    public void close() throws Exception {
        databaseHelper.close();
        Assert.assertNotNull("Connection is not closed", databaseHelper == null);
    }

}