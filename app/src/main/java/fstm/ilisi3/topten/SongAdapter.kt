package fstm.ilisi3.topten

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
class SongAdapter(private val songs: List<Track>) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val artistTextView: TextView = itemView.findViewById(R.id.artistTextView)
        val songImageView: ImageView = itemView.findViewById(R.id.songImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = songs[position]
        holder.titleTextView.text = song.name
        holder.artistTextView.text = song.artist.name

        // Load image using Picasso library
        Picasso.get().load(song.imageUrl).into(holder.songImageView)
    }

    override fun getItemCount(): Int = songs.size
}
