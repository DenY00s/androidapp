package com.novikov.myfirstapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.novikov.myfirstapp.R
import com.novikov.myfirstapp.model.PhoneModel

class PhonesAdapter : RecyclerView.Adapter<PhonesAdapter.PhoneViewHolder>() {

    private var phonesList: ArrayList<PhoneModel> = arrayListOf()

    class PhoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvModelName: TextView = itemView.findViewById(R.id.tv_model_name)
        private val tvReleaseYear: TextView = itemView.findViewById(R.id.tv_release_year)
        private val tvCameraRating: TextView = itemView.findViewById(R.id.tv_camera_rating)
        private val ivPhoneImage: ImageView = itemView.findViewById(R.id.phone_image)

        fun bind(phone: PhoneModel) {
            tvModelName.text = phone.modelName
            tvReleaseYear.text = phone.releaseYear.toString()
            tvCameraRating.text = "Рейтинг камеры: ${phone.cameraRating}"
            ivPhoneImage.setImageResource(phone.imageResId)  // Устанавливаем изображение
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return PhoneViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        holder.bind(phonesList[position])
    }

    override fun getItemCount(): Int = phonesList.size

    fun setupPhones(newList: ArrayList<PhoneModel>) {
        phonesList.clear()
        phonesList.addAll(newList)
        notifyDataSetChanged()
    }
}