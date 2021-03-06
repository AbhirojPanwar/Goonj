package com.abhiroj.goonj.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.abhiroj.goonj.R;
import com.abhiroj.goonj.adapter.TeamListAdapter;
import com.abhiroj.goonj.data.TeamMember;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static com.abhiroj.goonj.data.Constants.TEAM_ROOT;
import static com.abhiroj.goonj.data.Constants.fragtag;
import static com.abhiroj.goonj.utils.Utility.detectConnection;
import static com.abhiroj.goonj.utils.Utility.showSnackBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamListFragment extends Fragment {


    public static final String TAG=TeamListFragment.class.getSimpleName();
    private RecyclerView teamlist;
    private ArrayList<TeamMember> members;
    private TeamListAdapter teamListAdapter;
    private GridLayoutManager layoutManager;
    private ProgressBar progressBar;

    public TeamListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DatabaseReference database=FirebaseDatabase.getInstance().getReference(TEAM_ROOT);
        members=new ArrayList<TeamMember>();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(progressBar!=null)
                progressBar.setVisibility(View.INVISIBLE);
                int total = (int) dataSnapshot.getChildrenCount();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    for (DataSnapshot d1 : d.getChildren()) {
                        TeamMember member = new TeamMember();
                        member.setName((String) d1.child("name").getValue());
                        member.setPh( d1.child("ph").getValue().toString());
                       member.setComm(d.getKey()+"");
                        members.add(member);
                    }
                    teamListAdapter.addMembers(members);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
               showSnackBar(getActivity(),databaseError.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Toolbar toolbar=(Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Goonj'17 Team");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView=inflater.inflate(R.layout.fragment_team_list, container, false);
        progressBar=(ProgressBar) rootView.findViewById(R.id.load_bar);
        teamlist = (RecyclerView) rootView.findViewById(R.id.list_container);
        layoutManager = new GridLayoutManager(getContext(), 1);
        teamListAdapter = new TeamListAdapter(getContext());
        if(detectConnection(getContext())) {
            if(members.size()==0)
            {
                progressBar.setVisibility(View.VISIBLE);
            }
            else
                progressBar.setVisibility(View.INVISIBLE);
            teamlist.setLayoutManager(layoutManager);
            teamlist.setAdapter(teamListAdapter);
        }
        else
        {
            showSnackBar(getActivity(),R.string.no_internet);
        }
            return rootView;
    }

    public static TeamListFragment newInstance() {
        TeamListFragment teamListFragment=new TeamListFragment();
        fragtag.put(TeamListFragment.TAG, teamListFragment);
        return teamListFragment;
    }
}
