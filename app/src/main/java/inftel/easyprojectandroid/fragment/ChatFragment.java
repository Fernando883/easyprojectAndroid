package inftel.easyprojectandroid.fragment;

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
import inftel.easyprojectandroid.adapter.RecyclerViewChatAdapter;
import inftel.easyprojectandroid.model.Message;

/**
 * Created by csalas on 10/4/16.
 */
public class ChatFragment extends Fragment {
    private View view;
    private ArrayList<Message> messageList;
    private RecyclerView recyclerView;
    private RecyclerViewChatAdapter adapter;
    private LinearLayoutManager layoutManager;


    public ChatFragment() { }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.chatRecyclerView);
        adapter = new RecyclerViewChatAdapter(messageList, getContext(), R.layout.row_chat_mine, R.layout.row_chat);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(messageList.size() - 1);
        //layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnLayoutChangeListener(manageKeyboardScroll());

        return view;
    }

    public void setMessageList(ArrayList<Message> messageListList) {
        this.messageList = messageListList;
    }

    public void update() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            layoutManager.scrollToPosition(messageList.size() - 1);
        }
    }

    // Gestión del scroll cuando se abre el teclado
    private View.OnLayoutChangeListener manageKeyboardScroll() {
        return new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if ( bottom < oldBottom) {
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.scrollToPosition(messageList.size() - 1);
                        }
                    }, 100);
                }
            }
        };
    }


}

