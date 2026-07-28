package com.example.recursos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recursos.databinding.ItemDishBinding

class DishAdapter(
    private var items: MutableList<Dish>,
    private val onClick: (Dish) -> Unit,
    private val onFavoriteToggle: (Dish) -> Unit
) : RecyclerView.Adapter<DishAdapter.DishViewHolder>() {
    inner class DishViewHolder(val binding: ItemDishBinding) :
        RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            DishViewHolder {
        val binding =
            ItemDishBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false)
        return DishViewHolder(binding)
    }
    override fun onBindViewHolder(holder: DishViewHolder, position: Int)
    {
        val dish = items[position]
        val b = holder.binding
        b.tvName.text = dish.name
        b.tvPrice.text = dish.price
        val ctx = holder.itemView.context
        val resId = ctx.resources.getIdentifier(dish.imageName,
            "drawable", ctx.packageName)
        if (resId != 0) b.ivDish.setImageResource(resId)
        else b.ivDish.setImageResource(R.drawable.ic_launcher_foreground)
        b.ivFavorite.setImageResource(
            if (dish.isFavorite) R.drawable.ic_favorite else
                R.drawable.ic_favorite_border
        )
        b.ivFavorite.setOnClickListener {
            dish.isFavorite = !dish.isFavorite
            notifyItemChanged(position)
            onFavoriteToggle(dish)
        }
        holder.itemView.setOnClickListener { onClick(dish) }
    }
    override fun getItemCount(): Int = items.size
    fun updateList(newList: List<Dish>) {
        items = newList.toMutableList()
        notifyDataSetChanged()
    }
    fun removeAt(position: Int): Dish {
        val removed = items.removeAt(position)
        notifyItemRemoved(position)
        return removed
    }
    fun insertAt(position: Int, dish: Dish) {
        items.add(position, dish)
        notifyItemInserted(position)
    }
}