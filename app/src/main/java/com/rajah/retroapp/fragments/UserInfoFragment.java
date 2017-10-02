package com.rajah.retroapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajah.retroapp.Constant;
import com.rajah.retroapp.R;
import com.rajah.retroapp.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rajah.retroapp.R.id.phone_number;


public class UserInfoFragment extends Fragment {

    @BindView(phone_number)
    TextView phone_no;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.website)
    TextView website;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.company)
    TextView company;

    private User user;


    public UserInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            user = getArguments().getParcelable(Constant.USER);
            bindData();
        }
        return view;
    }

    private void bindData() {
        email.setText(user.getEmail());
        phone_no.setText(user.getPhone());
        website.setText(user.getWebsite());
        address.setText(user.getAddress().getStreet()+", " + user.getAddress().getCity() + ", "+
                user.getAddress().getSuite() +".");
        company.setText(user.getCompany().getName());
    }

}
