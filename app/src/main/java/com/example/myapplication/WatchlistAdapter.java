package com.example.myapplication;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.ViewHolder> {

    /**
     * Sets up the wathclist adapter using cards I made in xml
     */

    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public WatchlistAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onRemoveClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_card, parent, false);
        return new ViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.textViewMovieTitle.setText(movie.getMovieTitle());

        //Makes parts of the string bold

        SpannableString spannableGenre = new SpannableString("Genre: " + movie.getMovieGenre());
        spannableGenre.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // "Genre: " is bold
        holder.textViewMovieGenre.setText(spannableGenre);

        SpannableString spannableYear = new SpannableString("Year: " + movie.getMovieYear());
        spannableYear.setSpan(new StyleSpan(Typeface.BOLD), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // "Year: " is bold
        holder.textViewMovieYear.setText(spannableYear);

        SpannableString spannablePlatform = new SpannableString("Platform: " + movie.getPlatform().getDisplayName());
        spannablePlatform.setSpan(new StyleSpan(Typeface.BOLD), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // "Platform: " is bold
        holder.textViewMoviePlatform.setText(spannablePlatform);

        String removeText = "Remove";
        SpannableString content = new SpannableString(removeText);
        content.setSpan(new UnderlineSpan(), 0, removeText.length(), 0);
        holder.mRemoveClickTextView.setText(content);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewMovieTitle;
        public TextView textViewMovieYear;
        public TextView textViewMovieGenre;
        public TextView textViewMoviePlatform;
        private TextView mRemoveClickTextView;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewMovieTitle = itemView.findViewById(R.id.textViewTitle);
            textViewMovieYear = itemView.findViewById(R.id.textViewYear);
            textViewMovieGenre = itemView.findViewById(R.id.textViewGenre);
            textViewMoviePlatform = itemView.findViewById(R.id.textViewPlatform);
            mRemoveClickTextView = itemView.findViewById(R.id.remove_watchlist_clickable_text);

            mRemoveClickTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  listener.onRemoveClick(getAdapterPosition());
                }
            });
        }
    }
}
