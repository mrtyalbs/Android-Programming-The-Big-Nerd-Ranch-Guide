package dev.mya.criminalintent

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import dev.mya.criminalintent.databinding.ListItemCrimeBinding
import dev.mya.criminalintent.databinding.ListItemSeriousCrimeBinding

class CrimeHolder(private val binding: ListItemCrimeBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()

        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context, "${crime.title} clicked!", Toast.LENGTH_SHORT)
                .show()
        }
    }
}

class SeriousCrimeHolder(private val binding: ListItemSeriousCrimeBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()
        binding.contactPoliceButton.setOnClickListener {
            TODO()
        }
    }
}

class CrimeListAdapter(private val crimes: List<Crime>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_NORMAL = 0
        private const val VIEW_TYPE_SERIOUS = 1
    }


    override fun getItemViewType(position: Int): Int {
        return if (crimes[position].requiresPolice) {
            VIEW_TYPE_SERIOUS
        } else {
            VIEW_TYPE_NORMAL
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_SERIOUS) {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemSeriousCrimeBinding.inflate(inflater, parent, false)
            return SeriousCrimeHolder(binding)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
            return CrimeHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return crimes.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val crime = crimes[position]
        if (holder is SeriousCrimeHolder) {
            holder.bind(crime)
        } else if (holder is CrimeHolder) {
            holder.bind(crime)
        }
    }

}