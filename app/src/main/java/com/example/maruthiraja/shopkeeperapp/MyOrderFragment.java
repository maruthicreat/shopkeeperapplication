package com.example.maruthiraja.shopkeeperapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyOrderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyOrderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseAuth mAuth;
    private RecyclerView mList;
    private FirebaseUser cuser;
    private MaterialSearchView searchView;
    private FirebaseDatabase database,mdb;
    private Toolbar toolbar;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference myRef,mref;
    boolean doubleBackToExitPressedOnce = false;
    StaggeredGridLayoutManager gridLayoutManager;
    LinearLayoutManager horizontalLayoutManagaer;
    private List<String> listitems;

    private OnFragmentInteractionListener mListener;

    public MyOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyOrderFragment newInstance(String param1, String param2) {
        MyOrderFragment fragment = new MyOrderFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_order, container, false);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setrecycler(view);
    }

    private void setrecycler(View view) {
        mList = (RecyclerView) view.findViewById(R.id.orderlist);
        mList.setHasFixedSize(true);
        mAuth = FirebaseAuth.getInstance();
        cuser = mAuth.getCurrentUser();
        horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mList.setLayoutManager(horizontalLayoutManagaer);
        database = FirebaseDatabase.getInstance("https://shopkeeperapp-7d95b.firebaseio.com/");
        myRef = database.getReference("Purchased").child(cuser.getUid());
        myRef.keepSynced(true);

        FirebaseRecyclerAdapter<GetPurchaseData, purchaseHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<GetPurchaseData, purchaseHolder>(
                GetPurchaseData.class,
                R.layout.orderlist,
                purchaseHolder.class,
                myRef
        ) {
            @Override
            protected void populateViewHolder(purchaseHolder viewHolder, GetPurchaseData model, final int position) {
               // Toast.makeText(getContext(), "view", Toast.LENGTH_SHORT).show();
                String itemid = model.getItemid();
                String itemname = model.getItemname();
                String itemtime = model.getPurchaseTime();
                String itmerating = model.getItemrating();
                String itmepaymod = model.getPaymentMode();
                String itmeaddress = model.getOrderat();
                // mdb = FirebaseDatabase.getInstance("https://shopkeeperapp-7d95b.firebaseio.com/");
                //mref = database.getReference("shop_details").child(itemid);
                viewHolder.setTitleName(itemname);
                viewHolder.setTiming(itemtime);
                viewHolder.setPrice(model.getPrice());
                viewHolder.setadd(itmeaddress);
                viewHolder.setpaymode(itmepaymod);
                viewHolder.setImage(getContext(), model.getItemimage());
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myRef.child(getRef(position).getKey()).removeValue();
                        Toast.makeText(getContext(), "Order Successfully Canceled ..!!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        };
        mList.setAdapter(firebaseRecyclerAdapter);


    }

    public static class purchaseHolder extends RecyclerView.ViewHolder {
        View mview;
        Button button;

        public purchaseHolder(View itemView) {
            super(itemView);
            mview = itemView;
            button = (Button)mview.findViewById(R.id.cartremovebtn);
        }
        public void setTitleName(String title)
        {
            TextView textTitle = (TextView) mview.findViewById(R.id.cartitemname);
            textTitle.setText(title);
        }
        public void setTiming(String timing)
        {
            TextView textTitle = (TextView) mview.findViewById(R.id.orddatetxt);
            textTitle.setText(timing);
        }
        public void setadd(String address)
        {
            TextView textTitle = (TextView) mview.findViewById(R.id.ordadderestxt);
            textTitle.setText(address);
        }
        public void setpaymode(String paymode)
        {
            TextView textTitle = (TextView) mview.findViewById(R.id.ordpaymenttxt);
            textTitle.setText(paymode);
        }
        public void setPrice(String price)
        {
            TextView textTitle = (TextView) mview.findViewById(R.id.cartprice);
            textTitle.setText(price);
        }

        public void setImage(Context applicationContext, String itemimage) {
            ImageView imageView = (ImageView) mview.findViewById(R.id.cartimage);
            Picasso.with(mview.getContext()).load(itemimage).fit().centerCrop().error(R.drawable.ic_broken_image_black_24dp)
                    .placeholder(R.drawable.ic_image_black_24dp).into(imageView);
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
