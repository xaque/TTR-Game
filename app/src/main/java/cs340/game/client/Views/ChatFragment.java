package cs340.game.client.Views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cs340.game.R;
import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.ChatPresenter;

public class ChatFragment extends Fragment {

    private ChatPresenter presenter;

    private RecyclerView messageRecyclerView;
    private EditText editTextChat;
    private Button buttonChatSend;
    private MessageAdapter messageAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.chat_tab, container, false);
        messageRecyclerView = v.findViewById(R.id.message_reyclerview);
        editTextChat = v.findViewById(R.id.edittext_chat);
        buttonChatSend = v.findViewById(R.id.button_chat_send);
        buttonChatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextChat.getText().toString();
                presenter.sendMessage(message);
            }
        });
        presenter = new ChatPresenter(this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        List<String> messageList = InGameFacade.getInstance().getAllMessages();
        messageList.add("This is the first message");
        messageList.add("Second Message");
        messageList.add("DJ: Third Message");
        messageList.add("Tyler: This is a fun game");
        System.out.println(messageList.toString());
        messageAdapter = new MessageAdapter(messageList);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        messageRecyclerView.setAdapter(messageAdapter);
    }

    public void onError(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private class MessageHolder extends RecyclerView.ViewHolder {

        private RelativeLayout messageListItem;
        private TextView mMessage;

        private String message;

        private MessageHolder(View itemView) {
            super(itemView);

            messageListItem = itemView.findViewById(R.id.message_item);
            mMessage = itemView.findViewById(R.id.message);
        }

        private void bindMessage(String message) {
            this.message = message;
            mMessage.setText(this.message);
        }
    }

    private class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {

        private List<String> mMessages;

        private MessageAdapter(List<String> messages) {
            mMessages = messages;
        }

        @Override
        public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View view = layoutInflater.inflate(R.layout.message_list_item, parent, false);
            return new MessageHolder(view);
        }

        @Override
        public void onBindViewHolder(MessageHolder holder, int position) {
            String message = mMessages.get(position);
            holder.bindMessage(message);
        }

        @Override
        public int getItemCount() {
            if(mMessages == null) {
                return 0;
            }
            return mMessages.size();
        }
    }
}
