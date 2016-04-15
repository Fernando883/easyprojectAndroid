package inftel.easyprojectandroid.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import inftel.easyprojectandroid.R;
import inftel.easyprojectandroid.adapter.RecyclerViewCommentAdapter;
import inftel.easyprojectandroid.model.Comentario;
import inftel.easyprojectandroid.util.DividerItemDecoration;

/**
 * Created by inftel10 on 7/4/16.
 */
public class CommentListFragment extends Fragment {


    private View view;
    private ArrayList<Comentario> commentList;

    private RecyclerView recyclerView;
    private RecyclerViewCommentAdapter adapter;
    private LinearLayoutManager layoutManager;


    public CommentListFragment() { }
    public void setCommentList(ArrayList<Comentario> commentList) {
        this.commentList= commentList;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_comment_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.commentRecyclerView);
        adapter = new RecyclerViewCommentAdapter(commentList, getContext());
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(commentList.size() - 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        return view;
    }
    public void addMessage(Comentario comentario){


        commentList.add(comentario);
        adapter.notifyDataSetChanged();
        layoutManager.scrollToPosition(commentList.size() - 1);
    }



}
