package com.raywenderlich.android.creatures.ui

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.raywenderlich.android.creatures.R
import com.raywenderlich.android.creatures.app.inflate
import com.raywenderlich.android.creatures.model.Creature
import com.raywenderlich.android.creatures.model.CreatureStore

import kotlinx.android.synthetic.main.list_item_creature_with_food.view.*


class CreatureWithFoodAdapter(private val creatures: MutableList<Creature>) : RecyclerView.Adapter<CreatureWithFoodAdapter.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder =  ViewHolder(parent.inflate(R.layout.list_item_creature_with_food))
        holder.itemView.foodRecyclerView.recycledViewPool = viewPool
        LinearSnapHelper().attachToRecyclerView(holder.itemView.foodRecyclerView)
        return holder
    }

    override fun getItemCount() = creatures.size




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(creatures[position])
    }


    class ViewHolder(v:View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private val adapter = FoodAdapter(mutableListOf())

        private lateinit var creature: Creature

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(creature: Creature) {
            this.creature = creature
            val context = itemView.context
            itemView.creatureImage.setImageResource(context.resources.getIdentifier(creature.uri, null, context.packageName))
            setupFoods()

        }


        // opens detail screen
        override fun onClick(view: View) {
            val context = view.context
            val intent = CreatureActivity.newIntent(context, creature.id)
            context.startActivity(intent)
        }

        private fun setupFoods() {

            itemView.foodRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            itemView.foodRecyclerView.adapter = adapter


            val foods = CreatureStore.getCreatureFoods(creature)
            adapter.updateFoods(foods)
        }
    }

}