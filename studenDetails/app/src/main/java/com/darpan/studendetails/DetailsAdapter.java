package com.darpan.studendetails;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DetailsAdapter extends FirebaseRecyclerAdapter<UserDetails, DetailsAdapter.DetailsViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */


    private Animation animationUp, animationDown;
    private final int COUNTDOWN_RUNNING_TIME = 500;
    DetailsAdapter(@NonNull FirebaseRecyclerOptions<UserDetails> options) {


        super(options);


    }

    @Override
    protected void onBindViewHolder(@NonNull final DetailsViewHolder holder, int position,
                                    @NonNull UserDetails model) {

        int integer=position+1;

        holder.number.setText(Integer.toString(integer));
        holder.branch.setText("Branch: "+model.getBranch());
        holder.name.setText(model.getName());
        holder.email.setText("Email:"+model.getEmail());
        holder.rollNumber.setText("RollNumber: "+model.getRollNumber());
        holder.contact.setText("Contact: "+model.getContact());
        holder.semester.setText("Sem: "+model.getSemester());
        holder.year.setText("Year: "+model.getYear());



        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.content_shown.isShown())
                {
                    holder.content_shown.startAnimation(animationUp);
                    CountDownTimer countDownTimerStatic=new CountDownTimer(COUNTDOWN_RUNNING_TIME, 16) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            holder.content_shown.setVisibility(View.GONE);

                        }
                    };


                    countDownTimerStatic.start();
                    holder.showMore.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);

                }else {
                    holder.content_shown.setVisibility(View.VISIBLE);
                    holder.content_shown.startAnimation(animationDown);
                    holder.showMore.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @NonNull
    @Override
    public UserDetails getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.details_item, viewGroup,
                        false);
        animationUp = AnimationUtils.loadAnimation(viewGroup.getContext(), R.anim.slide_up);
        animationDown = AnimationUtils.loadAnimation(viewGroup.getContext(), R.anim.slide_down);


        return new DetailsViewHolder(view);
    }

    static class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, contact, year, branch, semester, rollNumber,number;
        CardView rootLayout;
        LinearLayout content_shown;
        ImageView showMore;


        DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            rollNumber = itemView.findViewById(R.id.roll_number);
            semester = itemView.findViewById(R.id.semester);
            year = itemView.findViewById(R.id.year);
            contact = itemView.findViewById(R.id.contact);
            branch = itemView.findViewById(R.id.branch);
            number=itemView.findViewById(R.id.number);
            rootLayout=itemView.findViewById(R.id.root);
            content_shown=itemView.findViewById(R.id.shown_content_layout);
            showMore=itemView.findViewById(R.id.show_more);

        }
    }
}
