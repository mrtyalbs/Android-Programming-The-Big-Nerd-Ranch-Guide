package dev.mya.criminalintent

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
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

class CrimeListAdapter(private val crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
        return CrimeHolder(binding)
    }

    override fun getItemCount(): Int {
        return crimes.size
    }

    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime = crimes[position]
        holder.bind(crime)
    }

}