package com.androidparty.test.ui.servers

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidparty.test.R
import com.androidparty.test.model.ServerResponse
import kotlinx.android.synthetic.main.item_server.view.*
import java.util.*

/**
 * Created by Ivan Danilov on 8/1/2018.
 * Email - esseckers@gmail.com
 */
class ServerAdapter : RecyclerView.Adapter<ServerAdapter.ServerHolder>() {

    private val servers = ArrayList<ServerResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServerHolder {
        return ServerHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_server, parent, false))
    }

    override fun getItemCount(): Int {
        return servers.size
    }

    override fun onBindViewHolder(holder: ServerHolder, position: Int) {
        holder.bind(servers[position])
    }

    fun addServers(models: List<ServerResponse>?) {
        if (models != null) {
            this.servers.addAll(models)
            notifyDataSetChanged()
        }
    }

    inner class ServerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(server: ServerResponse) {
            itemView.serverName.text = server.name
            itemView.distance.text = String.format("%s km", server.distance)
        }
    }
}