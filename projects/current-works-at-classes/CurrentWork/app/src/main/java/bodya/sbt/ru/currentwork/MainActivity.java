package bodya.sbt.ru.currentwork;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static int ANIMAL_ID = 0;
    private AnimalsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new AnimalsAdapter();
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportLoaderManager().getLoader(ANIMAL_ID).forceLoad();
            }
        });

        getSupportLoaderManager().initLoader(ANIMAL_ID, null, new AnimalLoaderCallbacks());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.animals_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = false;
        switch (item.getItemId()) {
            case R.id.add_animal_menu_item: {
                startActivity(AddNewAnimalActivity.newIntent(this));
                break;
            }
            default: {
                handled = super.onOptionsItemSelected(item);
                break;
            }
        }
        return handled;
    }

    private class AnimalLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<Animal>> {

        @Override
        public Loader<List<Animal>> onCreateLoader(int id, Bundle args) {
            return new AnimalLoader(MainActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<List<Animal>> loader, List<Animal> data) {
            adapter.setAnimals(data);
        }

        @Override
        public void onLoaderReset(Loader<List<Animal>> loader) {
        }
    }
}
