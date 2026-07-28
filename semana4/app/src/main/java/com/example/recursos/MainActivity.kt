package com.example.recursos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recursos.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DishAdapter
    private var allDishes = listOf<Dish>()
    private var currentCategory = "Todos"
    private var currentQuery = ""
    private val prefs by lazy { getSharedPreferences("favorites",
        MODE_PRIVATE) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                binding = ActivityMainBinding.inflate(layoutInflater)
                setContentView(binding.root)
                setSupportActionBar(binding.toolbar)
                allDishes = buildDishList()
                adapter = DishAdapter(
                allDishes.toMutableList(),
            onClick = { dish -> openDetail(dish) },
            onFavoriteToggle = { dish -> saveFavorite(dish) }
        )
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                binding.recyclerView.adapter = adapter
                resources.getStringArray(R.array.tab_categories).forEach {

                    binding.tabLayout.addTab(binding.tabLayout.newTab().setText(it))
                }
                binding.tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                currentCategory = tab.text.toString()
                applyFilters()
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
                attachSwipeToDelete(binding.recyclerView)
    }
    private fun buildDishList(): List<Dish> {
        val names = resources.getStringArray(R.array.dish_names)
        val descriptions =
            resources.getStringArray(R.array.dish_descriptions)
        val prices = resources.getStringArray(R.array.dish_prices)
        val images = resources.getStringArray(R.array.dish_images)
        val categories =
            resources.getStringArray(R.array.dish_categories)
        val favorites = prefs.getStringSet("favorite_names", emptySet())
            ?: emptySet()
        val size = listOf(names.size, descriptions.size, prices.size,
            images.size, categories.size).minOrNull() ?: 0
        return (0 until size).map { i ->
            Dish(names[i], descriptions[i], prices[i], images[i],
                categories[i], favorites.contains(names[i]))
        }
    }
    private fun applyFilters() {
        val filtered = allDishes.filter { dish ->
            (currentCategory == "Todos" || dish.category ==
                    currentCategory) &&
                    dish.name.contains(currentQuery, ignoreCase = true)
        }
        adapter.updateList(filtered)
        title = resources.getQuantityString(R.plurals.results_count,
            filtered.size, filtered.size)
    }
    private fun saveFavorite(dish: Dish) {
        val current = prefs.getStringSet("favorite_names",
            emptySet())?.toMutableSet() ?: mutableSetOf()
        if (dish.isFavorite) current.add(dish.name) else
            current.remove(dish.name)
        prefs.edit().putStringSet("favorite_names", current).apply()
    }
    private fun attachSwipeToDelete(recyclerView: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(rv: RecyclerView, vh:
            RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder,
                                  direction: Int) {
                val position = viewHolder.adapterPosition
                val removed = adapter.removeAt(position)
                Snackbar.make(recyclerView, "${removed.name} eliminado",
                    Snackbar.LENGTH_LONG)
                    .setAction("Deshacer") { adapter.insertAt(position,
                        removed) }
                    .show()
            }
        }
        ItemTouchHelper(callback).attachToRecyclerView(recyclerView)
    }
    private fun openDetail(dish: Dish) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("name", dish.name)
            putExtra("description", dish.description)
            putExtra("price", dish.price)
            putExtra("imageName", dish.imageName)
        }
        startActivity(intent)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                currentQuery = newText ?: ""
                applyFilters()
                return true
            }
        })
        return true
    }
}
