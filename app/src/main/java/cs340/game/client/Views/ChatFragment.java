package cs340.game.client.Views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cs340.game.R;
import cs340.game.client.InGameFacade;
import cs340.game.client.Presenters.ChatPresenter;
import cs340.game.shared.GameHistoryAction;

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
        messageRecyclerView.setHasFixedSize(true);
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

    public void updateUI() {
        ArrayList<GameHistoryAction> actions = InGameFacade.getInstance().getCurrentGame().getHistory().getActions();
        ArrayList<String> messageList = new ArrayList<>();
        for(int i = 0; i < actions.size(); i++){
            messageList.add(actions.get(i).getActionMessage());
        }

        messageAdapter = new MessageAdapter(messageList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        messageRecyclerView.setLayoutManager(linearLayoutManager);
        messageRecyclerView.setAdapter(messageAdapter);
    }

    public void onError(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private class MessageHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout messageListItem;
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

        private ArrayList<String> mMessages;

        private MessageAdapter(ArrayList<String> messages) {
            mMessages = messages;
        }

        @Override
        public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
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
