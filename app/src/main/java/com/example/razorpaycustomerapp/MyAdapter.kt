package com.example.razorpaycustomerapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

interface InterfaceForPostion{
    fun update(id : String,position: Int,name:String,email:String,contact:String)
    fun deleteData(id: String,position: Int)
}
class MyAdapter(val context: Context,val listItem: List<Item>,val listener : InterfaceForPostion):RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val getName : TextView = itemView.findViewById(R.id.getName)
        val getEmail : TextView =itemView.findViewById(R.id.getEmail)
        val getContact : TextView = itemView.findViewById(R.id.getContact)
        val moreHori : ImageView = itemView.findViewById(R.id.moreHori)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_design,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p0 = listItem[position]
        holder.getName.text=p0.name
        holder.getEmail.text= p0.email
        holder.getContact.text=p0.contact

        holder.moreHori.setOnClickListener {
            val popupMenu = PopupMenu(context,holder.moreHori)
            popupMenu.inflate(R.menu.menu_item)
            popupMenu.setOnMenuItemClickListener {

                when(it.itemId){
                    R.id.update->{
                        listener.update(p0.id.toString(),position,p0.name.toString(),p0.email.toString(),p0.contact.toString())
                    }
                    R.id.delete->{
                        listener.deleteData(p0.id,position)
                        Toast.makeText(context, "Wait..", Toast.LENGTH_SHORT).show()
                    }
                }
                true

            }
            popupMenu.show()
        }

    }
}