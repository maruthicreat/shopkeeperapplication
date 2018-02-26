package com.example.maruthiraja.shopkeeperapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView itemlist;
    private DatabaseReference mdatabase;
    StaggeredGridLayoutManager gridLayoutManager;
    private List<String> listitems ;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            String get = getArguments().getString("maru");
            System.out.println("in on create :  " + get);
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("called");
        Toast.makeText(getActivity(), "okok", Toast.LENGTH_SHORT).show();
        setrecycler(view);
    }


    public void setrecycler(View view)
    {

        mdatabase = FirebaseDatabase.getInstance().getReference().child("shop_details");
        gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mdatabase.keepSynced(true);

      /*  mdatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String key = ds.getKey();
                    //listitems.add(ds.child("title").getValue().toString());
                    // setsuggestion(listitems);
                    // Toast.makeText(shopkeeperfirstpage.this, listitems.get(1), Toast.LENGTH_SHORT).show();
                   // System.out.println("value");
                }

                //String value = dataSnapshot.getValue(String.class);
                //System.out.println(value);
                // Toast.makeText(viewItem.this, value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Log.w(TAG, "Failed to read value.", error.toException());
                System.out.println(error.toException());
            }
        });
*/


        //Toast.makeText(getContext(), "called", Toast.LENGTH_SHORT).show();
        itemlist = (RecyclerView) view.findViewById(R.id.item_list);
        itemlist.setHasFixedSize(true);
        itemlist.setLayoutManager(gridLayoutManager);
        listitems = new ArrayList<String>();
        if (getArguments() != null) {
            String get = getArguments().getString("maru");
            System.out.println("in onviewcreate :  " + get);
        }
        FirebaseRecyclerAdapter<ItemShow,ItemHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ItemShow, ItemHolder>(
                ItemShow.class,
                R.layout.item_list,
                ItemHolder.class,
                mdatabase.orderByChild("id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid())

        ) {

            @Override
            protected void populateViewHolder(ItemHolder viewHolder, ItemShow model, int position) {
                viewHolder.setItemName(model.getTitle());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setImage(model.getImage());
                viewHolder.setRating(model.getRating());
            }
        };

        itemlist.setAdapter(firebaseRecyclerAdapter);

    }

    public static class ItemHolder extends RecyclerView.ViewHolder{

        View mview;

        public ItemHolder(View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setItemName(String name)
        {
            System.out.println("name"+name);
            TextView item_n = (TextView) mview.findViewById(R.id.item_name);
            item_n.setText(name);
        }

        public void setPrice(String price)
        {
            System.out.println("price"+price);
            TextView item_p = (TextView) mview.findViewById(R.id.item_price);
            item_p.setText(price);
        }

        public void setDescription(String desc)
        {
            System.out.println("desc"+desc);
            TextView item_d = (TextView) mview.findViewById(R.id.item_description);
            item_d.setText(desc);
        }

        public void setImage(String image)
        {
            System.out.println("image"+image);
            ImageView imageView = (ImageView) mview.findViewById(R.id.item_image);
            Picasso.with(mview.getContext()).load(image).into(imageView);
        }

        public void setRating(String rating)
        {
            System.out.println("Rating"+rating);
            RatingBar ratingBar = (RatingBar) mview.findViewById(R.id.ratingBar);
            Drawable progress = ratingBar.getProgressDrawable();
            DrawableCompat.setTint(progress, Color.WHITE);
            ratingBar.setNumStars(5);
            ratingBar.setRating(Float.parseFloat(rating));
        }
    }





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
