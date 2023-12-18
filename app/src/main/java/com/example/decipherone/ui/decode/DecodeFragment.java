package com.example.decipherone.ui.decode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.decipherone.MainActivity;
import com.example.decipherone.R;
import com.example.decipherone.databinding.FragmentDecodeBinding;
import com.example.decipherone.databinding.FragmentEncodeBinding;
import com.example.decipherone.ui.decode.NotificationsViewModel;
import com.example.decipherone.ui.encode.EncodeFragment;

public class DecodeFragment extends Fragment {

    private FragmentDecodeBinding binding;
    int key = MainActivity.key;
    TextView messageInput;
    TextView messageOutput;

    public static String decodeData;

    char [] encodedData;
    char [] alphabet = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentDecodeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messageInput = view.findViewById(R.id.decodeText);
        messageOutput = view.findViewById(R.id.decodeTextView2);
        Button decode = (Button) view.findViewById(R.id.decodeButton);
        Button clear = (Button) view.findViewById(R.id.clearDecodeButton);
        Button copy = (Button) view.findViewById(R.id.copyDecodeButton);
        decode.setOnClickListener(v -> {
            char [] data = messageInput.getText().toString().toCharArray();
            messageOutput.setText(String.valueOf(Decoder(data,key)));
            messageInput.setText(null);
        });
        clear.setOnClickListener(v -> {
            messageInput.setText(null);
            messageOutput.setText(null);
        });
        copy.setOnClickListener(v -> {
            messageInput.setText(null);
            encodedData = EncodeFragment.encodeData.toCharArray();
            messageInput.setText(String.valueOf(encodedData));
            messageOutput.setText(String.valueOf(Decoder(encodedData,key)));
            decodeData = String.valueOf(Decoder(encodedData,key));
        });
    }

    public String Decoder(char [] string, int offset) {
        String decode = "";
        for (char letter : string) {
            for (int i = 0; i < alphabet.length; i++) {
                if (letter == alphabet[i]) {
                    int step = (i + offset) % 26;
                    if (step < 0) {
                        decode += alphabet[26 + step];
                        break;
                    }
                    decode += alphabet[step];
                    break;
                }
                else if (letter == ' ') {
                    decode += ' ';
                    break;
                }
                else if (letter == '!') {
                    decode += '!';
                    break;
                }
                else if (letter == '.') {
                    decode += '.';
                    break;
                }
                else if (letter == '\'') {
                    decode += '\'';
                    break;
                }
            }
        }
        return decode;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}