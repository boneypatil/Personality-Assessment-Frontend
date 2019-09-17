package com.sparknetwork.personalityassest.ui.QuestionAssests


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sparknetwork.personalityassest.R


/**
 * Created by rahul.p
 * on 9/15/2019
 * as project Personality Assesstment
 *
 */

class OptionsAdapter(
    private var options: List<String>?,
    private val mOnClickListener: OptionClickckedLiscner
) : RecyclerView.Adapter<OptionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.row_options, parent, false
        )
        return OptionsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return options!!.size
    }

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        val option = options!![position]

        holder.bind(option, mOnClickListener)
    }
}

class OptionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val container: View = itemView.findViewById(R.id.constraintContainer)
    private val optionText: TextView = itemView.findViewById(R.id.txt_options)


    fun bind(option: String, mOnClickListener: OptionClickckedLiscner) {
        optionText.text = option.capitalize()
        container.setOnClickListener{ mOnClickListener.onOptionClicked(option) }
    }
}

interface OptionClickckedLiscner {
    fun onOptionClicked(option: String)
}