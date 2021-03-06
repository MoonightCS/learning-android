package bodya.sbt.ru.currentwork.db;

import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.RuleChain;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;

import java.util.List;

import bodya.sbt.ru.currentwork.Animal;
import bodya.sbt.ru.currentwork.EntitiesGenerator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.*;

public class SQLiteAnimalDaoTest {

    private static final String TAG = SQLiteAnimalDaoTest.class.getSimpleName();

    private final SQLiteAnimalsDaoRule daoRule = new SQLiteAnimalsDaoRule();
    private final ExpectedException expectedException = ExpectedException.none();
    private final TestName testName = new TestName();

    @Rule
    public TestRule rule = RuleChain
            .outerRule(daoRule)
            .around(expectedException)
            .around(testName);

    @Test
    public void testInsert() {
        Log.e(TAG, testName.getMethodName());
        Animal animal = EntitiesGenerator.createRandomAnimal();
        long id = daoRule.getSqLiteAnimalsDao().insertAnimal(animal);
        assertThat(true, is(id > 0));
    }

    @Test
    public void testInsertNull() {
        Log.e(TAG, testName.getMethodName());
        expectedException.expect(NullPointerException.class);
        daoRule.getSqLiteAnimalsDao().insertAnimal(null);
    }

    @Test
    public void testGetAnimals() {
        Log.e(TAG, testName.getMethodName());
        List<Animal> expected = EntitiesGenerator.createRandomAnimalsList();
        for (Animal animal : expected) {
            long id = daoRule.getSqLiteAnimalsDao().insertAnimal(animal);
            animal.setId(id);
        }
        List<Animal> actual = daoRule.getSqLiteAnimalsDao().getAnimals();
        assertThat(actual, everyItem(isIn(expected)));
    }

    @Test
    public void testGetAnimalById() {
        Log.e(TAG, testName.getMethodName());
        Animal expected = EntitiesGenerator.createRandomAnimal();
        long id = daoRule.getSqLiteAnimalsDao().insertAnimal(expected);
        expected.setId(id);
        Animal actual = daoRule.getSqLiteAnimalsDao().getAnimalById(id);
        assertThat(actual, is(expected));
    }

    @Test
    public void testGetAnimalByUnknownId() {
        Log.e(TAG, testName.getMethodName());
        long id = -1;
        Animal actual = daoRule.getSqLiteAnimalsDao().getAnimalById(id);
        assertThat(actual, is(nullValue()));
    }


    @Test
    public void testUpdateAnimal() {
        Log.e(TAG, testName.getMethodName());
        Animal expected = EntitiesGenerator.createRandomAnimal();
        long id = daoRule.getSqLiteAnimalsDao().insertAnimal(expected);
        expected.setId(id);
        EntitiesGenerator.updateAnimal(expected);
        int rowsUpdatedCount = daoRule.getSqLiteAnimalsDao().updateAnimal(expected);
        assertThat(rowsUpdatedCount, is(1));
        Animal actualAnimal = daoRule.getSqLiteAnimalsDao().getAnimalById(id);
        assertThat(actualAnimal, is(expected));
    }

    @Test
    public void testUpdateNullAnimal() {
        Log.e(TAG, testName.getMethodName());
        expectedException.expect(NullPointerException.class);
        daoRule.getSqLiteAnimalsDao().updateAnimal(null);
    }

}
